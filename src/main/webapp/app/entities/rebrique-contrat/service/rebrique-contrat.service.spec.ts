import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IRebriqueContrat, RebriqueContrat } from '../rebrique-contrat.model';

import { RebriqueContratService } from './rebrique-contrat.service';

describe('RebriqueContrat Service', () => {
  let service: RebriqueContratService;
  let httpMock: HttpTestingController;
  let elemDefault: IRebriqueContrat;
  let expectedResult: IRebriqueContrat | IRebriqueContrat[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RebriqueContratService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
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

    it('should create a RebriqueContrat', () => {
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

      service.create(new RebriqueContrat()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RebriqueContrat', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
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

    it('should partial update a RebriqueContrat', () => {
      const patchObject = Object.assign(
        {
          util: 'BBBBBB',
          dateSituation: currentDate.format(DATE_FORMAT),
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          createdBy: 'BBBBBB',
          op: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new RebriqueContrat()
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

    it('should return a list of RebriqueContrat', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
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

    it('should delete a RebriqueContrat', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addRebriqueContratToCollectionIfMissing', () => {
      it('should add a RebriqueContrat to an empty array', () => {
        const rebriqueContrat: IRebriqueContrat = { id: 123 };
        expectedResult = service.addRebriqueContratToCollectionIfMissing([], rebriqueContrat);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(rebriqueContrat);
      });

      it('should not add a RebriqueContrat to an array that contains it', () => {
        const rebriqueContrat: IRebriqueContrat = { id: 123 };
        const rebriqueContratCollection: IRebriqueContrat[] = [
          {
            ...rebriqueContrat,
          },
          { id: 456 },
        ];
        expectedResult = service.addRebriqueContratToCollectionIfMissing(rebriqueContratCollection, rebriqueContrat);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RebriqueContrat to an array that doesn't contain it", () => {
        const rebriqueContrat: IRebriqueContrat = { id: 123 };
        const rebriqueContratCollection: IRebriqueContrat[] = [{ id: 456 }];
        expectedResult = service.addRebriqueContratToCollectionIfMissing(rebriqueContratCollection, rebriqueContrat);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(rebriqueContrat);
      });

      it('should add only unique RebriqueContrat to an array', () => {
        const rebriqueContratArray: IRebriqueContrat[] = [{ id: 123 }, { id: 456 }, { id: 68338 }];
        const rebriqueContratCollection: IRebriqueContrat[] = [{ id: 123 }];
        expectedResult = service.addRebriqueContratToCollectionIfMissing(rebriqueContratCollection, ...rebriqueContratArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const rebriqueContrat: IRebriqueContrat = { id: 123 };
        const rebriqueContrat2: IRebriqueContrat = { id: 456 };
        expectedResult = service.addRebriqueContratToCollectionIfMissing([], rebriqueContrat, rebriqueContrat2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(rebriqueContrat);
        expect(expectedResult).toContain(rebriqueContrat2);
      });

      it('should accept null and undefined values', () => {
        const rebriqueContrat: IRebriqueContrat = { id: 123 };
        expectedResult = service.addRebriqueContratToCollectionIfMissing([], null, rebriqueContrat, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(rebriqueContrat);
      });

      it('should return initial array if no RebriqueContrat is added', () => {
        const rebriqueContratCollection: IRebriqueContrat[] = [{ id: 123 }];
        expectedResult = service.addRebriqueContratToCollectionIfMissing(rebriqueContratCollection, undefined, null);
        expect(expectedResult).toEqual(rebriqueContratCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
