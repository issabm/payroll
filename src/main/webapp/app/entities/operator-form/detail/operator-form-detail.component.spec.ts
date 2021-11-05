import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OperatorFormDetailComponent } from './operator-form-detail.component';

describe('OperatorForm Management Detail Component', () => {
  let comp: OperatorFormDetailComponent;
  let fixture: ComponentFixture<OperatorFormDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OperatorFormDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ operatorForm: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(OperatorFormDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OperatorFormDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load operatorForm on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.operatorForm).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
