import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISoldeAbsenceContrat, getSoldeAbsenceContratIdentifier } from '../solde-absence-contrat.model';

export type EntityResponseType = HttpResponse<ISoldeAbsenceContrat>;
export type EntityArrayResponseType = HttpResponse<ISoldeAbsenceContrat[]>;

@Injectable({ providedIn: 'root' })
export class SoldeAbsenceContratService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/solde-absence-contrats');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(soldeAbsenceContrat: ISoldeAbsenceContrat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(soldeAbsenceContrat);
    return this.http
      .post<ISoldeAbsenceContrat>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(soldeAbsenceContrat: ISoldeAbsenceContrat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(soldeAbsenceContrat);
    return this.http
      .put<ISoldeAbsenceContrat>(`${this.resourceUrl}/${getSoldeAbsenceContratIdentifier(soldeAbsenceContrat) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(soldeAbsenceContrat: ISoldeAbsenceContrat): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(soldeAbsenceContrat);
    return this.http
      .patch<ISoldeAbsenceContrat>(`${this.resourceUrl}/${getSoldeAbsenceContratIdentifier(soldeAbsenceContrat) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISoldeAbsenceContrat>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISoldeAbsenceContrat[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSoldeAbsenceContratToCollectionIfMissing(
    soldeAbsenceContratCollection: ISoldeAbsenceContrat[],
    ...soldeAbsenceContratsToCheck: (ISoldeAbsenceContrat | null | undefined)[]
  ): ISoldeAbsenceContrat[] {
    const soldeAbsenceContrats: ISoldeAbsenceContrat[] = soldeAbsenceContratsToCheck.filter(isPresent);
    if (soldeAbsenceContrats.length > 0) {
      const soldeAbsenceContratCollectionIdentifiers = soldeAbsenceContratCollection.map(
        soldeAbsenceContratItem => getSoldeAbsenceContratIdentifier(soldeAbsenceContratItem)!
      );
      const soldeAbsenceContratsToAdd = soldeAbsenceContrats.filter(soldeAbsenceContratItem => {
        const soldeAbsenceContratIdentifier = getSoldeAbsenceContratIdentifier(soldeAbsenceContratItem);
        if (soldeAbsenceContratIdentifier == null || soldeAbsenceContratCollectionIdentifiers.includes(soldeAbsenceContratIdentifier)) {
          return false;
        }
        soldeAbsenceContratCollectionIdentifiers.push(soldeAbsenceContratIdentifier);
        return true;
      });
      return [...soldeAbsenceContratsToAdd, ...soldeAbsenceContratCollection];
    }
    return soldeAbsenceContratCollection;
  }

  protected convertDateFromClient(soldeAbsenceContrat: ISoldeAbsenceContrat): ISoldeAbsenceContrat {
    return Object.assign({}, soldeAbsenceContrat, {
      dateop: soldeAbsenceContrat.dateop?.isValid() ? soldeAbsenceContrat.dateop.toJSON() : undefined,
      createdDate: soldeAbsenceContrat.createdDate?.isValid() ? soldeAbsenceContrat.createdDate.toJSON() : undefined,
      modifiedDate: soldeAbsenceContrat.modifiedDate?.isValid() ? soldeAbsenceContrat.modifiedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateop = res.body.dateop ? dayjs(res.body.dateop) : undefined;
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.modifiedDate = res.body.modifiedDate ? dayjs(res.body.modifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((soldeAbsenceContrat: ISoldeAbsenceContrat) => {
        soldeAbsenceContrat.dateop = soldeAbsenceContrat.dateop ? dayjs(soldeAbsenceContrat.dateop) : undefined;
        soldeAbsenceContrat.createdDate = soldeAbsenceContrat.createdDate ? dayjs(soldeAbsenceContrat.createdDate) : undefined;
        soldeAbsenceContrat.modifiedDate = soldeAbsenceContrat.modifiedDate ? dayjs(soldeAbsenceContrat.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
