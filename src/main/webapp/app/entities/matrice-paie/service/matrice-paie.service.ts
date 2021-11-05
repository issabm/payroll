import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMatricePaie, getMatricePaieIdentifier } from '../matrice-paie.model';

export type EntityResponseType = HttpResponse<IMatricePaie>;
export type EntityArrayResponseType = HttpResponse<IMatricePaie[]>;

@Injectable({ providedIn: 'root' })
export class MatricePaieService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/matrice-paies');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(matricePaie: IMatricePaie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(matricePaie);
    return this.http
      .post<IMatricePaie>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(matricePaie: IMatricePaie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(matricePaie);
    return this.http
      .put<IMatricePaie>(`${this.resourceUrl}/${getMatricePaieIdentifier(matricePaie) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(matricePaie: IMatricePaie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(matricePaie);
    return this.http
      .patch<IMatricePaie>(`${this.resourceUrl}/${getMatricePaieIdentifier(matricePaie) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMatricePaie>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMatricePaie[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMatricePaieToCollectionIfMissing(
    matricePaieCollection: IMatricePaie[],
    ...matricePaiesToCheck: (IMatricePaie | null | undefined)[]
  ): IMatricePaie[] {
    const matricePaies: IMatricePaie[] = matricePaiesToCheck.filter(isPresent);
    if (matricePaies.length > 0) {
      const matricePaieCollectionIdentifiers = matricePaieCollection.map(matricePaieItem => getMatricePaieIdentifier(matricePaieItem)!);
      const matricePaiesToAdd = matricePaies.filter(matricePaieItem => {
        const matricePaieIdentifier = getMatricePaieIdentifier(matricePaieItem);
        if (matricePaieIdentifier == null || matricePaieCollectionIdentifiers.includes(matricePaieIdentifier)) {
          return false;
        }
        matricePaieCollectionIdentifiers.push(matricePaieIdentifier);
        return true;
      });
      return [...matricePaiesToAdd, ...matricePaieCollection];
    }
    return matricePaieCollection;
  }

  protected convertDateFromClient(matricePaie: IMatricePaie): IMatricePaie {
    return Object.assign({}, matricePaie, {
      dateop: matricePaie.dateop?.isValid() ? matricePaie.dateop.toJSON() : undefined,
      createdDate: matricePaie.createdDate?.isValid() ? matricePaie.createdDate.toJSON() : undefined,
      modifiedDate: matricePaie.modifiedDate?.isValid() ? matricePaie.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((matricePaie: IMatricePaie) => {
        matricePaie.dateop = matricePaie.dateop ? dayjs(matricePaie.dateop) : undefined;
        matricePaie.createdDate = matricePaie.createdDate ? dayjs(matricePaie.createdDate) : undefined;
        matricePaie.modifiedDate = matricePaie.modifiedDate ? dayjs(matricePaie.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
