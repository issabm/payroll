import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MatricePaieComponent } from './list/matrice-paie.component';
import { MatricePaieDetailComponent } from './detail/matrice-paie-detail.component';
import { MatricePaieUpdateComponent } from './update/matrice-paie-update.component';
import { MatricePaieDeleteDialogComponent } from './delete/matrice-paie-delete-dialog.component';
import { MatricePaieRoutingModule } from './route/matrice-paie-routing.module';

@NgModule({
  imports: [SharedModule, MatricePaieRoutingModule],
  declarations: [MatricePaieComponent, MatricePaieDetailComponent, MatricePaieUpdateComponent, MatricePaieDeleteDialogComponent],
  entryComponents: [MatricePaieDeleteDialogComponent],
})
export class MatricePaieModule {}
