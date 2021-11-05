import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAssieteInfo, AssieteInfo } from '../assiete-info.model';

import { AssieteInfoService } from './assiete-info.service';

describe('AssieteInfo Service', () => {
  let service: AssieteInfoService;
  let httpMock: HttpTestingController;
  let elemDefault: IAssieteInfo;
  let expectedResult: IAssieteInfo | IAssieteInfo[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AssieteInfoService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      priorite: 0,
      val: 0,
      util: 'AAAAAAA',
      dateSituation: currentDate,
      dateop: currentDate,
      modifiedBy: 'AAAAAAA',
      createdBy: 'AAAAAAA',
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

    it('should create a AssieteInfo', () => {
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

      service.create(new AssieteInfo()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AssieteInfo', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          priorite: 1,
          val: 1,
          util: 'BBBBBB',
          dateSituation: currentDate.format(DATE_FORMAT),
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          createdBy: 'BBBBBB',
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

    it('should partial update a AssieteInfo', () => {
      const patchObject = Object.assign(
        {
          util: 'BBBBBB',
          dateSituation: currentDate.format(DATE_FORMAT),
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          op: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new AssieteInfo()
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

    it('should return a list of AssieteInfo', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          priorite: 1,
          val: 1,
          util: 'BBBBBB',
          dateSituation: currentDate.format(DATE_FORMAT),
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          createdBy: 'BBBBBB',
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

    it('should delete a AssieteInfo', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAssieteInfoToCollectionIfMissing', () => {
      it('should add a AssieteInfo to an empty array', () => {
        const assieteInfo: IAssieteInfo = { id: 123 };
        expectedResult = service.addAssieteInfoToCollectionIfMissing([], assieteInfo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(assieteInfo);
      });

      it('should not add a AssieteInfo to an array that contains it', () => {
        const assieteInfo: IAssieteInfo = { id: 123 };
        const assieteInfoCollection: IAssieteInfo[] = [
          {
            ...assieteInfo,
          },
          { id: 456 },
        ];
        expectedResult = service.addAssieteInfoToCollectionIfMissing(assieteInfoCollection, assieteInfo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AssieteInfo to an array that doesn't contain it", () => {
        const assieteInfo: IAssieteInfo = { id: 123 };
        const assieteInfoCollection: IAssieteInfo[] = [{ id: 456 }];
        expectedResult = service.addAssieteInfoToCollectionIfMissing(assieteInfoCollection, assieteInfo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(assieteInfo);
      });

      it('should add only unique AssieteInfo to an array', () => {
        const assieteInfoArray: IAssieteInfo[] = [{ id: 123 }, { id: 456 }, { id: 78258 }];
        const assieteInfoCollection: IAssieteInfo[] = [{ id: 123 }];
        expectedResult = service.addAssieteInfoToCollectionIfMissing(assieteInfoCollection, ...assieteInfoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const assieteInfo: IAssieteInfo = { id: 123 };
        const assieteInfo2: IAssieteInfo = { id: 456 };
        expectedResult = service.addAssieteInfoToCollectionIfMissing([], assieteInfo, assieteInfo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(assieteInfo);
        expect(expectedResult).toContain(assieteInfo2);
      });

      it('should accept null and undefined values', () => {
        const assieteInfo: IAssieteInfo = { id: 123 };
        expectedResult = service.addAssieteInfoToCollectionIfMissing([], null, assieteInfo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(assieteInfo);
      });

      it('should return initial array if no AssieteInfo is added', () => {
        const assieteInfoCollection: IAssieteInfo[] = [{ id: 123 }];
        expectedResult = service.addAssieteInfoToCollectionIfMissing(assieteInfoCollection, undefined, null);
        expect(expectedResult).toEqual(assieteInfoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
