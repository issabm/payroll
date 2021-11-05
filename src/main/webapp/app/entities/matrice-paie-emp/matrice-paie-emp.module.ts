import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MatricePaieEmpComponent } from './list/matrice-paie-emp.component';
import { MatricePaieEmpDetailComponent } from './detail/matrice-paie-emp-detail.component';
import { MatricePaieEmpUpdateComponent } from './update/matrice-paie-emp-update.component';
import { MatricePaieEmpDeleteDialogComponent } from './delete/matrice-paie-emp-delete-dialog.component';
import { MatricePaieEmpRoutingModule } from './route/matrice-paie-emp-routing.module';

@NgModule({
  imports: [SharedModule, MatricePaieEmpRoutingModule],
  declarations: [
    MatricePaieEmpComponent,
    MatricePaieEmpDetailComponent,
    MatricePaieEmpUpdateComponent,
    MatricePaieEmpDeleteDialogComponent,
  ],
  entryComponents: [MatricePaieEmpDeleteDialogComponent],
})
export class MatricePaieEmpModule {}
