/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ShanaTestModule } from '../../../test.module';
import { CommentaireDialogComponent } from '../../../../../../main/webapp/app/entities/commentaire/commentaire-dialog.component';
import { CommentaireService } from '../../../../../../main/webapp/app/entities/commentaire/commentaire.service';
import { Commentaire } from '../../../../../../main/webapp/app/entities/commentaire/commentaire.model';
import { ArticleService } from '../../../../../../main/webapp/app/entities/article';
import { AbonneService } from '../../../../../../main/webapp/app/entities/abonne';

describe('Component Tests', () => {

    describe('Commentaire Management Dialog Component', () => {
        let comp: CommentaireDialogComponent;
        let fixture: ComponentFixture<CommentaireDialogComponent>;
        let service: CommentaireService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ShanaTestModule],
                declarations: [CommentaireDialogComponent],
                providers: [
                    ArticleService,
                    AbonneService,
                    CommentaireService
                ]
            })
            .overrideTemplate(CommentaireDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CommentaireDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommentaireService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Commentaire(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.commentaire = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'commentaireListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Commentaire();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.commentaire = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'commentaireListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
