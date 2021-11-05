import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRebriqueContrat } from '../rebrique-contrat.model';
import { RebriqueContratService } from '../service/rebrique-contrat.service';

@Component({
  templateUrl: './rebrique-contrat-delete-dialog.component.html',
})
export class RebriqueContratDeleteDialogComponent {
  rebriqueContrat?: IRebriqueContrat;

  constructor(protected rebriqueContratService: RebriqueContratService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rebriqueContratService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
