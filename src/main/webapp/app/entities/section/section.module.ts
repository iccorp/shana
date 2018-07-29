import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShanaSharedModule } from '../../shared';
import {
    SectionService,
    SectionPopupService,
    SectionComponent,
    SectionDetailComponent,
    SectionDialogComponent,
    SectionPopupComponent,
    SectionDeletePopupComponent,
    SectionDeleteDialogComponent,
    sectionRoute,
    sectionPopupRoute,
    SectionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...sectionRoute,
    ...sectionPopupRoute,
];

@NgModule({
    imports: [
        ShanaSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SectionComponent,
        SectionDetailComponent,
        SectionDialogComponent,
        SectionDeleteDialogComponent,
        SectionPopupComponent,
        SectionDeletePopupComponent,
    ],
    entryComponents: [
        SectionComponent,
        SectionDialogComponent,
        SectionPopupComponent,
        SectionDeleteDialogComponent,
        SectionDeletePopupComponent,
    ],
    providers: [
        SectionService,
        SectionPopupService,
        SectionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ShanaSectionModule {}
