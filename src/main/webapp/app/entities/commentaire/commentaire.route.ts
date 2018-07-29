import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { CommentaireComponent } from './commentaire.component';
import { CommentaireDetailComponent } from './commentaire-detail.component';
import { CommentairePopupComponent } from './commentaire-dialog.component';
import { CommentaireDeletePopupComponent } from './commentaire-delete-dialog.component';

@Injectable()
export class CommentaireResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const commentaireRoute: Routes = [
    {
        path: 'commentaire',
        component: CommentaireComponent,
        resolve: {
            'pagingParams': CommentaireResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shanaApp.commentaire.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'commentaire/:id',
        component: CommentaireDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shanaApp.commentaire.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commentairePopupRoute: Routes = [
    {
        path: 'commentaire-new',
        component: CommentairePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shanaApp.commentaire.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'commentaire/:id/edit',
        component: CommentairePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shanaApp.commentaire.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'commentaire/:id/delete',
        component: CommentaireDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shanaApp.commentaire.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
