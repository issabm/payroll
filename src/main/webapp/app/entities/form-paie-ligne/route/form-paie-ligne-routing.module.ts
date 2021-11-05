import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FormPaieLigneComponent } from '../list/form-paie-ligne.component';
import { FormPaieLigneDetailComponent } from '../detail/form-paie-ligne-detail.component';
import { FormPaieLigneUpdateComponent } from '../update/form-paie-ligne-update.component';
import { FormPaieLigneRoutingResolveService } from './form-paie-ligne-routing-resolve.service';

const formPaieLigneRoute: Routes = [
  {
    path: '',
    component: FormPaieLigneComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FormPaieLigneDetailComponent,
    resolve: {
      formPaieLigne: FormPaieLigneRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FormPaieLigneUpdateComponent,
    resolve: {
      formPaieLigne: FormPaieLigneRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FormPaieLigneUpdateComponent,
    resolve: {
      formPaieLigne: FormPaieLigneRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(formPaieLigneRoute)],
  exports: [RouterModule],
})
export class FormPaieLigneRoutingModule {}
