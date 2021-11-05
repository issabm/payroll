import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IManagementResourceProfile, getManagementResourceProfileIdentifier } from '../management-resource-profile.model';

export type EntityResponseType = HttpResponse<IManagementResourceProfile>;
export type EntityArrayResponseType = HttpResponse<IManagementResourceProfile[]>;

@Injectable({ providedIn: 'root' })
export class ManagementResourceProfileService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/management-resource-profiles');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(managementResourceProfile: IManagementResourceProfile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(managementResourceProfile);
    return this.http
      .post<IManagementResourceProfile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(managementResourceProfile: IManagementResourceProfile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(managementResourceProfile);
    return this.http
      .put<IManagementResourceProfile>(
        `${this.resourceUrl}/${getManagementResourceProfileIdentifier(managementResourceProfile) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(managementResourceProfile: IManagementResourceProfile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(managementResourceProfile);
    return this.http
      .patch<IManagementResourceProfile>(
        `${this.resourceUrl}/${getManagementResourceProfileIdentifier(managementResourceProfile) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IManagementResourceProfile>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IManagementResourceProfile[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addManagementResourceProfileToCollectionIfMissing(
    managementResourceProfileCollection: IManagementResourceProfile[],
    ...managementResourceProfilesToCheck: (IManagementResourceProfile | null | undefined)[]
  ): IManagementResourceProfile[] {
    const managementResourceProfiles: IManagementResourceProfile[] = managementResourceProfilesToCheck.filter(isPresent);
    if (managementResourceProfiles.length > 0) {
      const managementResourceProfileCollectionIdentifiers = managementResourceProfileCollection.map(
        managementResourceProfileItem => getManagementResourceProfileIdentifier(managementResourceProfileItem)!
      );
      const managementResourceProfilesToAdd = managementResourceProfiles.filter(managementResourceProfileItem => {
        const managementResourceProfileIdentifier = getManagementResourceProfileIdentifier(managementResourceProfileItem);
        if (
          managementResourceProfileIdentifier == null ||
          managementResourceProfileCollectionIdentifiers.includes(managementResourceProfileIdentifier)
        ) {
          return false;
        }
        managementResourceProfileCollectionIdentifiers.push(managementResourceProfileIdentifier);
        return true;
      });
      return [...managementResourceProfilesToAdd, ...managementResourceProfileCollection];
    }
    return managementResourceProfileCollection;
  }

  protected convertDateFromClient(managementResourceProfile: IManagementResourceProfile): IManagementResourceProfile {
    return Object.assign({}, managementResourceProfile, {
      dateop: managementResourceProfile.dateop?.isValid() ? managementResourceProfile.dateop.toJSON() : undefined,
      createdDate: managementResourceProfile.createdDate?.isValid() ? managementResourceProfile.createdDate.toJSON() : undefined,
      modifiedDate: managementResourceProfile.modifiedDate?.isValid() ? managementResourceProfile.modifiedDate.toJSON() : undefined,
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
      res.body.forEach((managementResourceProfile: IManagementResourceProfile) => {
        managementResourceProfile.dateop = managementResourceProfile.dateop ? dayjs(managementResourceProfile.dateop) : undefined;
        managementResourceProfile.createdDate = managementResourceProfile.createdDate
          ? dayjs(managementResourceProfile.createdDate)
          : undefined;
        managementResourceProfile.modifiedDate = managementResourceProfile.modifiedDate
          ? dayjs(managementResourceProfile.modifiedDate)
          : undefined;
      });
    }
    return res;
  }
}
