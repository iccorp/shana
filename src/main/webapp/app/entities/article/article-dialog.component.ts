import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Article } from './article.model';
import { ArticlePopupService } from './article-popup.service';
import { ArticleService } from './article.service';
import { Categorie, CategorieService } from '../categorie';
import { ResponseWrapper } from '../../shared';
import { DatePipe, Location } from '@angular/common';

@Component({
    selector: 'jhi-article-dialog',
    templateUrl: './article-dialog.component.html'
})
export class ArticleDialogComponent implements OnInit, OnDestroy {

    article: Article;
    isSaving: boolean;

    categories: Categorie[];
    routeSub: any;
    photoRadio = 'new';

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private articleService: ArticleService,
        private categorieService: CategorieService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager,
        private route: ActivatedRoute,
        private datePipe: DatePipe,
        private router: Router,
        private location: Location
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.categorieService.query()
            .subscribe((res: ResponseWrapper) => { this.categories = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.routeSub = this.route.params.subscribe((params) => {
            const id = params['id'];
            if ( id ) {
                this.articleService.find(id).subscribe((article) => {
                    this.article = article;
                    article.dateCreation = this.datePipe
                        .transform(article.dateCreation, 'yyyy-MM-ddTHH:mm:ss');
                    article.dateDerniereModification = this.datePipe
                        .transform(article.dateDerniereModification, 'yyyy-MM-ddTHH:mm:ss');
                    // TODO récupérer la vignette
                   /*  this.ngbModalRef = this.articleModalRef(component, article);
                    resolve(this.ngbModalRef); */
                });
            } else {
                this.article = new Article();
                this.article.nbVue = 0;
                this.article.nbLike = 0;
                this.article.nbPartage = 0;
                this.article.position = 1;
                this.article.positionDansCategorie = 1;
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.article, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.location.back();
    }

    save() {
        this.isSaving = true;
        if (this.article.id !== undefined) {
            this.subscribeToSaveResponse(
                this.articleService.update(this.article));
        } else {
            this.subscribeToSaveResponse(
                this.articleService.create(this.article));
        }
        this.router.navigateByUrl('article');
    }

    private subscribeToSaveResponse(result: Observable<Article>) {
        result.subscribe((res: Article) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Article) {
        this.eventManager.broadcast({ name: 'articleListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCategorieById(index: number, item: Categorie) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-article-popup',
    template: './article-dialog.component.html'
})
export class ArticlePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private articlePopupService: ArticlePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.articlePopupService
                    .open(ArticleDialogComponent as Component, params['id']);
            } else {
                this.articlePopupService
                    .open(ArticleDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
