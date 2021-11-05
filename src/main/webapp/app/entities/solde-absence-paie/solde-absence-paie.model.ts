import * as dayjs from 'dayjs';
import { IPayroll } from 'app/entities/payroll/payroll.model';
import { INatureAbsence } from 'app/entities/nature-absence/nature-absence.model';

export interface ISoldeAbsencePaie {
  id?: number;
  annee?: number | null;
  mois?: number | null;
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
  payroll?: IPayroll | null;
  natureAbsence?: INatureAbsence | null;
}

export class SoldeAbsencePaie implements ISoldeAbsencePaie {
  constructor(
    public id?: number,
    public annee?: number | null,
    public mois?: number | null,
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
    public payroll?: IPayroll | null,
    public natureAbsence?: INatureAbsence | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getSoldeAbsencePaieIdentifier(soldeAbsencePaie: ISoldeAbsencePaie): number | undefined {
  return soldeAbsencePaie.id;
}
