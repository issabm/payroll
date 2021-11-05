import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'employe',
        data: { pageTitle: 'Employes' },
        loadChildren: () => import('./employe/employe.module').then(m => m.EmployeModule),
      },
      {
        path: 'palier-impo',
        data: { pageTitle: 'PalierImpos' },
        loadChildren: () => import('./palier-impo/palier-impo.module').then(m => m.PalierImpoModule),
      },
      {
        path: 'conjoint',
        data: { pageTitle: 'Conjoints' },
        loadChildren: () => import('./conjoint/conjoint.module').then(m => m.ConjointModule),
      },
      {
        path: 'enfant',
        data: { pageTitle: 'Enfants' },
        loadChildren: () => import('./enfant/enfant.module').then(m => m.EnfantModule),
      },
      {
        path: 'contact',
        data: { pageTitle: 'Contacts' },
        loadChildren: () => import('./contact/contact.module').then(m => m.ContactModule),
      },
      {
        path: 'frequence',
        data: { pageTitle: 'Frequences' },
        loadChildren: () => import('./frequence/frequence.module').then(m => m.FrequenceModule),
      },
      {
        path: 'sens',
        data: { pageTitle: 'Sens' },
        loadChildren: () => import('./sens/sens.module').then(m => m.SensModule),
      },
      {
        path: 'mode-calcul',
        data: { pageTitle: 'ModeCalculs' },
        loadChildren: () => import('./mode-calcul/mode-calcul.module').then(m => m.ModeCalculModule),
      },
      {
        path: 'concerne',
        data: { pageTitle: 'Concernes' },
        loadChildren: () => import('./concerne/concerne.module').then(m => m.ConcerneModule),
      },
      {
        path: 'type-contrat',
        data: { pageTitle: 'TypeContrats' },
        loadChildren: () => import('./type-contrat/type-contrat.module').then(m => m.TypeContratModule),
      },
      {
        path: 'sous-type-contrat',
        data: { pageTitle: 'SousTypeContrats' },
        loadChildren: () => import('./sous-type-contrat/sous-type-contrat.module').then(m => m.SousTypeContratModule),
      },
      {
        path: 'niveau-scolaire',
        data: { pageTitle: 'NiveauScolaires' },
        loadChildren: () => import('./niveau-scolaire/niveau-scolaire.module').then(m => m.NiveauScolaireModule),
      },
      {
        path: 'situation-familiale',
        data: { pageTitle: 'SituationFamiliales' },
        loadChildren: () => import('./situation-familiale/situation-familiale.module').then(m => m.SituationFamilialeModule),
      },
      {
        path: 'type-identite',
        data: { pageTitle: 'TypeIdentites' },
        loadChildren: () => import('./type-identite/type-identite.module').then(m => m.TypeIdentiteModule),
      },
      {
        path: 'identite',
        data: { pageTitle: 'Identites' },
        loadChildren: () => import('./identite/identite.module').then(m => m.IdentiteModule),
      },
      {
        path: 'nature-adhesion',
        data: { pageTitle: 'NatureAdhesions' },
        loadChildren: () => import('./nature-adhesion/nature-adhesion.module').then(m => m.NatureAdhesionModule),
      },
      {
        path: 'situation',
        data: { pageTitle: 'Situations' },
        loadChildren: () => import('./situation/situation.module').then(m => m.SituationModule),
      },
      {
        path: 'sexe',
        data: { pageTitle: 'Sexes' },
        loadChildren: () => import('./sexe/sexe.module').then(m => m.SexeModule),
      },
      {
        path: 'nature-absence',
        data: { pageTitle: 'NatureAbsences' },
        loadChildren: () => import('./nature-absence/nature-absence.module').then(m => m.NatureAbsenceModule),
      },
      {
        path: 'solde-absence',
        data: { pageTitle: 'SoldeAbsences' },
        loadChildren: () => import('./solde-absence/solde-absence.module').then(m => m.SoldeAbsenceModule),
      },
      {
        path: 'solde-absence-contrat',
        data: { pageTitle: 'SoldeAbsenceContrats' },
        loadChildren: () => import('./solde-absence-contrat/solde-absence-contrat.module').then(m => m.SoldeAbsenceContratModule),
      },
      {
        path: 'nature-absence-rebrique',
        data: { pageTitle: 'NatureAbsenceRebriques' },
        loadChildren: () => import('./nature-absence-rebrique/nature-absence-rebrique.module').then(m => m.NatureAbsenceRebriqueModule),
      },
      {
        path: 'emploi',
        data: { pageTitle: 'Emplois' },
        loadChildren: () => import('./emploi/emploi.module').then(m => m.EmploiModule),
      },
      {
        path: 'category',
        data: { pageTitle: 'Categories' },
        loadChildren: () => import('./category/category.module').then(m => m.CategoryModule),
      },
      {
        path: 'echlon',
        data: { pageTitle: 'Echlons' },
        loadChildren: () => import('./echlon/echlon.module').then(m => m.EchlonModule),
      },
      {
        path: 'type-handicap',
        data: { pageTitle: 'TypeHandicaps' },
        loadChildren: () => import('./type-handicap/type-handicap.module').then(m => m.TypeHandicapModule),
      },
      {
        path: 'groupe',
        data: { pageTitle: 'Groupes' },
        loadChildren: () => import('./groupe/groupe.module').then(m => m.GroupeModule),
      },
      {
        path: 'entreprise',
        data: { pageTitle: 'Entreprises' },
        loadChildren: () => import('./entreprise/entreprise.module').then(m => m.EntrepriseModule),
      },
      {
        path: 'affiliation',
        data: { pageTitle: 'Affiliations' },
        loadChildren: () => import('./affiliation/affiliation.module').then(m => m.AffiliationModule),
      },
      {
        path: 'devise',
        data: { pageTitle: 'Devises' },
        loadChildren: () => import('./devise/devise.module').then(m => m.DeviseModule),
      },
      {
        path: 'carriere',
        data: { pageTitle: 'Carrieres' },
        loadChildren: () => import('./carriere/carriere.module').then(m => m.CarriereModule),
      },
      {
        path: 'contrat',
        data: { pageTitle: 'Contrats' },
        loadChildren: () => import('./contrat/contrat.module').then(m => m.ContratModule),
      },
      {
        path: 'adhesion',
        data: { pageTitle: 'Adhesions' },
        loadChildren: () => import('./adhesion/adhesion.module').then(m => m.AdhesionModule),
      },
      {
        path: 'entity-adhesion',
        data: { pageTitle: 'EntityAdhesions' },
        loadChildren: () => import('./entity-adhesion/entity-adhesion.module').then(m => m.EntityAdhesionModule),
      },
      {
        path: 'affectation',
        data: { pageTitle: 'Affectations' },
        loadChildren: () => import('./affectation/affectation.module').then(m => m.AffectationModule),
      },
      {
        path: 'pays',
        data: { pageTitle: 'Pays' },
        loadChildren: () => import('./pays/pays.module').then(m => m.PaysModule),
      },
      {
        path: 'matrice-paie',
        data: { pageTitle: 'MatricePaies' },
        loadChildren: () => import('./matrice-paie/matrice-paie.module').then(m => m.MatricePaieModule),
      },
      {
        path: 'matrice-paie-emp',
        data: { pageTitle: 'MatricePaieEmps' },
        loadChildren: () => import('./matrice-paie-emp/matrice-paie-emp.module').then(m => m.MatricePaieEmpModule),
      },
      {
        path: 'famille',
        data: { pageTitle: 'Familles' },
        loadChildren: () => import('./famille/famille.module').then(m => m.FamilleModule),
      },
      {
        path: 'rebrique',
        data: { pageTitle: 'Rebriques' },
        loadChildren: () => import('./rebrique/rebrique.module').then(m => m.RebriqueModule),
      },
      {
        path: 'assiete',
        data: { pageTitle: 'Assietes' },
        loadChildren: () => import('./assiete/assiete.module').then(m => m.AssieteModule),
      },
      {
        path: 'rebrique-contrat',
        data: { pageTitle: 'RebriqueContrats' },
        loadChildren: () => import('./rebrique-contrat/rebrique-contrat.module').then(m => m.RebriqueContratModule),
      },
      {
        path: 'assiete-info',
        data: { pageTitle: 'AssieteInfos' },
        loadChildren: () => import('./assiete-info/assiete-info.module').then(m => m.AssieteInfoModule),
      },
      {
        path: 'solde-absence-paie',
        data: { pageTitle: 'SoldeAbsencePaies' },
        loadChildren: () => import('./solde-absence-paie/solde-absence-paie.module').then(m => m.SoldeAbsencePaieModule),
      },
      {
        path: 'payroll-info',
        data: { pageTitle: 'PayrollInfos' },
        loadChildren: () => import('./payroll-info/payroll-info.module').then(m => m.PayrollInfoModule),
      },
      {
        path: 'management-resource',
        data: { pageTitle: 'ManagementResources' },
        loadChildren: () => import('./management-resource/management-resource.module').then(m => m.ManagementResourceModule),
      },
      {
        path: 'form-paie',
        data: { pageTitle: 'FormPaies' },
        loadChildren: () => import('./form-paie/form-paie.module').then(m => m.FormPaieModule),
      },
      {
        path: 'operator-form',
        data: { pageTitle: 'OperatorForms' },
        loadChildren: () => import('./operator-form/operator-form.module').then(m => m.OperatorFormModule),
      },
      {
        path: 'form-paie-ligne',
        data: { pageTitle: 'FormPaieLignes' },
        loadChildren: () => import('./form-paie-ligne/form-paie-ligne.module').then(m => m.FormPaieLigneModule),
      },
      {
        path: 'form-paie-ligne-reb',
        data: { pageTitle: 'FormPaieLigneRebs' },
        loadChildren: () => import('./form-paie-ligne-reb/form-paie-ligne-reb.module').then(m => m.FormPaieLigneRebModule),
      },
      {
        path: 'management-resource-profile',
        data: { pageTitle: 'ManagementResourceProfiles' },
        loadChildren: () =>
          import('./management-resource-profile/management-resource-profile.module').then(m => m.ManagementResourceProfileModule),
      },
      {
        path: 'payroll',
        data: { pageTitle: 'Payrolls' },
        loadChildren: () => import('./payroll/payroll.module').then(m => m.PayrollModule),
      },
      {
        path: 'mouvement-paie',
        data: { pageTitle: 'MouvementPaies' },
        loadChildren: () => import('./mouvement-paie/mouvement-paie.module').then(m => m.MouvementPaieModule),
      },
      {
        path: 'user-log',
        data: { pageTitle: 'UserLogs' },
        loadChildren: () => import('./user-log/user-log.module').then(m => m.UserLogModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
