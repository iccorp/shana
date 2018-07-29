import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { SectionComponent } from './section.component';
import { SectionDetailComponent } from './section-detail.component';
import { SectionPopupComponent } from './section-dialog.component';
import { SectionDeletePopupComponent } from './section-delete-dialog.component';

@Injectable()
export class SectionResolvePagingParams implements Resolve<any> {

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

export const sectionRoute: Routes = [
    {
        path: 'section',
        component: SectionComponent,
        resolve: {
            'pagingParams': SectionResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shanaApp.section.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'section/:id',
        component: SectionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shanaApp.section.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sectionPopupRoute: Routes = [
    {
        path: 'section-new',
        component: SectionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shanaApp.section.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'section/:id/edit',
        component: SectionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shanaApp.section.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'section/:id/delete',
        component: SectionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shanaApp.section.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
