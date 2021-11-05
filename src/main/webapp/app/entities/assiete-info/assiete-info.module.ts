import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AssieteInfoComponent } from './list/assiete-info.component';
import { AssieteInfoDetailComponent } from './detail/assiete-info-detail.component';
import { AssieteInfoUpdateComponent } from './update/assiete-info-update.component';
import { AssieteInfoDeleteDialogComponent } from './delete/assiete-info-delete-dialog.component';
import { AssieteInfoRoutingModule } from './route/assiete-info-routing.module';

@NgModule({
  imports: [SharedModule, AssieteInfoRoutingModule],
  declarations: [AssieteInfoComponent, AssieteInfoDetailComponent, AssieteInfoUpdateComponent, AssieteInfoDeleteDialogComponent],
  entryComponents: [AssieteInfoDeleteDialogComponent],
})
export class AssieteInfoModule {}
