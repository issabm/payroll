import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFormPaieLigne } from '../form-paie-ligne.model';
import { FormPaieLigneService } from '../service/form-paie-ligne.service';

@Component({
  templateUrl: './form-paie-ligne-delete-dialog.component.html',
})
export class FormPaieLigneDeleteDialogComponent {
  formPaieLigne?: IFormPaieLigne;

  constructor(protected formPaieLigneService: FormPaieLigneService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.formPaieLigneService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
