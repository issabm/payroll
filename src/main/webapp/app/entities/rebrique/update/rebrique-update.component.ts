import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IRebrique, Rebrique } from '../rebrique.model';
import { RebriqueService } from '../service/rebrique.service';
import { ISens } from 'app/entities/sens/sens.model';
import { SensService } from 'app/entities/sens/service/sens.service';
import { IConcerne } from 'app/entities/concerne/concerne.model';
import { ConcerneService } from 'app/entities/concerne/service/concerne.service';
import { IFrequence } from 'app/entities/frequence/frequence.model';
import { FrequenceService } from 'app/entities/frequence/service/frequence.service';

@Component({
  selector: 'jhi-rebrique-update',
  templateUrl: './rebrique-update.component.html',
})
export class RebriqueUpdateComponent implements OnInit {
  isSaving = false;

  sensSharedCollection: ISens[] = [];
  concernesSharedCollection: IConcerne[] = [];
  frequencesSharedCollection: IFrequence[] = [];

  editForm = this.fb.group({
    id: [],
    priorite: [],
    code: [],
    libAr: [],
    libEn: [],
    inTax: [],
    inSocial: [],
    inBp: [],
    val: [],
    dateSituation: [],
    dateop: [],
    modifiedBy: [],
    createdBy: [],
    util: [],
    op: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    sens: [],
    concerne: [],
    frequence: [],
  });

  constructor(
    protected rebriqueService: RebriqueService,
    protected sensService: SensService,
    protected concerneService: ConcerneService,
    protected frequenceService: FrequenceService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rebrique }) => {
      if (rebrique.id === undefined) {
        const today = dayjs().startOf('day');
        rebrique.dateop = today;
        rebrique.createdDate = today;
        rebrique.modifiedDate = today;
      }

      this.updateForm(rebrique);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rebrique = this.createFromForm();
    if (rebrique.id !== undefined) {
      this.subscribeToSaveResponse(this.rebriqueService.update(rebrique));
    } else {
      this.subscribeToSaveResponse(this.rebriqueService.create(rebrique));
    }
  }

  trackSensById(index: number, item: ISens): number {
    return item.id!;
  }

  trackConcerneById(index: number, item: IConcerne): number {
    return item.id!;
  }

  trackFrequenceById(index: number, item: IFrequence): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRebrique>>): void {
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

  protected updateForm(rebrique: IRebrique): void {
    this.editForm.patchValue({
      id: rebrique.id,
      priorite: rebrique.priorite,
      code: rebrique.code,
      libAr: rebrique.libAr,
      libEn: rebrique.libEn,
      inTax: rebrique.inTax,
      inSocial: rebrique.inSocial,
      inBp: rebrique.inBp,
      val: rebrique.val,
      dateSituation: rebrique.dateSituation,
      dateop: rebrique.dateop ? rebrique.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: rebrique.modifiedBy,
      createdBy: rebrique.createdBy,
      util: rebrique.util,
      op: rebrique.op,
      isDeleted: rebrique.isDeleted,
      createdDate: rebrique.createdDate ? rebrique.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: rebrique.modifiedDate ? rebrique.modifiedDate.format(DATE_TIME_FORMAT) : null,
      sens: rebrique.sens,
      concerne: rebrique.concerne,
      frequence: rebrique.frequence,
    });

    this.sensSharedCollection = this.sensService.addSensToCollectionIfMissing(this.sensSharedCollection, rebrique.sens);
    this.concernesSharedCollection = this.concerneService.addConcerneToCollectionIfMissing(
      this.concernesSharedCollection,
      rebrique.concerne
    );
    this.frequencesSharedCollection = this.frequenceService.addFrequenceToCollectionIfMissing(
      this.frequencesSharedCollection,
      rebrique.frequence
    );
  }

  protected loadRelationshipsOptions(): void {
    this.sensService
      .query()
      .pipe(map((res: HttpResponse<ISens[]>) => res.body ?? []))
      .pipe(map((sens: ISens[]) => this.sensService.addSensToCollectionIfMissing(sens, this.editForm.get('sens')!.value)))
      .subscribe((sens: ISens[]) => (this.sensSharedCollection = sens));

    this.concerneService
      .query()
      .pipe(map((res: HttpResponse<IConcerne[]>) => res.body ?? []))
      .pipe(
        map((concernes: IConcerne[]) =>
          this.concerneService.addConcerneToCollectionIfMissing(concernes, this.editForm.get('concerne')!.value)
        )
      )
      .subscribe((concernes: IConcerne[]) => (this.concernesSharedCollection = concernes));

    this.frequenceService
      .query()
      .pipe(map((res: HttpResponse<IFrequence[]>) => res.body ?? []))
      .pipe(
        map((frequences: IFrequence[]) =>
          this.frequenceService.addFrequenceToCollectionIfMissing(frequences, this.editForm.get('frequence')!.value)
        )
      )
      .subscribe((frequences: IFrequence[]) => (this.frequencesSharedCollection = frequences));
  }

  protected createFromForm(): IRebrique {
    return {
      ...new Rebrique(),
      id: this.editForm.get(['id'])!.value,
      priorite: this.editForm.get(['priorite'])!.value,
      code: this.editForm.get(['code'])!.value,
      libAr: this.editForm.get(['libAr'])!.value,
      libEn: this.editForm.get(['libEn'])!.value,
      inTax: this.editForm.get(['inTax'])!.value,
      inSocial: this.editForm.get(['inSocial'])!.value,
      inBp: this.editForm.get(['inBp'])!.value,
      val: this.editForm.get(['val'])!.value,
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
      sens: this.editForm.get(['sens'])!.value,
      concerne: this.editForm.get(['concerne'])!.value,
      frequence: this.editForm.get(['frequence'])!.value,
    };
  }
}
