import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IManagementResourceProfile, ManagementResourceProfile } from '../management-resource-profile.model';
import { ManagementResourceProfileService } from '../service/management-resource-profile.service';
import { IManagementResource } from 'app/entities/management-resource/management-resource.model';
import { ManagementResourceService } from 'app/entities/management-resource/service/management-resource.service';

@Component({
  selector: 'jhi-management-resource-profile-update',
  templateUrl: './management-resource-profile-update.component.html',
})
export class ManagementResourceProfileUpdateComponent implements OnInit {
  isSaving = false;

  managementResourcesSharedCollection: IManagementResource[] = [];

  editForm = this.fb.group({
    id: [],
    profile: [],
    dateop: [],
    modifiedBy: [],
    createdBy: [],
    op: [],
    util: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    ressourceManage: [],
  });

  constructor(
    protected managementResourceProfileService: ManagementResourceProfileService,
    protected managementResourceService: ManagementResourceService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ managementResourceProfile }) => {
      if (managementResourceProfile.id === undefined) {
        const today = dayjs().startOf('day');
        managementResourceProfile.dateop = today;
        managementResourceProfile.createdDate = today;
        managementResourceProfile.modifiedDate = today;
      }

      this.updateForm(managementResourceProfile);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const managementResourceProfile = this.createFromForm();
    if (managementResourceProfile.id !== undefined) {
      this.subscribeToSaveResponse(this.managementResourceProfileService.update(managementResourceProfile));
    } else {
      this.subscribeToSaveResponse(this.managementResourceProfileService.create(managementResourceProfile));
    }
  }

  trackManagementResourceById(index: number, item: IManagementResource): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IManagementResourceProfile>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(managementResourceProfile: IManagementResourceProfile): void {
    this.editForm.patchValue({
      id: managementResourceProfile.id,
      profile: managementResourceProfile.profile,
      dateop: managementResourceProfile.dateop ? managementResourceProfile.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: managementResourceProfile.modifiedBy,
      createdBy: managementResourceProfile.createdBy,
      op: managementResourceProfile.op,
      util: managementResourceProfile.util,
      isDeleted: managementResourceProfile.isDeleted,
      createdDate: managementResourceProfile.createdDate ? managementResourceProfile.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: managementResourceProfile.modifiedDate ? managementResourceProfile.modifiedDate.format(DATE_TIME_FORMAT) : null,
      ressourceManage: managementResourceProfile.ressourceManage,
    });

    this.managementResourcesSharedCollection = this.managementResourceService.addManagementResourceToCollectionIfMissing(
      this.managementResourcesSharedCollection,
      managementResourceProfile.ressourceManage
    );
  }

  protected loadRelationshipsOptions(): void {
    this.managementResourceService
      .query()
      .pipe(map((res: HttpResponse<IManagementResource[]>) => res.body ?? []))
      .pipe(
        map((managementResources: IManagementResource[]) =>
          this.managementResourceService.addManagementResourceToCollectionIfMissing(
            managementResources,
            this.editForm.get('ressourceManage')!.value
          )
        )
      )
      .subscribe((managementResources: IManagementResource[]) => (this.managementResourcesSharedCollection = managementResources));
  }

  protected createFromForm(): IManagementResourceProfile {
    return {
      ...new ManagementResourceProfile(),
      id: this.editForm.get(['id'])!.value,
      profile: this.editForm.get(['profile'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      op: this.editForm.get(['op'])!.value,
      util: this.editForm.get(['util'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      modifiedDate: this.editForm.get(['modifiedDate'])!.value
        ? dayjs(this.editForm.get(['modifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      ressourceManage: this.editForm.get(['ressourceManage'])!.value,
    };
  }
}
