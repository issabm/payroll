import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RebriqueContratComponent } from '../list/rebrique-contrat.component';
import { RebriqueContratDetailComponent } from '../detail/rebrique-contrat-detail.component';
import { RebriqueContratUpdateComponent } from '../update/rebrique-contrat-update.component';
import { RebriqueContratRoutingResolveService } from './rebrique-contrat-routing-resolve.service';

const rebriqueContratRoute: Routes = [
  {
    path: '',
    component: RebriqueContratComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RebriqueContratDetailComponent,
    resolve: {
      rebriqueContrat: RebriqueContratRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RebriqueContratUpdateComponent,
    resolve: {
      rebriqueContrat: RebriqueContratRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RebriqueContratUpdateComponent,
    resolve: {
      rebriqueContrat: RebriqueContratRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(rebriqueContratRoute)],
  exports: [RouterModule],
})
export class RebriqueContratRoutingModule {}
