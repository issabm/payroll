import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAssiete, getAssieteIdentifier } from '../assiete.model';

export type EntityResponseType = HttpResponse<IAssiete>;
export type EntityArrayResponseType = HttpResponse<IAssiete[]>;

@Injectable({ providedIn: 'root' })
export class AssieteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/assietes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(assiete: IAssiete): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(assiete);
    return this.http
      .post<IAssiete>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(assiete: IAssiete): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(assiete);
    return this.http
      .put<IAssiete>(`${this.resourceUrl}/${getAssieteIdentifier(assiete) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(assiete: IAssiete): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(assiete);
    return this.http
      .patch<IAssiete>(`${this.resourceUrl}/${getAssieteIdentifier(assiete) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAssiete>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAssiete[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAssieteToCollectionIfMissing(assieteCollection: IAssiete[], ...assietesToCheck: (IAssiete | null | undefined)[]): IAssiete[] {
    const assietes: IAssiete[] = assietesToCheck.filter(isPresent);
    if (assietes.length > 0) {
      const assieteCollectionIdentifiers = assieteCollection.map(assieteItem => getAssieteIdentifier(assieteItem)!);
      const assietesToAdd = assietes.filter(assieteItem => {
        const assieteIdentifier = getAssieteIdentifier(assieteItem);
        if (assieteIdentifier == null || assieteCollectionIdentifiers.includes(assieteIdentifier)) {
          return false;
        }
        assieteCollectionIdentifiers.push(assieteIdentifier);
        return true;
      });
      return [...assietesToAdd, ...assieteCollection];
    }
    return assieteCollection;
  }

  protected convertDateFromClient(assiete: IAssiete): IAssiete {
    return Object.assign({}, assiete, {
      dateSituation: assiete.dateSituation?.isValid() ? assiete.dateSituation.format(DATE_FORMAT) : undefined,
      dateop: assiete.dateop?.isValid() ? assiete.dateop.toJSON() : undefined,
      createdDate: assiete.createdDate?.isValid() ? assiete.createdDate.toJSON() : undefined,
      modifiedDate: assiete.modifiedDate?.isValid() ? assiete.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((assiete: IAssiete) => {
        assiete.dateSituation = assiete.dateSituation ? dayjs(assiete.dateSituation) : undefined;
        assiete.dateop = assiete.dateop ? dayjs(assiete.dateop) : undefined;
        assiete.createdDate = assiete.createdDate ? dayjs(assiete.createdDate) : undefined;
        assiete.modifiedDate = assiete.modifiedDate ? dayjs(assiete.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
