jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FormPaieService } from '../service/form-paie.service';
import { IFormPaie, FormPaie } from '../form-paie.model';

import { FormPaieUpdateComponent } from './form-paie-update.component';

describe('FormPaie Management Update Component', () => {
  let comp: FormPaieUpdateComponent;
  let fixture: ComponentFixture<FormPaieUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let formPaieService: FormPaieService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [FormPaieUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(FormPaieUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FormPaieUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    formPaieService = TestBed.inject(FormPaieService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const formPaie: IFormPaie = { id: 456 };

      activatedRoute.data = of({ formPaie });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(formPaie));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FormPaie>>();
      const formPaie = { id: 123 };
      jest.spyOn(formPaieService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ formPaie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: formPaie }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(formPaieService.update).toHaveBeenCalledWith(formPaie);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FormPaie>>();
      const formPaie = new FormPaie();
      jest.spyOn(formPaieService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ formPaie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: formPaie }));
      saveSubject.complete();

      // THEN
      expect(formPaieService.create).toHaveBeenCalledWith(formPaie);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FormPaie>>();
      const formPaie = { id: 123 };
      jest.spyOn(formPaieService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ formPaie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(formPaieService.update).toHaveBeenCalledWith(formPaie);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
