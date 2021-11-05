jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AssieteInfoService } from '../service/assiete-info.service';
import { IAssieteInfo, AssieteInfo } from '../assiete-info.model';
import { IRebrique } from 'app/entities/rebrique/rebrique.model';
import { RebriqueService } from 'app/entities/rebrique/service/rebrique.service';
import { IAssiete } from 'app/entities/assiete/assiete.model';
import { AssieteService } from 'app/entities/assiete/service/assiete.service';
import { IModeCalcul } from 'app/entities/mode-calcul/mode-calcul.model';
import { ModeCalculService } from 'app/entities/mode-calcul/service/mode-calcul.service';

import { AssieteInfoUpdateComponent } from './assiete-info-update.component';

describe('AssieteInfo Management Update Component', () => {
  let comp: AssieteInfoUpdateComponent;
  let fixture: ComponentFixture<AssieteInfoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let assieteInfoService: AssieteInfoService;
  let rebriqueService: RebriqueService;
  let assieteService: AssieteService;
  let modeCalculService: ModeCalculService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [AssieteInfoUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(AssieteInfoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AssieteInfoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    assieteInfoService = TestBed.inject(AssieteInfoService);
    rebriqueService = TestBed.inject(RebriqueService);
    assieteService = TestBed.inject(AssieteService);
    modeCalculService = TestBed.inject(ModeCalculService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Rebrique query and add missing value', () => {
      const assieteInfo: IAssieteInfo = { id: 456 };
      const rebrique: IRebrique = { id: 2532 };
      assieteInfo.rebrique = rebrique;

      const rebriqueCollection: IRebrique[] = [{ id: 928 }];
      jest.spyOn(rebriqueService, 'query').mockReturnValue(of(new HttpResponse({ body: rebriqueCollection })));
      const additionalRebriques = [rebrique];
      const expectedCollection: IRebrique[] = [...additionalRebriques, ...rebriqueCollection];
      jest.spyOn(rebriqueService, 'addRebriqueToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ assieteInfo });
      comp.ngOnInit();

      expect(rebriqueService.query).toHaveBeenCalled();
      expect(rebriqueService.addRebriqueToCollectionIfMissing).toHaveBeenCalledWith(rebriqueCollection, ...additionalRebriques);
      expect(comp.rebriquesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Assiete query and add missing value', () => {
      const assieteInfo: IAssieteInfo = { id: 456 };
      const assiete: IAssiete = { id: 3752 };
      assieteInfo.assiete = assiete;

      const assieteCollection: IAssiete[] = [{ id: 52370 }];
      jest.spyOn(assieteService, 'query').mockReturnValue(of(new HttpResponse({ body: assieteCollection })));
      const additionalAssietes = [assiete];
      const expectedCollection: IAssiete[] = [...additionalAssietes, ...assieteCollection];
      jest.spyOn(assieteService, 'addAssieteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ assieteInfo });
      comp.ngOnInit();

      expect(assieteService.query).toHaveBeenCalled();
      expect(assieteService.addAssieteToCollectionIfMissing).toHaveBeenCalledWith(assieteCollection, ...additionalAssietes);
      expect(comp.assietesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ModeCalcul query and add missing value', () => {
      const assieteInfo: IAssieteInfo = { id: 456 };
      const modeCal: IModeCalcul = { id: 86123 };
      assieteInfo.modeCal = modeCal;

      const modeCalculCollection: IModeCalcul[] = [{ id: 52931 }];
      jest.spyOn(modeCalculService, 'query').mockReturnValue(of(new HttpResponse({ body: modeCalculCollection })));
      const additionalModeCalculs = [modeCal];
      const expectedCollection: IModeCalcul[] = [...additionalModeCalculs, ...modeCalculCollection];
      jest.spyOn(modeCalculService, 'addModeCalculToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ assieteInfo });
      comp.ngOnInit();

      expect(modeCalculService.query).toHaveBeenCalled();
      expect(modeCalculService.addModeCalculToCollectionIfMissing).toHaveBeenCalledWith(modeCalculCollection, ...additionalModeCalculs);
      expect(comp.modeCalculsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const assieteInfo: IAssieteInfo = { id: 456 };
      const rebrique: IRebrique = { id: 53299 };
      assieteInfo.rebrique = rebrique;
      const assiete: IAssiete = { id: 16873 };
      assieteInfo.assiete = assiete;
      const modeCal: IModeCalcul = { id: 34285 };
      assieteInfo.modeCal = modeCal;

      activatedRoute.data = of({ assieteInfo });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(assieteInfo));
      expect(comp.rebriquesSharedCollection).toContain(rebrique);
      expect(comp.assietesSharedCollection).toContain(assiete);
      expect(comp.modeCalculsSharedCollection).toContain(modeCal);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AssieteInfo>>();
      const assieteInfo = { id: 123 };
      jest.spyOn(assieteInfoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assieteInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: assieteInfo }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(assieteInfoService.update).toHaveBeenCalledWith(assieteInfo);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AssieteInfo>>();
      const assieteInfo = new AssieteInfo();
      jest.spyOn(assieteInfoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assieteInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: assieteInfo }));
      saveSubject.complete();

      // THEN
      expect(assieteInfoService.create).toHaveBeenCalledWith(assieteInfo);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AssieteInfo>>();
      const assieteInfo = { id: 123 };
      jest.spyOn(assieteInfoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assieteInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(assieteInfoService.update).toHaveBeenCalledWith(assieteInfo);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackRebriqueById', () => {
      it('Should return tracked Rebrique primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackRebriqueById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackAssieteById', () => {
      it('Should return tracked Assiete primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAssieteById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackModeCalculById', () => {
      it('Should return tracked ModeCalcul primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackModeCalculById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
