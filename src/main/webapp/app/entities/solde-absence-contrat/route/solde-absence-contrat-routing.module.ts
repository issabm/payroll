import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SoldeAbsenceContratComponent } from '../list/solde-absence-contrat.component';
import { SoldeAbsenceContratDetailComponent } from '../detail/solde-absence-contrat-detail.component';
import { SoldeAbsenceContratUpdateComponent } from '../update/solde-absence-contrat-update.component';
import { SoldeAbsenceContratRoutingResolveService } from './solde-absence-contrat-routing-resolve.service';

const soldeAbsenceContratRoute: Routes = [
  {
    path: '',
    component: SoldeAbsenceContratComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SoldeAbsenceContratDetailComponent,
    resolve: {
      soldeAbsenceContrat: SoldeAbsenceContratRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SoldeAbsenceContratUpdateComponent,
    resolve: {
      soldeAbsenceContrat: SoldeAbsenceContratRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SoldeAbsenceContratUpdateComponent,
    resolve: {
      soldeAbsenceContrat: SoldeAbsenceContratRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(soldeAbsenceContratRoute)],
  exports: [RouterModule],
})
export class SoldeAbsenceContratRoutingModule {}
