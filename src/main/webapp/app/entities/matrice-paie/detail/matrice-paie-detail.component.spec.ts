import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MatricePaieDetailComponent } from './matrice-paie-detail.component';

describe('MatricePaie Management Detail Component', () => {
  let comp: MatricePaieDetailComponent;
  let fixture: ComponentFixture<MatricePaieDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MatricePaieDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ matricePaie: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MatricePaieDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MatricePaieDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load matricePaie on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.matricePaie).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
