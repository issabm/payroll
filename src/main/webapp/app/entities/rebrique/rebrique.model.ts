import * as dayjs from 'dayjs';
import { ISens } from 'app/entities/sens/sens.model';
import { IConcerne } from 'app/entities/concerne/concerne.model';
import { IFrequence } from 'app/entities/frequence/frequence.model';

export interface IRebrique {
  id?: number;
  priorite?: number | null;
  code?: string | null;
  libAr?: string | null;
  libEn?: string | null;
  inTax?: boolean | null;
  inSocial?: boolean | null;
  inBp?: boolean | null;
  val?: number | null;
  dateSituation?: dayjs.Dayjs | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  createdBy?: string | null;
  util?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  sens?: ISens | null;
  concerne?: IConcerne | null;
  frequence?: IFrequence | null;
}

export class Rebrique implements IRebrique {
  constructor(
    public id?: number,
    public priorite?: number | null,
    public code?: string | null,
    public libAr?: string | null,
    public libEn?: string | null,
    public inTax?: boolean | null,
    public inSocial?: boolean | null,
    public inBp?: boolean | null,
    public val?: number | null,
    public dateSituation?: dayjs.Dayjs | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public createdBy?: string | null,
    public util?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public sens?: ISens | null,
    public concerne?: IConcerne | null,
    public frequence?: IFrequence | null
  ) {
    this.inTax = this.inTax ?? false;
    this.inSocial = this.inSocial ?? false;
    this.inBp = this.inBp ?? false;
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getRebriqueIdentifier(rebrique: IRebrique): number | undefined {
  return rebrique.id;
}
