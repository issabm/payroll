import * as dayjs from 'dayjs';
import { IEmploye } from 'app/entities/employe/employe.model';
import { INatureAbsence } from 'app/entities/nature-absence/nature-absence.model';

export interface ISoldeAbsence {
  id?: number;
  annee?: number | null;
  fullPayRight?: number | null;
  halfPayRight?: number | null;
  fullPay?: number | null;
  halfPay?: number | null;
  util?: string | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  affectation?: IEmploye | null;
  natureAbsence?: INatureAbsence | null;
}

export class SoldeAbsence implements ISoldeAbsence {
  constructor(
    public id?: number,
    public annee?: number | null,
    public fullPayRight?: number | null,
    public halfPayRight?: number | null,
    public fullPay?: number | null,
    public halfPay?: number | null,
    public util?: string | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public affectation?: IEmploye | null,
    public natureAbsence?: INatureAbsence | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getSoldeAbsenceIdentifier(soldeAbsence: ISoldeAbsence): number | undefined {
  return soldeAbsence.id;
}
