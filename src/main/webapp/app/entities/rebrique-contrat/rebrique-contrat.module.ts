import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RebriqueContratComponent } from './list/rebrique-contrat.component';
import { RebriqueContratDetailComponent } from './detail/rebrique-contrat-detail.component';
import { RebriqueContratUpdateComponent } from './update/rebrique-contrat-update.component';
import { RebriqueContratDeleteDialogComponent } from './delete/rebrique-contrat-delete-dialog.component';
import { RebriqueContratRoutingModule } from './route/rebrique-contrat-routing.module';

@NgModule({
  imports: [SharedModule, RebriqueContratRoutingModule],
  declarations: [
    RebriqueContratComponent,
    RebriqueContratDetailComponent,
    RebriqueContratUpdateComponent,
    RebriqueContratDeleteDialogComponent,
  ],
  entryComponents: [RebriqueContratDeleteDialogComponent],
})
export class RebriqueContratModule {}
