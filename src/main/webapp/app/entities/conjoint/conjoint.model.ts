import * as dayjs from 'dayjs';
import { ISexe } from 'app/entities/sexe/sexe.model';

export interface IConjoint {
  id?: number;
  matricule?: string | null;
  nomAr?: string | null;
  prenomAr?: string | null;
  nomEn?: string | null;
  prenomEn?: string | null;
  dateNaiss?: dayjs.Dayjs | null;
  util?: string | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  sexe?: ISexe | null;
}

export class Conjoint implements IConjoint {
  constructor(
    public id?: number,
    public matricule?: string | null,
    public nomAr?: string | null,
    public prenomAr?: string | null,
    public nomEn?: string | null,
    public prenomEn?: string | null,
    public dateNaiss?: dayjs.Dayjs | null,
    public util?: string | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public sexe?: ISexe | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getConjointIdentifier(conjoint: IConjoint): number | undefined {
  return conjoint.id;
}
