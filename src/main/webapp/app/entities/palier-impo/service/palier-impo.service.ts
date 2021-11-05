import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPalierImpo, getPalierImpoIdentifier } from '../palier-impo.model';

export type EntityResponseType = HttpResponse<IPalierImpo>;
export type EntityArrayResponseType = HttpResponse<IPalierImpo[]>;

@Injectable({ providedIn: 'root' })
export class PalierImpoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/palier-impos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(palierImpo: IPalierImpo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(palierImpo);
    return this.http
      .post<IPalierImpo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(palierImpo: IPalierImpo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(palierImpo);
    return this.http
      .put<IPalierImpo>(`${this.resourceUrl}/${getPalierImpoIdentifier(palierImpo) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(palierImpo: IPalierImpo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(palierImpo);
    return this.http
      .patch<IPalierImpo>(`${this.resourceUrl}/${getPalierImpoIdentifier(palierImpo) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPalierImpo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPalierImpo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPalierImpoToCollectionIfMissing(
    palierImpoCollection: IPalierImpo[],
    ...palierImposToCheck: (IPalierImpo | null | undefined)[]
  ): IPalierImpo[] {
    const palierImpos: IPalierImpo[] = palierImposToCheck.filter(isPresent);
    if (palierImpos.length > 0) {
      const palierImpoCollectionIdentifiers = palierImpoCollection.map(palierImpoItem => getPalierImpoIdentifier(palierImpoItem)!);
      const palierImposToAdd = palierImpos.filter(palierImpoItem => {
        const palierImpoIdentifier = getPalierImpoIdentifier(palierImpoItem);
        if (palierImpoIdentifier == null || palierImpoCollectionIdentifiers.includes(palierImpoIdentifier)) {
          return false;
        }
        palierImpoCollectionIdentifiers.push(palierImpoIdentifier);
        return true;
      });
      return [...palierImposToAdd, ...palierImpoCollection];
    }
    return palierImpoCollection;
  }

  protected convertDateFromClient(palierImpo: IPalierImpo): IPalierImpo {
    return Object.assign({}, palierImpo, {
      dateop: palierImpo.dateop?.isValid() ? palierImpo.dateop.toJSON() : undefined,
      dateModif: palierImpo.dateModif?.isValid() ? palierImpo.dateModif.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateop = res.body.dateop ? dayjs(res.body.dateop) : undefined;
      res.body.dateModif = res.body.dateModif ? dayjs(res.body.dateModif) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((palierImpo: IPalierImpo) => {
        palierImpo.dateop = palierImpo.dateop ? dayjs(palierImpo.dateop) : undefined;
        palierImpo.dateModif = palierImpo.dateModif ? dayjs(palierImpo.dateModif) : undefined;
      });
    }
    return res;
  }
}
