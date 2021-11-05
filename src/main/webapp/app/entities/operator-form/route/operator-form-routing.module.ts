import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OperatorFormComponent } from '../list/operator-form.component';
import { OperatorFormDetailComponent } from '../detail/operator-form-detail.component';
import { OperatorFormUpdateComponent } from '../update/operator-form-update.component';
import { OperatorFormRoutingResolveService } from './operator-form-routing-resolve.service';

const operatorFormRoute: Routes = [
  {
    path: '',
    component: OperatorFormComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OperatorFormDetailComponent,
    resolve: {
      operatorForm: OperatorFormRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OperatorFormUpdateComponent,
    resolve: {
      operatorForm: OperatorFormRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OperatorFormUpdateComponent,
    resolve: {
      operatorForm: OperatorFormRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(operatorFormRoute)],
  exports: [RouterModule],
})
export class OperatorFormRoutingModule {}
