jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AssieteService } from '../service/assiete.service';
import { IAssiete, Assiete } from '../assiete.model';

import { AssieteUpdateComponent } from './assiete-update.component';

describe('Assiete Management Update Component', () => {
  let comp: AssieteUpdateComponent;
  let fixture: ComponentFixture<AssieteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let assieteService: AssieteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [AssieteUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(AssieteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AssieteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    assieteService = TestBed.inject(AssieteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const assiete: IAssiete = { id: 456 };

      activatedRoute.data = of({ assiete });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(assiete));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Assiete>>();
      const assiete = { id: 123 };
      jest.spyOn(assieteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assiete });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: assiete }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(assieteService.update).toHaveBeenCalledWith(assiete);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Assiete>>();
      const assiete = new Assiete();
      jest.spyOn(assieteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assiete });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: assiete }));
      saveSubject.complete();

      // THEN
      expect(assieteService.create).toHaveBeenCalledWith(assiete);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Assiete>>();
      const assiete = { id: 123 };
      jest.spyOn(assieteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assiete });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(assieteService.update).toHaveBeenCalledWith(assiete);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
