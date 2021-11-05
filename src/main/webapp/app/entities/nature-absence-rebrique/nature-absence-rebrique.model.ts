import * as dayjs from 'dayjs';
import { INatureAbsence } from 'app/entities/nature-absence/nature-absence.model';
import { IRebrique } from 'app/entities/rebrique/rebrique.model';

export interface INatureAbsenceRebrique {
  id?: number;
  code?: string | null;
  libAr?: string | null;
  libEn?: string | null;
  valueTaken?: number | null;
  util?: string | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  natureAbsence?: INatureAbsence | null;
  rebrique?: IRebrique | null;
}

export class NatureAbsenceRebrique implements INatureAbsenceRebrique {
  constructor(
    public id?: number,
    public code?: string | null,
    public libAr?: string | null,
    public libEn?: string | null,
    public valueTaken?: number | null,
    public util?: string | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public natureAbsence?: INatureAbsence | null,
    public rebrique?: IRebrique | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getNatureAbsenceRebriqueIdentifier(natureAbsenceRebrique: INatureAbsenceRebrique): number | undefined {
  return natureAbsenceRebrique.id;
}
