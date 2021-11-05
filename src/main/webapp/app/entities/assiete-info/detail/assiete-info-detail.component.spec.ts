import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AssieteInfoDetailComponent } from './assiete-info-detail.component';

describe('AssieteInfo Management Detail Component', () => {
  let comp: AssieteInfoDetailComponent;
  let fixture: ComponentFixture<AssieteInfoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AssieteInfoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ assieteInfo: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AssieteInfoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AssieteInfoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load assieteInfo on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.assieteInfo).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
