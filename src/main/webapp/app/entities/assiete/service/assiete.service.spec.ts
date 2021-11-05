import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAssiete, Assiete } from '../assiete.model';

import { AssieteService } from './assiete.service';

describe('Assiete Service', () => {
  let service: AssieteService;
  let httpMock: HttpTestingController;
  let elemDefault: IAssiete;
  let expectedResult: IAssiete | IAssiete[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AssieteService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      priorite: 0,
      code: 'AAAAAAA',
      lib: 'AAAAAAA',
      dateSituation: currentDate,
      dateop: currentDate,
      modifiedBy: 'AAAAAAA',
      createdBy: 'AAAAAAA',
      util: 'AAAAAAA',
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
          dateSituation: currentDate.format(DATE_FORMAT),
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

    it('should create a Assiete', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateSituation: currentDate.format(DATE_FORMAT),
          dateop: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateSituation: currentDate,
          dateop: currentDate,
          createdDate: currentDate,
          modifiedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new Assiete()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Assiete', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          priorite: 1,
          code: 'BBBBBB',
          lib: 'BBBBBB',
          dateSituation: currentDate.format(DATE_FORMAT),
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          createdBy: 'BBBBBB',
          util: 'BBBBBB',
          op: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateSituation: currentDate,
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

    it('should partial update a Assiete', () => {
      const patchObject = Object.assign(
        {
          priorite: 1,
          code: 'BBBBBB',
          createdBy: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new Assiete()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateSituation: currentDate,
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

    it('should return a list of Assiete', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          priorite: 1,
          code: 'BBBBBB',
          lib: 'BBBBBB',
          dateSituation: currentDate.format(DATE_FORMAT),
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          createdBy: 'BBBBBB',
          util: 'BBBBBB',
          op: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateSituation: currentDate,
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

    it('should delete a Assiete', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAssieteToCollectionIfMissing', () => {
      it('should add a Assiete to an empty array', () => {
        const assiete: IAssiete = { id: 123 };
        expectedResult = service.addAssieteToCollectionIfMissing([], assiete);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(assiete);
      });

      it('should not add a Assiete to an array that contains it', () => {
        const assiete: IAssiete = { id: 123 };
        const assieteCollection: IAssiete[] = [
          {
            ...assiete,
          },
          { id: 456 },
        ];
        expectedResult = service.addAssieteToCollectionIfMissing(assieteCollection, assiete);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Assiete to an array that doesn't contain it", () => {
        const assiete: IAssiete = { id: 123 };
        const assieteCollection: IAssiete[] = [{ id: 456 }];
        expectedResult = service.addAssieteToCollectionIfMissing(assieteCollection, assiete);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(assiete);
      });

      it('should add only unique Assiete to an array', () => {
        const assieteArray: IAssiete[] = [{ id: 123 }, { id: 456 }, { id: 89051 }];
        const assieteCollection: IAssiete[] = [{ id: 123 }];
        expectedResult = service.addAssieteToCollectionIfMissing(assieteCollection, ...assieteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const assiete: IAssiete = { id: 123 };
        const assiete2: IAssiete = { id: 456 };
        expectedResult = service.addAssieteToCollectionIfMissing([], assiete, assiete2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(assiete);
        expect(expectedResult).toContain(assiete2);
      });

      it('should accept null and undefined values', () => {
        const assiete: IAssiete = { id: 123 };
        expectedResult = service.addAssieteToCollectionIfMissing([], null, assiete, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(assiete);
      });

      it('should return initial array if no Assiete is added', () => {
        const assieteCollection: IAssiete[] = [{ id: 123 }];
        expectedResult = service.addAssieteToCollectionIfMissing(assieteCollection, undefined, null);
        expect(expectedResult).toEqual(assieteCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
