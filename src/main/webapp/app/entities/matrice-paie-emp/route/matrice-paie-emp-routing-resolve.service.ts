import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMatricePaieEmp, MatricePaieEmp } from '../matrice-paie-emp.model';
import { MatricePaieEmpService } from '../service/matrice-paie-emp.service';

@Injectable({ providedIn: 'root' })
export class MatricePaieEmpRoutingResolveService implements Resolve<IMatricePaieEmp> {
  constructor(protected service: MatricePaieEmpService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMatricePaieEmp> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((matricePaieEmp: HttpResponse<MatricePaieEmp>) => {
          if (matricePaieEmp.body) {
            return of(matricePaieEmp.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MatricePaieEmp());
  }
}
