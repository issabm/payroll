import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMatricePaie } from '../matrice-paie.model';
import { MatricePaieService } from '../service/matrice-paie.service';

@Component({
  templateUrl: './matrice-paie-delete-dialog.component.html',
})
export class MatricePaieDeleteDialogComponent {
  matricePaie?: IMatricePaie;

  constructor(protected matricePaieService: MatricePaieService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.matricePaieService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
