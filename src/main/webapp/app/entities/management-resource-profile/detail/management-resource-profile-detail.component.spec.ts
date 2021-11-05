import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ManagementResourceProfileDetailComponent } from './management-resource-profile-detail.component';

describe('ManagementResourceProfile Management Detail Component', () => {
  let comp: ManagementResourceProfileDetailComponent;
  let fixture: ComponentFixture<ManagementResourceProfileDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManagementResourceProfileDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ managementResourceProfile: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ManagementResourceProfileDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ManagementResourceProfileDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load managementResourceProfile on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.managementResourceProfile).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
