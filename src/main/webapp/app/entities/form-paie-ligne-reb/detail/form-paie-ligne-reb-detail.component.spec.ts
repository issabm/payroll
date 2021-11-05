import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FormPaieLigneRebDetailComponent } from './form-paie-ligne-reb-detail.component';

describe('FormPaieLigneReb Management Detail Component', () => {
  let comp: FormPaieLigneRebDetailComponent;
  let fixture: ComponentFixture<FormPaieLigneRebDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FormPaieLigneRebDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ formPaieLigneReb: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(FormPaieLigneRebDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FormPaieLigneRebDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load formPaieLigneReb on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.formPaieLigneReb).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
