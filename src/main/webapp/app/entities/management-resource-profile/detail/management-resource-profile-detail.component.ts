import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IManagementResourceProfile } from '../management-resource-profile.model';

@Component({
  selector: 'jhi-management-resource-profile-detail',
  templateUrl: './management-resource-profile-detail.component.html',
})
export class ManagementResourceProfileDetailComponent implements OnInit {
  managementResourceProfile: IManagementResourceProfile | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ managementResourceProfile }) => {
      this.managementResourceProfile = managementResourceProfile;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
