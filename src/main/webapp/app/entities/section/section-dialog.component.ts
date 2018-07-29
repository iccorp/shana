import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Section } from './section.model';
import { SectionPopupService } from './section-popup.service';
import { SectionService } from './section.service';
import { Article, ArticleService } from '../article';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-section-dialog',
    templateUrl: './section-dialog.component.html'
})
export class SectionDialogComponent implements OnInit {

    section: Section;
    isSaving: boolean;

    articles: Article[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private sectionService: SectionService,
        private articleService: ArticleService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.articleService.query()
            .subscribe((res: ResponseWrapper) => { this.articles = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
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
        this.dataUtils.clearInputImage(this.section, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.section.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sectionService.update(this.section));
        } else {
            this.subscribeToSaveResponse(
                this.sectionService.create(this.section));
        }
    }

    private subscribeToSaveResponse(result: Observable<Section>) {
        result.subscribe((res: Section) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Section) {
        this.eventManager.broadcast({ name: 'sectionListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-section-popup',
    template: ''
})
export class SectionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sectionPopupService: SectionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.sectionPopupService
                    .open(SectionDialogComponent as Component, params['id']);
            } else {
                this.sectionPopupService
                    .open(SectionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
