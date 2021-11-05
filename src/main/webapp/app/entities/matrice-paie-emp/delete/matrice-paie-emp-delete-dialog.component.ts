import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMatricePaieEmp } from '../matrice-paie-emp.model';
import { MatricePaieEmpService } from '../service/matrice-paie-emp.service';

@Component({
  templateUrl: './matrice-paie-emp-delete-dialog.component.html',
})
export class MatricePaieEmpDeleteDialogComponent {
  matricePaieEmp?: IMatricePaieEmp;

  constructor(protected matricePaieEmpService: MatricePaieEmpService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.matricePaieEmpService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
