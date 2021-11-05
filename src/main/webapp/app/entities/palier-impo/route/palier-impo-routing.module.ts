import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PalierImpoComponent } from '../list/palier-impo.component';
import { PalierImpoDetailComponent } from '../detail/palier-impo-detail.component';
import { PalierImpoUpdateComponent } from '../update/palier-impo-update.component';
import { PalierImpoRoutingResolveService } from './palier-impo-routing-resolve.service';

const palierImpoRoute: Routes = [
  {
    path: '',
    component: PalierImpoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PalierImpoDetailComponent,
    resolve: {
      palierImpo: PalierImpoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PalierImpoUpdateComponent,
    resolve: {
      palierImpo: PalierImpoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PalierImpoUpdateComponent,
    resolve: {
      palierImpo: PalierImpoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(palierImpoRoute)],
  exports: [RouterModule],
})
export class PalierImpoRoutingModule {}
