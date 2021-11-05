jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IFormPaieLigne, FormPaieLigne } from '../form-paie-ligne.model';
import { FormPaieLigneService } from '../service/form-paie-ligne.service';

import { FormPaieLigneRoutingResolveService } from './form-paie-ligne-routing-resolve.service';

describe('FormPaieLigne routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: FormPaieLigneRoutingResolveService;
  let service: FormPaieLigneService;
  let resultFormPaieLigne: IFormPaieLigne | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(FormPaieLigneRoutingResolveService);
    service = TestBed.inject(FormPaieLigneService);
    resultFormPaieLigne = undefined;
  });

  describe('resolve', () => {
    it('should return IFormPaieLigne returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFormPaieLigne = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultFormPaieLigne).toEqual({ id: 123 });
    });

    it('should return new IFormPaieLigne if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFormPaieLigne = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultFormPaieLigne).toEqual(new FormPaieLigne());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as FormPaieLigne })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFormPaieLigne = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultFormPaieLigne).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
