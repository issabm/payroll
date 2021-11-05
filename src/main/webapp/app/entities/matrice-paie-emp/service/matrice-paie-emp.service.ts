import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMatricePaieEmp, getMatricePaieEmpIdentifier } from '../matrice-paie-emp.model';

export type EntityResponseType = HttpResponse<IMatricePaieEmp>;
export type EntityArrayResponseType = HttpResponse<IMatricePaieEmp[]>;

@Injectable({ providedIn: 'root' })
export class MatricePaieEmpService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/matrice-paie-emps');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(matricePaieEmp: IMatricePaieEmp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(matricePaieEmp);
    return this.http
      .post<IMatricePaieEmp>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(matricePaieEmp: IMatricePaieEmp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(matricePaieEmp);
    return this.http
      .put<IMatricePaieEmp>(`${this.resourceUrl}/${getMatricePaieEmpIdentifier(matricePaieEmp) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(matricePaieEmp: IMatricePaieEmp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(matricePaieEmp);
    return this.http
      .patch<IMatricePaieEmp>(`${this.resourceUrl}/${getMatricePaieEmpIdentifier(matricePaieEmp) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMatricePaieEmp>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMatricePaieEmp[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMatricePaieEmpToCollectionIfMissing(
    matricePaieEmpCollection: IMatricePaieEmp[],
    ...matricePaieEmpsToCheck: (IMatricePaieEmp | null | undefined)[]
  ): IMatricePaieEmp[] {
    const matricePaieEmps: IMatricePaieEmp[] = matricePaieEmpsToCheck.filter(isPresent);
    if (matricePaieEmps.length > 0) {
      const matricePaieEmpCollectionIdentifiers = matricePaieEmpCollection.map(
        matricePaieEmpItem => getMatricePaieEmpIdentifier(matricePaieEmpItem)!
      );
      const matricePaieEmpsToAdd = matricePaieEmps.filter(matricePaieEmpItem => {
        const matricePaieEmpIdentifier = getMatricePaieEmpIdentifier(matricePaieEmpItem);
        if (matricePaieEmpIdentifier == null || matricePaieEmpCollectionIdentifiers.includes(matricePaieEmpIdentifier)) {
          return false;
        }
        matricePaieEmpCollectionIdentifiers.push(matricePaieEmpIdentifier);
        return true;
      });
      return [...matricePaieEmpsToAdd, ...matricePaieEmpCollection];
    }
    return matricePaieEmpCollection;
  }

  protected convertDateFromClient(matricePaieEmp: IMatricePaieEmp): IMatricePaieEmp {
    return Object.assign({}, matricePaieEmp, {
      dateop: matricePaieEmp.dateop?.isValid() ? matricePaieEmp.dateop.toJSON() : undefined,
      createdDate: matricePaieEmp.createdDate?.isValid() ? matricePaieEmp.createdDate.toJSON() : undefined,
      modifiedDate: matricePaieEmp.modifiedDate?.isValid() ? matricePaieEmp.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((matricePaieEmp: IMatricePaieEmp) => {
        matricePaieEmp.dateop = matricePaieEmp.dateop ? dayjs(matricePaieEmp.dateop) : undefined;
        matricePaieEmp.createdDate = matricePaieEmp.createdDate ? dayjs(matricePaieEmp.createdDate) : undefined;
        matricePaieEmp.modifiedDate = matricePaieEmp.modifiedDate ? dayjs(matricePaieEmp.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
