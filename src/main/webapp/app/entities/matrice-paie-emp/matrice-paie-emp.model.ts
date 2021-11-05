import * as dayjs from 'dayjs';
import { IMatricePaie } from 'app/entities/matrice-paie/matrice-paie.model';
import { IEmploye } from 'app/entities/employe/employe.model';
import { ISituation } from 'app/entities/situation/situation.model';

export interface IMatricePaieEmp {
  id?: number;
  util?: string | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  matricePaie?: IMatricePaie | null;
  employe?: IEmploye | null;
  situation?: ISituation | null;
}

export class MatricePaieEmp implements IMatricePaieEmp {
  constructor(
    public id?: number,
    public util?: string | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public matricePaie?: IMatricePaie | null,
    public employe?: IEmploye | null,
    public situation?: ISituation | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getMatricePaieEmpIdentifier(matricePaieEmp: IMatricePaieEmp): number | undefined {
  return matricePaieEmp.id;
}
