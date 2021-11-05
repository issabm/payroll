import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OperatorFormComponent } from './list/operator-form.component';
import { OperatorFormDetailComponent } from './detail/operator-form-detail.component';
import { OperatorFormUpdateComponent } from './update/operator-form-update.component';
import { OperatorFormDeleteDialogComponent } from './delete/operator-form-delete-dialog.component';
import { OperatorFormRoutingModule } from './route/operator-form-routing.module';

@NgModule({
  imports: [SharedModule, OperatorFormRoutingModule],
  declarations: [OperatorFormComponent, OperatorFormDetailComponent, OperatorFormUpdateComponent, OperatorFormDeleteDialogComponent],
  entryComponents: [OperatorFormDeleteDialogComponent],
})
export class OperatorFormModule {}
