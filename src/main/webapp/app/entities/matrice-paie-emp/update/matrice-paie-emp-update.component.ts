import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IMatricePaieEmp, MatricePaieEmp } from '../matrice-paie-emp.model';
import { MatricePaieEmpService } from '../service/matrice-paie-emp.service';
import { IMatricePaie } from 'app/entities/matrice-paie/matrice-paie.model';
import { MatricePaieService } from 'app/entities/matrice-paie/service/matrice-paie.service';
import { IEmploye } from 'app/entities/employe/employe.model';
import { EmployeService } from 'app/entities/employe/service/employe.service';
import { ISituation } from 'app/entities/situation/situation.model';
import { SituationService } from 'app/entities/situation/service/situation.service';

@Component({
  selector: 'jhi-matrice-paie-emp-update',
  templateUrl: './matrice-paie-emp-update.component.html',
})
export class MatricePaieEmpUpdateComponent implements OnInit {
  isSaving = false;

  matricePaiesSharedCollection: IMatricePaie[] = [];
  employesSharedCollection: IEmploye[] = [];
  situationsSharedCollection: ISituation[] = [];

  editForm = this.fb.group({
    id: [],
    util: [],
    dateop: [],
    modifiedBy: [],
    op: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    matricePaie: [],
    employe: [],
    situation: [],
  });

  constructor(
    protected matricePaieEmpService: MatricePaieEmpService,
    protected matricePaieService: MatricePaieService,
    protected employeService: EmployeService,
    protected situationService: SituationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matricePaieEmp }) => {
      if (matricePaieEmp.id === undefined) {
        const today = dayjs().startOf('day');
        matricePaieEmp.dateop = today;
        matricePaieEmp.createdDate = today;
        matricePaieEmp.modifiedDate = today;
      }

      this.updateForm(matricePaieEmp);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const matricePaieEmp = this.createFromForm();
    if (matricePaieEmp.id !== undefined) {
      this.subscribeToSaveResponse(this.matricePaieEmpService.update(matricePaieEmp));
    } else {
      this.subscribeToSaveResponse(this.matricePaieEmpService.create(matricePaieEmp));
    }
  }

  trackMatricePaieById(index: number, item: IMatricePaie): number {
    return item.id!;
  }

  trackEmployeById(index: number, item: IEmploye): number {
    return item.id!;
  }

  trackSituationById(index: number, item: ISituation): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMatricePaieEmp>>): void {
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

  protected updateForm(matricePaieEmp: IMatricePaieEmp): void {
    this.editForm.patchValue({
      id: matricePaieEmp.id,
      util: matricePaieEmp.util,
      dateop: matricePaieEmp.dateop ? matricePaieEmp.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: matricePaieEmp.modifiedBy,
      op: matricePaieEmp.op,
      isDeleted: matricePaieEmp.isDeleted,
      createdDate: matricePaieEmp.createdDate ? matricePaieEmp.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: matricePaieEmp.modifiedDate ? matricePaieEmp.modifiedDate.format(DATE_TIME_FORMAT) : null,
      matricePaie: matricePaieEmp.matricePaie,
      employe: matricePaieEmp.employe,
      situation: matricePaieEmp.situation,
    });

    this.matricePaiesSharedCollection = this.matricePaieService.addMatricePaieToCollectionIfMissing(
      this.matricePaiesSharedCollection,
      matricePaieEmp.matricePaie
    );
    this.employesSharedCollection = this.employeService.addEmployeToCollectionIfMissing(
      this.employesSharedCollection,
      matricePaieEmp.employe
    );
    this.situationsSharedCollection = this.situationService.addSituationToCollectionIfMissing(
      this.situationsSharedCollection,
      matricePaieEmp.situation
    );
  }

  protected loadRelationshipsOptions(): void {
    this.matricePaieService
      .query()
      .pipe(map((res: HttpResponse<IMatricePaie[]>) => res.body ?? []))
      .pipe(
        map((matricePaies: IMatricePaie[]) =>
          this.matricePaieService.addMatricePaieToCollectionIfMissing(matricePaies, this.editForm.get('matricePaie')!.value)
        )
      )
      .subscribe((matricePaies: IMatricePaie[]) => (this.matricePaiesSharedCollection = matricePaies));

    this.employeService
      .query()
      .pipe(map((res: HttpResponse<IEmploye[]>) => res.body ?? []))
      .pipe(
        map((employes: IEmploye[]) => this.employeService.addEmployeToCollectionIfMissing(employes, this.editForm.get('employe')!.value))
      )
      .subscribe((employes: IEmploye[]) => (this.employesSharedCollection = employes));

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

  protected createFromForm(): IMatricePaieEmp {
    return {
      ...new MatricePaieEmp(),
      id: this.editForm.get(['id'])!.value,
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
      matricePaie: this.editForm.get(['matricePaie'])!.value,
      employe: this.editForm.get(['employe'])!.value,
      situation: this.editForm.get(['situation'])!.value,
    };
  }
}
