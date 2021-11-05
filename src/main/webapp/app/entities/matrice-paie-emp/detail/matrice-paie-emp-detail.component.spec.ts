import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MatricePaieEmpDetailComponent } from './matrice-paie-emp-detail.component';

describe('MatricePaieEmp Management Detail Component', () => {
  let comp: MatricePaieEmpDetailComponent;
  let fixture: ComponentFixture<MatricePaieEmpDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MatricePaieEmpDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ matricePaieEmp: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MatricePaieEmpDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MatricePaieEmpDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load matricePaieEmp on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.matricePaieEmp).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
