import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PalierImpoDetailComponent } from './palier-impo-detail.component';

describe('PalierImpo Management Detail Component', () => {
  let comp: PalierImpoDetailComponent;
  let fixture: ComponentFixture<PalierImpoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PalierImpoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ palierImpo: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PalierImpoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PalierImpoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load palierImpo on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.palierImpo).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
