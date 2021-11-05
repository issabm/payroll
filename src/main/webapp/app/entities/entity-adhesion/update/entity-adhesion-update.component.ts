import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IEntityAdhesion, EntityAdhesion } from '../entity-adhesion.model';
import { EntityAdhesionService } from '../service/entity-adhesion.service';
import { INatureAdhesion } from 'app/entities/nature-adhesion/nature-adhesion.model';
import { NatureAdhesionService } from 'app/entities/nature-adhesion/service/nature-adhesion.service';

@Component({
  selector: 'jhi-entity-adhesion-update',
  templateUrl: './entity-adhesion-update.component.html',
})
export class EntityAdhesionUpdateComponent implements OnInit {
  isSaving = false;

  natureAdhesionsSharedCollection: INatureAdhesion[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    libEn: [],
    libAr: [],
    util: [],
    dateop: [],
    modifiedBy: [],
    op: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    natureAdhesion: [],
  });

  constructor(
    protected entityAdhesionService: EntityAdhesionService,
    protected natureAdhesionService: NatureAdhesionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entityAdhesion }) => {
      if (entityAdhesion.id === undefined) {
        const today = dayjs().startOf('day');
        entityAdhesion.dateop = today;
        entityAdhesion.createdDate = today;
        entityAdhesion.modifiedDate = today;
      }

      this.updateForm(entityAdhesion);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const entityAdhesion = this.createFromForm();
    if (entityAdhesion.id !== undefined) {
      this.subscribeToSaveResponse(this.entityAdhesionService.update(entityAdhesion));
    } else {
      this.subscribeToSaveResponse(this.entityAdhesionService.create(entityAdhesion));
    }
  }

  trackNatureAdhesionById(index: number, item: INatureAdhesion): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEntityAdhesion>>): void {
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

  protected updateForm(entityAdhesion: IEntityAdhesion): void {
    this.editForm.patchValue({
      id: entityAdhesion.id,
      code: entityAdhesion.code,
      libEn: entityAdhesion.libEn,
      libAr: entityAdhesion.libAr,
      util: entityAdhesion.util,
      dateop: entityAdhesion.dateop ? entityAdhesion.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: entityAdhesion.modifiedBy,
      op: entityAdhesion.op,
      isDeleted: entityAdhesion.isDeleted,
      createdDate: entityAdhesion.createdDate ? entityAdhesion.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: entityAdhesion.modifiedDate ? entityAdhesion.modifiedDate.format(DATE_TIME_FORMAT) : null,
      natureAdhesion: entityAdhesion.natureAdhesion,
    });

    this.natureAdhesionsSharedCollection = this.natureAdhesionService.addNatureAdhesionToCollectionIfMissing(
      this.natureAdhesionsSharedCollection,
      entityAdhesion.natureAdhesion
    );
  }

  protected loadRelationshipsOptions(): void {
    this.natureAdhesionService
      .query()
      .pipe(map((res: HttpResponse<INatureAdhesion[]>) => res.body ?? []))
      .pipe(
        map((natureAdhesions: INatureAdhesion[]) =>
          this.natureAdhesionService.addNatureAdhesionToCollectionIfMissing(natureAdhesions, this.editForm.get('natureAdhesion')!.value)
        )
      )
      .subscribe((natureAdhesions: INatureAdhesion[]) => (this.natureAdhesionsSharedCollection = natureAdhesions));
  }

  protected createFromForm(): IEntityAdhesion {
    return {
      ...new EntityAdhesion(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libEn: this.editForm.get(['libEn'])!.value,
      libAr: this.editForm.get(['libAr'])!.value,
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
      natureAdhesion: this.editForm.get(['natureAdhesion'])!.value,
    };
  }
}
