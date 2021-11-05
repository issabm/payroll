import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IRebriqueContrat, RebriqueContrat } from '../rebrique-contrat.model';
import { RebriqueContratService } from '../service/rebrique-contrat.service';
import { IRebrique } from 'app/entities/rebrique/rebrique.model';
import { RebriqueService } from 'app/entities/rebrique/service/rebrique.service';
import { ISousTypeContrat } from 'app/entities/sous-type-contrat/sous-type-contrat.model';
import { SousTypeContratService } from 'app/entities/sous-type-contrat/service/sous-type-contrat.service';
import { ITypeContrat } from 'app/entities/type-contrat/type-contrat.model';
import { TypeContratService } from 'app/entities/type-contrat/service/type-contrat.service';

@Component({
  selector: 'jhi-rebrique-contrat-update',
  templateUrl: './rebrique-contrat-update.component.html',
})
export class RebriqueContratUpdateComponent implements OnInit {
  isSaving = false;

  rebriquesSharedCollection: IRebrique[] = [];
  sousTypeContratsSharedCollection: ISousTypeContrat[] = [];
  typeContratsSharedCollection: ITypeContrat[] = [];

  editForm = this.fb.group({
    id: [],
    util: [],
    dateSituation: [],
    dateop: [],
    modifiedBy: [],
    createdBy: [],
    op: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    rebrqiue: [],
    sousTypeContrat: [],
    typeContrat: [],
  });

  constructor(
    protected rebriqueContratService: RebriqueContratService,
    protected rebriqueService: RebriqueService,
    protected sousTypeContratService: SousTypeContratService,
    protected typeContratService: TypeContratService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rebriqueContrat }) => {
      if (rebriqueContrat.id === undefined) {
        const today = dayjs().startOf('day');
        rebriqueContrat.dateop = today;
        rebriqueContrat.createdDate = today;
        rebriqueContrat.modifiedDate = today;
      }

      this.updateForm(rebriqueContrat);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rebriqueContrat = this.createFromForm();
    if (rebriqueContrat.id !== undefined) {
      this.subscribeToSaveResponse(this.rebriqueContratService.update(rebriqueContrat));
    } else {
      this.subscribeToSaveResponse(this.rebriqueContratService.create(rebriqueContrat));
    }
  }

  trackRebriqueById(index: number, item: IRebrique): number {
    return item.id!;
  }

  trackSousTypeContratById(index: number, item: ISousTypeContrat): number {
    return item.id!;
  }

  trackTypeContratById(index: number, item: ITypeContrat): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRebriqueContrat>>): void {
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

  protected updateForm(rebriqueContrat: IRebriqueContrat): void {
    this.editForm.patchValue({
      id: rebriqueContrat.id,
      util: rebriqueContrat.util,
      dateSituation: rebriqueContrat.dateSituation,
      dateop: rebriqueContrat.dateop ? rebriqueContrat.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: rebriqueContrat.modifiedBy,
      createdBy: rebriqueContrat.createdBy,
      op: rebriqueContrat.op,
      isDeleted: rebriqueContrat.isDeleted,
      createdDate: rebriqueContrat.createdDate ? rebriqueContrat.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: rebriqueContrat.modifiedDate ? rebriqueContrat.modifiedDate.format(DATE_TIME_FORMAT) : null,
      rebrqiue: rebriqueContrat.rebrqiue,
      sousTypeContrat: rebriqueContrat.sousTypeContrat,
      typeContrat: rebriqueContrat.typeContrat,
    });

    this.rebriquesSharedCollection = this.rebriqueService.addRebriqueToCollectionIfMissing(
      this.rebriquesSharedCollection,
      rebriqueContrat.rebrqiue
    );
    this.sousTypeContratsSharedCollection = this.sousTypeContratService.addSousTypeContratToCollectionIfMissing(
      this.sousTypeContratsSharedCollection,
      rebriqueContrat.sousTypeContrat
    );
    this.typeContratsSharedCollection = this.typeContratService.addTypeContratToCollectionIfMissing(
      this.typeContratsSharedCollection,
      rebriqueContrat.typeContrat
    );
  }

  protected loadRelationshipsOptions(): void {
    this.rebriqueService
      .query()
      .pipe(map((res: HttpResponse<IRebrique[]>) => res.body ?? []))
      .pipe(
        map((rebriques: IRebrique[]) =>
          this.rebriqueService.addRebriqueToCollectionIfMissing(rebriques, this.editForm.get('rebrqiue')!.value)
        )
      )
      .subscribe((rebriques: IRebrique[]) => (this.rebriquesSharedCollection = rebriques));

    this.sousTypeContratService
      .query()
      .pipe(map((res: HttpResponse<ISousTypeContrat[]>) => res.body ?? []))
      .pipe(
        map((sousTypeContrats: ISousTypeContrat[]) =>
          this.sousTypeContratService.addSousTypeContratToCollectionIfMissing(sousTypeContrats, this.editForm.get('sousTypeContrat')!.value)
        )
      )
      .subscribe((sousTypeContrats: ISousTypeContrat[]) => (this.sousTypeContratsSharedCollection = sousTypeContrats));

    this.typeContratService
      .query()
      .pipe(map((res: HttpResponse<ITypeContrat[]>) => res.body ?? []))
      .pipe(
        map((typeContrats: ITypeContrat[]) =>
          this.typeContratService.addTypeContratToCollectionIfMissing(typeContrats, this.editForm.get('typeContrat')!.value)
        )
      )
      .subscribe((typeContrats: ITypeContrat[]) => (this.typeContratsSharedCollection = typeContrats));
  }

  protected createFromForm(): IRebriqueContrat {
    return {
      ...new RebriqueContrat(),
      id: this.editForm.get(['id'])!.value,
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
      rebrqiue: this.editForm.get(['rebrqiue'])!.value,
      sousTypeContrat: this.editForm.get(['sousTypeContrat'])!.value,
      typeContrat: this.editForm.get(['typeContrat'])!.value,
    };
  }
}
