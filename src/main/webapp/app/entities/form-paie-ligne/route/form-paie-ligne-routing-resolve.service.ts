import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFormPaieLigne, FormPaieLigne } from '../form-paie-ligne.model';
import { FormPaieLigneService } from '../service/form-paie-ligne.service';

@Injectable({ providedIn: 'root' })
export class FormPaieLigneRoutingResolveService implements Resolve<IFormPaieLigne> {
  constructor(protected service: FormPaieLigneService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFormPaieLigne> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((formPaieLigne: HttpResponse<FormPaieLigne>) => {
          if (formPaieLigne.body) {
            return of(formPaieLigne.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FormPaieLigne());
  }
}
