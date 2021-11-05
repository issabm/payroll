import * as dayjs from 'dayjs';
import { ITypeContrat } from 'app/entities/type-contrat/type-contrat.model';

export interface ISoldeAbsenceContrat {
  id?: number;
  annee?: number | null;
  fullPayRight?: number | null;
  halfPayRight?: number | null;
  util?: string | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  typeContrat?: ITypeContrat | null;
}

export class SoldeAbsenceContrat implements ISoldeAbsenceContrat {
  constructor(
    public id?: number,
    public annee?: number | null,
    public fullPayRight?: number | null,
    public halfPayRight?: number | null,
    public util?: string | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public typeContrat?: ITypeContrat | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getSoldeAbsenceContratIdentifier(soldeAbsenceContrat: ISoldeAbsenceContrat): number | undefined {
  return soldeAbsenceContrat.id;
}
