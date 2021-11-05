import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FormPaieLigneRebComponent } from '../list/form-paie-ligne-reb.component';
import { FormPaieLigneRebDetailComponent } from '../detail/form-paie-ligne-reb-detail.component';
import { FormPaieLigneRebUpdateComponent } from '../update/form-paie-ligne-reb-update.component';
import { FormPaieLigneRebRoutingResolveService } from './form-paie-ligne-reb-routing-resolve.service';

const formPaieLigneRebRoute: Routes = [
  {
    path: '',
    component: FormPaieLigneRebComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FormPaieLigneRebDetailComponent,
    resolve: {
      formPaieLigneReb: FormPaieLigneRebRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FormPaieLigneRebUpdateComponent,
    resolve: {
      formPaieLigneReb: FormPaieLigneRebRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FormPaieLigneRebUpdateComponent,
    resolve: {
      formPaieLigneReb: FormPaieLigneRebRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(formPaieLigneRebRoute)],
  exports: [RouterModule],
})
export class FormPaieLigneRebRoutingModule {}
