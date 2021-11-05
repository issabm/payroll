import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FormPaieLigneDetailComponent } from './form-paie-ligne-detail.component';

describe('FormPaieLigne Management Detail Component', () => {
  let comp: FormPaieLigneDetailComponent;
  let fixture: ComponentFixture<FormPaieLigneDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FormPaieLigneDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ formPaieLigne: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(FormPaieLigneDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FormPaieLigneDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load formPaieLigne on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.formPaieLigne).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
