import * as dayjs from 'dayjs';
import { IFormPaie } from 'app/entities/form-paie/form-paie.model';
import { IOperatorForm } from 'app/entities/operator-form/operator-form.model';
import { IAssiete } from 'app/entities/assiete/assiete.model';

export interface IFormPaieLigne {
  id?: number;
  priorite?: number | null;
  code?: string | null;
  libEn?: string | null;
  libAr?: string | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  createdBy?: string | null;
  op?: string | null;
  util?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  formPaie?: IFormPaie | null;
  operatorForm?: IOperatorForm | null;
  assiete?: IAssiete | null;
}

export class FormPaieLigne implements IFormPaieLigne {
  constructor(
    public id?: number,
    public priorite?: number | null,
    public code?: string | null,
    public libEn?: string | null,
    public libAr?: string | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public createdBy?: string | null,
    public op?: string | null,
    public util?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public formPaie?: IFormPaie | null,
    public operatorForm?: IOperatorForm | null,
    public assiete?: IAssiete | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getFormPaieLigneIdentifier(formPaieLigne: IFormPaieLigne): number | undefined {
  return formPaieLigne.id;
}
