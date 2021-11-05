jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { NatureAbsenceRebriqueService } from '../service/nature-absence-rebrique.service';
import { INatureAbsenceRebrique, NatureAbsenceRebrique } from '../nature-absence-rebrique.model';
import { INatureAbsence } from 'app/entities/nature-absence/nature-absence.model';
import { NatureAbsenceService } from 'app/entities/nature-absence/service/nature-absence.service';
import { IRebrique } from 'app/entities/rebrique/rebrique.model';
import { RebriqueService } from 'app/entities/rebrique/service/rebrique.service';

import { NatureAbsenceRebriqueUpdateComponent } from './nature-absence-rebrique-update.component';

describe('NatureAbsenceRebrique Management Update Component', () => {
  let comp: NatureAbsenceRebriqueUpdateComponent;
  let fixture: ComponentFixture<NatureAbsenceRebriqueUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let natureAbsenceRebriqueService: NatureAbsenceRebriqueService;
  let natureAbsenceService: NatureAbsenceService;
  let rebriqueService: RebriqueService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [NatureAbsenceRebriqueUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(NatureAbsenceRebriqueUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NatureAbsenceRebriqueUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    natureAbsenceRebriqueService = TestBed.inject(NatureAbsenceRebriqueService);
    natureAbsenceService = TestBed.inject(NatureAbsenceService);
    rebriqueService = TestBed.inject(RebriqueService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call NatureAbsence query and add missing value', () => {
      const natureAbsenceRebrique: INatureAbsenceRebrique = { id: 456 };
      const natureAbsence: INatureAbsence = { id: 60689 };
      natureAbsenceRebrique.natureAbsence = natureAbsence;

      const natureAbsenceCollection: INatureAbsence[] = [{ id: 52263 }];
      jest.spyOn(natureAbsenceService, 'query').mockReturnValue(of(new HttpResponse({ body: natureAbsenceCollection })));
      const additionalNatureAbsences = [natureAbsence];
      const expectedCollection: INatureAbsence[] = [...additionalNatureAbsences, ...natureAbsenceCollection];
      jest.spyOn(natureAbsenceService, 'addNatureAbsenceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ natureAbsenceRebrique });
      comp.ngOnInit();

      expect(natureAbsenceService.query).toHaveBeenCalled();
      expect(natureAbsenceService.addNatureAbsenceToCollectionIfMissing).toHaveBeenCalledWith(
        natureAbsenceCollection,
        ...additionalNatureAbsences
      );
      expect(comp.natureAbsencesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Rebrique query and add missing value', () => {
      const natureAbsenceRebrique: INatureAbsenceRebrique = { id: 456 };
      const rebrique: IRebrique = { id: 30609 };
      natureAbsenceRebrique.rebrique = rebrique;

      const rebriqueCollection: IRebrique[] = [{ id: 86064 }];
      jest.spyOn(rebriqueService, 'query').mockReturnValue(of(new HttpResponse({ body: rebriqueCollection })));
      const additionalRebriques = [rebrique];
      const expectedCollection: IRebrique[] = [...additionalRebriques, ...rebriqueCollection];
      jest.spyOn(rebriqueService, 'addRebriqueToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ natureAbsenceRebrique });
      comp.ngOnInit();

      expect(rebriqueService.query).toHaveBeenCalled();
      expect(rebriqueService.addRebriqueToCollectionIfMissing).toHaveBeenCalledWith(rebriqueCollection, ...additionalRebriques);
      expect(comp.rebriquesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const natureAbsenceRebrique: INatureAbsenceRebrique = { id: 456 };
      const natureAbsence: INatureAbsence = { id: 4154 };
      natureAbsenceRebrique.natureAbsence = natureAbsence;
      const rebrique: IRebrique = { id: 66151 };
      natureAbsenceRebrique.rebrique = rebrique;

      activatedRoute.data = of({ natureAbsenceRebrique });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(natureAbsenceRebrique));
      expect(comp.natureAbsencesSharedCollection).toContain(natureAbsence);
      expect(comp.rebriquesSharedCollection).toContain(rebrique);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NatureAbsenceRebrique>>();
      const natureAbsenceRebrique = { id: 123 };
      jest.spyOn(natureAbsenceRebriqueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureAbsenceRebrique });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: natureAbsenceRebrique }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(natureAbsenceRebriqueService.update).toHaveBeenCalledWith(natureAbsenceRebrique);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NatureAbsenceRebrique>>();
      const natureAbsenceRebrique = new NatureAbsenceRebrique();
      jest.spyOn(natureAbsenceRebriqueService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureAbsenceRebrique });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: natureAbsenceRebrique }));
      saveSubject.complete();

      // THEN
      expect(natureAbsenceRebriqueService.create).toHaveBeenCalledWith(natureAbsenceRebrique);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NatureAbsenceRebrique>>();
      const natureAbsenceRebrique = { id: 123 };
      jest.spyOn(natureAbsenceRebriqueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureAbsenceRebrique });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(natureAbsenceRebriqueService.update).toHaveBeenCalledWith(natureAbsenceRebrique);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackNatureAbsenceById', () => {
      it('Should return tracked NatureAbsence primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackNatureAbsenceById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackRebriqueById', () => {
      it('Should return tracked Rebrique primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackRebriqueById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
