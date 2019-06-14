import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Account, LoginModalService, Principal, ResponseWrapper } from '../shared';
import { Photo, PhotoService, FORMAT_PHOTO } from '../entities/photo';
import { SettingsService } from '../admin';
import { CategorieService, Categorie } from '../entities/categorie';
import { Article, ArticleService } from '../entities/article';
import { DeviceDetectorService } from 'ngx-device-detector';
import { ActivatedRoute } from '@angular/router';

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
    selectedCategoryId;

    constructor(
        private deviceService: DeviceDetectorService,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private photoService: PhotoService,
        private settingsService: SettingsService,
        private articleService: ArticleService,
        private categorieService: CategorieService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.route.queryParams.subscribe((queryParams) => {
            this.selectedCategoryId = queryParams['c'];
            this.loadArticles();
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
        if (this.selectedCategoryId) {
            this.articleService.findByCategory(this.selectedCategoryId)
            .subscribe((articles) => {
                this.handleArticles(articles.json);
            })
        } else {
            this.articleService.query()
            .subscribe((articles) => {
                this.handleArticles(articles.json);
            })
        }
    }

    handleArticles(articles: Array<Article>) {
        this.articles = articles;
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

    /* categorieOffset() {
        if (this.isDesktop()) {
            const i = Math.floor(6 - this.categories.length / 2);
            if (this.categories.length < 12 && i > 0) {
                return 'ui-g-offset-' + i;
            }
        } else {
            const i = Math.floor(6 - this.categories.length);
            if (this.categories.length < 6 && i > 0) {
                return 'ui-g-offset-' + i;
            }
        }
        return '';
    } */
}
