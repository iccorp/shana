import { Component, OnInit, OnDestroy, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription, Observable, pipe } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Article } from './article.model';
import { ArticleService } from './article.service';
import { PhotoService, Photo, FORMAT_PHOTO } from '../photo';
import { SectionService, Section } from '../section';
import { ResponseWrapper } from '../../shared';
import { DomSanitizer } from '../../../../../../node_modules/@angular/platform-browser';
import { BASE_LINK_YOUTUBE_EMBED } from '../../app.constants';

@Component({
    selector: 'jhi-article-detail',
    templateUrl: './article-detail.component.html',
    styleUrls: ['./article-detail.component.css']
})
export class ArticleDetailComponent implements OnInit, OnDestroy/* , AfterViewInit */ {

    article: Article;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    private sectionWidth =  window.screen.width;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private articleService: ArticleService,
        private route: ActivatedRoute,
        private photoService: PhotoService,
        private sectionService: SectionService,
        public sanitizer: DomSanitizer
    ) {
    }
    @ViewChild('sectionCard')
    set sectionCard(sectionCard: ElementRef | undefined) {
        console.log('inside set sectionCard');
        if (sectionCard !== undefined) {
            Promise.resolve(null).then(() => this.sectionWidth = sectionCard.nativeElement.offsetWidth);
        }
    }
/*
    ngAfterViewInit(): void {
        console.log('Section Card: ' + JSON.stringify(this.sectionCard.nativeElement));
         this.sectionWidth = this.sectionCard.nativeElement.offsetWidth;
    }
*/
    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInArticles();
    }

    load(id) {
        this.articleService.find(id).subscribe((article) => {
            this.article = article;
            if (this.article.idPhoto) {
                this.loadPhotoCouverture();
            }
            this.article.sections = [];
            this.sectionService
            .findByArticleId(this.article.id)
            .subscribe((res: ResponseWrapper) => {
                this.article.sections = res.json;
                this.article.sections.forEach((section) => {
                    this.loadPhotoSection((section as Section));
                });
            });

        });
    }

    loadPhotoCouverture() {
        const photoCouvertureParam = new Photo();
        photoCouvertureParam.idPhoto = this.article.idPhoto;
        photoCouvertureParam.format = FORMAT_PHOTO.COUVERTURE;
        this.loadPhoto(photoCouvertureParam)
        .subscribe((photo) => {
            this.article.photo = photo.photo;
            this.article.photoContentType = photo.photoContentType;
        });
    }

    loadPhotoSection(section: Section) {
        if (section.idPhoto) {
            const photoSectionParam = new Photo();
            photoSectionParam.idPhoto = section.idPhoto;
            photoSectionParam.format = FORMAT_PHOTO.SECTION;
            this.loadPhoto(photoSectionParam)
            .subscribe((photo) => {
                section.photo = photo.photo;
                section.photoContentType = photo.photoContentType;
            });
        }
    }

    loadPhoto(photoParam: Photo): Observable<Photo> {
        return this.photoService.findByPhoto(photoParam);
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInArticles() {
        this.eventSubscriber = this.eventManager.subscribe(
            'articleListModification',
            (response) => this.load(this.article.id)
        );
    }

    getVideoUrl(i) {
        return this.sanitizer.bypassSecurityTrustResourceUrl(
            BASE_LINK_YOUTUBE_EMBED + (this.article.sections[i] as Section).idVideo
        );
    }

    getVideoHeight() {
        const r = 1.5;
        const height =  Math.round((this.sectionWidth / r));
        return height;
    }

}
