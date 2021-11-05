import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NatureAbsenceRebriqueDetailComponent } from './nature-absence-rebrique-detail.component';

describe('NatureAbsenceRebrique Management Detail Component', () => {
  let comp: NatureAbsenceRebriqueDetailComponent;
  let fixture: ComponentFixture<NatureAbsenceRebriqueDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NatureAbsenceRebriqueDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ natureAbsenceRebrique: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NatureAbsenceRebriqueDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NatureAbsenceRebriqueDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load natureAbsenceRebrique on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.natureAbsenceRebrique).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
