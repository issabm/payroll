import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INatureAbsenceRebrique, NatureAbsenceRebrique } from '../nature-absence-rebrique.model';
import { NatureAbsenceRebriqueService } from '../service/nature-absence-rebrique.service';

@Injectable({ providedIn: 'root' })
export class NatureAbsenceRebriqueRoutingResolveService implements Resolve<INatureAbsenceRebrique> {
  constructor(protected service: NatureAbsenceRebriqueService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INatureAbsenceRebrique> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((natureAbsenceRebrique: HttpResponse<NatureAbsenceRebrique>) => {
          if (natureAbsenceRebrique.body) {
            return of(natureAbsenceRebrique.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NatureAbsenceRebrique());
  }
}
