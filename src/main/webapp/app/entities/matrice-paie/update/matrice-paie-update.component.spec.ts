jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { MatricePaieService } from '../service/matrice-paie.service';
import { IMatricePaie, MatricePaie } from '../matrice-paie.model';
import { IAssiete } from 'app/entities/assiete/assiete.model';
import { AssieteService } from 'app/entities/assiete/service/assiete.service';
import { IEchlon } from 'app/entities/echlon/echlon.model';
import { EchlonService } from 'app/entities/echlon/service/echlon.service';
import { IEmploi } from 'app/entities/emploi/emploi.model';
import { EmploiService } from 'app/entities/emploi/service/emploi.service';
import { ICategory } from 'app/entities/category/category.model';
import { CategoryService } from 'app/entities/category/service/category.service';
import { IAffiliation } from 'app/entities/affiliation/affiliation.model';
import { AffiliationService } from 'app/entities/affiliation/service/affiliation.service';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { EntrepriseService } from 'app/entities/entreprise/service/entreprise.service';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { GroupeService } from 'app/entities/groupe/service/groupe.service';
import { ISexe } from 'app/entities/sexe/sexe.model';
import { SexeService } from 'app/entities/sexe/service/sexe.service';

import { MatricePaieUpdateComponent } from './matrice-paie-update.component';

describe('MatricePaie Management Update Component', () => {
  let comp: MatricePaieUpdateComponent;
  let fixture: ComponentFixture<MatricePaieUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let matricePaieService: MatricePaieService;
  let assieteService: AssieteService;
  let echlonService: EchlonService;
  let emploiService: EmploiService;
  let categoryService: CategoryService;
  let affiliationService: AffiliationService;
  let entrepriseService: EntrepriseService;
  let groupeService: GroupeService;
  let sexeService: SexeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [MatricePaieUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(MatricePaieUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MatricePaieUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    matricePaieService = TestBed.inject(MatricePaieService);
    assieteService = TestBed.inject(AssieteService);
    echlonService = TestBed.inject(EchlonService);
    emploiService = TestBed.inject(EmploiService);
    categoryService = TestBed.inject(CategoryService);
    affiliationService = TestBed.inject(AffiliationService);
    entrepriseService = TestBed.inject(EntrepriseService);
    groupeService = TestBed.inject(GroupeService);
    sexeService = TestBed.inject(SexeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Assiete query and add missing value', () => {
      const matricePaie: IMatricePaie = { id: 456 };
      const assiete: IAssiete = { id: 16694 };
      matricePaie.assiete = assiete;

      const assieteCollection: IAssiete[] = [{ id: 58219 }];
      jest.spyOn(assieteService, 'query').mockReturnValue(of(new HttpResponse({ body: assieteCollection })));
      const additionalAssietes = [assiete];
      const expectedCollection: IAssiete[] = [...additionalAssietes, ...assieteCollection];
      jest.spyOn(assieteService, 'addAssieteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ matricePaie });
      comp.ngOnInit();

      expect(assieteService.query).toHaveBeenCalled();
      expect(assieteService.addAssieteToCollectionIfMissing).toHaveBeenCalledWith(assieteCollection, ...additionalAssietes);
      expect(comp.assietesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Echlon query and add missing value', () => {
      const matricePaie: IMatricePaie = { id: 456 };
      const echlon: IEchlon = { id: 51034 };
      matricePaie.echlon = echlon;

      const echlonCollection: IEchlon[] = [{ id: 61672 }];
      jest.spyOn(echlonService, 'query').mockReturnValue(of(new HttpResponse({ body: echlonCollection })));
      const additionalEchlons = [echlon];
      const expectedCollection: IEchlon[] = [...additionalEchlons, ...echlonCollection];
      jest.spyOn(echlonService, 'addEchlonToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ matricePaie });
      comp.ngOnInit();

      expect(echlonService.query).toHaveBeenCalled();
      expect(echlonService.addEchlonToCollectionIfMissing).toHaveBeenCalledWith(echlonCollection, ...additionalEchlons);
      expect(comp.echlonsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Emploi query and add missing value', () => {
      const matricePaie: IMatricePaie = { id: 456 };
      const emploi: IEmploi = { id: 79113 };
      matricePaie.emploi = emploi;

      const emploiCollection: IEmploi[] = [{ id: 20715 }];
      jest.spyOn(emploiService, 'query').mockReturnValue(of(new HttpResponse({ body: emploiCollection })));
      const additionalEmplois = [emploi];
      const expectedCollection: IEmploi[] = [...additionalEmplois, ...emploiCollection];
      jest.spyOn(emploiService, 'addEmploiToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ matricePaie });
      comp.ngOnInit();

      expect(emploiService.query).toHaveBeenCalled();
      expect(emploiService.addEmploiToCollectionIfMissing).toHaveBeenCalledWith(emploiCollection, ...additionalEmplois);
      expect(comp.emploisSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Category query and add missing value', () => {
      const matricePaie: IMatricePaie = { id: 456 };
      const category: ICategory = { id: 33032 };
      matricePaie.category = category;

      const categoryCollection: ICategory[] = [{ id: 74893 }];
      jest.spyOn(categoryService, 'query').mockReturnValue(of(new HttpResponse({ body: categoryCollection })));
      const additionalCategories = [category];
      const expectedCollection: ICategory[] = [...additionalCategories, ...categoryCollection];
      jest.spyOn(categoryService, 'addCategoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ matricePaie });
      comp.ngOnInit();

      expect(categoryService.query).toHaveBeenCalled();
      expect(categoryService.addCategoryToCollectionIfMissing).toHaveBeenCalledWith(categoryCollection, ...additionalCategories);
      expect(comp.categoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Affiliation query and add missing value', () => {
      const matricePaie: IMatricePaie = { id: 456 };
      const affilication: IAffiliation = { id: 55706 };
      matricePaie.affilication = affilication;

      const affiliationCollection: IAffiliation[] = [{ id: 1573 }];
      jest.spyOn(affiliationService, 'query').mockReturnValue(of(new HttpResponse({ body: affiliationCollection })));
      const additionalAffiliations = [affilication];
      const expectedCollection: IAffiliation[] = [...additionalAffiliations, ...affiliationCollection];
      jest.spyOn(affiliationService, 'addAffiliationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ matricePaie });
      comp.ngOnInit();

      expect(affiliationService.query).toHaveBeenCalled();
      expect(affiliationService.addAffiliationToCollectionIfMissing).toHaveBeenCalledWith(affiliationCollection, ...additionalAffiliations);
      expect(comp.affiliationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Entreprise query and add missing value', () => {
      const matricePaie: IMatricePaie = { id: 456 };
      const entreprise: IEntreprise = { id: 54283 };
      matricePaie.entreprise = entreprise;

      const entrepriseCollection: IEntreprise[] = [{ id: 47777 }];
      jest.spyOn(entrepriseService, 'query').mockReturnValue(of(new HttpResponse({ body: entrepriseCollection })));
      const additionalEntreprises = [entreprise];
      const expectedCollection: IEntreprise[] = [...additionalEntreprises, ...entrepriseCollection];
      jest.spyOn(entrepriseService, 'addEntrepriseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ matricePaie });
      comp.ngOnInit();

      expect(entrepriseService.query).toHaveBeenCalled();
      expect(entrepriseService.addEntrepriseToCollectionIfMissing).toHaveBeenCalledWith(entrepriseCollection, ...additionalEntreprises);
      expect(comp.entreprisesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Groupe query and add missing value', () => {
      const matricePaie: IMatricePaie = { id: 456 };
      const groupe: IGroupe = { id: 39325 };
      matricePaie.groupe = groupe;

      const groupeCollection: IGroupe[] = [{ id: 78690 }];
      jest.spyOn(groupeService, 'query').mockReturnValue(of(new HttpResponse({ body: groupeCollection })));
      const additionalGroupes = [groupe];
      const expectedCollection: IGroupe[] = [...additionalGroupes, ...groupeCollection];
      jest.spyOn(groupeService, 'addGroupeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ matricePaie });
      comp.ngOnInit();

      expect(groupeService.query).toHaveBeenCalled();
      expect(groupeService.addGroupeToCollectionIfMissing).toHaveBeenCalledWith(groupeCollection, ...additionalGroupes);
      expect(comp.groupesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Sexe query and add missing value', () => {
      const matricePaie: IMatricePaie = { id: 456 };
      const sexe: ISexe = { id: 85197 };
      matricePaie.sexe = sexe;

      const sexeCollection: ISexe[] = [{ id: 30672 }];
      jest.spyOn(sexeService, 'query').mockReturnValue(of(new HttpResponse({ body: sexeCollection })));
      const additionalSexes = [sexe];
      const expectedCollection: ISexe[] = [...additionalSexes, ...sexeCollection];
      jest.spyOn(sexeService, 'addSexeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ matricePaie });
      comp.ngOnInit();

      expect(sexeService.query).toHaveBeenCalled();
      expect(sexeService.addSexeToCollectionIfMissing).toHaveBeenCalledWith(sexeCollection, ...additionalSexes);
      expect(comp.sexesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const matricePaie: IMatricePaie = { id: 456 };
      const assiete: IAssiete = { id: 58223 };
      matricePaie.assiete = assiete;
      const echlon: IEchlon = { id: 87848 };
      matricePaie.echlon = echlon;
      const emploi: IEmploi = { id: 10374 };
      matricePaie.emploi = emploi;
      const category: ICategory = { id: 95906 };
      matricePaie.category = category;
      const affilication: IAffiliation = { id: 1537 };
      matricePaie.affilication = affilication;
      const entreprise: IEntreprise = { id: 60617 };
      matricePaie.entreprise = entreprise;
      const groupe: IGroupe = { id: 71651 };
      matricePaie.groupe = groupe;
      const sexe: ISexe = { id: 95181 };
      matricePaie.sexe = sexe;

      activatedRoute.data = of({ matricePaie });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(matricePaie));
      expect(comp.assietesSharedCollection).toContain(assiete);
      expect(comp.echlonsSharedCollection).toContain(echlon);
      expect(comp.emploisSharedCollection).toContain(emploi);
      expect(comp.categoriesSharedCollection).toContain(category);
      expect(comp.affiliationsSharedCollection).toContain(affilication);
      expect(comp.entreprisesSharedCollection).toContain(entreprise);
      expect(comp.groupesSharedCollection).toContain(groupe);
      expect(comp.sexesSharedCollection).toContain(sexe);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MatricePaie>>();
      const matricePaie = { id: 123 };
      jest.spyOn(matricePaieService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ matricePaie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: matricePaie }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(matricePaieService.update).toHaveBeenCalledWith(matricePaie);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MatricePaie>>();
      const matricePaie = new MatricePaie();
      jest.spyOn(matricePaieService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ matricePaie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: matricePaie }));
      saveSubject.complete();

      // THEN
      expect(matricePaieService.create).toHaveBeenCalledWith(matricePaie);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MatricePaie>>();
      const matricePaie = { id: 123 };
      jest.spyOn(matricePaieService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ matricePaie });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(matricePaieService.update).toHaveBeenCalledWith(matricePaie);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackAssieteById', () => {
      it('Should return tracked Assiete primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAssieteById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackEchlonById', () => {
      it('Should return tracked Echlon primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEchlonById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackEmploiById', () => {
      it('Should return tracked Emploi primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEmploiById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackCategoryById', () => {
      it('Should return tracked Category primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCategoryById(0, entity);
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

    describe('trackEntrepriseById', () => {
      it('Should return tracked Entreprise primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEntrepriseById(0, entity);
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

    describe('trackSexeById', () => {
      it('Should return tracked Sexe primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSexeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
