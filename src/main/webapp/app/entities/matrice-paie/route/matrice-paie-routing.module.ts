import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MatricePaieComponent } from '../list/matrice-paie.component';
import { MatricePaieDetailComponent } from '../detail/matrice-paie-detail.component';
import { MatricePaieUpdateComponent } from '../update/matrice-paie-update.component';
import { MatricePaieRoutingResolveService } from './matrice-paie-routing-resolve.service';

const matricePaieRoute: Routes = [
  {
    path: '',
    component: MatricePaieComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MatricePaieDetailComponent,
    resolve: {
      matricePaie: MatricePaieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MatricePaieUpdateComponent,
    resolve: {
      matricePaie: MatricePaieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MatricePaieUpdateComponent,
    resolve: {
      matricePaie: MatricePaieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(matricePaieRoute)],
  exports: [RouterModule],
})
export class MatricePaieRoutingModule {}
