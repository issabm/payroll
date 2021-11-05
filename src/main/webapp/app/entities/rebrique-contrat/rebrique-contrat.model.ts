import * as dayjs from 'dayjs';
import { IRebrique } from 'app/entities/rebrique/rebrique.model';
import { ISousTypeContrat } from 'app/entities/sous-type-contrat/sous-type-contrat.model';
import { ITypeContrat } from 'app/entities/type-contrat/type-contrat.model';

export interface IRebriqueContrat {
  id?: number;
  util?: string | null;
  dateSituation?: dayjs.Dayjs | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  createdBy?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  rebrqiue?: IRebrique | null;
  sousTypeContrat?: ISousTypeContrat | null;
  typeContrat?: ITypeContrat | null;
}

export class RebriqueContrat implements IRebriqueContrat {
  constructor(
    public id?: number,
    public util?: string | null,
    public dateSituation?: dayjs.Dayjs | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public createdBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public rebrqiue?: IRebrique | null,
    public sousTypeContrat?: ISousTypeContrat | null,
    public typeContrat?: ITypeContrat | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getRebriqueContratIdentifier(rebriqueContrat: IRebriqueContrat): number | undefined {
  return rebriqueContrat.id;
}
