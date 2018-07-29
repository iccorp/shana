/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { ShanaTestModule } from '../../../test.module';
import { CommentaireDetailComponent } from '../../../../../../main/webapp/app/entities/commentaire/commentaire-detail.component';
import { CommentaireService } from '../../../../../../main/webapp/app/entities/commentaire/commentaire.service';
import { Commentaire } from '../../../../../../main/webapp/app/entities/commentaire/commentaire.model';

describe('Component Tests', () => {

    describe('Commentaire Management Detail Component', () => {
        let comp: CommentaireDetailComponent;
        let fixture: ComponentFixture<CommentaireDetailComponent>;
        let service: CommentaireService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ShanaTestModule],
                declarations: [CommentaireDetailComponent],
                providers: [
                    CommentaireService
                ]
            })
            .overrideTemplate(CommentaireDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CommentaireDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommentaireService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Commentaire(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.commentaire).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
