import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Commentaire } from './commentaire.model';
import { CommentairePopupService } from './commentaire-popup.service';
import { CommentaireService } from './commentaire.service';
import { Article, ArticleService } from '../article';
import { Abonne, AbonneService } from '../abonne';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-commentaire-dialog',
    templateUrl: './commentaire-dialog.component.html'
})
export class CommentaireDialogComponent implements OnInit {

    commentaire: Commentaire;
    isSaving: boolean;

    articles: Article[];

    abonnes: Abonne[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private commentaireService: CommentaireService,
        private articleService: ArticleService,
        private abonneService: AbonneService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.articleService.query()
            .subscribe((res: ResponseWrapper) => { this.articles = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.abonneService.query()
            .subscribe((res: ResponseWrapper) => { this.abonnes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.commentaire.id !== undefined) {
            this.subscribeToSaveResponse(
                this.commentaireService.update(this.commentaire));
        } else {
            this.subscribeToSaveResponse(
                this.commentaireService.create(this.commentaire));
        }
    }

    private subscribeToSaveResponse(result: Observable<Commentaire>) {
        result.subscribe((res: Commentaire) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Commentaire) {
        this.eventManager.broadcast({ name: 'commentaireListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackArticleById(index: number, item: Article) {
        return item.id;
    }

    trackAbonneById(index: number, item: Abonne) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-commentaire-popup',
    template: ''
})
export class CommentairePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private commentairePopupService: CommentairePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.commentairePopupService
                    .open(CommentaireDialogComponent as Component, params['id']);
            } else {
                this.commentairePopupService
                    .open(CommentaireDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
