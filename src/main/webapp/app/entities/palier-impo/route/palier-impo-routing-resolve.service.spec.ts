jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IPalierImpo, PalierImpo } from '../palier-impo.model';
import { PalierImpoService } from '../service/palier-impo.service';

import { PalierImpoRoutingResolveService } from './palier-impo-routing-resolve.service';

describe('PalierImpo routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PalierImpoRoutingResolveService;
  let service: PalierImpoService;
  let resultPalierImpo: IPalierImpo | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(PalierImpoRoutingResolveService);
    service = TestBed.inject(PalierImpoService);
    resultPalierImpo = undefined;
  });

  describe('resolve', () => {
    it('should return IPalierImpo returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPalierImpo = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPalierImpo).toEqual({ id: 123 });
    });

    it('should return new IPalierImpo if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPalierImpo = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPalierImpo).toEqual(new PalierImpo());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PalierImpo })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPalierImpo = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPalierImpo).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
