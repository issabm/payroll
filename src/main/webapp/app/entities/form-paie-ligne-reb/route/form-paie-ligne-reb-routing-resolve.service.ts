import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFormPaieLigneReb, FormPaieLigneReb } from '../form-paie-ligne-reb.model';
import { FormPaieLigneRebService } from '../service/form-paie-ligne-reb.service';

@Injectable({ providedIn: 'root' })
export class FormPaieLigneRebRoutingResolveService implements Resolve<IFormPaieLigneReb> {
  constructor(protected service: FormPaieLigneRebService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFormPaieLigneReb> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((formPaieLigneReb: HttpResponse<FormPaieLigneReb>) => {
          if (formPaieLigneReb.body) {
            return of(formPaieLigneReb.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FormPaieLigneReb());
  }
}
