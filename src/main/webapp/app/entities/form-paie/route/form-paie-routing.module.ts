import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FormPaieComponent } from '../list/form-paie.component';
import { FormPaieDetailComponent } from '../detail/form-paie-detail.component';
import { FormPaieUpdateComponent } from '../update/form-paie-update.component';
import { FormPaieRoutingResolveService } from './form-paie-routing-resolve.service';

const formPaieRoute: Routes = [
  {
    path: '',
    component: FormPaieComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FormPaieDetailComponent,
    resolve: {
      formPaie: FormPaieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FormPaieUpdateComponent,
    resolve: {
      formPaie: FormPaieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FormPaieUpdateComponent,
    resolve: {
      formPaie: FormPaieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(formPaieRoute)],
  exports: [RouterModule],
})
export class FormPaieRoutingModule {}
