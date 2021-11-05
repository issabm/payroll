import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FormPaieLigneRebComponent } from './list/form-paie-ligne-reb.component';
import { FormPaieLigneRebDetailComponent } from './detail/form-paie-ligne-reb-detail.component';
import { FormPaieLigneRebUpdateComponent } from './update/form-paie-ligne-reb-update.component';
import { FormPaieLigneRebDeleteDialogComponent } from './delete/form-paie-ligne-reb-delete-dialog.component';
import { FormPaieLigneRebRoutingModule } from './route/form-paie-ligne-reb-routing.module';

@NgModule({
  imports: [SharedModule, FormPaieLigneRebRoutingModule],
  declarations: [
    FormPaieLigneRebComponent,
    FormPaieLigneRebDetailComponent,
    FormPaieLigneRebUpdateComponent,
    FormPaieLigneRebDeleteDialogComponent,
  ],
  entryComponents: [FormPaieLigneRebDeleteDialogComponent],
})
export class FormPaieLigneRebModule {}
