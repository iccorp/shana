/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ShanaTestModule } from '../../../test.module';
import { CommentaireDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/commentaire/commentaire-delete-dialog.component';
import { CommentaireService } from '../../../../../../main/webapp/app/entities/commentaire/commentaire.service';

describe('Component Tests', () => {

    describe('Commentaire Management Delete Component', () => {
        let comp: CommentaireDeleteDialogComponent;
        let fixture: ComponentFixture<CommentaireDeleteDialogComponent>;
        let service: CommentaireService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ShanaTestModule],
                declarations: [CommentaireDeleteDialogComponent],
                providers: [
                    CommentaireService
                ]
            })
            .overrideTemplate(CommentaireDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CommentaireDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommentaireService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
