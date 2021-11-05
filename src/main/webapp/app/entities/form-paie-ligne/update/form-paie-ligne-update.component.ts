import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IFormPaieLigne, FormPaieLigne } from '../form-paie-ligne.model';
import { FormPaieLigneService } from '../service/form-paie-ligne.service';
import { IFormPaie } from 'app/entities/form-paie/form-paie.model';
import { FormPaieService } from 'app/entities/form-paie/service/form-paie.service';
import { IOperatorForm } from 'app/entities/operator-form/operator-form.model';
import { OperatorFormService } from 'app/entities/operator-form/service/operator-form.service';
import { IAssiete } from 'app/entities/assiete/assiete.model';
import { AssieteService } from 'app/entities/assiete/service/assiete.service';

@Component({
  selector: 'jhi-form-paie-ligne-update',
  templateUrl: './form-paie-ligne-update.component.html',
})
export class FormPaieLigneUpdateComponent implements OnInit {
  isSaving = false;

  formPaiesSharedCollection: IFormPaie[] = [];
  operatorFormsSharedCollection: IOperatorForm[] = [];
  assietesSharedCollection: IAssiete[] = [];

  editForm = this.fb.group({
    id: [],
    priorite: [],
    code: [],
    libEn: [],
    libAr: [],
    dateop: [],
    modifiedBy: [],
    createdBy: [],
    op: [],
    util: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    formPaie: [],
    operatorForm: [],
    assiete: [],
  });

  constructor(
    protected formPaieLigneService: FormPaieLigneService,
    protected formPaieService: FormPaieService,
    protected operatorFormService: OperatorFormService,
    protected assieteService: AssieteService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ formPaieLigne }) => {
      if (formPaieLigne.id === undefined) {
        const today = dayjs().startOf('day');
        formPaieLigne.dateop = today;
        formPaieLigne.createdDate = today;
        formPaieLigne.modifiedDate = today;
      }

      this.updateForm(formPaieLigne);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const formPaieLigne = this.createFromForm();
    if (formPaieLigne.id !== undefined) {
      this.subscribeToSaveResponse(this.formPaieLigneService.update(formPaieLigne));
    } else {
      this.subscribeToSaveResponse(this.formPaieLigneService.create(formPaieLigne));
    }
  }

  trackFormPaieById(index: number, item: IFormPaie): number {
    return item.id!;
  }

  trackOperatorFormById(index: number, item: IOperatorForm): number {
    return item.id!;
  }

  trackAssieteById(index: number, item: IAssiete): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFormPaieLigne>>): void {
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

  protected updateForm(formPaieLigne: IFormPaieLigne): void {
    this.editForm.patchValue({
      id: formPaieLigne.id,
      priorite: formPaieLigne.priorite,
      code: formPaieLigne.code,
      libEn: formPaieLigne.libEn,
      libAr: formPaieLigne.libAr,
      dateop: formPaieLigne.dateop ? formPaieLigne.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: formPaieLigne.modifiedBy,
      createdBy: formPaieLigne.createdBy,
      op: formPaieLigne.op,
      util: formPaieLigne.util,
      isDeleted: formPaieLigne.isDeleted,
      createdDate: formPaieLigne.createdDate ? formPaieLigne.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: formPaieLigne.modifiedDate ? formPaieLigne.modifiedDate.format(DATE_TIME_FORMAT) : null,
      formPaie: formPaieLigne.formPaie,
      operatorForm: formPaieLigne.operatorForm,
      assiete: formPaieLigne.assiete,
    });

    this.formPaiesSharedCollection = this.formPaieService.addFormPaieToCollectionIfMissing(
      this.formPaiesSharedCollection,
      formPaieLigne.formPaie
    );
    this.operatorFormsSharedCollection = this.operatorFormService.addOperatorFormToCollectionIfMissing(
      this.operatorFormsSharedCollection,
      formPaieLigne.operatorForm
    );
    this.assietesSharedCollection = this.assieteService.addAssieteToCollectionIfMissing(
      this.assietesSharedCollection,
      formPaieLigne.assiete
    );
  }

  protected loadRelationshipsOptions(): void {
    this.formPaieService
      .query()
      .pipe(map((res: HttpResponse<IFormPaie[]>) => res.body ?? []))
      .pipe(
        map((formPaies: IFormPaie[]) =>
          this.formPaieService.addFormPaieToCollectionIfMissing(formPaies, this.editForm.get('formPaie')!.value)
        )
      )
      .subscribe((formPaies: IFormPaie[]) => (this.formPaiesSharedCollection = formPaies));

    this.operatorFormService
      .query()
      .pipe(map((res: HttpResponse<IOperatorForm[]>) => res.body ?? []))
      .pipe(
        map((operatorForms: IOperatorForm[]) =>
          this.operatorFormService.addOperatorFormToCollectionIfMissing(operatorForms, this.editForm.get('operatorForm')!.value)
        )
      )
      .subscribe((operatorForms: IOperatorForm[]) => (this.operatorFormsSharedCollection = operatorForms));

    this.assieteService
      .query()
      .pipe(map((res: HttpResponse<IAssiete[]>) => res.body ?? []))
      .pipe(
        map((assietes: IAssiete[]) => this.assieteService.addAssieteToCollectionIfMissing(assietes, this.editForm.get('assiete')!.value))
      )
      .subscribe((assietes: IAssiete[]) => (this.assietesSharedCollection = assietes));
  }

  protected createFromForm(): IFormPaieLigne {
    return {
      ...new FormPaieLigne(),
      id: this.editForm.get(['id'])!.value,
      priorite: this.editForm.get(['priorite'])!.value,
      code: this.editForm.get(['code'])!.value,
      libEn: this.editForm.get(['libEn'])!.value,
      libAr: this.editForm.get(['libAr'])!.value,
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
      formPaie: this.editForm.get(['formPaie'])!.value,
      operatorForm: this.editForm.get(['operatorForm'])!.value,
      assiete: this.editForm.get(['assiete'])!.value,
    };
  }
}
