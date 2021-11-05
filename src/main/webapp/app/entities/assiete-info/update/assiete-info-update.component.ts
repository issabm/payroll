import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IAssieteInfo, AssieteInfo } from '../assiete-info.model';
import { AssieteInfoService } from '../service/assiete-info.service';
import { IRebrique } from 'app/entities/rebrique/rebrique.model';
import { RebriqueService } from 'app/entities/rebrique/service/rebrique.service';
import { IAssiete } from 'app/entities/assiete/assiete.model';
import { AssieteService } from 'app/entities/assiete/service/assiete.service';
import { IModeCalcul } from 'app/entities/mode-calcul/mode-calcul.model';
import { ModeCalculService } from 'app/entities/mode-calcul/service/mode-calcul.service';

@Component({
  selector: 'jhi-assiete-info-update',
  templateUrl: './assiete-info-update.component.html',
})
export class AssieteInfoUpdateComponent implements OnInit {
  isSaving = false;

  rebriquesSharedCollection: IRebrique[] = [];
  assietesSharedCollection: IAssiete[] = [];
  modeCalculsSharedCollection: IModeCalcul[] = [];

  editForm = this.fb.group({
    id: [],
    priorite: [],
    val: [],
    util: [],
    dateSituation: [],
    dateop: [],
    modifiedBy: [],
    createdBy: [],
    op: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    rebrique: [],
    assiete: [],
    modeCal: [],
  });

  constructor(
    protected assieteInfoService: AssieteInfoService,
    protected rebriqueService: RebriqueService,
    protected assieteService: AssieteService,
    protected modeCalculService: ModeCalculService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ assieteInfo }) => {
      if (assieteInfo.id === undefined) {
        const today = dayjs().startOf('day');
        assieteInfo.dateop = today;
        assieteInfo.createdDate = today;
        assieteInfo.modifiedDate = today;
      }

      this.updateForm(assieteInfo);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const assieteInfo = this.createFromForm();
    if (assieteInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.assieteInfoService.update(assieteInfo));
    } else {
      this.subscribeToSaveResponse(this.assieteInfoService.create(assieteInfo));
    }
  }

  trackRebriqueById(index: number, item: IRebrique): number {
    return item.id!;
  }

  trackAssieteById(index: number, item: IAssiete): number {
    return item.id!;
  }

  trackModeCalculById(index: number, item: IModeCalcul): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAssieteInfo>>): void {
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

  protected updateForm(assieteInfo: IAssieteInfo): void {
    this.editForm.patchValue({
      id: assieteInfo.id,
      priorite: assieteInfo.priorite,
      val: assieteInfo.val,
      util: assieteInfo.util,
      dateSituation: assieteInfo.dateSituation,
      dateop: assieteInfo.dateop ? assieteInfo.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: assieteInfo.modifiedBy,
      createdBy: assieteInfo.createdBy,
      op: assieteInfo.op,
      isDeleted: assieteInfo.isDeleted,
      createdDate: assieteInfo.createdDate ? assieteInfo.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: assieteInfo.modifiedDate ? assieteInfo.modifiedDate.format(DATE_TIME_FORMAT) : null,
      rebrique: assieteInfo.rebrique,
      assiete: assieteInfo.assiete,
      modeCal: assieteInfo.modeCal,
    });

    this.rebriquesSharedCollection = this.rebriqueService.addRebriqueToCollectionIfMissing(
      this.rebriquesSharedCollection,
      assieteInfo.rebrique
    );
    this.assietesSharedCollection = this.assieteService.addAssieteToCollectionIfMissing(this.assietesSharedCollection, assieteInfo.assiete);
    this.modeCalculsSharedCollection = this.modeCalculService.addModeCalculToCollectionIfMissing(
      this.modeCalculsSharedCollection,
      assieteInfo.modeCal
    );
  }

  protected loadRelationshipsOptions(): void {
    this.rebriqueService
      .query()
      .pipe(map((res: HttpResponse<IRebrique[]>) => res.body ?? []))
      .pipe(
        map((rebriques: IRebrique[]) =>
          this.rebriqueService.addRebriqueToCollectionIfMissing(rebriques, this.editForm.get('rebrique')!.value)
        )
      )
      .subscribe((rebriques: IRebrique[]) => (this.rebriquesSharedCollection = rebriques));

    this.assieteService
      .query()
      .pipe(map((res: HttpResponse<IAssiete[]>) => res.body ?? []))
      .pipe(
        map((assietes: IAssiete[]) => this.assieteService.addAssieteToCollectionIfMissing(assietes, this.editForm.get('assiete')!.value))
      )
      .subscribe((assietes: IAssiete[]) => (this.assietesSharedCollection = assietes));

    this.modeCalculService
      .query()
      .pipe(map((res: HttpResponse<IModeCalcul[]>) => res.body ?? []))
      .pipe(
        map((modeCalculs: IModeCalcul[]) =>
          this.modeCalculService.addModeCalculToCollectionIfMissing(modeCalculs, this.editForm.get('modeCal')!.value)
        )
      )
      .subscribe((modeCalculs: IModeCalcul[]) => (this.modeCalculsSharedCollection = modeCalculs));
  }

  protected createFromForm(): IAssieteInfo {
    return {
      ...new AssieteInfo(),
      id: this.editForm.get(['id'])!.value,
      priorite: this.editForm.get(['priorite'])!.value,
      val: this.editForm.get(['val'])!.value,
      util: this.editForm.get(['util'])!.value,
      dateSituation: this.editForm.get(['dateSituation'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      op: this.editForm.get(['op'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      modifiedDate: this.editForm.get(['modifiedDate'])!.value
        ? dayjs(this.editForm.get(['modifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      rebrique: this.editForm.get(['rebrique'])!.value,
      assiete: this.editForm.get(['assiete'])!.value,
      modeCal: this.editForm.get(['modeCal'])!.value,
    };
  }
}
