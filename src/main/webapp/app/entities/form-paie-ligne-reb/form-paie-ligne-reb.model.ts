import * as dayjs from 'dayjs';
import { IFormPaieLigne } from 'app/entities/form-paie-ligne/form-paie-ligne.model';

export interface IFormPaieLigneReb {
  id?: number;
  priorite?: number | null;
  code?: string | null;
  libEn?: string | null;
  libAr?: string | null;
  valOrigin?: number | null;
  valCalcul?: number | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  createdBy?: string | null;
  op?: string | null;
  util?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  formPaieLigne?: IFormPaieLigne | null;
}

export class FormPaieLigneReb implements IFormPaieLigneReb {
  constructor(
    public id?: number,
    public priorite?: number | null,
    public code?: string | null,
    public libEn?: string | null,
    public libAr?: string | null,
    public valOrigin?: number | null,
    public valCalcul?: number | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public createdBy?: string | null,
    public op?: string | null,
    public util?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public formPaieLigne?: IFormPaieLigne | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getFormPaieLigneRebIdentifier(formPaieLigneReb: IFormPaieLigneReb): number | undefined {
  return formPaieLigneReb.id;
}
