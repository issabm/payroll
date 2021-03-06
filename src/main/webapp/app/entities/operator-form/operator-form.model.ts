import * as dayjs from 'dayjs';

export interface IOperatorForm {
  id?: number;
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
}

export class OperatorForm implements IOperatorForm {
  constructor(
    public id?: number,
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
    public modifiedDate?: dayjs.Dayjs | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getOperatorFormIdentifier(operatorForm: IOperatorForm): number | undefined {
  return operatorForm.id;
}
