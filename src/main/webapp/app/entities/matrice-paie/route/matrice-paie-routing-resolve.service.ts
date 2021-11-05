import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMatricePaie, MatricePaie } from '../matrice-paie.model';
import { MatricePaieService } from '../service/matrice-paie.service';

@Injectable({ providedIn: 'root' })
export class MatricePaieRoutingResolveService implements Resolve<IMatricePaie> {
  constructor(protected service: MatricePaieService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMatricePaie> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((matricePaie: HttpResponse<MatricePaie>) => {
          if (matricePaie.body) {
            return of(matricePaie.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MatricePaie());
  }
}
