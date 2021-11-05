import * as dayjs from 'dayjs';

export interface IAssiete {
  id?: number;
  priorite?: number | null;
  code?: string | null;
  lib?: string | null;
  dateSituation?: dayjs.Dayjs | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  createdBy?: string | null;
  util?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
}

export class Assiete implements IAssiete {
  constructor(
    public id?: number,
    public priorite?: number | null,
    public code?: string | null,
    public lib?: string | null,
    public dateSituation?: dayjs.Dayjs | null,
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

export function getAssieteIdentifier(assiete: IAssiete): number | undefined {
  return assiete.id;
}
