import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RebriqueContratDetailComponent } from './rebrique-contrat-detail.component';

describe('RebriqueContrat Management Detail Component', () => {
  let comp: RebriqueContratDetailComponent;
  let fixture: ComponentFixture<RebriqueContratDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RebriqueContratDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ rebriqueContrat: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RebriqueContratDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RebriqueContratDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load rebriqueContrat on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.rebriqueContrat).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
