jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FormPaieLigneRebService } from '../service/form-paie-ligne-reb.service';
import { IFormPaieLigneReb, FormPaieLigneReb } from '../form-paie-ligne-reb.model';
import { IFormPaieLigne } from 'app/entities/form-paie-ligne/form-paie-ligne.model';
import { FormPaieLigneService } from 'app/entities/form-paie-ligne/service/form-paie-ligne.service';

import { FormPaieLigneRebUpdateComponent } from './form-paie-ligne-reb-update.component';

describe('FormPaieLigneReb Management Update Component', () => {
  let comp: FormPaieLigneRebUpdateComponent;
  let fixture: ComponentFixture<FormPaieLigneRebUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let formPaieLigneRebService: FormPaieLigneRebService;
  let formPaieLigneService: FormPaieLigneService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [FormPaieLigneRebUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(FormPaieLigneRebUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FormPaieLigneRebUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    formPaieLigneRebService = TestBed.inject(FormPaieLigneRebService);
    formPaieLigneService = TestBed.inject(FormPaieLigneService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call FormPaieLigne query and add missing value', () => {
      const formPaieLigneReb: IFormPaieLigneReb = { id: 456 };
      const formPaieLigne: IFormPaieLigne = { id: 11999 };
      formPaieLigneReb.formPaieLigne = formPaieLigne;

      const formPaieLigneCollection: IFormPaieLigne[] = [{ id: 6029 }];
      jest.spyOn(formPaieLigneService, 'query').mockReturnValue(of(new HttpResponse({ body: formPaieLigneCollection })));
      const additionalFormPaieLignes = [formPaieLigne];
      const expectedCollection: IFormPaieLigne[] = [...additionalFormPaieLignes, ...formPaieLigneCollection];
      jest.spyOn(formPaieLigneService, 'addFormPaieLigneToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ formPaieLigneReb });
      comp.ngOnInit();

      expect(formPaieLigneService.query).toHaveBeenCalled();
      expect(formPaieLigneService.addFormPaieLigneToCollectionIfMissing).toHaveBeenCalledWith(
        formPaieLigneCollection,
        ...additionalFormPaieLignes
      );
      expect(comp.formPaieLignesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const formPaieLigneReb: IFormPaieLigneReb = { id: 456 };
      const formPaieLigne: IFormPaieLigne = { id: 71508 };
      formPaieLigneReb.formPaieLigne = formPaieLigne;

      activatedRoute.data = of({ formPaieLigneReb });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(formPaieLigneReb));
      expect(comp.formPaieLignesSharedCollection).toContain(formPaieLigne);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FormPaieLigneReb>>();
      const formPaieLigneReb = { id: 123 };
      jest.spyOn(formPaieLigneRebService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ formPaieLigneReb });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: formPaieLigneReb }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(formPaieLigneRebService.update).toHaveBeenCalledWith(formPaieLigneReb);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FormPaieLigneReb>>();
      const formPaieLigneReb = new FormPaieLigneReb();
      jest.spyOn(formPaieLigneRebService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ formPaieLigneReb });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: formPaieLigneReb }));
      saveSubject.complete();

      // THEN
      expect(formPaieLigneRebService.create).toHaveBeenCalledWith(formPaieLigneReb);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FormPaieLigneReb>>();
      const formPaieLigneReb = { id: 123 };
      jest.spyOn(formPaieLigneRebService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ formPaieLigneReb });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(formPaieLigneRebService.update).toHaveBeenCalledWith(formPaieLigneReb);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackFormPaieLigneById', () => {
      it('Should return tracked FormPaieLigne primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackFormPaieLigneById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
