import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAssieteInfo } from '../assiete-info.model';
import { AssieteInfoService } from '../service/assiete-info.service';

@Component({
  templateUrl: './assiete-info-delete-dialog.component.html',
})
export class AssieteInfoDeleteDialogComponent {
  assieteInfo?: IAssieteInfo;

  constructor(protected assieteInfoService: AssieteInfoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.assieteInfoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
