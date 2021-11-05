jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { RebriqueService } from '../service/rebrique.service';
import { IRebrique, Rebrique } from '../rebrique.model';
import { ISens } from 'app/entities/sens/sens.model';
import { SensService } from 'app/entities/sens/service/sens.service';
import { IConcerne } from 'app/entities/concerne/concerne.model';
import { ConcerneService } from 'app/entities/concerne/service/concerne.service';
import { IFrequence } from 'app/entities/frequence/frequence.model';
import { FrequenceService } from 'app/entities/frequence/service/frequence.service';

import { RebriqueUpdateComponent } from './rebrique-update.component';

describe('Rebrique Management Update Component', () => {
  let comp: RebriqueUpdateComponent;
  let fixture: ComponentFixture<RebriqueUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let rebriqueService: RebriqueService;
  let sensService: SensService;
  let concerneService: ConcerneService;
  let frequenceService: FrequenceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [RebriqueUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(RebriqueUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RebriqueUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    rebriqueService = TestBed.inject(RebriqueService);
    sensService = TestBed.inject(SensService);
    concerneService = TestBed.inject(ConcerneService);
    frequenceService = TestBed.inject(FrequenceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Sens query and add missing value', () => {
      const rebrique: IRebrique = { id: 456 };
      const sens: ISens = { id: 60635 };
      rebrique.sens = sens;

      const sensCollection: ISens[] = [{ id: 10161 }];
      jest.spyOn(sensService, 'query').mockReturnValue(of(new HttpResponse({ body: sensCollection })));
      const additionalSens = [sens];
      const expectedCollection: ISens[] = [...additionalSens, ...sensCollection];
      jest.spyOn(sensService, 'addSensToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ rebrique });
      comp.ngOnInit();

      expect(sensService.query).toHaveBeenCalled();
      expect(sensService.addSensToCollectionIfMissing).toHaveBeenCalledWith(sensCollection, ...additionalSens);
      expect(comp.sensSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Concerne query and add missing value', () => {
      const rebrique: IRebrique = { id: 456 };
      const concerne: IConcerne = { id: 32510 };
      rebrique.concerne = concerne;

      const concerneCollection: IConcerne[] = [{ id: 18913 }];
      jest.spyOn(concerneService, 'query').mockReturnValue(of(new HttpResponse({ body: concerneCollection })));
      const additionalConcernes = [concerne];
      const expectedCollection: IConcerne[] = [...additionalConcernes, ...concerneCollection];
      jest.spyOn(concerneService, 'addConcerneToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ rebrique });
      comp.ngOnInit();

      expect(concerneService.query).toHaveBeenCalled();
      expect(concerneService.addConcerneToCollectionIfMissing).toHaveBeenCalledWith(concerneCollection, ...additionalConcernes);
      expect(comp.concernesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Frequence query and add missing value', () => {
      const rebrique: IRebrique = { id: 456 };
      const frequence: IFrequence = { id: 23102 };
      rebrique.frequence = frequence;

      const frequenceCollection: IFrequence[] = [{ id: 23141 }];
      jest.spyOn(frequenceService, 'query').mockReturnValue(of(new HttpResponse({ body: frequenceCollection })));
      const additionalFrequences = [frequence];
      const expectedCollection: IFrequence[] = [...additionalFrequences, ...frequenceCollection];
      jest.spyOn(frequenceService, 'addFrequenceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ rebrique });
      comp.ngOnInit();

      expect(frequenceService.query).toHaveBeenCalled();
      expect(frequenceService.addFrequenceToCollectionIfMissing).toHaveBeenCalledWith(frequenceCollection, ...additionalFrequences);
      expect(comp.frequencesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const rebrique: IRebrique = { id: 456 };
      const sens: ISens = { id: 52290 };
      rebrique.sens = sens;
      const concerne: IConcerne = { id: 41044 };
      rebrique.concerne = concerne;
      const frequence: IFrequence = { id: 32874 };
      rebrique.frequence = frequence;

      activatedRoute.data = of({ rebrique });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(rebrique));
      expect(comp.sensSharedCollection).toContain(sens);
      expect(comp.concernesSharedCollection).toContain(concerne);
      expect(comp.frequencesSharedCollection).toContain(frequence);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Rebrique>>();
      const rebrique = { id: 123 };
      jest.spyOn(rebriqueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rebrique });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: rebrique }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(rebriqueService.update).toHaveBeenCalledWith(rebrique);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Rebrique>>();
      const rebrique = new Rebrique();
      jest.spyOn(rebriqueService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rebrique });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: rebrique }));
      saveSubject.complete();

      // THEN
      expect(rebriqueService.create).toHaveBeenCalledWith(rebrique);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Rebrique>>();
      const rebrique = { id: 123 };
      jest.spyOn(rebriqueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rebrique });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(rebriqueService.update).toHaveBeenCalledWith(rebrique);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSensById', () => {
      it('Should return tracked Sens primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSensById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackConcerneById', () => {
      it('Should return tracked Concerne primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackConcerneById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackFrequenceById', () => {
      it('Should return tracked Frequence primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackFrequenceById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
