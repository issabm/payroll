jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { RebriqueContratService } from '../service/rebrique-contrat.service';
import { IRebriqueContrat, RebriqueContrat } from '../rebrique-contrat.model';
import { IRebrique } from 'app/entities/rebrique/rebrique.model';
import { RebriqueService } from 'app/entities/rebrique/service/rebrique.service';
import { ISousTypeContrat } from 'app/entities/sous-type-contrat/sous-type-contrat.model';
import { SousTypeContratService } from 'app/entities/sous-type-contrat/service/sous-type-contrat.service';
import { ITypeContrat } from 'app/entities/type-contrat/type-contrat.model';
import { TypeContratService } from 'app/entities/type-contrat/service/type-contrat.service';

import { RebriqueContratUpdateComponent } from './rebrique-contrat-update.component';

describe('RebriqueContrat Management Update Component', () => {
  let comp: RebriqueContratUpdateComponent;
  let fixture: ComponentFixture<RebriqueContratUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let rebriqueContratService: RebriqueContratService;
  let rebriqueService: RebriqueService;
  let sousTypeContratService: SousTypeContratService;
  let typeContratService: TypeContratService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [RebriqueContratUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(RebriqueContratUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RebriqueContratUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    rebriqueContratService = TestBed.inject(RebriqueContratService);
    rebriqueService = TestBed.inject(RebriqueService);
    sousTypeContratService = TestBed.inject(SousTypeContratService);
    typeContratService = TestBed.inject(TypeContratService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Rebrique query and add missing value', () => {
      const rebriqueContrat: IRebriqueContrat = { id: 456 };
      const rebrqiue: IRebrique = { id: 60207 };
      rebriqueContrat.rebrqiue = rebrqiue;

      const rebriqueCollection: IRebrique[] = [{ id: 80987 }];
      jest.spyOn(rebriqueService, 'query').mockReturnValue(of(new HttpResponse({ body: rebriqueCollection })));
      const additionalRebriques = [rebrqiue];
      const expectedCollection: IRebrique[] = [...additionalRebriques, ...rebriqueCollection];
      jest.spyOn(rebriqueService, 'addRebriqueToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ rebriqueContrat });
      comp.ngOnInit();

      expect(rebriqueService.query).toHaveBeenCalled();
      expect(rebriqueService.addRebriqueToCollectionIfMissing).toHaveBeenCalledWith(rebriqueCollection, ...additionalRebriques);
      expect(comp.rebriquesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SousTypeContrat query and add missing value', () => {
      const rebriqueContrat: IRebriqueContrat = { id: 456 };
      const sousTypeContrat: ISousTypeContrat = { id: 12147 };
      rebriqueContrat.sousTypeContrat = sousTypeContrat;

      const sousTypeContratCollection: ISousTypeContrat[] = [{ id: 84694 }];
      jest.spyOn(sousTypeContratService, 'query').mockReturnValue(of(new HttpResponse({ body: sousTypeContratCollection })));
      const additionalSousTypeContrats = [sousTypeContrat];
      const expectedCollection: ISousTypeContrat[] = [...additionalSousTypeContrats, ...sousTypeContratCollection];
      jest.spyOn(sousTypeContratService, 'addSousTypeContratToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ rebriqueContrat });
      comp.ngOnInit();

      expect(sousTypeContratService.query).toHaveBeenCalled();
      expect(sousTypeContratService.addSousTypeContratToCollectionIfMissing).toHaveBeenCalledWith(
        sousTypeContratCollection,
        ...additionalSousTypeContrats
      );
      expect(comp.sousTypeContratsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TypeContrat query and add missing value', () => {
      const rebriqueContrat: IRebriqueContrat = { id: 456 };
      const typeContrat: ITypeContrat = { id: 15921 };
      rebriqueContrat.typeContrat = typeContrat;

      const typeContratCollection: ITypeContrat[] = [{ id: 23608 }];
      jest.spyOn(typeContratService, 'query').mockReturnValue(of(new HttpResponse({ body: typeContratCollection })));
      const additionalTypeContrats = [typeContrat];
      const expectedCollection: ITypeContrat[] = [...additionalTypeContrats, ...typeContratCollection];
      jest.spyOn(typeContratService, 'addTypeContratToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ rebriqueContrat });
      comp.ngOnInit();

      expect(typeContratService.query).toHaveBeenCalled();
      expect(typeContratService.addTypeContratToCollectionIfMissing).toHaveBeenCalledWith(typeContratCollection, ...additionalTypeContrats);
      expect(comp.typeContratsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const rebriqueContrat: IRebriqueContrat = { id: 456 };
      const rebrqiue: IRebrique = { id: 42535 };
      rebriqueContrat.rebrqiue = rebrqiue;
      const sousTypeContrat: ISousTypeContrat = { id: 62300 };
      rebriqueContrat.sousTypeContrat = sousTypeContrat;
      const typeContrat: ITypeContrat = { id: 1026 };
      rebriqueContrat.typeContrat = typeContrat;

      activatedRoute.data = of({ rebriqueContrat });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(rebriqueContrat));
      expect(comp.rebriquesSharedCollection).toContain(rebrqiue);
      expect(comp.sousTypeContratsSharedCollection).toContain(sousTypeContrat);
      expect(comp.typeContratsSharedCollection).toContain(typeContrat);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RebriqueContrat>>();
      const rebriqueContrat = { id: 123 };
      jest.spyOn(rebriqueContratService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rebriqueContrat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: rebriqueContrat }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(rebriqueContratService.update).toHaveBeenCalledWith(rebriqueContrat);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RebriqueContrat>>();
      const rebriqueContrat = new RebriqueContrat();
      jest.spyOn(rebriqueContratService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rebriqueContrat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: rebriqueContrat }));
      saveSubject.complete();

      // THEN
      expect(rebriqueContratService.create).toHaveBeenCalledWith(rebriqueContrat);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RebriqueContrat>>();
      const rebriqueContrat = { id: 123 };
      jest.spyOn(rebriqueContratService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rebriqueContrat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(rebriqueContratService.update).toHaveBeenCalledWith(rebriqueContrat);
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

    describe('trackSousTypeContratById', () => {
      it('Should return tracked SousTypeContrat primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSousTypeContratById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackTypeContratById', () => {
      it('Should return tracked TypeContrat primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTypeContratById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
