import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShanaSharedModule } from '../../shared';
import {
    PhotoService,
    PhotoPopupService,
    PhotoComponent,
    PhotoDetailComponent,
    PhotoDialogComponent,
    PhotoPopupComponent,
    PhotoDeletePopupComponent,
    PhotoDeleteDialogComponent,
    photoRoute,
    photoPopupRoute,
    PhotoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...photoRoute,
    ...photoPopupRoute,
];

@NgModule({
    imports: [
        ShanaSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PhotoComponent,
        PhotoDetailComponent,
        PhotoDialogComponent,
        PhotoDeleteDialogComponent,
        PhotoPopupComponent,
        PhotoDeletePopupComponent,
    ],
    entryComponents: [
        PhotoComponent,
        PhotoDialogComponent,
        PhotoPopupComponent,
        PhotoDeleteDialogComponent,
        PhotoDeletePopupComponent,
    ],
    providers: [
        PhotoService,
        PhotoPopupService,
        PhotoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ShanaPhotoModule {}
