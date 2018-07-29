import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShanaSharedModule } from '../../shared';
import {
    AbonneService,
    AbonnePopupService,
    AbonneComponent,
    AbonneDetailComponent,
    AbonneDialogComponent,
    AbonnePopupComponent,
    AbonneDeletePopupComponent,
    AbonneDeleteDialogComponent,
    abonneRoute,
    abonnePopupRoute,
    AbonneResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...abonneRoute,
    ...abonnePopupRoute,
];

@NgModule({
    imports: [
        ShanaSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AbonneComponent,
        AbonneDetailComponent,
        AbonneDialogComponent,
        AbonneDeleteDialogComponent,
        AbonnePopupComponent,
        AbonneDeletePopupComponent,
    ],
    entryComponents: [
        AbonneComponent,
        AbonneDialogComponent,
        AbonnePopupComponent,
        AbonneDeleteDialogComponent,
        AbonneDeletePopupComponent,
    ],
    providers: [
        AbonneService,
        AbonnePopupService,
        AbonneResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ShanaAbonneModule {}
