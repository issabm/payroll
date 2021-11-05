import * as dayjs from 'dayjs';
import { IAssiete } from 'app/entities/assiete/assiete.model';
import { IEchlon } from 'app/entities/echlon/echlon.model';
import { IEmploi } from 'app/entities/emploi/emploi.model';
import { ICategory } from 'app/entities/category/category.model';
import { IAffiliation } from 'app/entities/affiliation/affiliation.model';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { ISexe } from 'app/entities/sexe/sexe.model';

export interface IMatricePaie {
  id?: number;
  code?: string | null;
  libAr?: string | null;
  libEn?: string | null;
  isDisplay?: boolean | null;
  anneeDebut?: number | null;
  moisDebut?: number | null;
  anneeFin?: number | null;
  moisFin?: number | null;
  util?: string | null;
  dateop?: dayjs.Dayjs | null;
  modifiedBy?: string | null;
  op?: string | null;
  isDeleted?: boolean | null;
  createdDate?: dayjs.Dayjs | null;
  modifiedDate?: dayjs.Dayjs | null;
  assiete?: IAssiete | null;
  echlon?: IEchlon | null;
  emploi?: IEmploi | null;
  category?: ICategory | null;
  affilication?: IAffiliation | null;
  entreprise?: IEntreprise | null;
  groupe?: IGroupe | null;
  sexe?: ISexe | null;
}

export class MatricePaie implements IMatricePaie {
  constructor(
    public id?: number,
    public code?: string | null,
    public libAr?: string | null,
    public libEn?: string | null,
    public isDisplay?: boolean | null,
    public anneeDebut?: number | null,
    public moisDebut?: number | null,
    public anneeFin?: number | null,
    public moisFin?: number | null,
    public util?: string | null,
    public dateop?: dayjs.Dayjs | null,
    public modifiedBy?: string | null,
    public op?: string | null,
    public isDeleted?: boolean | null,
    public createdDate?: dayjs.Dayjs | null,
    public modifiedDate?: dayjs.Dayjs | null,
    public assiete?: IAssiete | null,
    public echlon?: IEchlon | null,
    public emploi?: IEmploi | null,
    public category?: ICategory | null,
    public affilication?: IAffiliation | null,
    public entreprise?: IEntreprise | null,
    public groupe?: IGroupe | null,
    public sexe?: ISexe | null
  ) {
    this.isDisplay = this.isDisplay ?? false;
    this.isDeleted = this.isDeleted ?? false;
  }
}

export function getMatricePaieIdentifier(matricePaie: IMatricePaie): number | undefined {
  return matricePaie.id;
}
