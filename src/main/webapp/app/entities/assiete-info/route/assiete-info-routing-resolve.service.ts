import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAssieteInfo, AssieteInfo } from '../assiete-info.model';
import { AssieteInfoService } from '../service/assiete-info.service';

@Injectable({ providedIn: 'root' })
export class AssieteInfoRoutingResolveService implements Resolve<IAssieteInfo> {
  constructor(protected service: AssieteInfoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAssieteInfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((assieteInfo: HttpResponse<AssieteInfo>) => {
          if (assieteInfo.body) {
            return of(assieteInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AssieteInfo());
  }
}
