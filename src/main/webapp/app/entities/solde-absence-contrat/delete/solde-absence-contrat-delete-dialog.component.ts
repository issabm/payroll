import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISoldeAbsenceContrat } from '../solde-absence-contrat.model';
import { SoldeAbsenceContratService } from '../service/solde-absence-contrat.service';

@Component({
  templateUrl: './solde-absence-contrat-delete-dialog.component.html',
})
export class SoldeAbsenceContratDeleteDialogComponent {
  soldeAbsenceContrat?: ISoldeAbsenceContrat;

  constructor(protected soldeAbsenceContratService: SoldeAbsenceContratService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.soldeAbsenceContratService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
