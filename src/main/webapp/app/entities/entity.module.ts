import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ShanaArticleModule } from './article/article.module';
import { ShanaCategorieModule } from './categorie/categorie.module';
import { ShanaAbonneModule } from './abonne/abonne.module';
import { ShanaCommentaireModule } from './commentaire/commentaire.module';
import { ShanaMessageModule } from './message/message.module';
import { ShanaSectionModule } from './section/section.module';
import { ShanaPhotoModule } from './photo/photo.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ShanaArticleModule,
        ShanaCategorieModule,
        ShanaAbonneModule,
        ShanaCommentaireModule,
        ShanaMessageModule,
        ShanaSectionModule,
        ShanaPhotoModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ShanaEntityModule {}
