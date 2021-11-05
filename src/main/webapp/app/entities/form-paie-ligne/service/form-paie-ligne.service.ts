import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFormPaieLigne, getFormPaieLigneIdentifier } from '../form-paie-ligne.model';

export type EntityResponseType = HttpResponse<IFormPaieLigne>;
export type EntityArrayResponseType = HttpResponse<IFormPaieLigne[]>;

@Injectable({ providedIn: 'root' })
export class FormPaieLigneService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/form-paie-lignes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(formPaieLigne: IFormPaieLigne): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(formPaieLigne);
    return this.http
      .post<IFormPaieLigne>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(formPaieLigne: IFormPaieLigne): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(formPaieLigne);
    return this.http
      .put<IFormPaieLigne>(`${this.resourceUrl}/${getFormPaieLigneIdentifier(formPaieLigne) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(formPaieLigne: IFormPaieLigne): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(formPaieLigne);
    return this.http
      .patch<IFormPaieLigne>(`${this.resourceUrl}/${getFormPaieLigneIdentifier(formPaieLigne) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFormPaieLigne>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFormPaieLigne[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFormPaieLigneToCollectionIfMissing(
    formPaieLigneCollection: IFormPaieLigne[],
    ...formPaieLignesToCheck: (IFormPaieLigne | null | undefined)[]
  ): IFormPaieLigne[] {
    const formPaieLignes: IFormPaieLigne[] = formPaieLignesToCheck.filter(isPresent);
    if (formPaieLignes.length > 0) {
      const formPaieLigneCollectionIdentifiers = formPaieLigneCollection.map(
        formPaieLigneItem => getFormPaieLigneIdentifier(formPaieLigneItem)!
      );
      const formPaieLignesToAdd = formPaieLignes.filter(formPaieLigneItem => {
        const formPaieLigneIdentifier = getFormPaieLigneIdentifier(formPaieLigneItem);
        if (formPaieLigneIdentifier == null || formPaieLigneCollectionIdentifiers.includes(formPaieLigneIdentifier)) {
          return false;
        }
        formPaieLigneCollectionIdentifiers.push(formPaieLigneIdentifier);
        return true;
      });
      return [...formPaieLignesToAdd, ...formPaieLigneCollection];
    }
    return formPaieLigneCollection;
  }

  protected convertDateFromClient(formPaieLigne: IFormPaieLigne): IFormPaieLigne {
    return Object.assign({}, formPaieLigne, {
      dateop: formPaieLigne.dateop?.isValid() ? formPaieLigne.dateop.toJSON() : undefined,
      createdDate: formPaieLigne.createdDate?.isValid() ? formPaieLigne.createdDate.toJSON() : undefined,
      modifiedDate: formPaieLigne.modifiedDate?.isValid() ? formPaieLigne.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((formPaieLigne: IFormPaieLigne) => {
        formPaieLigne.dateop = formPaieLigne.dateop ? dayjs(formPaieLigne.dateop) : undefined;
        formPaieLigne.createdDate = formPaieLigne.createdDate ? dayjs(formPaieLigne.createdDate) : undefined;
        formPaieLigne.modifiedDate = formPaieLigne.modifiedDate ? dayjs(formPaieLigne.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
