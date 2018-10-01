import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Account, LoginModalService, Principal, ResponseWrapper } from '../shared';
import { Photo, PhotoService, FORMAT_PHOTO } from '../entities/photo';
import { SettingsService } from '../admin';
import { CategorieService, Categorie } from '../entities/categorie';
import { Article, ArticleService } from '../entities/article';
import { DeviceDetectorService } from 'ngx-device-detector';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.css'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    photoCouverture: Photo;
    categories: Categorie[];
    articles: Article[];
    isPhone;

    constructor(
        private deviceService: DeviceDetectorService,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private photoService: PhotoService,
        private settingsService: SettingsService,
        private articleService: ArticleService,
        private categorieService: CategorieService,
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
        this.loadPhotoCouverture();
        this.loadCategories();
        this.loadArticles();
    }

    loadPhotoCouverture() {
        this.settingsService.getSettings()
        .subscribe((setting) => {
            if (setting.photoCouverture) {
                const param: Photo = new Photo();
                param.idPhoto = setting.photoCouverture;
                param.format = FORMAT_PHOTO.COUVERTURE;
                this.photoService.findByPhoto(param)
                .subscribe((photo: Photo) => {
                    this.photoCouverture = photo;
                })
            }
        });
    }

    loadCategories() {
        this.categorieService.query()
        .subscribe((res: ResponseWrapper) => {
            this.categories = res.json;
        });
    }

    loadArticles() {
        this.articleService.query()
        .subscribe((articles) => {
            this.articles = articles.json;
            this.articles.forEach((article: Article) => {
                if (article.idPhoto) {
                    const photoParam: Photo = new Photo();
                    photoParam.idPhoto = article.idPhoto;
                    photoParam.format = FORMAT_PHOTO.CARTE;
                    this.photoService.findByPhoto(photoParam)
                    .subscribe((photo: Photo) => {
                        article.photo = photo.photo;
                        article.photoContentType = photo.photoContentType;
                    });
                }
            });
        })
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    isDesktop() {
        return this.deviceService.isDesktop();
    }

}
