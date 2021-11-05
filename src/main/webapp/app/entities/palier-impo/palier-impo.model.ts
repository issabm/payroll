import * as dayjs from 'dayjs';
import { IPays } from 'app/entities/pays/pays.model';
import { ISituation } from 'app/entities/situation/situation.model';

export interface IPalierImpo {
  id?: number;
  annee?: number | null;
  payrollMin?: number | null;
  payrollMax?: number | null;
  impoValue?: number | null;
  util?: string | null;
  dateop?: dayjs.Dayjs | null;
  dateModif?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  pays?: IPays | null;
  situation?: ISituation | null;
}

export class PalierImpo implements IPalierImpo {
  constructor(
    public id?: number,
    public annee?: number | null,
    public payrollMin?: number | null,
    public payrollMax?: number | null,
    public impoValue?: number | null,
    public util?: string | null,
    public dateop?: dayjs.Dayjs | null,
    public dateModif?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public pays?: IPays | null,
    public situation?: ISituation | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getPalierImpoIdentifier(palierImpo: IPalierImpo): number | undefined {
  return palierImpo.id;
}
