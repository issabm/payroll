import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAssiete } from '../assiete.model';
import { AssieteService } from '../service/assiete.service';

@Component({
  templateUrl: './assiete-delete-dialog.component.html',
})
export class AssieteDeleteDialogComponent {
  assiete?: IAssiete;

  constructor(protected assieteService: AssieteService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.assieteService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
