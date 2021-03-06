import * as dayjs from 'dayjs';
import { IEmploye } from 'app/entities/employe/employe.model';
import { IDevise } from 'app/entities/devise/devise.model';
import { IMouvementPaie } from 'app/entities/mouvement-paie/mouvement-paie.model';
import { ISituation } from 'app/entities/situation/situation.model';

export interface IPayroll {
  id?: number;
  code?: string | null;
  lib?: string | null;
  annee?: number | null;
  mois?: number | null;
  nbFullPaie?: number | null;
  nbHalfPaie?: number | null;
  nbNonPaie?: number | null;
  dateCalcul?: dayjs.Dayjs | null;
  dateValid?: dayjs.Dayjs | null;
  datePayroll?: dayjs.Dayjs | null;
  totalCotis?: number | null;
  totalRetenue?: number | null;
  totalTaxable?: number | null;
  totalTax?: number | null;
  totalNet?: number | null;
  totalNetDevise?: number | null;
  tauxChange?: number | null;
  calculBy?: string | null;
  effectBy?: string | null;
  dateSituation?: dayjs.Dayjs | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  createdBy?: string | null;
  op?: string | null;
  util?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  employe?: IEmploye | null;
  devise?: IDevise | null;
  mvt?: IMouvementPaie | null;
  situation?: ISituation | null;
}

export class Payroll implements IPayroll {
  constructor(
    public id?: number,
    public code?: string | null,
    public lib?: string | null,
    public annee?: number | null,
    public mois?: number | null,
    public nbFullPaie?: number | null,
    public nbHalfPaie?: number | null,
    public nbNonPaie?: number | null,
    public dateCalcul?: dayjs.Dayjs | null,
    public dateValid?: dayjs.Dayjs | null,
    public datePayroll?: dayjs.Dayjs | null,
    public totalCotis?: number | null,
    public totalRetenue?: number | null,
    public totalTaxable?: number | null,
    public totalTax?: number | null,
    public totalNet?: number | null,
    public totalNetDevise?: number | null,
    public tauxChange?: number | null,
    public calculBy?: string | null,
    public effectBy?: string | null,
    public dateSituation?: dayjs.Dayjs | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public createdBy?: string | null,
    public op?: string | null,
    public util?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public employe?: IEmploye | null,
    public devise?: IDevise | null,
    public mvt?: IMouvementPaie | null,
    public situation?: ISituation | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getPayrollIdentifier(payroll: IPayroll): number | undefined {
  return payroll.id;
}
