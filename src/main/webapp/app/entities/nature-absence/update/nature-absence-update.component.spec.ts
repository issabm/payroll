jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { NatureAbsenceService } from '../service/nature-absence.service';
import { INatureAbsence, NatureAbsence } from '../nature-absence.model';

import { NatureAbsenceUpdateComponent } from './nature-absence-update.component';

describe('NatureAbsence Management Update Component', () => {
  let comp: NatureAbsenceUpdateComponent;
  let fixture: ComponentFixture<NatureAbsenceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let natureAbsenceService: NatureAbsenceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [NatureAbsenceUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(NatureAbsenceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NatureAbsenceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    natureAbsenceService = TestBed.inject(NatureAbsenceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const natureAbsence: INatureAbsence = { id: 456 };

      activatedRoute.data = of({ natureAbsence });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(natureAbsence));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NatureAbsence>>();
      const natureAbsence = { id: 123 };
      jest.spyOn(natureAbsenceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureAbsence });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: natureAbsence }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(natureAbsenceService.update).toHaveBeenCalledWith(natureAbsence);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NatureAbsence>>();
      const natureAbsence = new NatureAbsence();
      jest.spyOn(natureAbsenceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureAbsence });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: natureAbsence }));
      saveSubject.complete();

      // THEN
      expect(natureAbsenceService.create).toHaveBeenCalledWith(natureAbsence);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NatureAbsence>>();
      const natureAbsence = { id: 123 };
      jest.spyOn(natureAbsenceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureAbsence });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(natureAbsenceService.update).toHaveBeenCalledWith(natureAbsence);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
