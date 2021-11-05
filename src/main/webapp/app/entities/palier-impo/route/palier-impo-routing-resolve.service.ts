import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPalierImpo, PalierImpo } from '../palier-impo.model';
import { PalierImpoService } from '../service/palier-impo.service';

@Injectable({ providedIn: 'root' })
export class PalierImpoRoutingResolveService implements Resolve<IPalierImpo> {
  constructor(protected service: PalierImpoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPalierImpo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((palierImpo: HttpResponse<PalierImpo>) => {
          if (palierImpo.body) {
            return of(palierImpo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PalierImpo());
  }
}
