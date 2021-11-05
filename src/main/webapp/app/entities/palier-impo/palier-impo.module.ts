import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PalierImpoComponent } from './list/palier-impo.component';
import { PalierImpoDetailComponent } from './detail/palier-impo-detail.component';
import { PalierImpoUpdateComponent } from './update/palier-impo-update.component';
import { PalierImpoDeleteDialogComponent } from './delete/palier-impo-delete-dialog.component';
import { PalierImpoRoutingModule } from './route/palier-impo-routing.module';

@NgModule({
  imports: [SharedModule, PalierImpoRoutingModule],
  declarations: [PalierImpoComponent, PalierImpoDetailComponent, PalierImpoUpdateComponent, PalierImpoDeleteDialogComponent],
  entryComponents: [PalierImpoDeleteDialogComponent],
})
export class PalierImpoModule {}
