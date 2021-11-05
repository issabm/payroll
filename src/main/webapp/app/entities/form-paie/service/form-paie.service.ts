import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFormPaie, getFormPaieIdentifier } from '../form-paie.model';

export type EntityResponseType = HttpResponse<IFormPaie>;
export type EntityArrayResponseType = HttpResponse<IFormPaie[]>;

@Injectable({ providedIn: 'root' })
export class FormPaieService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/form-paies');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(formPaie: IFormPaie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(formPaie);
    return this.http
      .post<IFormPaie>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(formPaie: IFormPaie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(formPaie);
    return this.http
      .put<IFormPaie>(`${this.resourceUrl}/${getFormPaieIdentifier(formPaie) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(formPaie: IFormPaie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(formPaie);
    return this.http
      .patch<IFormPaie>(`${this.resourceUrl}/${getFormPaieIdentifier(formPaie) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFormPaie>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFormPaie[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFormPaieToCollectionIfMissing(formPaieCollection: IFormPaie[], ...formPaiesToCheck: (IFormPaie | null | undefined)[]): IFormPaie[] {
    const formPaies: IFormPaie[] = formPaiesToCheck.filter(isPresent);
    if (formPaies.length > 0) {
      const formPaieCollectionIdentifiers = formPaieCollection.map(formPaieItem => getFormPaieIdentifier(formPaieItem)!);
      const formPaiesToAdd = formPaies.filter(formPaieItem => {
        const formPaieIdentifier = getFormPaieIdentifier(formPaieItem);
        if (formPaieIdentifier == null || formPaieCollectionIdentifiers.includes(formPaieIdentifier)) {
          return false;
        }
        formPaieCollectionIdentifiers.push(formPaieIdentifier);
        return true;
      });
      return [...formPaiesToAdd, ...formPaieCollection];
    }
    return formPaieCollection;
  }

  protected convertDateFromClient(formPaie: IFormPaie): IFormPaie {
    return Object.assign({}, formPaie, {
      dateop: formPaie.dateop?.isValid() ? formPaie.dateop.toJSON() : undefined,
      createdDate: formPaie.createdDate?.isValid() ? formPaie.createdDate.toJSON() : undefined,
      modifiedDate: formPaie.modifiedDate?.isValid() ? formPaie.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((formPaie: IFormPaie) => {
        formPaie.dateop = formPaie.dateop ? dayjs(formPaie.dateop) : undefined;
        formPaie.createdDate = formPaie.createdDate ? dayjs(formPaie.createdDate) : undefined;
        formPaie.modifiedDate = formPaie.modifiedDate ? dayjs(formPaie.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
