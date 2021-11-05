import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AssieteComponent } from './list/assiete.component';
import { AssieteDetailComponent } from './detail/assiete-detail.component';
import { AssieteUpdateComponent } from './update/assiete-update.component';
import { AssieteDeleteDialogComponent } from './delete/assiete-delete-dialog.component';
import { AssieteRoutingModule } from './route/assiete-routing.module';

@NgModule({
  imports: [SharedModule, AssieteRoutingModule],
  declarations: [AssieteComponent, AssieteDetailComponent, AssieteUpdateComponent, AssieteDeleteDialogComponent],
  entryComponents: [AssieteDeleteDialogComponent],
})
export class AssieteModule {}
