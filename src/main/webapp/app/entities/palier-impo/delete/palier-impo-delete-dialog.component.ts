import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPalierImpo } from '../palier-impo.model';
import { PalierImpoService } from '../service/palier-impo.service';

@Component({
  templateUrl: './palier-impo-delete-dialog.component.html',
})
export class PalierImpoDeleteDialogComponent {
  palierImpo?: IPalierImpo;

  constructor(protected palierImpoService: PalierImpoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.palierImpoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
