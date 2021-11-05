jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { NiveauScolaireService } from '../service/niveau-scolaire.service';
import { INiveauScolaire, NiveauScolaire } from '../niveau-scolaire.model';

import { NiveauScolaireUpdateComponent } from './niveau-scolaire-update.component';

describe('NiveauScolaire Management Update Component', () => {
  let comp: NiveauScolaireUpdateComponent;
  let fixture: ComponentFixture<NiveauScolaireUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let niveauScolaireService: NiveauScolaireService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [NiveauScolaireUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(NiveauScolaireUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NiveauScolaireUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    niveauScolaireService = TestBed.inject(NiveauScolaireService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const niveauScolaire: INiveauScolaire = { id: 456 };

      activatedRoute.data = of({ niveauScolaire });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(niveauScolaire));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NiveauScolaire>>();
      const niveauScolaire = { id: 123 };
      jest.spyOn(niveauScolaireService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ niveauScolaire });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: niveauScolaire }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(niveauScolaireService.update).toHaveBeenCalledWith(niveauScolaire);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NiveauScolaire>>();
      const niveauScolaire = new NiveauScolaire();
      jest.spyOn(niveauScolaireService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ niveauScolaire });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: niveauScolaire }));
      saveSubject.complete();

      // THEN
      expect(niveauScolaireService.create).toHaveBeenCalledWith(niveauScolaire);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NiveauScolaire>>();
      const niveauScolaire = { id: 123 };
      jest.spyOn(niveauScolaireService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ niveauScolaire });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(niveauScolaireService.update).toHaveBeenCalledWith(niveauScolaire);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
