import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AssieteComponent } from '../list/assiete.component';
import { AssieteDetailComponent } from '../detail/assiete-detail.component';
import { AssieteUpdateComponent } from '../update/assiete-update.component';
import { AssieteRoutingResolveService } from './assiete-routing-resolve.service';

const assieteRoute: Routes = [
  {
    path: '',
    component: AssieteComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AssieteDetailComponent,
    resolve: {
      assiete: AssieteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AssieteUpdateComponent,
    resolve: {
      assiete: AssieteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AssieteUpdateComponent,
    resolve: {
      assiete: AssieteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(assieteRoute)],
  exports: [RouterModule],
})
export class AssieteRoutingModule {}
