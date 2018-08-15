import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription, Observable } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Article } from './article.model';
import { ArticleService } from './article.service';
import { PhotoService, Photo, FORMAT_PHOTO } from '../photo';

@Component({
    selector: 'jhi-article-detail',
    templateUrl: './article-detail.component.html',
    styleUrls: ['./article-detail.component.css']
})
export class ArticleDetailComponent implements OnInit, OnDestroy {

    article: Article;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private articleService: ArticleService,
        private route: ActivatedRoute,
        private photoService: PhotoService
    ) {
    }

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
                const photoCouvertureParam = new Photo();
                photoCouvertureParam.idPhoto = this.article.idPhoto;
                photoCouvertureParam.format = FORMAT_PHOTO.COUVERTURE;
                this.loadPhoto(photoCouvertureParam)
                    .subscribe((photo) => {
                        this.article.photo = photo.photo;
                        this.article.photoContentType = photo.photoContentType;
                    })
            }
        });
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
}
