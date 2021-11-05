import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOperatorForm } from '../operator-form.model';
import { OperatorFormService } from '../service/operator-form.service';

@Component({
  templateUrl: './operator-form-delete-dialog.component.html',
})
export class OperatorFormDeleteDialogComponent {
  operatorForm?: IOperatorForm;

  constructor(protected operatorFormService: OperatorFormService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.operatorFormService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
