import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRebriqueContrat, getRebriqueContratIdentifier } from '../rebrique-contrat.model';

export type EntityResponseType = HttpResponse<IRebriqueContrat>;
export type EntityArrayResponseType = HttpResponse<IRebriqueContrat[]>;

@Injectable({ providedIn: 'root' })
export class RebriqueContratService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/rebrique-contrats');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(rebriqueContrat: IRebriqueContrat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rebriqueContrat);
    return this.http
      .post<IRebriqueContrat>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(rebriqueContrat: IRebriqueContrat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rebriqueContrat);
    return this.http
      .put<IRebriqueContrat>(`${this.resourceUrl}/${getRebriqueContratIdentifier(rebriqueContrat) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(rebriqueContrat: IRebriqueContrat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rebriqueContrat);
    return this.http
      .patch<IRebriqueContrat>(`${this.resourceUrl}/${getRebriqueContratIdentifier(rebriqueContrat) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRebriqueContrat>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRebriqueContrat[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRebriqueContratToCollectionIfMissing(
    rebriqueContratCollection: IRebriqueContrat[],
    ...rebriqueContratsToCheck: (IRebriqueContrat | null | undefined)[]
  ): IRebriqueContrat[] {
    const rebriqueContrats: IRebriqueContrat[] = rebriqueContratsToCheck.filter(isPresent);
    if (rebriqueContrats.length > 0) {
      const rebriqueContratCollectionIdentifiers = rebriqueContratCollection.map(
        rebriqueContratItem => getRebriqueContratIdentifier(rebriqueContratItem)!
      );
      const rebriqueContratsToAdd = rebriqueContrats.filter(rebriqueContratItem => {
        const rebriqueContratIdentifier = getRebriqueContratIdentifier(rebriqueContratItem);
        if (rebriqueContratIdentifier == null || rebriqueContratCollectionIdentifiers.includes(rebriqueContratIdentifier)) {
          return false;
        }
        rebriqueContratCollectionIdentifiers.push(rebriqueContratIdentifier);
        return true;
      });
      return [...rebriqueContratsToAdd, ...rebriqueContratCollection];
    }
    return rebriqueContratCollection;
  }

  protected convertDateFromClient(rebriqueContrat: IRebriqueContrat): IRebriqueContrat {
    return Object.assign({}, rebriqueContrat, {
      dateSituation: rebriqueContrat.dateSituation?.isValid() ? rebriqueContrat.dateSituation.format(DATE_FORMAT) : undefined,
      dateop: rebriqueContrat.dateop?.isValid() ? rebriqueContrat.dateop.toJSON() : undefined,
      createdDate: rebriqueContrat.createdDate?.isValid() ? rebriqueContrat.createdDate.toJSON() : undefined,
      modifiedDate: rebriqueContrat.modifiedDate?.isValid() ? rebriqueContrat.modifiedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateSituation = res.body.dateSituation ? dayjs(res.body.dateSituation) : undefined;
      res.body.dateop = res.body.dateop ? dayjs(res.body.dateop) : undefined;
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.modifiedDate = res.body.modifiedDate ? dayjs(res.body.modifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((rebriqueContrat: IRebriqueContrat) => {
        rebriqueContrat.dateSituation = rebriqueContrat.dateSituation ? dayjs(rebriqueContrat.dateSituation) : undefined;
        rebriqueContrat.dateop = rebriqueContrat.dateop ? dayjs(rebriqueContrat.dateop) : undefined;
        rebriqueContrat.createdDate = rebriqueContrat.createdDate ? dayjs(rebriqueContrat.createdDate) : undefined;
        rebriqueContrat.modifiedDate = rebriqueContrat.modifiedDate ? dayjs(rebriqueContrat.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
