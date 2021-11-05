jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PalierImpoService } from '../service/palier-impo.service';
import { IPalierImpo, PalierImpo } from '../palier-impo.model';
import { IPays } from 'app/entities/pays/pays.model';
import { PaysService } from 'app/entities/pays/service/pays.service';
import { ISituation } from 'app/entities/situation/situation.model';
import { SituationService } from 'app/entities/situation/service/situation.service';

import { PalierImpoUpdateComponent } from './palier-impo-update.component';

describe('PalierImpo Management Update Component', () => {
  let comp: PalierImpoUpdateComponent;
  let fixture: ComponentFixture<PalierImpoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let palierImpoService: PalierImpoService;
  let paysService: PaysService;
  let situationService: SituationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PalierImpoUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(PalierImpoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PalierImpoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    palierImpoService = TestBed.inject(PalierImpoService);
    paysService = TestBed.inject(PaysService);
    situationService = TestBed.inject(SituationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pays query and add missing value', () => {
      const palierImpo: IPalierImpo = { id: 456 };
      const pays: IPays = { id: 11219 };
      palierImpo.pays = pays;

      const paysCollection: IPays[] = [{ id: 63278 }];
      jest.spyOn(paysService, 'query').mockReturnValue(of(new HttpResponse({ body: paysCollection })));
      const additionalPays = [pays];
      const expectedCollection: IPays[] = [...additionalPays, ...paysCollection];
      jest.spyOn(paysService, 'addPaysToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ palierImpo });
      comp.ngOnInit();

      expect(paysService.query).toHaveBeenCalled();
      expect(paysService.addPaysToCollectionIfMissing).toHaveBeenCalledWith(paysCollection, ...additionalPays);
      expect(comp.paysSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Situation query and add missing value', () => {
      const palierImpo: IPalierImpo = { id: 456 };
      const situation: ISituation = { id: 86186 };
      palierImpo.situation = situation;

      const situationCollection: ISituation[] = [{ id: 99959 }];
      jest.spyOn(situationService, 'query').mockReturnValue(of(new HttpResponse({ body: situationCollection })));
      const additionalSituations = [situation];
      const expectedCollection: ISituation[] = [...additionalSituations, ...situationCollection];
      jest.spyOn(situationService, 'addSituationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ palierImpo });
      comp.ngOnInit();

      expect(situationService.query).toHaveBeenCalled();
      expect(situationService.addSituationToCollectionIfMissing).toHaveBeenCalledWith(situationCollection, ...additionalSituations);
      expect(comp.situationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const palierImpo: IPalierImpo = { id: 456 };
      const pays: IPays = { id: 19286 };
      palierImpo.pays = pays;
      const situation: ISituation = { id: 95946 };
      palierImpo.situation = situation;

      activatedRoute.data = of({ palierImpo });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(palierImpo));
      expect(comp.paysSharedCollection).toContain(pays);
      expect(comp.situationsSharedCollection).toContain(situation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PalierImpo>>();
      const palierImpo = { id: 123 };
      jest.spyOn(palierImpoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ palierImpo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: palierImpo }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(palierImpoService.update).toHaveBeenCalledWith(palierImpo);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PalierImpo>>();
      const palierImpo = new PalierImpo();
      jest.spyOn(palierImpoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ palierImpo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: palierImpo }));
      saveSubject.complete();

      // THEN
      expect(palierImpoService.create).toHaveBeenCalledWith(palierImpo);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PalierImpo>>();
      const palierImpo = { id: 123 };
      jest.spyOn(palierImpoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ palierImpo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(palierImpoService.update).toHaveBeenCalledWith(palierImpo);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPaysById', () => {
      it('Should return tracked Pays primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPaysById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSituationById', () => {
      it('Should return tracked Situation primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSituationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
