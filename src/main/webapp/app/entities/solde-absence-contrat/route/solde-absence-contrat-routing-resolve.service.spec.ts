jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ISoldeAbsenceContrat, SoldeAbsenceContrat } from '../solde-absence-contrat.model';
import { SoldeAbsenceContratService } from '../service/solde-absence-contrat.service';

import { SoldeAbsenceContratRoutingResolveService } from './solde-absence-contrat-routing-resolve.service';

describe('SoldeAbsenceContrat routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SoldeAbsenceContratRoutingResolveService;
  let service: SoldeAbsenceContratService;
  let resultSoldeAbsenceContrat: ISoldeAbsenceContrat | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(SoldeAbsenceContratRoutingResolveService);
    service = TestBed.inject(SoldeAbsenceContratService);
    resultSoldeAbsenceContrat = undefined;
  });

  describe('resolve', () => {
    it('should return ISoldeAbsenceContrat returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSoldeAbsenceContrat = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSoldeAbsenceContrat).toEqual({ id: 123 });
    });

    it('should return new ISoldeAbsenceContrat if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSoldeAbsenceContrat = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSoldeAbsenceContrat).toEqual(new SoldeAbsenceContrat());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as SoldeAbsenceContrat })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSoldeAbsenceContrat = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSoldeAbsenceContrat).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
