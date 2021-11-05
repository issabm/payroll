import * as dayjs from 'dayjs';
import { ISituation } from 'app/entities/situation/situation.model';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { IAffiliation } from 'app/entities/affiliation/affiliation.model';

export interface IMouvementPaie {
  id?: number;
  code?: string | null;
  lib?: string | null;
  annee?: number | null;
  mois?: number | null;
  dateCalcul?: dayjs.Dayjs | null;
  dateValid?: dayjs.Dayjs | null;
  datePayroll?: dayjs.Dayjs | null;
  totalCotis?: number | null;
  totalRetenue?: number | null;
  totalTaxable?: number | null;
  totalTax?: number | null;
  totalNet?: number | null;
  totalNetDevise?: number | null;
  tauxChange?: number | null;
  calculBy?: string | null;
  effectBy?: string | null;
  dateSituation?: dayjs.Dayjs | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  createdBy?: string | null;
  op?: string | null;
  util?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  situation?: ISituation | null;
  groupe?: IGroupe | null;
  entreprise?: IEntreprise | null;
  affiliation?: IAffiliation | null;
}

export class MouvementPaie implements IMouvementPaie {
  constructor(
    public id?: number,
    public code?: string | null,
    public lib?: string | null,
    public annee?: number | null,
    public mois?: number | null,
    public dateCalcul?: dayjs.Dayjs | null,
    public dateValid?: dayjs.Dayjs | null,
    public datePayroll?: dayjs.Dayjs | null,
    public totalCotis?: number | null,
    public totalRetenue?: number | null,
    public totalTaxable?: number | null,
    public totalTax?: number | null,
    public totalNet?: number | null,
    public totalNetDevise?: number | null,
    public tauxChange?: number | null,
    public calculBy?: string | null,
    public effectBy?: string | null,
    public dateSituation?: dayjs.Dayjs | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public createdBy?: string | null,
    public op?: string | null,
    public util?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public situation?: ISituation | null,
    public groupe?: IGroupe | null,
    public entreprise?: IEntreprise | null,
    public affiliation?: IAffiliation | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getMouvementPaieIdentifier(mouvementPaie: IMouvementPaie): number | undefined {
  return mouvementPaie.id;
}
