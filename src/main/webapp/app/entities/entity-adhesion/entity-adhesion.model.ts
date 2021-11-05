import * as dayjs from 'dayjs';
import { INatureAdhesion } from 'app/entities/nature-adhesion/nature-adhesion.model';

export interface IEntityAdhesion {
  id?: number;
  code?: string | null;
  libEn?: string | null;
  libAr?: string | null;
  util?: string | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  natureAdhesion?: INatureAdhesion | null;
}

export class EntityAdhesion implements IEntityAdhesion {
  constructor(
    public id?: number,
    public code?: string | null,
    public libEn?: string | null,
    public libAr?: string | null,
    public util?: string | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public natureAdhesion?: INatureAdhesion | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getEntityAdhesionIdentifier(entityAdhesion: IEntityAdhesion): number | undefined {
  return entityAdhesion.id;
}
