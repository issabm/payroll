import * as dayjs from 'dayjs';

export interface IFormPaie {
  id?: number;
  code?: string | null;
  libEn?: string | null;
  libAr?: string | null;
  anneDebut?: number | null;
  anneeFin?: number | null;
  moisDebut?: number | null;
  moisFin?: number | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  createdBy?: string | null;
  util?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
}

export class FormPaie implements IFormPaie {
  constructor(
    public id?: number,
    public code?: string | null,
    public libEn?: string | null,
    public libAr?: string | null,
    public anneDebut?: number | null,
    public anneeFin?: number | null,
    public moisDebut?: number | null,
    public moisFin?: number | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public createdBy?: string | null,
    public util?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getFormPaieIdentifier(formPaie: IFormPaie): number | undefined {
  return formPaie.id;
}
