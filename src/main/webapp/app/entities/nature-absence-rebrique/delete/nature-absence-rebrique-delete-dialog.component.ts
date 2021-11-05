import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INatureAbsenceRebrique } from '../nature-absence-rebrique.model';
import { NatureAbsenceRebriqueService } from '../service/nature-absence-rebrique.service';

@Component({
  templateUrl: './nature-absence-rebrique-delete-dialog.component.html',
})
export class NatureAbsenceRebriqueDeleteDialogComponent {
  natureAbsenceRebrique?: INatureAbsenceRebrique;

  constructor(protected natureAbsenceRebriqueService: NatureAbsenceRebriqueService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.natureAbsenceRebriqueService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
