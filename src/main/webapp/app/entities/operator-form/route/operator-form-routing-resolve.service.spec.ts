jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IOperatorForm, OperatorForm } from '../operator-form.model';
import { OperatorFormService } from '../service/operator-form.service';

import { OperatorFormRoutingResolveService } from './operator-form-routing-resolve.service';

describe('OperatorForm routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: OperatorFormRoutingResolveService;
  let service: OperatorFormService;
  let resultOperatorForm: IOperatorForm | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(OperatorFormRoutingResolveService);
    service = TestBed.inject(OperatorFormService);
    resultOperatorForm = undefined;
  });

  describe('resolve', () => {
    it('should return IOperatorForm returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOperatorForm = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultOperatorForm).toEqual({ id: 123 });
    });

    it('should return new IOperatorForm if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOperatorForm = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultOperatorForm).toEqual(new OperatorForm());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as OperatorForm })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOperatorForm = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultOperatorForm).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
