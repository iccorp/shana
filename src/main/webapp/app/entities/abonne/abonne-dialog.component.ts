import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Abonne } from './abonne.model';
import { AbonnePopupService } from './abonne-popup.service';
import { AbonneService } from './abonne.service';

@Component({
    selector: 'jhi-abonne-dialog',
    templateUrl: './abonne-dialog.component.html'
})
export class AbonneDialogComponent implements OnInit {

    abonne: Abonne;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private abonneService: AbonneService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.abonne.id !== undefined) {
            this.subscribeToSaveResponse(
                this.abonneService.update(this.abonne));
        } else {
            this.subscribeToSaveResponse(
                this.abonneService.create(this.abonne));
        }
    }

    private subscribeToSaveResponse(result: Observable<Abonne>) {
        result.subscribe((res: Abonne) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Abonne) {
        this.eventManager.broadcast({ name: 'abonneListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-abonne-popup',
    template: ''
})
export class AbonnePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private abonnePopupService: AbonnePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.abonnePopupService
                    .open(AbonneDialogComponent as Component, params['id']);
            } else {
                this.abonnePopupService
                    .open(AbonneDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
