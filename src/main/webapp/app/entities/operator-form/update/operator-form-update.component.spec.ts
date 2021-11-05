jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { OperatorFormService } from '../service/operator-form.service';
import { IOperatorForm, OperatorForm } from '../operator-form.model';

import { OperatorFormUpdateComponent } from './operator-form-update.component';

describe('OperatorForm Management Update Component', () => {
  let comp: OperatorFormUpdateComponent;
  let fixture: ComponentFixture<OperatorFormUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let operatorFormService: OperatorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [OperatorFormUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(OperatorFormUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OperatorFormUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    operatorFormService = TestBed.inject(OperatorFormService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const operatorForm: IOperatorForm = { id: 456 };

      activatedRoute.data = of({ operatorForm });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(operatorForm));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<OperatorForm>>();
      const operatorForm = { id: 123 };
      jest.spyOn(operatorFormService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ operatorForm });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: operatorForm }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(operatorFormService.update).toHaveBeenCalledWith(operatorForm);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<OperatorForm>>();
      const operatorForm = new OperatorForm();
      jest.spyOn(operatorFormService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ operatorForm });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: operatorForm }));
      saveSubject.complete();

      // THEN
      expect(operatorFormService.create).toHaveBeenCalledWith(operatorForm);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<OperatorForm>>();
      const operatorForm = { id: 123 };
      jest.spyOn(operatorFormService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ operatorForm });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(operatorFormService.update).toHaveBeenCalledWith(operatorForm);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
