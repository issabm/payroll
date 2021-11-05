jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IMatricePaie, MatricePaie } from '../matrice-paie.model';
import { MatricePaieService } from '../service/matrice-paie.service';

import { MatricePaieRoutingResolveService } from './matrice-paie-routing-resolve.service';

describe('MatricePaie routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: MatricePaieRoutingResolveService;
  let service: MatricePaieService;
  let resultMatricePaie: IMatricePaie | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(MatricePaieRoutingResolveService);
    service = TestBed.inject(MatricePaieService);
    resultMatricePaie = undefined;
  });

  describe('resolve', () => {
    it('should return IMatricePaie returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMatricePaie = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMatricePaie).toEqual({ id: 123 });
    });

    it('should return new IMatricePaie if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMatricePaie = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultMatricePaie).toEqual(new MatricePaie());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as MatricePaie })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMatricePaie = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMatricePaie).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
