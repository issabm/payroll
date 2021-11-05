import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IManagementResourceProfile, ManagementResourceProfile } from '../management-resource-profile.model';
import { ManagementResourceProfileService } from '../service/management-resource-profile.service';

@Injectable({ providedIn: 'root' })
export class ManagementResourceProfileRoutingResolveService implements Resolve<IManagementResourceProfile> {
  constructor(protected service: ManagementResourceProfileService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IManagementResourceProfile> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((managementResourceProfile: HttpResponse<ManagementResourceProfile>) => {
          if (managementResourceProfile.body) {
            return of(managementResourceProfile.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ManagementResourceProfile());
  }
}
