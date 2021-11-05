import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MatricePaieEmpComponent } from '../list/matrice-paie-emp.component';
import { MatricePaieEmpDetailComponent } from '../detail/matrice-paie-emp-detail.component';
import { MatricePaieEmpUpdateComponent } from '../update/matrice-paie-emp-update.component';
import { MatricePaieEmpRoutingResolveService } from './matrice-paie-emp-routing-resolve.service';

const matricePaieEmpRoute: Routes = [
  {
    path: '',
    component: MatricePaieEmpComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MatricePaieEmpDetailComponent,
    resolve: {
      matricePaieEmp: MatricePaieEmpRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MatricePaieEmpUpdateComponent,
    resolve: {
      matricePaieEmp: MatricePaieEmpRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MatricePaieEmpUpdateComponent,
    resolve: {
      matricePaieEmp: MatricePaieEmpRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(matricePaieEmpRoute)],
  exports: [RouterModule],
})
export class MatricePaieEmpRoutingModule {}
