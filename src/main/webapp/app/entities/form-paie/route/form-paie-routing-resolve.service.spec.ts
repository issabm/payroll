jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IFormPaie, FormPaie } from '../form-paie.model';
import { FormPaieService } from '../service/form-paie.service';

import { FormPaieRoutingResolveService } from './form-paie-routing-resolve.service';

describe('FormPaie routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: FormPaieRoutingResolveService;
  let service: FormPaieService;
  let resultFormPaie: IFormPaie | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(FormPaieRoutingResolveService);
    service = TestBed.inject(FormPaieService);
    resultFormPaie = undefined;
  });

  describe('resolve', () => {
    it('should return IFormPaie returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFormPaie = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultFormPaie).toEqual({ id: 123 });
    });

    it('should return new IFormPaie if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFormPaie = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultFormPaie).toEqual(new FormPaie());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as FormPaie })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFormPaie = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultFormPaie).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
