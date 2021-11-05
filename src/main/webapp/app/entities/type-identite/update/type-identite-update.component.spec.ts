jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TypeIdentiteService } from '../service/type-identite.service';
import { ITypeIdentite, TypeIdentite } from '../type-identite.model';

import { TypeIdentiteUpdateComponent } from './type-identite-update.component';

describe('TypeIdentite Management Update Component', () => {
  let comp: TypeIdentiteUpdateComponent;
  let fixture: ComponentFixture<TypeIdentiteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let typeIdentiteService: TypeIdentiteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TypeIdentiteUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(TypeIdentiteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TypeIdentiteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    typeIdentiteService = TestBed.inject(TypeIdentiteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const typeIdentite: ITypeIdentite = { id: 456 };

      activatedRoute.data = of({ typeIdentite });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(typeIdentite));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TypeIdentite>>();
      const typeIdentite = { id: 123 };
      jest.spyOn(typeIdentiteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typeIdentite });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: typeIdentite }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(typeIdentiteService.update).toHaveBeenCalledWith(typeIdentite);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TypeIdentite>>();
      const typeIdentite = new TypeIdentite();
      jest.spyOn(typeIdentiteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typeIdentite });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: typeIdentite }));
      saveSubject.complete();

      // THEN
      expect(typeIdentiteService.create).toHaveBeenCalledWith(typeIdentite);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TypeIdentite>>();
      const typeIdentite = { id: 123 };
      jest.spyOn(typeIdentiteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typeIdentite });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(typeIdentiteService.update).toHaveBeenCalledWith(typeIdentite);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
