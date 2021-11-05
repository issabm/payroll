import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NatureAbsenceRebriqueComponent } from '../list/nature-absence-rebrique.component';
import { NatureAbsenceRebriqueDetailComponent } from '../detail/nature-absence-rebrique-detail.component';
import { NatureAbsenceRebriqueUpdateComponent } from '../update/nature-absence-rebrique-update.component';
import { NatureAbsenceRebriqueRoutingResolveService } from './nature-absence-rebrique-routing-resolve.service';

const natureAbsenceRebriqueRoute: Routes = [
  {
    path: '',
    component: NatureAbsenceRebriqueComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NatureAbsenceRebriqueDetailComponent,
    resolve: {
      natureAbsenceRebrique: NatureAbsenceRebriqueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NatureAbsenceRebriqueUpdateComponent,
    resolve: {
      natureAbsenceRebrique: NatureAbsenceRebriqueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NatureAbsenceRebriqueUpdateComponent,
    resolve: {
      natureAbsenceRebrique: NatureAbsenceRebriqueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(natureAbsenceRebriqueRoute)],
  exports: [RouterModule],
})
export class NatureAbsenceRebriqueRoutingModule {}
