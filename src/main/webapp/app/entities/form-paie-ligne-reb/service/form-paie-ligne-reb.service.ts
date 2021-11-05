import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFormPaieLigneReb, getFormPaieLigneRebIdentifier } from '../form-paie-ligne-reb.model';

export type EntityResponseType = HttpResponse<IFormPaieLigneReb>;
export type EntityArrayResponseType = HttpResponse<IFormPaieLigneReb[]>;

@Injectable({ providedIn: 'root' })
export class FormPaieLigneRebService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/form-paie-ligne-rebs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(formPaieLigneReb: IFormPaieLigneReb): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(formPaieLigneReb);
    return this.http
      .post<IFormPaieLigneReb>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(formPaieLigneReb: IFormPaieLigneReb): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(formPaieLigneReb);
    return this.http
      .put<IFormPaieLigneReb>(`${this.resourceUrl}/${getFormPaieLigneRebIdentifier(formPaieLigneReb) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(formPaieLigneReb: IFormPaieLigneReb): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(formPaieLigneReb);
    return this.http
      .patch<IFormPaieLigneReb>(`${this.resourceUrl}/${getFormPaieLigneRebIdentifier(formPaieLigneReb) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFormPaieLigneReb>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFormPaieLigneReb[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFormPaieLigneRebToCollectionIfMissing(
    formPaieLigneRebCollection: IFormPaieLigneReb[],
    ...formPaieLigneRebsToCheck: (IFormPaieLigneReb | null | undefined)[]
  ): IFormPaieLigneReb[] {
    const formPaieLigneRebs: IFormPaieLigneReb[] = formPaieLigneRebsToCheck.filter(isPresent);
    if (formPaieLigneRebs.length > 0) {
      const formPaieLigneRebCollectionIdentifiers = formPaieLigneRebCollection.map(
        formPaieLigneRebItem => getFormPaieLigneRebIdentifier(formPaieLigneRebItem)!
      );
      const formPaieLigneRebsToAdd = formPaieLigneRebs.filter(formPaieLigneRebItem => {
        const formPaieLigneRebIdentifier = getFormPaieLigneRebIdentifier(formPaieLigneRebItem);
        if (formPaieLigneRebIdentifier == null || formPaieLigneRebCollectionIdentifiers.includes(formPaieLigneRebIdentifier)) {
          return false;
        }
        formPaieLigneRebCollectionIdentifiers.push(formPaieLigneRebIdentifier);
        return true;
      });
      return [...formPaieLigneRebsToAdd, ...formPaieLigneRebCollection];
    }
    return formPaieLigneRebCollection;
  }

  protected convertDateFromClient(formPaieLigneReb: IFormPaieLigneReb): IFormPaieLigneReb {
    return Object.assign({}, formPaieLigneReb, {
      dateop: formPaieLigneReb.dateop?.isValid() ? formPaieLigneReb.dateop.toJSON() : undefined,
      createdDate: formPaieLigneReb.createdDate?.isValid() ? formPaieLigneReb.createdDate.toJSON() : undefined,
      modifiedDate: formPaieLigneReb.modifiedDate?.isValid() ? formPaieLigneReb.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((formPaieLigneReb: IFormPaieLigneReb) => {
        formPaieLigneReb.dateop = formPaieLigneReb.dateop ? dayjs(formPaieLigneReb.dateop) : undefined;
        formPaieLigneReb.createdDate = formPaieLigneReb.createdDate ? dayjs(formPaieLigneReb.createdDate) : undefined;
        formPaieLigneReb.modifiedDate = formPaieLigneReb.modifiedDate ? dayjs(formPaieLigneReb.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
