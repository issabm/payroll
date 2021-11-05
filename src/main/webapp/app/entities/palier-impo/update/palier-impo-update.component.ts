import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPalierImpo, PalierImpo } from '../palier-impo.model';
import { PalierImpoService } from '../service/palier-impo.service';
import { IPays } from 'app/entities/pays/pays.model';
import { PaysService } from 'app/entities/pays/service/pays.service';
import { ISituation } from 'app/entities/situation/situation.model';
import { SituationService } from 'app/entities/situation/service/situation.service';

@Component({
  selector: 'jhi-palier-impo-update',
  templateUrl: './palier-impo-update.component.html',
})
export class PalierImpoUpdateComponent implements OnInit {
  isSaving = false;

  paysSharedCollection: IPays[] = [];
  situationsSharedCollection: ISituation[] = [];

  editForm = this.fb.group({
    id: [],
    annee: [],
    payrollMin: [],
    payrollMax: [],
    impoValue: [],
    util: [],
    dateop: [],
    dateModif: [],
    modifiedBy: [],
    op: [],
    isDeleted: [],
    pays: [],
    situation: [],
  });

  constructor(
    protected palierImpoService: PalierImpoService,
    protected paysService: PaysService,
    protected situationService: SituationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ palierImpo }) => {
      if (palierImpo.id === undefined) {
        const today = dayjs().startOf('day');
        palierImpo.dateop = today;
        palierImpo.dateModif = today;
      }

      this.updateForm(palierImpo);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const palierImpo = this.createFromForm();
    if (palierImpo.id !== undefined) {
      this.subscribeToSaveResponse(this.palierImpoService.update(palierImpo));
    } else {
      this.subscribeToSaveResponse(this.palierImpoService.create(palierImpo));
    }
  }

  trackPaysById(index: number, item: IPays): number {
    return item.id!;
  }

  trackSituationById(index: number, item: ISituation): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPalierImpo>>): void {
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

  protected updateForm(palierImpo: IPalierImpo): void {
    this.editForm.patchValue({
      id: palierImpo.id,
      annee: palierImpo.annee,
      payrollMin: palierImpo.payrollMin,
      payrollMax: palierImpo.payrollMax,
      impoValue: palierImpo.impoValue,
      util: palierImpo.util,
      dateop: palierImpo.dateop ? palierImpo.dateop.format(DATE_TIME_FORMAT) : null,
      dateModif: palierImpo.dateModif ? palierImpo.dateModif.format(DATE_TIME_FORMAT) : null,
      modifiedBy: palierImpo.modifiedBy,
      op: palierImpo.op,
      isDeleted: palierImpo.isDeleted,
      pays: palierImpo.pays,
      situation: palierImpo.situation,
    });

    this.paysSharedCollection = this.paysService.addPaysToCollectionIfMissing(this.paysSharedCollection, palierImpo.pays);
    this.situationsSharedCollection = this.situationService.addSituationToCollectionIfMissing(
      this.situationsSharedCollection,
      palierImpo.situation
    );
  }

  protected loadRelationshipsOptions(): void {
    this.paysService
      .query()
      .pipe(map((res: HttpResponse<IPays[]>) => res.body ?? []))
      .pipe(map((pays: IPays[]) => this.paysService.addPaysToCollectionIfMissing(pays, this.editForm.get('pays')!.value)))
      .subscribe((pays: IPays[]) => (this.paysSharedCollection = pays));

    this.situationService
      .query()
      .pipe(map((res: HttpResponse<ISituation[]>) => res.body ?? []))
      .pipe(
        map((situations: ISituation[]) =>
          this.situationService.addSituationToCollectionIfMissing(situations, this.editForm.get('situation')!.value)
        )
      )
      .subscribe((situations: ISituation[]) => (this.situationsSharedCollection = situations));
  }

  protected createFromForm(): IPalierImpo {
    return {
      ...new PalierImpo(),
      id: this.editForm.get(['id'])!.value,
      annee: this.editForm.get(['annee'])!.value,
      payrollMin: this.editForm.get(['payrollMin'])!.value,
      payrollMax: this.editForm.get(['payrollMax'])!.value,
      impoValue: this.editForm.get(['impoValue'])!.value,
      util: this.editForm.get(['util'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      dateModif: this.editForm.get(['dateModif'])!.value ? dayjs(this.editForm.get(['dateModif'])!.value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      op: this.editForm.get(['op'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      pays: this.editForm.get(['pays'])!.value,
      situation: this.editForm.get(['situation'])!.value,
    };
  }
}
