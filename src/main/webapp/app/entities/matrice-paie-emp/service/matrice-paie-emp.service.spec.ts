import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IMatricePaieEmp, MatricePaieEmp } from '../matrice-paie-emp.model';

import { MatricePaieEmpService } from './matrice-paie-emp.service';

describe('MatricePaieEmp Service', () => {
  let service: MatricePaieEmpService;
  let httpMock: HttpTestingController;
  let elemDefault: IMatricePaieEmp;
  let expectedResult: IMatricePaieEmp | IMatricePaieEmp[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MatricePaieEmpService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      util: 'AAAAAAA',
      dateop: currentDate,
      modifiedBy: 'AAAAAAA',
      op: 'AAAAAAA',
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

    it('should create a MatricePaieEmp', () => {
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

      service.create(new MatricePaieEmp()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MatricePaieEmp', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          util: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          op: 'BBBBBB',
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

    it('should partial update a MatricePaieEmp', () => {
      const patchObject = Object.assign(
        {
          util: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new MatricePaieEmp()
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

    it('should return a list of MatricePaieEmp', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          util: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          op: 'BBBBBB',
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

    it('should delete a MatricePaieEmp', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addMatricePaieEmpToCollectionIfMissing', () => {
      it('should add a MatricePaieEmp to an empty array', () => {
        const matricePaieEmp: IMatricePaieEmp = { id: 123 };
        expectedResult = service.addMatricePaieEmpToCollectionIfMissing([], matricePaieEmp);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(matricePaieEmp);
      });

      it('should not add a MatricePaieEmp to an array that contains it', () => {
        const matricePaieEmp: IMatricePaieEmp = { id: 123 };
        const matricePaieEmpCollection: IMatricePaieEmp[] = [
          {
            ...matricePaieEmp,
          },
          { id: 456 },
        ];
        expectedResult = service.addMatricePaieEmpToCollectionIfMissing(matricePaieEmpCollection, matricePaieEmp);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MatricePaieEmp to an array that doesn't contain it", () => {
        const matricePaieEmp: IMatricePaieEmp = { id: 123 };
        const matricePaieEmpCollection: IMatricePaieEmp[] = [{ id: 456 }];
        expectedResult = service.addMatricePaieEmpToCollectionIfMissing(matricePaieEmpCollection, matricePaieEmp);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(matricePaieEmp);
      });

      it('should add only unique MatricePaieEmp to an array', () => {
        const matricePaieEmpArray: IMatricePaieEmp[] = [{ id: 123 }, { id: 456 }, { id: 93591 }];
        const matricePaieEmpCollection: IMatricePaieEmp[] = [{ id: 123 }];
        expectedResult = service.addMatricePaieEmpToCollectionIfMissing(matricePaieEmpCollection, ...matricePaieEmpArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const matricePaieEmp: IMatricePaieEmp = { id: 123 };
        const matricePaieEmp2: IMatricePaieEmp = { id: 456 };
        expectedResult = service.addMatricePaieEmpToCollectionIfMissing([], matricePaieEmp, matricePaieEmp2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(matricePaieEmp);
        expect(expectedResult).toContain(matricePaieEmp2);
      });

      it('should accept null and undefined values', () => {
        const matricePaieEmp: IMatricePaieEmp = { id: 123 };
        expectedResult = service.addMatricePaieEmpToCollectionIfMissing([], null, matricePaieEmp, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(matricePaieEmp);
      });

      it('should return initial array if no MatricePaieEmp is added', () => {
        const matricePaieEmpCollection: IMatricePaieEmp[] = [{ id: 123 }];
        expectedResult = service.addMatricePaieEmpToCollectionIfMissing(matricePaieEmpCollection, undefined, null);
        expect(expectedResult).toEqual(matricePaieEmpCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
