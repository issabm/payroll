jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EchlonService } from '../service/echlon.service';
import { IEchlon, Echlon } from '../echlon.model';

import { EchlonUpdateComponent } from './echlon-update.component';

describe('Echlon Management Update Component', () => {
  let comp: EchlonUpdateComponent;
  let fixture: ComponentFixture<EchlonUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let echlonService: EchlonService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [EchlonUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(EchlonUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EchlonUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    echlonService = TestBed.inject(EchlonService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const echlon: IEchlon = { id: 456 };

      activatedRoute.data = of({ echlon });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(echlon));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Echlon>>();
      const echlon = { id: 123 };
      jest.spyOn(echlonService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ echlon });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: echlon }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(echlonService.update).toHaveBeenCalledWith(echlon);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Echlon>>();
      const echlon = new Echlon();
      jest.spyOn(echlonService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ echlon });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: echlon }));
      saveSubject.complete();

      // THEN
      expect(echlonService.create).toHaveBeenCalledWith(echlon);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Echlon>>();
      const echlon = { id: 123 };
      jest.spyOn(echlonService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ echlon });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(echlonService.update).toHaveBeenCalledWith(echlon);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
