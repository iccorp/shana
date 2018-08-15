import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Article } from './article.model';
import { ArticleService } from './article.service';

@Injectable()
export class ArticlePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private articleService: ArticleService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
        const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.articleService.find(id).subscribe((article) => {
                    article.dateCreation = this.datePipe
                        .transform(article.dateCreation, 'yyyy-MM-ddTHH:mm:ss');
                    article.dateDerniereModification = this.datePipe
                        .transform(article.dateDerniereModification, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.articleModalRef(component, article);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    const article = new Article();
                    article.position = 1;
                    article.positionDansCategorie = 1;
                    article.nbVue = 0;
                    article.nbLike = 0;
                    article.nbPartage = 0;

                    this.ngbModalRef = this.articleModalRef(component, article);
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    articleModalRef(component: Component, article: Article): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.article = article;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
