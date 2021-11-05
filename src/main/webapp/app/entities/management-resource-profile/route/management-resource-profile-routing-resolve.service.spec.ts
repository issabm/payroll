jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IManagementResourceProfile, ManagementResourceProfile } from '../management-resource-profile.model';
import { ManagementResourceProfileService } from '../service/management-resource-profile.service';

import { ManagementResourceProfileRoutingResolveService } from './management-resource-profile-routing-resolve.service';

describe('ManagementResourceProfile routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ManagementResourceProfileRoutingResolveService;
  let service: ManagementResourceProfileService;
  let resultManagementResourceProfile: IManagementResourceProfile | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(ManagementResourceProfileRoutingResolveService);
    service = TestBed.inject(ManagementResourceProfileService);
    resultManagementResourceProfile = undefined;
  });

  describe('resolve', () => {
    it('should return IManagementResourceProfile returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultManagementResourceProfile = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultManagementResourceProfile).toEqual({ id: 123 });
    });

    it('should return new IManagementResourceProfile if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultManagementResourceProfile = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultManagementResourceProfile).toEqual(new ManagementResourceProfile());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ManagementResourceProfile })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultManagementResourceProfile = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultManagementResourceProfile).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
