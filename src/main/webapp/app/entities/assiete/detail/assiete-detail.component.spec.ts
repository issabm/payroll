import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AssieteDetailComponent } from './assiete-detail.component';

describe('Assiete Management Detail Component', () => {
  let comp: AssieteDetailComponent;
  let fixture: ComponentFixture<AssieteDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AssieteDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ assiete: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AssieteDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AssieteDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load assiete on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.assiete).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
