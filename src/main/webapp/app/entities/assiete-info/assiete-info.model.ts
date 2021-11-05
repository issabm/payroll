import * as dayjs from 'dayjs';
import { IRebrique } from 'app/entities/rebrique/rebrique.model';
import { IAssiete } from 'app/entities/assiete/assiete.model';
import { IModeCalcul } from 'app/entities/mode-calcul/mode-calcul.model';

export interface IAssieteInfo {
  id?: number;
  priorite?: number | null;
  val?: number | null;
  util?: string | null;
  dateSituation?: dayjs.Dayjs | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  createdBy?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  rebrique?: IRebrique | null;
  assiete?: IAssiete | null;
  modeCal?: IModeCalcul | null;
}

export class AssieteInfo implements IAssieteInfo {
  constructor(
    public id?: number,
    public priorite?: number | null,
    public val?: number | null,
    public util?: string | null,
    public dateSituation?: dayjs.Dayjs | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public createdBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public rebrique?: IRebrique | null,
    public assiete?: IAssiete | null,
    public modeCal?: IModeCalcul | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getAssieteInfoIdentifier(assieteInfo: IAssieteInfo): number | undefined {
  return assieteInfo.id;
}
