import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ManagementResourceProfileComponent } from '../list/management-resource-profile.component';
import { ManagementResourceProfileDetailComponent } from '../detail/management-resource-profile-detail.component';
import { ManagementResourceProfileUpdateComponent } from '../update/management-resource-profile-update.component';
import { ManagementResourceProfileRoutingResolveService } from './management-resource-profile-routing-resolve.service';

const managementResourceProfileRoute: Routes = [
  {
    path: '',
    component: ManagementResourceProfileComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ManagementResourceProfileDetailComponent,
    resolve: {
      managementResourceProfile: ManagementResourceProfileRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ManagementResourceProfileUpdateComponent,
    resolve: {
      managementResourceProfile: ManagementResourceProfileRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ManagementResourceProfileUpdateComponent,
    resolve: {
      managementResourceProfile: ManagementResourceProfileRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(managementResourceProfileRoute)],
  exports: [RouterModule],
})
export class ManagementResourceProfileRoutingModule {}
