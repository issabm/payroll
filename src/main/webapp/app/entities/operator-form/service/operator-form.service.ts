import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOperatorForm, getOperatorFormIdentifier } from '../operator-form.model';

export type EntityResponseType = HttpResponse<IOperatorForm>;
export type EntityArrayResponseType = HttpResponse<IOperatorForm[]>;

@Injectable({ providedIn: 'root' })
export class OperatorFormService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/operator-forms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(operatorForm: IOperatorForm): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(operatorForm);
    return this.http
      .post<IOperatorForm>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(operatorForm: IOperatorForm): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(operatorForm);
    return this.http
      .put<IOperatorForm>(`${this.resourceUrl}/${getOperatorFormIdentifier(operatorForm) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(operatorForm: IOperatorForm): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(operatorForm);
    return this.http
      .patch<IOperatorForm>(`${this.resourceUrl}/${getOperatorFormIdentifier(operatorForm) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOperatorForm>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOperatorForm[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addOperatorFormToCollectionIfMissing(
    operatorFormCollection: IOperatorForm[],
    ...operatorFormsToCheck: (IOperatorForm | null | undefined)[]
  ): IOperatorForm[] {
    const operatorForms: IOperatorForm[] = operatorFormsToCheck.filter(isPresent);
    if (operatorForms.length > 0) {
      const operatorFormCollectionIdentifiers = operatorFormCollection.map(
        operatorFormItem => getOperatorFormIdentifier(operatorFormItem)!
      );
      const operatorFormsToAdd = operatorForms.filter(operatorFormItem => {
        const operatorFormIdentifier = getOperatorFormIdentifier(operatorFormItem);
        if (operatorFormIdentifier == null || operatorFormCollectionIdentifiers.includes(operatorFormIdentifier)) {
          return false;
        }
        operatorFormCollectionIdentifiers.push(operatorFormIdentifier);
        return true;
      });
      return [...operatorFormsToAdd, ...operatorFormCollection];
    }
    return operatorFormCollection;
  }

  protected convertDateFromClient(operatorForm: IOperatorForm): IOperatorForm {
    return Object.assign({}, operatorForm, {
      dateop: operatorForm.dateop?.isValid() ? operatorForm.dateop.toJSON() : undefined,
      createdDate: operatorForm.createdDate?.isValid() ? operatorForm.createdDate.toJSON() : undefined,
      modifiedDate: operatorForm.modifiedDate?.isValid() ? operatorForm.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((operatorForm: IOperatorForm) => {
        operatorForm.dateop = operatorForm.dateop ? dayjs(operatorForm.dateop) : undefined;
        operatorForm.createdDate = operatorForm.createdDate ? dayjs(operatorForm.createdDate) : undefined;
        operatorForm.modifiedDate = operatorForm.modifiedDate ? dayjs(operatorForm.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
