jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IFormPaieLigneReb, FormPaieLigneReb } from '../form-paie-ligne-reb.model';
import { FormPaieLigneRebService } from '../service/form-paie-ligne-reb.service';

import { FormPaieLigneRebRoutingResolveService } from './form-paie-ligne-reb-routing-resolve.service';

describe('FormPaieLigneReb routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: FormPaieLigneRebRoutingResolveService;
  let service: FormPaieLigneRebService;
  let resultFormPaieLigneReb: IFormPaieLigneReb | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(FormPaieLigneRebRoutingResolveService);
    service = TestBed.inject(FormPaieLigneRebService);
    resultFormPaieLigneReb = undefined;
  });

  describe('resolve', () => {
    it('should return IFormPaieLigneReb returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFormPaieLigneReb = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultFormPaieLigneReb).toEqual({ id: 123 });
    });

    it('should return new IFormPaieLigneReb if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFormPaieLigneReb = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultFormPaieLigneReb).toEqual(new FormPaieLigneReb());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as FormPaieLigneReb })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFormPaieLigneReb = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultFormPaieLigneReb).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
