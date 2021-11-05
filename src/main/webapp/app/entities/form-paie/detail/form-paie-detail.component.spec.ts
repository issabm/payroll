import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FormPaieDetailComponent } from './form-paie-detail.component';

describe('FormPaie Management Detail Component', () => {
  let comp: FormPaieDetailComponent;
  let fixture: ComponentFixture<FormPaieDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FormPaieDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ formPaie: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(FormPaieDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FormPaieDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load formPaie on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.formPaie).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
