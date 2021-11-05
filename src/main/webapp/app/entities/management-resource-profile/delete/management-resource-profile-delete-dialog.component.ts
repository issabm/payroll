import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IManagementResourceProfile } from '../management-resource-profile.model';
import { ManagementResourceProfileService } from '../service/management-resource-profile.service';

@Component({
  templateUrl: './management-resource-profile-delete-dialog.component.html',
})
export class ManagementResourceProfileDeleteDialogComponent {
  managementResourceProfile?: IManagementResourceProfile;

  constructor(protected managementResourceProfileService: ManagementResourceProfileService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.managementResourceProfileService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
