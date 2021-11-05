import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { INatureAbsenceRebrique, NatureAbsenceRebrique } from '../nature-absence-rebrique.model';

import { NatureAbsenceRebriqueService } from './nature-absence-rebrique.service';

describe('NatureAbsenceRebrique Service', () => {
  let service: NatureAbsenceRebriqueService;
  let httpMock: HttpTestingController;
  let elemDefault: INatureAbsenceRebrique;
  let expectedResult: INatureAbsenceRebrique | INatureAbsenceRebrique[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NatureAbsenceRebriqueService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      code: 'AAAAAAA',
      libAr: 'AAAAAAA',
      libEn: 'AAAAAAA',
      valueTaken: 0,
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

    it('should create a NatureAbsenceRebrique', () => {
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

      service.create(new NatureAbsenceRebrique()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NatureAbsenceRebrique', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          libAr: 'BBBBBB',
          libEn: 'BBBBBB',
          valueTaken: 1,
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

    it('should partial update a NatureAbsenceRebrique', () => {
      const patchObject = Object.assign(
        {
          code: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new NatureAbsenceRebrique()
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

    it('should return a list of NatureAbsenceRebrique', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          libAr: 'BBBBBB',
          libEn: 'BBBBBB',
          valueTaken: 1,
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

    it('should delete a NatureAbsenceRebrique', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addNatureAbsenceRebriqueToCollectionIfMissing', () => {
      it('should add a NatureAbsenceRebrique to an empty array', () => {
        const natureAbsenceRebrique: INatureAbsenceRebrique = { id: 123 };
        expectedResult = service.addNatureAbsenceRebriqueToCollectionIfMissing([], natureAbsenceRebrique);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(natureAbsenceRebrique);
      });

      it('should not add a NatureAbsenceRebrique to an array that contains it', () => {
        const natureAbsenceRebrique: INatureAbsenceRebrique = { id: 123 };
        const natureAbsenceRebriqueCollection: INatureAbsenceRebrique[] = [
          {
            ...natureAbsenceRebrique,
          },
          { id: 456 },
        ];
        expectedResult = service.addNatureAbsenceRebriqueToCollectionIfMissing(natureAbsenceRebriqueCollection, natureAbsenceRebrique);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NatureAbsenceRebrique to an array that doesn't contain it", () => {
        const natureAbsenceRebrique: INatureAbsenceRebrique = { id: 123 };
        const natureAbsenceRebriqueCollection: INatureAbsenceRebrique[] = [{ id: 456 }];
        expectedResult = service.addNatureAbsenceRebriqueToCollectionIfMissing(natureAbsenceRebriqueCollection, natureAbsenceRebrique);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(natureAbsenceRebrique);
      });

      it('should add only unique NatureAbsenceRebrique to an array', () => {
        const natureAbsenceRebriqueArray: INatureAbsenceRebrique[] = [{ id: 123 }, { id: 456 }, { id: 31343 }];
        const natureAbsenceRebriqueCollection: INatureAbsenceRebrique[] = [{ id: 123 }];
        expectedResult = service.addNatureAbsenceRebriqueToCollectionIfMissing(
          natureAbsenceRebriqueCollection,
          ...natureAbsenceRebriqueArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const natureAbsenceRebrique: INatureAbsenceRebrique = { id: 123 };
        const natureAbsenceRebrique2: INatureAbsenceRebrique = { id: 456 };
        expectedResult = service.addNatureAbsenceRebriqueToCollectionIfMissing([], natureAbsenceRebrique, natureAbsenceRebrique2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(natureAbsenceRebrique);
        expect(expectedResult).toContain(natureAbsenceRebrique2);
      });

      it('should accept null and undefined values', () => {
        const natureAbsenceRebrique: INatureAbsenceRebrique = { id: 123 };
        expectedResult = service.addNatureAbsenceRebriqueToCollectionIfMissing([], null, natureAbsenceRebrique, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(natureAbsenceRebrique);
      });

      it('should return initial array if no NatureAbsenceRebrique is added', () => {
        const natureAbsenceRebriqueCollection: INatureAbsenceRebrique[] = [{ id: 123 }];
        expectedResult = service.addNatureAbsenceRebriqueToCollectionIfMissing(natureAbsenceRebriqueCollection, undefined, null);
        expect(expectedResult).toEqual(natureAbsenceRebriqueCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
