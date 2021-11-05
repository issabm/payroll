import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AssieteInfoComponent } from '../list/assiete-info.component';
import { AssieteInfoDetailComponent } from '../detail/assiete-info-detail.component';
import { AssieteInfoUpdateComponent } from '../update/assiete-info-update.component';
import { AssieteInfoRoutingResolveService } from './assiete-info-routing-resolve.service';

const assieteInfoRoute: Routes = [
  {
    path: '',
    component: AssieteInfoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AssieteInfoDetailComponent,
    resolve: {
      assieteInfo: AssieteInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AssieteInfoUpdateComponent,
    resolve: {
      assieteInfo: AssieteInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AssieteInfoUpdateComponent,
    resolve: {
      assieteInfo: AssieteInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(assieteInfoRoute)],
  exports: [RouterModule],
})
export class AssieteInfoRoutingModule {}
