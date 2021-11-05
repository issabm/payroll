import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NatureAbsenceRebriqueComponent } from './list/nature-absence-rebrique.component';
import { NatureAbsenceRebriqueDetailComponent } from './detail/nature-absence-rebrique-detail.component';
import { NatureAbsenceRebriqueUpdateComponent } from './update/nature-absence-rebrique-update.component';
import { NatureAbsenceRebriqueDeleteDialogComponent } from './delete/nature-absence-rebrique-delete-dialog.component';
import { NatureAbsenceRebriqueRoutingModule } from './route/nature-absence-rebrique-routing.module';

@NgModule({
  imports: [SharedModule, NatureAbsenceRebriqueRoutingModule],
  declarations: [
    NatureAbsenceRebriqueComponent,
    NatureAbsenceRebriqueDetailComponent,
    NatureAbsenceRebriqueUpdateComponent,
    NatureAbsenceRebriqueDeleteDialogComponent,
  ],
  entryComponents: [NatureAbsenceRebriqueDeleteDialogComponent],
})
export class NatureAbsenceRebriqueModule {}
