import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAssiete, Assiete } from '../assiete.model';
import { AssieteService } from '../service/assiete.service';

@Injectable({ providedIn: 'root' })
export class AssieteRoutingResolveService implements Resolve<IAssiete> {
  constructor(protected service: AssieteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAssiete> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((assiete: HttpResponse<Assiete>) => {
          if (assiete.body) {
            return of(assiete.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Assiete());
  }
}
