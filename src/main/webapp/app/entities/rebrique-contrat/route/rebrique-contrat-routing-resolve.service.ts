import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRebriqueContrat, RebriqueContrat } from '../rebrique-contrat.model';
import { RebriqueContratService } from '../service/rebrique-contrat.service';

@Injectable({ providedIn: 'root' })
export class RebriqueContratRoutingResolveService implements Resolve<IRebriqueContrat> {
  constructor(protected service: RebriqueContratService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRebriqueContrat> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((rebriqueContrat: HttpResponse<RebriqueContrat>) => {
          if (rebriqueContrat.body) {
            return of(rebriqueContrat.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RebriqueContrat());
  }
}
