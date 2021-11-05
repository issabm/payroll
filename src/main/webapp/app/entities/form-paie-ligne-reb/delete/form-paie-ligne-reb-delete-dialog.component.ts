import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFormPaieLigneReb } from '../form-paie-ligne-reb.model';
import { FormPaieLigneRebService } from '../service/form-paie-ligne-reb.service';

@Component({
  templateUrl: './form-paie-ligne-reb-delete-dialog.component.html',
})
export class FormPaieLigneRebDeleteDialogComponent {
  formPaieLigneReb?: IFormPaieLigneReb;

  constructor(protected formPaieLigneRebService: FormPaieLigneRebService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.formPaieLigneRebService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
