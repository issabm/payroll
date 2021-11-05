import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { INiveauScolaire, NiveauScolaire } from '../niveau-scolaire.model';
import { NiveauScolaireService } from '../service/niveau-scolaire.service';

@Component({
  selector: 'jhi-niveau-scolaire-update',
  templateUrl: './niveau-scolaire-update.component.html',
})
export class NiveauScolaireUpdateComponent implements OnInit {
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

  constructor(
    protected niveauScolaireService: NiveauScolaireService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ niveauScolaire }) => {
      if (niveauScolaire.id === undefined) {
        const today = dayjs().startOf('day');
        niveauScolaire.dateop = today;
        niveauScolaire.createdDate = today;
        niveauScolaire.modifiedDate = today;
      }

      this.updateForm(niveauScolaire);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const niveauScolaire = this.createFromForm();
    if (niveauScolaire.id !== undefined) {
      this.subscribeToSaveResponse(this.niveauScolaireService.update(niveauScolaire));
    } else {
      this.subscribeToSaveResponse(this.niveauScolaireService.create(niveauScolaire));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INiveauScolaire>>): void {
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

  protected updateForm(niveauScolaire: INiveauScolaire): void {
    this.editForm.patchValue({
      id: niveauScolaire.id,
      code: niveauScolaire.code,
      libAr: niveauScolaire.libAr,
      libEn: niveauScolaire.libEn,
      util: niveauScolaire.util,
      dateop: niveauScolaire.dateop ? niveauScolaire.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: niveauScolaire.modifiedBy,
      op: niveauScolaire.op,
      isDeleted: niveauScolaire.isDeleted,
      createdDate: niveauScolaire.createdDate ? niveauScolaire.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: niveauScolaire.modifiedDate ? niveauScolaire.modifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): INiveauScolaire {
    return {
      ...new NiveauScolaire(),
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
