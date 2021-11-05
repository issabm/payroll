import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INatureAbsenceRebrique, getNatureAbsenceRebriqueIdentifier } from '../nature-absence-rebrique.model';

export type EntityResponseType = HttpResponse<INatureAbsenceRebrique>;
export type EntityArrayResponseType = HttpResponse<INatureAbsenceRebrique[]>;

@Injectable({ providedIn: 'root' })
export class NatureAbsenceRebriqueService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/nature-absence-rebriques');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(natureAbsenceRebrique: INatureAbsenceRebrique): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(natureAbsenceRebrique);
    return this.http
      .post<INatureAbsenceRebrique>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(natureAbsenceRebrique: INatureAbsenceRebrique): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(natureAbsenceRebrique);
    return this.http
      .put<INatureAbsenceRebrique>(`${this.resourceUrl}/${getNatureAbsenceRebriqueIdentifier(natureAbsenceRebrique) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(natureAbsenceRebrique: INatureAbsenceRebrique): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(natureAbsenceRebrique);
    return this.http
      .patch<INatureAbsenceRebrique>(`${this.resourceUrl}/${getNatureAbsenceRebriqueIdentifier(natureAbsenceRebrique) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INatureAbsenceRebrique>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INatureAbsenceRebrique[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addNatureAbsenceRebriqueToCollectionIfMissing(
    natureAbsenceRebriqueCollection: INatureAbsenceRebrique[],
    ...natureAbsenceRebriquesToCheck: (INatureAbsenceRebrique | null | undefined)[]
  ): INatureAbsenceRebrique[] {
    const natureAbsenceRebriques: INatureAbsenceRebrique[] = natureAbsenceRebriquesToCheck.filter(isPresent);
    if (natureAbsenceRebriques.length > 0) {
      const natureAbsenceRebriqueCollectionIdentifiers = natureAbsenceRebriqueCollection.map(
        natureAbsenceRebriqueItem => getNatureAbsenceRebriqueIdentifier(natureAbsenceRebriqueItem)!
      );
      const natureAbsenceRebriquesToAdd = natureAbsenceRebriques.filter(natureAbsenceRebriqueItem => {
        const natureAbsenceRebriqueIdentifier = getNatureAbsenceRebriqueIdentifier(natureAbsenceRebriqueItem);
        if (
          natureAbsenceRebriqueIdentifier == null ||
          natureAbsenceRebriqueCollectionIdentifiers.includes(natureAbsenceRebriqueIdentifier)
        ) {
          return false;
        }
        natureAbsenceRebriqueCollectionIdentifiers.push(natureAbsenceRebriqueIdentifier);
        return true;
      });
      return [...natureAbsenceRebriquesToAdd, ...natureAbsenceRebriqueCollection];
    }
    return natureAbsenceRebriqueCollection;
  }

  protected convertDateFromClient(natureAbsenceRebrique: INatureAbsenceRebrique): INatureAbsenceRebrique {
    return Object.assign({}, natureAbsenceRebrique, {
      dateop: natureAbsenceRebrique.dateop?.isValid() ? natureAbsenceRebrique.dateop.toJSON() : undefined,
      createdDate: natureAbsenceRebrique.createdDate?.isValid() ? natureAbsenceRebrique.createdDate.toJSON() : undefined,
      modifiedDate: natureAbsenceRebrique.modifiedDate?.isValid() ? natureAbsenceRebrique.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((natureAbsenceRebrique: INatureAbsenceRebrique) => {
        natureAbsenceRebrique.dateop = natureAbsenceRebrique.dateop ? dayjs(natureAbsenceRebrique.dateop) : undefined;
        natureAbsenceRebrique.createdDate = natureAbsenceRebrique.createdDate ? dayjs(natureAbsenceRebrique.createdDate) : undefined;
        natureAbsenceRebrique.modifiedDate = natureAbsenceRebrique.modifiedDate ? dayjs(natureAbsenceRebrique.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
