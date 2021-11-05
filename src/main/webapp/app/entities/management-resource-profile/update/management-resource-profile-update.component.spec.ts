jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ManagementResourceProfileService } from '../service/management-resource-profile.service';
import { IManagementResourceProfile, ManagementResourceProfile } from '../management-resource-profile.model';
import { IManagementResource } from 'app/entities/management-resource/management-resource.model';
import { ManagementResourceService } from 'app/entities/management-resource/service/management-resource.service';

import { ManagementResourceProfileUpdateComponent } from './management-resource-profile-update.component';

describe('ManagementResourceProfile Management Update Component', () => {
  let comp: ManagementResourceProfileUpdateComponent;
  let fixture: ComponentFixture<ManagementResourceProfileUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let managementResourceProfileService: ManagementResourceProfileService;
  let managementResourceService: ManagementResourceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ManagementResourceProfileUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(ManagementResourceProfileUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ManagementResourceProfileUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    managementResourceProfileService = TestBed.inject(ManagementResourceProfileService);
    managementResourceService = TestBed.inject(ManagementResourceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ManagementResource query and add missing value', () => {
      const managementResourceProfile: IManagementResourceProfile = { id: 456 };
      const ressourceManage: IManagementResource = { id: 1565 };
      managementResourceProfile.ressourceManage = ressourceManage;

      const managementResourceCollection: IManagementResource[] = [{ id: 83655 }];
      jest.spyOn(managementResourceService, 'query').mockReturnValue(of(new HttpResponse({ body: managementResourceCollection })));
      const additionalManagementResources = [ressourceManage];
      const expectedCollection: IManagementResource[] = [...additionalManagementResources, ...managementResourceCollection];
      jest.spyOn(managementResourceService, 'addManagementResourceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ managementResourceProfile });
      comp.ngOnInit();

      expect(managementResourceService.query).toHaveBeenCalled();
      expect(managementResourceService.addManagementResourceToCollectionIfMissing).toHaveBeenCalledWith(
        managementResourceCollection,
        ...additionalManagementResources
      );
      expect(comp.managementResourcesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const managementResourceProfile: IManagementResourceProfile = { id: 456 };
      const ressourceManage: IManagementResource = { id: 87557 };
      managementResourceProfile.ressourceManage = ressourceManage;

      activatedRoute.data = of({ managementResourceProfile });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(managementResourceProfile));
      expect(comp.managementResourcesSharedCollection).toContain(ressourceManage);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ManagementResourceProfile>>();
      const managementResourceProfile = { id: 123 };
      jest.spyOn(managementResourceProfileService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ managementResourceProfile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: managementResourceProfile }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(managementResourceProfileService.update).toHaveBeenCalledWith(managementResourceProfile);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ManagementResourceProfile>>();
      const managementResourceProfile = new ManagementResourceProfile();
      jest.spyOn(managementResourceProfileService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ managementResourceProfile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: managementResourceProfile }));
      saveSubject.complete();

      // THEN
      expect(managementResourceProfileService.create).toHaveBeenCalledWith(managementResourceProfile);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ManagementResourceProfile>>();
      const managementResourceProfile = { id: 123 };
      jest.spyOn(managementResourceProfileService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ managementResourceProfile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(managementResourceProfileService.update).toHaveBeenCalledWith(managementResourceProfile);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackManagementResourceById', () => {
      it('Should return tracked ManagementResource primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackManagementResourceById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
