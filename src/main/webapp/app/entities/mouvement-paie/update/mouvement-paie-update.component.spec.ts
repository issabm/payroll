jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { MouvementPaieService } from '../service/mouvement-paie.service';
import { IMouvementPaie, MouvementPaie } from '../mouvement-paie.model';
import { ISituation } from 'app/entities/situation/situation.model';
import { SituationService } from 'app/entities/situation/service/situation.service';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { GroupeService } from 'app/entities/groupe/service/groupe.service';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { EntrepriseService } from 'app/entities/entreprise/service/entreprise.service';
import { IAffiliation } from 'app/entities/affiliation/affiliation.model';
import { AffiliationService } from 'app/entities/affiliation/service/affiliation.service';

import { MouvementPaieUpdateComponent } from './mouvement-paie-update.component';

describe('MouvementPaie Management Update Component', () => {
  let comp: MouvementPaieUpdateComponent;
  let fixture: ComponentFixture<MouvementPaieUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let mouvementPaieService: MouvementPaieService;
  let situationService: SituationService;
  let groupeService: GroupeService;
  let entrepriseService: EntrepriseService;
  let affiliationService: AffiliationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [MouvementPaieUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(MouvementPaieUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MouvementPaieUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    mouvementPaieService = TestBed.inject(MouvementPaieService);
    situationService = TestBed.inject(SituationService);
    groupeService = TestBed.inject(GroupeService);
    entrepriseService = TestBed.inject(EntrepriseService);
    affiliationService = TestBed.inject(AffiliationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Situation query and add missing value', () => {
      const mouvementPaie: IMouvementPaie = { id: 456 };
      const situation: ISituation = { id: 75925 };
      mouvementPaie.situation = situation;

      const situationCollection: ISituation[] = [{ id: 97910 }];
      jest.spyOn(situationService, 'query').mockReturnValue(of(new HttpResponse({ body: situationCollection })));
      const additionalSituations = [situation];
      const expectedCollection: ISituation[] = [...additionalSituations, ...situationCollection];
      jest.spyOn(situationService, 'addSituationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mouvementPaie });
      comp.ngOnInit();

      expect(situationService.query).toHaveBeenCalled();
      expect(situationService.addSituationToCollectionIfMissing).toHaveBeenCalledWith(situationCollection, ...additionalSituations);
      expect(comp.situationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Groupe query and add missing value', () => {
      const mouvementPaie: IMouvementPaie = { id: 456 };
      const groupe: IGroupe = { id: 67434 };
      mouvementPaie.groupe = groupe;

      const groupeCollection: IGroupe[] = [{ id: 15430 }];
      jest.spyOn(groupeService, 'query').mockReturnValue(of(new HttpResponse({ body: groupeCollection })));
      const additionalGroupes = [groupe];
      const expectedCollection: IGroupe[] = [...additionalGroupes, ...groupeCollection];
      jest.spyOn(groupeService, 'addGroupeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mouvementPaie });
      comp.ngOnInit();

      expect(groupeService.query).toHaveBeenCalled();
      expect(groupeService.addGroupeToCollectionIfMissing).toHaveBeenCalledWith(groupeCollection, ...additionalGroupes);
      expect(comp.groupesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Entreprise query and add missing value', () => {
      const mouvementPaie: IMouvementPaie = { id: 456 };
      const entreprise: IEntreprise = { id: 71183 };
      mouvementPaie.entreprise = entreprise;

      const entrepriseCollection: IEntreprise[] = [{ id: 39355 }];
      jest.spyOn(entrepriseService, 'query').mockReturnValue(of(new HttpResponse({ body: entrepriseCollection })));
      const additionalEntreprises = [entreprise];
      const expectedCollection: IEntreprise[] = [...additionalEntreprises, ...entrepriseCollection];
      jest.spyOn(entrepriseService, 'addEntrepriseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mouvementPaie });
      comp.ngOnInit();

      expect(entrepriseService.query).toHaveBeenCalled();
      expect(entrepriseService.addEntrepriseToCollectionIfMissing).toHaveBeenCalledWith(entrepriseCollection, ...additionalEntreprises);
      expect(comp.entreprisesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Affiliation query and add missing value', () => {
      const mouvementPaie: IMouvementPaie = { id: 456 };
      const affiliation: IAffiliation = { id: 3829 };
      mouvementPaie.affiliation = affiliation;

      const affiliationCollection: IAffiliation[] = [{ id: 17132 }];
      jest.spyOn(affiliationService, 'query').mockReturnValue(of(new HttpResponse({ body: affiliationCollection })));
      const additionalAffiliations = [affiliation];
      const expectedCollection: IAffiliation[] = [...additionalAffiliations, ...affiliationCollection];
      jest.spyOn(affiliationService, 'addAffiliationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mouvementPaie });
      comp.ngOnInit();

      expect(affiliationService.query).toHaveBeenCalled();
      expect(affiliationService.addAffiliationToCollectionIfMissing).toHaveBeenCalledWith(affiliationCollection, ...additionalAffiliations);
      expect(comp.affiliationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const mouvementPaie: IMouvementPaie = { id: 456 };
      const situation: ISituation = { id: 7289 };
      mouvementPaie.situation = situation;
      const groupe: IGroupe = { id: 34511 };
      mouvementPaie.groupe = groupe;
      const entreprise: IEntreprise = { id: 59291 };
      mouvementPaie.entreprise = entreprise;
      const affiliation: IAffiliation = { id: 14131 };
      mouvementPaie.affiliation = affiliation;

      activatedRoute.data = of({ mouvementPaie });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(mouvementPaie));
      expect(comp.situationsSharedCollection).toContain(situation);
      expect(comp.groupesSharedCollection).toContain(groupe);
      expect(comp.entreprisesSharedCollection).toContain(entreprise);
      expect(comp.affiliationsSharedCollection).toContain(affiliation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MouvementPaie>>();
      const mouvementPaie = { id: 123 };
      jest.spyOn(mouvementPaieService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mouvementPaie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mouvementPaie }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(mouvementPaieService.update).toHaveBeenCalledWith(mouvementPaie);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MouvementPaie>>();
      const mouvementPaie = new MouvementPaie();
      jest.spyOn(mouvementPaieService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mouvementPaie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mouvementPaie }));
      saveSubject.complete();

      // THEN
      expect(mouvementPaieService.create).toHaveBeenCalledWith(mouvementPaie);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MouvementPaie>>();
      const mouvementPaie = { id: 123 };
      jest.spyOn(mouvementPaieService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mouvementPaie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(mouvementPaieService.update).toHaveBeenCalledWith(mouvementPaie);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSituationById', () => {
      it('Should return tracked Situation primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSituationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackGroupeById', () => {
      it('Should return tracked Groupe primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackGroupeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackEntrepriseById', () => {
      it('Should return tracked Entreprise primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEntrepriseById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackAffiliationById', () => {
      it('Should return tracked Affiliation primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAffiliationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
