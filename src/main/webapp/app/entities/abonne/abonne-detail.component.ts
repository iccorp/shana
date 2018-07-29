import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Abonne } from './abonne.model';
import { AbonneService } from './abonne.service';

@Component({
    selector: 'jhi-abonne-detail',
    templateUrl: './abonne-detail.component.html'
})
export class AbonneDetailComponent implements OnInit, OnDestroy {

    abonne: Abonne;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private abonneService: AbonneService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAbonnes();
    }

    load(id) {
        this.abonneService.find(id).subscribe((abonne) => {
            this.abonne = abonne;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAbonnes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'abonneListModification',
            (response) => this.load(this.abonne.id)
        );
    }
}
