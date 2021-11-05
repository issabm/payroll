import * as dayjs from 'dayjs';
import { IManagementResource } from 'app/entities/management-resource/management-resource.model';

export interface IManagementResourceProfile {
  id?: number;
  profile?: string | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  createdBy?: string | null;
  op?: string | null;
  util?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  ressourceManage?: IManagementResource | null;
}

export class ManagementResourceProfile implements IManagementResourceProfile {
  constructor(
    public id?: number,
    public profile?: string | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public createdBy?: string | null,
    public op?: string | null,
    public util?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public ressourceManage?: IManagementResource | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getManagementResourceProfileIdentifier(managementResourceProfile: IManagementResourceProfile): number | undefined {
  return managementResourceProfile.id;
}
