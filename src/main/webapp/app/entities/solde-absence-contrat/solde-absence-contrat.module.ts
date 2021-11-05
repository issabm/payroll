import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SoldeAbsenceContratComponent } from './list/solde-absence-contrat.component';
import { SoldeAbsenceContratDetailComponent } from './detail/solde-absence-contrat-detail.component';
import { SoldeAbsenceContratUpdateComponent } from './update/solde-absence-contrat-update.component';
import { SoldeAbsenceContratDeleteDialogComponent } from './delete/solde-absence-contrat-delete-dialog.component';
import { SoldeAbsenceContratRoutingModule } from './route/solde-absence-contrat-routing.module';

@NgModule({
  imports: [SharedModule, SoldeAbsenceContratRoutingModule],
  declarations: [
    SoldeAbsenceContratComponent,
    SoldeAbsenceContratDetailComponent,
    SoldeAbsenceContratUpdateComponent,
    SoldeAbsenceContratDeleteDialogComponent,
  ],
  entryComponents: [SoldeAbsenceContratDeleteDialogComponent],
})
export class SoldeAbsenceContratModule {}
