import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IEchlon, Echlon } from '../echlon.model';
import { EchlonService } from '../service/echlon.service';

@Component({
  selector: 'jhi-echlon-update',
  templateUrl: './echlon-update.component.html',
})
export class EchlonUpdateComponent implements OnInit {
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

  constructor(protected echlonService: EchlonService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ echlon }) => {
      if (echlon.id === undefined) {
        const today = dayjs().startOf('day');
        echlon.dateop = today;
        echlon.createdDate = today;
        echlon.modifiedDate = today;
      }

      this.updateForm(echlon);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const echlon = this.createFromForm();
    if (echlon.id !== undefined) {
      this.subscribeToSaveResponse(this.echlonService.update(echlon));
    } else {
      this.subscribeToSaveResponse(this.echlonService.create(echlon));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEchlon>>): void {
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

  protected updateForm(echlon: IEchlon): void {
    this.editForm.patchValue({
      id: echlon.id,
      code: echlon.code,
      libAr: echlon.libAr,
      libEn: echlon.libEn,
      util: echlon.util,
      dateop: echlon.dateop ? echlon.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: echlon.modifiedBy,
      op: echlon.op,
      isDeleted: echlon.isDeleted,
      createdDate: echlon.createdDate ? echlon.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: echlon.modifiedDate ? echlon.modifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IEchlon {
    return {
      ...new Echlon(),
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
