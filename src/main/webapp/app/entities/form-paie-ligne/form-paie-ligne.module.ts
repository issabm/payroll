import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FormPaieLigneComponent } from './list/form-paie-ligne.component';
import { FormPaieLigneDetailComponent } from './detail/form-paie-ligne-detail.component';
import { FormPaieLigneUpdateComponent } from './update/form-paie-ligne-update.component';
import { FormPaieLigneDeleteDialogComponent } from './delete/form-paie-ligne-delete-dialog.component';
import { FormPaieLigneRoutingModule } from './route/form-paie-ligne-routing.module';

@NgModule({
  imports: [SharedModule, FormPaieLigneRoutingModule],
  declarations: [FormPaieLigneComponent, FormPaieLigneDetailComponent, FormPaieLigneUpdateComponent, FormPaieLigneDeleteDialogComponent],
  entryComponents: [FormPaieLigneDeleteDialogComponent],
})
export class FormPaieLigneModule {}
