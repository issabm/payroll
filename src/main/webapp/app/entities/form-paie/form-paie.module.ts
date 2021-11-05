import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FormPaieComponent } from './list/form-paie.component';
import { FormPaieDetailComponent } from './detail/form-paie-detail.component';
import { FormPaieUpdateComponent } from './update/form-paie-update.component';
import { FormPaieDeleteDialogComponent } from './delete/form-paie-delete-dialog.component';
import { FormPaieRoutingModule } from './route/form-paie-routing.module';

@NgModule({
  imports: [SharedModule, FormPaieRoutingModule],
  declarations: [FormPaieComponent, FormPaieDetailComponent, FormPaieUpdateComponent, FormPaieDeleteDialogComponent],
  entryComponents: [FormPaieDeleteDialogComponent],
})
export class FormPaieModule {}
