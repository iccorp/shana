import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Article } from './article.model';
import { ArticlePopupService } from './article-popup.service';
import { ArticleService } from './article.service';
import { Categorie, CategorieService } from '../categorie';
import { ResponseWrapper } from '../../shared';
import { DatePipe, Location } from '@angular/common';
import { PhotoService, Photo, FORMAT_PHOTO } from '../photo';
import { PhotoPickerComponent } from '../photo-picker/photo-picker.component';
import { Section, SectionService } from '../section';

@Component({
    selector: 'jhi-article-dialog',
    templateUrl: './article-dialog.component.html',
    styleUrls: ['./article-dialog.component.css']
})
export class ArticleDialogComponent implements OnInit, OnDestroy {

    article: Article;
    isSaving: boolean;

    categories: Categorie[];
    routeSub: any;
    photoRadio = 'new';
    vignettes: Photo[];

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
        private location: Location,
        private photoService: PhotoService,
        private sectionService: SectionService
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.loadCategories();
        this.loadVignettesAndSections();
    }

    loadCategories() {
        this.categorieService.query()
        .subscribe((res: ResponseWrapper) => { this.categories = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    loadVignettesAndSections() {
        this.vignettes = [];
        this.photoService
            .findAllVignette()
            .subscribe((response) => {
                this.vignettes = response;
                this.setPhotoCouvertureInitiale();
                this.loadSections();
            });
    }

    setPhotoCouvertureInitiale() {
        this.vignettes .forEach((vignette) => {
            if (vignette.idPhoto === this.article.idPhoto) {
                this.article.photo = vignette;
            }
        });
        if (this.article.photo === undefined) {
            this.article.photo = new Photo();
        }
    }

    loadSections() {
        this.article.sections = [];
        if (this.article.id !== undefined) {
            this.sectionService
                .findByArticleId(this.article.id)
                .subscribe((res: ResponseWrapper) => {
                     this.article.sections = res.json;
                     this.setPhotoSectionInitiale();
                });
        }
    }

    setPhotoSectionInitiale() {
        this.article.sections.forEach((s) => {
            const section = (s as Section);
            this.vignettes .forEach((vignette) => {
                if (vignette.idPhoto === section.idPhoto) {
                    section.photo = vignette;
                }
            })
            if (section.photo === undefined) {
                section.photo = new Photo();
            }

        })
    }

    updatePhotoCouverture(photo: Photo) {
        this.article.photo = photo;
        this.article.idPhoto = photo.idPhoto;
    }

    updatePhotoSection(photo: Photo, i: number) {
        (this.article.sections[i] as Section).photo = photo;
        (this.article.sections[i] as Section).idPhoto = photo.idPhoto;
    }

    ngOnDestroy() {}

    addSection() {
        if (!this.article.sections) {
            this.article.sections = [];
        }
        const section: Section = new Section();
        section.photo = new Photo();
        this.article.sections.push(section);
    }

    deleteSection(i: number) {
        this.article.sections.splice(i, 1);
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
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        const photoCouverture: Photo = (this.article.photo as Photo);
        if (photoCouverture && photoCouverture.photo && !photoCouverture.idPhoto) {
            this.photoService
            .create(photoCouverture)
            .subscribe((savedPhoto: Photo) => {
                this.article.idPhoto = savedPhoto.idPhoto;
                this.article.photo = null;
                this.saveArticle();
            });
        } else {
            this.article.photo = null;
            this.saveArticle();
        }
    }

    saveArticle() {
        if (this.article.id) {
            this.subscribeToSaveResponse(
                this.articleService.update(this.article));
        } else {
            this.subscribeToSaveResponse(
                this.articleService.create(this.article));
        }
    }
    private subscribeToSaveResponse(result: Observable<Article>) {
        result.subscribe((res: Article) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Article) {
        console.log('article sauvegardée: ' + JSON.stringify(result));
        this.article.sections.forEach((section: Section) => {
            console.log('section_' + this.article.sections.indexOf(section));
            section.articleId = result.id;
            const photoSection: Photo = (section.photo as Photo);
            if (photoSection && photoSection.photo && !photoSection.idPhoto) {
                this.photoService
                    .create(photoSection)
                    .subscribe((savedPhoto: Photo) => {
                        section.idPhoto = savedPhoto.idPhoto;
                        section.photo = null;
                        this.createOrUpdateSection(section);
                    });
            } else {
                section.photo = null;
                this.createOrUpdateSection(section);
            }
        });
        this.eventManager.broadcast({ name: 'articleListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    createOrUpdateSection(section: Section) {
        if (section.id) {
            console.log('update Section')
            this.subscribeToSavedSection(this.sectionService.update(section));
        } else {
            console.log('create Section')
            this.subscribeToSavedSection(this.sectionService.create(section));
        }
    }

    subscribeToSavedSection(result: Observable<Section>) {
        result.subscribe((savedSection) => {
            console.log('Saved section: ' + JSON.stringify(savedSection));
        })
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
