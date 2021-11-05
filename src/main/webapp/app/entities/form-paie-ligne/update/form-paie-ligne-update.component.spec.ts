jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FormPaieLigneService } from '../service/form-paie-ligne.service';
import { IFormPaieLigne, FormPaieLigne } from '../form-paie-ligne.model';
import { IFormPaie } from 'app/entities/form-paie/form-paie.model';
import { FormPaieService } from 'app/entities/form-paie/service/form-paie.service';
import { IOperatorForm } from 'app/entities/operator-form/operator-form.model';
import { OperatorFormService } from 'app/entities/operator-form/service/operator-form.service';
import { IAssiete } from 'app/entities/assiete/assiete.model';
import { AssieteService } from 'app/entities/assiete/service/assiete.service';

import { FormPaieLigneUpdateComponent } from './form-paie-ligne-update.component';

describe('FormPaieLigne Management Update Component', () => {
  let comp: FormPaieLigneUpdateComponent;
  let fixture: ComponentFixture<FormPaieLigneUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let formPaieLigneService: FormPaieLigneService;
  let formPaieService: FormPaieService;
  let operatorFormService: OperatorFormService;
  let assieteService: AssieteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [FormPaieLigneUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(FormPaieLigneUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FormPaieLigneUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    formPaieLigneService = TestBed.inject(FormPaieLigneService);
    formPaieService = TestBed.inject(FormPaieService);
    operatorFormService = TestBed.inject(OperatorFormService);
    assieteService = TestBed.inject(AssieteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call FormPaie query and add missing value', () => {
      const formPaieLigne: IFormPaieLigne = { id: 456 };
      const formPaie: IFormPaie = { id: 69179 };
      formPaieLigne.formPaie = formPaie;

      const formPaieCollection: IFormPaie[] = [{ id: 15486 }];
      jest.spyOn(formPaieService, 'query').mockReturnValue(of(new HttpResponse({ body: formPaieCollection })));
      const additionalFormPaies = [formPaie];
      const expectedCollection: IFormPaie[] = [...additionalFormPaies, ...formPaieCollection];
      jest.spyOn(formPaieService, 'addFormPaieToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ formPaieLigne });
      comp.ngOnInit();

      expect(formPaieService.query).toHaveBeenCalled();
      expect(formPaieService.addFormPaieToCollectionIfMissing).toHaveBeenCalledWith(formPaieCollection, ...additionalFormPaies);
      expect(comp.formPaiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call OperatorForm query and add missing value', () => {
      const formPaieLigne: IFormPaieLigne = { id: 456 };
      const operatorForm: IOperatorForm = { id: 92515 };
      formPaieLigne.operatorForm = operatorForm;

      const operatorFormCollection: IOperatorForm[] = [{ id: 90147 }];
      jest.spyOn(operatorFormService, 'query').mockReturnValue(of(new HttpResponse({ body: operatorFormCollection })));
      const additionalOperatorForms = [operatorForm];
      const expectedCollection: IOperatorForm[] = [...additionalOperatorForms, ...operatorFormCollection];
      jest.spyOn(operatorFormService, 'addOperatorFormToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ formPaieLigne });
      comp.ngOnInit();

      expect(operatorFormService.query).toHaveBeenCalled();
      expect(operatorFormService.addOperatorFormToCollectionIfMissing).toHaveBeenCalledWith(
        operatorFormCollection,
        ...additionalOperatorForms
      );
      expect(comp.operatorFormsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Assiete query and add missing value', () => {
      const formPaieLigne: IFormPaieLigne = { id: 456 };
      const assiete: IAssiete = { id: 44911 };
      formPaieLigne.assiete = assiete;

      const assieteCollection: IAssiete[] = [{ id: 51355 }];
      jest.spyOn(assieteService, 'query').mockReturnValue(of(new HttpResponse({ body: assieteCollection })));
      const additionalAssietes = [assiete];
      const expectedCollection: IAssiete[] = [...additionalAssietes, ...assieteCollection];
      jest.spyOn(assieteService, 'addAssieteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ formPaieLigne });
      comp.ngOnInit();

      expect(assieteService.query).toHaveBeenCalled();
      expect(assieteService.addAssieteToCollectionIfMissing).toHaveBeenCalledWith(assieteCollection, ...additionalAssietes);
      expect(comp.assietesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const formPaieLigne: IFormPaieLigne = { id: 456 };
      const formPaie: IFormPaie = { id: 52573 };
      formPaieLigne.formPaie = formPaie;
      const operatorForm: IOperatorForm = { id: 57263 };
      formPaieLigne.operatorForm = operatorForm;
      const assiete: IAssiete = { id: 30525 };
      formPaieLigne.assiete = assiete;

      activatedRoute.data = of({ formPaieLigne });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(formPaieLigne));
      expect(comp.formPaiesSharedCollection).toContain(formPaie);
      expect(comp.operatorFormsSharedCollection).toContain(operatorForm);
      expect(comp.assietesSharedCollection).toContain(assiete);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FormPaieLigne>>();
      const formPaieLigne = { id: 123 };
      jest.spyOn(formPaieLigneService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ formPaieLigne });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: formPaieLigne }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(formPaieLigneService.update).toHaveBeenCalledWith(formPaieLigne);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FormPaieLigne>>();
      const formPaieLigne = new FormPaieLigne();
      jest.spyOn(formPaieLigneService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ formPaieLigne });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: formPaieLigne }));
      saveSubject.complete();

      // THEN
      expect(formPaieLigneService.create).toHaveBeenCalledWith(formPaieLigne);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FormPaieLigne>>();
      const formPaieLigne = { id: 123 };
      jest.spyOn(formPaieLigneService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ formPaieLigne });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(formPaieLigneService.update).toHaveBeenCalledWith(formPaieLigne);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackFormPaieById', () => {
      it('Should return tracked FormPaie primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackFormPaieById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackOperatorFormById', () => {
      it('Should return tracked OperatorForm primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackOperatorFormById(0, entity);
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
  });
});
