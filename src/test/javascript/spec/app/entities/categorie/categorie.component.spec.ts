/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { ShanaTestModule } from '../../../test.module';
import { CategorieComponent } from '../../../../../../main/webapp/app/entities/categorie/categorie.component';
import { CategorieService } from '../../../../../../main/webapp/app/entities/categorie/categorie.service';
import { Categorie } from '../../../../../../main/webapp/app/entities/categorie/categorie.model';

describe('Component Tests', () => {

    describe('Categorie Management Component', () => {
        let comp: CategorieComponent;
        let fixture: ComponentFixture<CategorieComponent>;
        let service: CategorieService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ShanaTestModule],
                declarations: [CategorieComponent],
                providers: [
                    CategorieService
                ]
            })
            .overrideTemplate(CategorieComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CategorieComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategorieService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Categorie(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.categories[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
