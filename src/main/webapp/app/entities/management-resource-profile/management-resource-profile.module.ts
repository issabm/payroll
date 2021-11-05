import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ManagementResourceProfileComponent } from './list/management-resource-profile.component';
import { ManagementResourceProfileDetailComponent } from './detail/management-resource-profile-detail.component';
import { ManagementResourceProfileUpdateComponent } from './update/management-resource-profile-update.component';
import { ManagementResourceProfileDeleteDialogComponent } from './delete/management-resource-profile-delete-dialog.component';
import { ManagementResourceProfileRoutingModule } from './route/management-resource-profile-routing.module';

@NgModule({
  imports: [SharedModule, ManagementResourceProfileRoutingModule],
  declarations: [
    ManagementResourceProfileComponent,
    ManagementResourceProfileDetailComponent,
    ManagementResourceProfileUpdateComponent,
    ManagementResourceProfileDeleteDialogComponent,
  ],
  entryComponents: [ManagementResourceProfileDeleteDialogComponent],
})
export class ManagementResourceProfileModule {}
