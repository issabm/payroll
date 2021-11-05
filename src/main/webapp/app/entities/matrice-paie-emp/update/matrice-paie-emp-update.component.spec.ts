jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { MatricePaieEmpService } from '../service/matrice-paie-emp.service';
import { IMatricePaieEmp, MatricePaieEmp } from '../matrice-paie-emp.model';
import { IMatricePaie } from 'app/entities/matrice-paie/matrice-paie.model';
import { MatricePaieService } from 'app/entities/matrice-paie/service/matrice-paie.service';
import { IEmploye } from 'app/entities/employe/employe.model';
import { EmployeService } from 'app/entities/employe/service/employe.service';
import { ISituation } from 'app/entities/situation/situation.model';
import { SituationService } from 'app/entities/situation/service/situation.service';

import { MatricePaieEmpUpdateComponent } from './matrice-paie-emp-update.component';

describe('MatricePaieEmp Management Update Component', () => {
  let comp: MatricePaieEmpUpdateComponent;
  let fixture: ComponentFixture<MatricePaieEmpUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let matricePaieEmpService: MatricePaieEmpService;
  let matricePaieService: MatricePaieService;
  let employeService: EmployeService;
  let situationService: SituationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [MatricePaieEmpUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(MatricePaieEmpUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MatricePaieEmpUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    matricePaieEmpService = TestBed.inject(MatricePaieEmpService);
    matricePaieService = TestBed.inject(MatricePaieService);
    employeService = TestBed.inject(EmployeService);
    situationService = TestBed.inject(SituationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call MatricePaie query and add missing value', () => {
      const matricePaieEmp: IMatricePaieEmp = { id: 456 };
      const matricePaie: IMatricePaie = { id: 24748 };
      matricePaieEmp.matricePaie = matricePaie;

      const matricePaieCollection: IMatricePaie[] = [{ id: 30841 }];
      jest.spyOn(matricePaieService, 'query').mockReturnValue(of(new HttpResponse({ body: matricePaieCollection })));
      const additionalMatricePaies = [matricePaie];
      const expectedCollection: IMatricePaie[] = [...additionalMatricePaies, ...matricePaieCollection];
      jest.spyOn(matricePaieService, 'addMatricePaieToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ matricePaieEmp });
      comp.ngOnInit();

      expect(matricePaieService.query).toHaveBeenCalled();
      expect(matricePaieService.addMatricePaieToCollectionIfMissing).toHaveBeenCalledWith(matricePaieCollection, ...additionalMatricePaies);
      expect(comp.matricePaiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Employe query and add missing value', () => {
      const matricePaieEmp: IMatricePaieEmp = { id: 456 };
      const employe: IEmploye = { id: 26355 };
      matricePaieEmp.employe = employe;

      const employeCollection: IEmploye[] = [{ id: 99989 }];
      jest.spyOn(employeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeCollection })));
      const additionalEmployes = [employe];
      const expectedCollection: IEmploye[] = [...additionalEmployes, ...employeCollection];
      jest.spyOn(employeService, 'addEmployeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ matricePaieEmp });
      comp.ngOnInit();

      expect(employeService.query).toHaveBeenCalled();
      expect(employeService.addEmployeToCollectionIfMissing).toHaveBeenCalledWith(employeCollection, ...additionalEmployes);
      expect(comp.employesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Situation query and add missing value', () => {
      const matricePaieEmp: IMatricePaieEmp = { id: 456 };
      const situation: ISituation = { id: 89937 };
      matricePaieEmp.situation = situation;

      const situationCollection: ISituation[] = [{ id: 28416 }];
      jest.spyOn(situationService, 'query').mockReturnValue(of(new HttpResponse({ body: situationCollection })));
      const additionalSituations = [situation];
      const expectedCollection: ISituation[] = [...additionalSituations, ...situationCollection];
      jest.spyOn(situationService, 'addSituationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ matricePaieEmp });
      comp.ngOnInit();

      expect(situationService.query).toHaveBeenCalled();
      expect(situationService.addSituationToCollectionIfMissing).toHaveBeenCalledWith(situationCollection, ...additionalSituations);
      expect(comp.situationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const matricePaieEmp: IMatricePaieEmp = { id: 456 };
      const matricePaie: IMatricePaie = { id: 469 };
      matricePaieEmp.matricePaie = matricePaie;
      const employe: IEmploye = { id: 68154 };
      matricePaieEmp.employe = employe;
      const situation: ISituation = { id: 78667 };
      matricePaieEmp.situation = situation;

      activatedRoute.data = of({ matricePaieEmp });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(matricePaieEmp));
      expect(comp.matricePaiesSharedCollection).toContain(matricePaie);
      expect(comp.employesSharedCollection).toContain(employe);
      expect(comp.situationsSharedCollection).toContain(situation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MatricePaieEmp>>();
      const matricePaieEmp = { id: 123 };
      jest.spyOn(matricePaieEmpService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ matricePaieEmp });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: matricePaieEmp }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(matricePaieEmpService.update).toHaveBeenCalledWith(matricePaieEmp);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MatricePaieEmp>>();
      const matricePaieEmp = new MatricePaieEmp();
      jest.spyOn(matricePaieEmpService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ matricePaieEmp });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: matricePaieEmp }));
      saveSubject.complete();

      // THEN
      expect(matricePaieEmpService.create).toHaveBeenCalledWith(matricePaieEmp);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MatricePaieEmp>>();
      const matricePaieEmp = { id: 123 };
      jest.spyOn(matricePaieEmpService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ matricePaieEmp });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(matricePaieEmpService.update).toHaveBeenCalledWith(matricePaieEmp);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackMatricePaieById', () => {
      it('Should return tracked MatricePaie primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackMatricePaieById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackEmployeById', () => {
      it('Should return tracked Employe primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEmployeById(0, entity);
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
