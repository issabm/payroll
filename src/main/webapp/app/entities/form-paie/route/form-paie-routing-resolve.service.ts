import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFormPaie, FormPaie } from '../form-paie.model';
import { FormPaieService } from '../service/form-paie.service';

@Injectable({ providedIn: 'root' })
export class FormPaieRoutingResolveService implements Resolve<IFormPaie> {
  constructor(protected service: FormPaieService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFormPaie> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((formPaie: HttpResponse<FormPaie>) => {
          if (formPaie.body) {
            return of(formPaie.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FormPaie());
  }
}
