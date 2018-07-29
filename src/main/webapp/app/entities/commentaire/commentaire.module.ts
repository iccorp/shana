import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShanaSharedModule } from '../../shared';
import {
    CommentaireService,
    CommentairePopupService,
    CommentaireComponent,
    CommentaireDetailComponent,
    CommentaireDialogComponent,
    CommentairePopupComponent,
    CommentaireDeletePopupComponent,
    CommentaireDeleteDialogComponent,
    commentaireRoute,
    commentairePopupRoute,
    CommentaireResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...commentaireRoute,
    ...commentairePopupRoute,
];

@NgModule({
    imports: [
        ShanaSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CommentaireComponent,
        CommentaireDetailComponent,
        CommentaireDialogComponent,
        CommentaireDeleteDialogComponent,
        CommentairePopupComponent,
        CommentaireDeletePopupComponent,
    ],
    entryComponents: [
        CommentaireComponent,
        CommentaireDialogComponent,
        CommentairePopupComponent,
        CommentaireDeleteDialogComponent,
        CommentaireDeletePopupComponent,
    ],
    providers: [
        CommentaireService,
        CommentairePopupService,
        CommentaireResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ShanaCommentaireModule {}
