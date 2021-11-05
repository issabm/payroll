jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { INatureAbsenceRebrique, NatureAbsenceRebrique } from '../nature-absence-rebrique.model';
import { NatureAbsenceRebriqueService } from '../service/nature-absence-rebrique.service';

import { NatureAbsenceRebriqueRoutingResolveService } from './nature-absence-rebrique-routing-resolve.service';

describe('NatureAbsenceRebrique routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: NatureAbsenceRebriqueRoutingResolveService;
  let service: NatureAbsenceRebriqueService;
  let resultNatureAbsenceRebrique: INatureAbsenceRebrique | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(NatureAbsenceRebriqueRoutingResolveService);
    service = TestBed.inject(NatureAbsenceRebriqueService);
    resultNatureAbsenceRebrique = undefined;
  });

  describe('resolve', () => {
    it('should return INatureAbsenceRebrique returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNatureAbsenceRebrique = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNatureAbsenceRebrique).toEqual({ id: 123 });
    });

    it('should return new INatureAbsenceRebrique if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNatureAbsenceRebrique = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultNatureAbsenceRebrique).toEqual(new NatureAbsenceRebrique());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as NatureAbsenceRebrique })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNatureAbsenceRebrique = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNatureAbsenceRebrique).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
