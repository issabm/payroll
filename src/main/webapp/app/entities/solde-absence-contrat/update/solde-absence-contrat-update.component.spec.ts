jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SoldeAbsenceContratService } from '../service/solde-absence-contrat.service';
import { ISoldeAbsenceContrat, SoldeAbsenceContrat } from '../solde-absence-contrat.model';
import { ITypeContrat } from 'app/entities/type-contrat/type-contrat.model';
import { TypeContratService } from 'app/entities/type-contrat/service/type-contrat.service';

import { SoldeAbsenceContratUpdateComponent } from './solde-absence-contrat-update.component';

describe('SoldeAbsenceContrat Management Update Component', () => {
  let comp: SoldeAbsenceContratUpdateComponent;
  let fixture: ComponentFixture<SoldeAbsenceContratUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let soldeAbsenceContratService: SoldeAbsenceContratService;
  let typeContratService: TypeContratService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SoldeAbsenceContratUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(SoldeAbsenceContratUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SoldeAbsenceContratUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    soldeAbsenceContratService = TestBed.inject(SoldeAbsenceContratService);
    typeContratService = TestBed.inject(TypeContratService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TypeContrat query and add missing value', () => {
      const soldeAbsenceContrat: ISoldeAbsenceContrat = { id: 456 };
      const typeContrat: ITypeContrat = { id: 1038 };
      soldeAbsenceContrat.typeContrat = typeContrat;

      const typeContratCollection: ITypeContrat[] = [{ id: 90000 }];
      jest.spyOn(typeContratService, 'query').mockReturnValue(of(new HttpResponse({ body: typeContratCollection })));
      const additionalTypeContrats = [typeContrat];
      const expectedCollection: ITypeContrat[] = [...additionalTypeContrats, ...typeContratCollection];
      jest.spyOn(typeContratService, 'addTypeContratToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ soldeAbsenceContrat });
      comp.ngOnInit();

      expect(typeContratService.query).toHaveBeenCalled();
      expect(typeContratService.addTypeContratToCollectionIfMissing).toHaveBeenCalledWith(typeContratCollection, ...additionalTypeContrats);
      expect(comp.typeContratsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const soldeAbsenceContrat: ISoldeAbsenceContrat = { id: 456 };
      const typeContrat: ITypeContrat = { id: 65892 };
      soldeAbsenceContrat.typeContrat = typeContrat;

      activatedRoute.data = of({ soldeAbsenceContrat });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(soldeAbsenceContrat));
      expect(comp.typeContratsSharedCollection).toContain(typeContrat);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SoldeAbsenceContrat>>();
      const soldeAbsenceContrat = { id: 123 };
      jest.spyOn(soldeAbsenceContratService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ soldeAbsenceContrat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: soldeAbsenceContrat }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(soldeAbsenceContratService.update).toHaveBeenCalledWith(soldeAbsenceContrat);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SoldeAbsenceContrat>>();
      const soldeAbsenceContrat = new SoldeAbsenceContrat();
      jest.spyOn(soldeAbsenceContratService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ soldeAbsenceContrat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: soldeAbsenceContrat }));
      saveSubject.complete();

      // THEN
      expect(soldeAbsenceContratService.create).toHaveBeenCalledWith(soldeAbsenceContrat);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SoldeAbsenceContrat>>();
      const soldeAbsenceContrat = { id: 123 };
      jest.spyOn(soldeAbsenceContratService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ soldeAbsenceContrat });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(soldeAbsenceContratService.update).toHaveBeenCalledWith(soldeAbsenceContrat);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackTypeContratById', () => {
      it('Should return tracked TypeContrat primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTypeContratById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
