import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IOperatorForm, OperatorForm } from '../operator-form.model';

import { OperatorFormService } from './operator-form.service';

describe('OperatorForm Service', () => {
  let service: OperatorFormService;
  let httpMock: HttpTestingController;
  let elemDefault: IOperatorForm;
  let expectedResult: IOperatorForm | IOperatorForm[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(OperatorFormService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      code: 'AAAAAAA',
      libEn: 'AAAAAAA',
      libAr: 'AAAAAAA',
      dateop: currentDate,
      modifiedBy: 'AAAAAAA',
      createdBy: 'AAAAAAA',
      op: 'AAAAAAA',
      util: 'AAAAAAA',
      isDeleted: false,
      createdDate: currentDate,
      modifiedDate: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateop: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a OperatorForm', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateop: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateop: currentDate,
          createdDate: currentDate,
          modifiedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new OperatorForm()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OperatorForm', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          libEn: 'BBBBBB',
          libAr: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          createdBy: 'BBBBBB',
          op: 'BBBBBB',
          util: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateop: currentDate,
          createdDate: currentDate,
          modifiedDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OperatorForm', () => {
      const patchObject = Object.assign(
        {
          code: 'BBBBBB',
          libAr: 'BBBBBB',
          modifiedBy: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new OperatorForm()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateop: currentDate,
          createdDate: currentDate,
          modifiedDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OperatorForm', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          libEn: 'BBBBBB',
          libAr: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          createdBy: 'BBBBBB',
          op: 'BBBBBB',
          util: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateop: currentDate,
          createdDate: currentDate,
          modifiedDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a OperatorForm', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addOperatorFormToCollectionIfMissing', () => {
      it('should add a OperatorForm to an empty array', () => {
        const operatorForm: IOperatorForm = { id: 123 };
        expectedResult = service.addOperatorFormToCollectionIfMissing([], operatorForm);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(operatorForm);
      });

      it('should not add a OperatorForm to an array that contains it', () => {
        const operatorForm: IOperatorForm = { id: 123 };
        const operatorFormCollection: IOperatorForm[] = [
          {
            ...operatorForm,
          },
          { id: 456 },
        ];
        expectedResult = service.addOperatorFormToCollectionIfMissing(operatorFormCollection, operatorForm);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OperatorForm to an array that doesn't contain it", () => {
        const operatorForm: IOperatorForm = { id: 123 };
        const operatorFormCollection: IOperatorForm[] = [{ id: 456 }];
        expectedResult = service.addOperatorFormToCollectionIfMissing(operatorFormCollection, operatorForm);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(operatorForm);
      });

      it('should add only unique OperatorForm to an array', () => {
        const operatorFormArray: IOperatorForm[] = [{ id: 123 }, { id: 456 }, { id: 69389 }];
        const operatorFormCollection: IOperatorForm[] = [{ id: 123 }];
        expectedResult = service.addOperatorFormToCollectionIfMissing(operatorFormCollection, ...operatorFormArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const operatorForm: IOperatorForm = { id: 123 };
        const operatorForm2: IOperatorForm = { id: 456 };
        expectedResult = service.addOperatorFormToCollectionIfMissing([], operatorForm, operatorForm2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(operatorForm);
        expect(expectedResult).toContain(operatorForm2);
      });

      it('should accept null and undefined values', () => {
        const operatorForm: IOperatorForm = { id: 123 };
        expectedResult = service.addOperatorFormToCollectionIfMissing([], null, operatorForm, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(operatorForm);
      });

      it('should return initial array if no OperatorForm is added', () => {
        const operatorFormCollection: IOperatorForm[] = [{ id: 123 }];
        expectedResult = service.addOperatorFormToCollectionIfMissing(operatorFormCollection, undefined, null);
        expect(expectedResult).toEqual(operatorFormCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
