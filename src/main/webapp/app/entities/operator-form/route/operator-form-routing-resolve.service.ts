import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOperatorForm, OperatorForm } from '../operator-form.model';
import { OperatorFormService } from '../service/operator-form.service';

@Injectable({ providedIn: 'root' })
export class OperatorFormRoutingResolveService implements Resolve<IOperatorForm> {
  constructor(protected service: OperatorFormService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOperatorForm> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((operatorForm: HttpResponse<OperatorForm>) => {
          if (operatorForm.body) {
            return of(operatorForm.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OperatorForm());
  }
}
