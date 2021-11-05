import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFormPaie } from '../form-paie.model';
import { FormPaieService } from '../service/form-paie.service';

@Component({
  templateUrl: './form-paie-delete-dialog.component.html',
})
export class FormPaieDeleteDialogComponent {
  formPaie?: IFormPaie;

  constructor(protected formPaieService: FormPaieService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.formPaieService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
