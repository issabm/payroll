import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAssieteInfo, getAssieteInfoIdentifier } from '../assiete-info.model';

export type EntityResponseType = HttpResponse<IAssieteInfo>;
export type EntityArrayResponseType = HttpResponse<IAssieteInfo[]>;

@Injectable({ providedIn: 'root' })
export class AssieteInfoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/assiete-infos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(assieteInfo: IAssieteInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(assieteInfo);
    return this.http
      .post<IAssieteInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(assieteInfo: IAssieteInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(assieteInfo);
    return this.http
      .put<IAssieteInfo>(`${this.resourceUrl}/${getAssieteInfoIdentifier(assieteInfo) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(assieteInfo: IAssieteInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(assieteInfo);
    return this.http
      .patch<IAssieteInfo>(`${this.resourceUrl}/${getAssieteInfoIdentifier(assieteInfo) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAssieteInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAssieteInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAssieteInfoToCollectionIfMissing(
    assieteInfoCollection: IAssieteInfo[],
    ...assieteInfosToCheck: (IAssieteInfo | null | undefined)[]
  ): IAssieteInfo[] {
    const assieteInfos: IAssieteInfo[] = assieteInfosToCheck.filter(isPresent);
    if (assieteInfos.length > 0) {
      const assieteInfoCollectionIdentifiers = assieteInfoCollection.map(assieteInfoItem => getAssieteInfoIdentifier(assieteInfoItem)!);
      const assieteInfosToAdd = assieteInfos.filter(assieteInfoItem => {
        const assieteInfoIdentifier = getAssieteInfoIdentifier(assieteInfoItem);
        if (assieteInfoIdentifier == null || assieteInfoCollectionIdentifiers.includes(assieteInfoIdentifier)) {
          return false;
        }
        assieteInfoCollectionIdentifiers.push(assieteInfoIdentifier);
        return true;
      });
      return [...assieteInfosToAdd, ...assieteInfoCollection];
    }
    return assieteInfoCollection;
  }

  protected convertDateFromClient(assieteInfo: IAssieteInfo): IAssieteInfo {
    return Object.assign({}, assieteInfo, {
      dateSituation: assieteInfo.dateSituation?.isValid() ? assieteInfo.dateSituation.format(DATE_FORMAT) : undefined,
      dateop: assieteInfo.dateop?.isValid() ? assieteInfo.dateop.toJSON() : undefined,
      createdDate: assieteInfo.createdDate?.isValid() ? assieteInfo.createdDate.toJSON() : undefined,
      modifiedDate: assieteInfo.modifiedDate?.isValid() ? assieteInfo.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((assieteInfo: IAssieteInfo) => {
        assieteInfo.dateSituation = assieteInfo.dateSituation ? dayjs(assieteInfo.dateSituation) : undefined;
        assieteInfo.dateop = assieteInfo.dateop ? dayjs(assieteInfo.dateop) : undefined;
        assieteInfo.createdDate = assieteInfo.createdDate ? dayjs(assieteInfo.createdDate) : undefined;
        assieteInfo.modifiedDate = assieteInfo.modifiedDate ? dayjs(assieteInfo.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
