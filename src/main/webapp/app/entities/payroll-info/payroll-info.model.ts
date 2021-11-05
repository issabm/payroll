import * as dayjs from 'dayjs';
import { IPayroll } from 'app/entities/payroll/payroll.model';

export interface IPayrollInfo {
  id?: number;
  util?: string | null;
  code?: string | null;
  libAr?: string | null;
  libEr?: string | null;
  calculVal?: number | null;
  effectVal?: number | null;
  tauxAbs?: number | null;
  finalVal?: number | null;
  effectValDevise?: number | null;
  tauxChange?: number | null;
  dateCalcul?: dayjs.Dayjs | null;
  dateEffect?: dayjs.Dayjs | null;
  calculBy?: string | null;
  effectBy?: string | null;
  dateSituation?: dayjs.Dayjs | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  createdBy?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  payroll?: IPayroll | null;
}

export class PayrollInfo implements IPayrollInfo {
  constructor(
    public id?: number,
    public util?: string | null,
    public code?: string | null,
    public libAr?: string | null,
    public libEr?: string | null,
    public calculVal?: number | null,
    public effectVal?: number | null,
    public tauxAbs?: number | null,
    public finalVal?: number | null,
    public effectValDevise?: number | null,
    public tauxChange?: number | null,
    public dateCalcul?: dayjs.Dayjs | null,
    public dateEffect?: dayjs.Dayjs | null,
    public calculBy?: string | null,
    public effectBy?: string | null,
    public dateSituation?: dayjs.Dayjs | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public createdBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public payroll?: IPayroll | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getPayrollInfoIdentifier(payrollInfo: IPayrollInfo): number | undefined {
  return payrollInfo.id;
}
