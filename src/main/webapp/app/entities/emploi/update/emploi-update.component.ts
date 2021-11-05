import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IEmploi, Emploi } from '../emploi.model';
import { EmploiService } from '../service/emploi.service';

@Component({
  selector: 'jhi-emploi-update',
  templateUrl: './emploi-update.component.html',
})
export class EmploiUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    libAr: [],
    libEn: [],
    util: [],
    dateop: [],
    modifiedBy: [],
    op: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
  });

  constructor(protected emploiService: EmploiService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emploi }) => {
      if (emploi.id === undefined) {
        const today = dayjs().startOf('day');
        emploi.dateop = today;
        emploi.createdDate = today;
        emploi.modifiedDate = today;
      }

      this.updateForm(emploi);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const emploi = this.createFromForm();
    if (emploi.id !== undefined) {
      this.subscribeToSaveResponse(this.emploiService.update(emploi));
    } else {
      this.subscribeToSaveResponse(this.emploiService.create(emploi));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmploi>>): void {
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

  protected updateForm(emploi: IEmploi): void {
    this.editForm.patchValue({
      id: emploi.id,
      code: emploi.code,
      libAr: emploi.libAr,
      libEn: emploi.libEn,
      util: emploi.util,
      dateop: emploi.dateop ? emploi.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: emploi.modifiedBy,
      op: emploi.op,
      isDeleted: emploi.isDeleted,
      createdDate: emploi.createdDate ? emploi.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: emploi.modifiedDate ? emploi.modifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IEmploi {
    return {
      ...new Emploi(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libAr: this.editForm.get(['libAr'])!.value,
      libEn: this.editForm.get(['libEn'])!.value,
      util: this.editForm.get(['util'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
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
