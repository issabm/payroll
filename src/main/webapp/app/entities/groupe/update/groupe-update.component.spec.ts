jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { GroupeService } from '../service/groupe.service';
import { IGroupe, Groupe } from '../groupe.model';
import { IPays } from 'app/entities/pays/pays.model';
import { PaysService } from 'app/entities/pays/service/pays.service';
import { IDevise } from 'app/entities/devise/devise.model';
import { DeviseService } from 'app/entities/devise/service/devise.service';

import { GroupeUpdateComponent } from './groupe-update.component';

describe('Groupe Management Update Component', () => {
  let comp: GroupeUpdateComponent;
  let fixture: ComponentFixture<GroupeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let groupeService: GroupeService;
  let paysService: PaysService;
  let deviseService: DeviseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [GroupeUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(GroupeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GroupeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    groupeService = TestBed.inject(GroupeService);
    paysService = TestBed.inject(PaysService);
    deviseService = TestBed.inject(DeviseService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pays query and add missing value', () => {
      const groupe: IGroupe = { id: 456 };
      const pays: IPays = { id: 56672 };
      groupe.pays = pays;

      const paysCollection: IPays[] = [{ id: 57133 }];
      jest.spyOn(paysService, 'query').mockReturnValue(of(new HttpResponse({ body: paysCollection })));
      const additionalPays = [pays];
      const expectedCollection: IPays[] = [...additionalPays, ...paysCollection];
      jest.spyOn(paysService, 'addPaysToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ groupe });
      comp.ngOnInit();

      expect(paysService.query).toHaveBeenCalled();
      expect(paysService.addPaysToCollectionIfMissing).toHaveBeenCalledWith(paysCollection, ...additionalPays);
      expect(comp.paysSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Devise query and add missing value', () => {
      const groupe: IGroupe = { id: 456 };
      const devise: IDevise = { id: 92466 };
      groupe.devise = devise;

      const deviseCollection: IDevise[] = [{ id: 33470 }];
      jest.spyOn(deviseService, 'query').mockReturnValue(of(new HttpResponse({ body: deviseCollection })));
      const additionalDevises = [devise];
      const expectedCollection: IDevise[] = [...additionalDevises, ...deviseCollection];
      jest.spyOn(deviseService, 'addDeviseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ groupe });
      comp.ngOnInit();

      expect(deviseService.query).toHaveBeenCalled();
      expect(deviseService.addDeviseToCollectionIfMissing).toHaveBeenCalledWith(deviseCollection, ...additionalDevises);
      expect(comp.devisesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const groupe: IGroupe = { id: 456 };
      const pays: IPays = { id: 40030 };
      groupe.pays = pays;
      const devise: IDevise = { id: 69765 };
      groupe.devise = devise;

      activatedRoute.data = of({ groupe });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(groupe));
      expect(comp.paysSharedCollection).toContain(pays);
      expect(comp.devisesSharedCollection).toContain(devise);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Groupe>>();
      const groupe = { id: 123 };
      jest.spyOn(groupeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ groupe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: groupe }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(groupeService.update).toHaveBeenCalledWith(groupe);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Groupe>>();
      const groupe = new Groupe();
      jest.spyOn(groupeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ groupe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: groupe }));
      saveSubject.complete();

      // THEN
      expect(groupeService.create).toHaveBeenCalledWith(groupe);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Groupe>>();
      const groupe = { id: 123 };
      jest.spyOn(groupeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ groupe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(groupeService.update).toHaveBeenCalledWith(groupe);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPaysById', () => {
      it('Should return tracked Pays primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPaysById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackDeviseById', () => {
      it('Should return tracked Devise primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDeviseById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
