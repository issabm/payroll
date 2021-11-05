import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoldeAbsenceContratDetailComponent } from './solde-absence-contrat-detail.component';

describe('SoldeAbsenceContrat Management Detail Component', () => {
  let comp: SoldeAbsenceContratDetailComponent;
  let fixture: ComponentFixture<SoldeAbsenceContratDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SoldeAbsenceContratDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ soldeAbsenceContrat: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SoldeAbsenceContratDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SoldeAbsenceContratDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load soldeAbsenceContrat on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.soldeAbsenceContrat).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
