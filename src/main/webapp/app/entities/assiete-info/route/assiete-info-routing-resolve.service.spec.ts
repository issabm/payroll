jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IAssieteInfo, AssieteInfo } from '../assiete-info.model';
import { AssieteInfoService } from '../service/assiete-info.service';

import { AssieteInfoRoutingResolveService } from './assiete-info-routing-resolve.service';

describe('AssieteInfo routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AssieteInfoRoutingResolveService;
  let service: AssieteInfoService;
  let resultAssieteInfo: IAssieteInfo | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(AssieteInfoRoutingResolveService);
    service = TestBed.inject(AssieteInfoService);
    resultAssieteInfo = undefined;
  });

  describe('resolve', () => {
    it('should return IAssieteInfo returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAssieteInfo = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAssieteInfo).toEqual({ id: 123 });
    });

    it('should return new IAssieteInfo if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAssieteInfo = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAssieteInfo).toEqual(new AssieteInfo());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as AssieteInfo })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAssieteInfo = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAssieteInfo).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
