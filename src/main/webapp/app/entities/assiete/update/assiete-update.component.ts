import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IAssiete, Assiete } from '../assiete.model';
import { AssieteService } from '../service/assiete.service';

@Component({
  selector: 'jhi-assiete-update',
  templateUrl: './assiete-update.component.html',
})
export class AssieteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    priorite: [],
    code: [],
    lib: [],
    dateSituation: [],
    dateop: [],
    modifiedBy: [],
    createdBy: [],
    util: [],
    op: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
  });

  constructor(protected assieteService: AssieteService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ assiete }) => {
      if (assiete.id === undefined) {
        const today = dayjs().startOf('day');
        assiete.dateop = today;
        assiete.createdDate = today;
        assiete.modifiedDate = today;
      }

      this.updateForm(assiete);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const assiete = this.createFromForm();
    if (assiete.id !== undefined) {
      this.subscribeToSaveResponse(this.assieteService.update(assiete));
    } else {
      this.subscribeToSaveResponse(this.assieteService.create(assiete));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAssiete>>): void {
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

  protected updateForm(assiete: IAssiete): void {
    this.editForm.patchValue({
      id: assiete.id,
      priorite: assiete.priorite,
      code: assiete.code,
      lib: assiete.lib,
      dateSituation: assiete.dateSituation,
      dateop: assiete.dateop ? assiete.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: assiete.modifiedBy,
      createdBy: assiete.createdBy,
      util: assiete.util,
      op: assiete.op,
      isDeleted: assiete.isDeleted,
      createdDate: assiete.createdDate ? assiete.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: assiete.modifiedDate ? assiete.modifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IAssiete {
    return {
      ...new Assiete(),
      id: this.editForm.get(['id'])!.value,
      priorite: this.editForm.get(['priorite'])!.value,
      code: this.editForm.get(['code'])!.value,
      lib: this.editForm.get(['lib'])!.value,
      dateSituation: this.editForm.get(['dateSituation'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      util: this.editForm.get(['util'])!.value,
      op: this.editForm.get(['op'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      modifiedDate: this.editForm.get(['modifiedDate'])!.value
        ? dayjs(this.editForm.get(['modifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
