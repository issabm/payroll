import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISoldeAbsenceContrat, SoldeAbsenceContrat } from '../solde-absence-contrat.model';

import { SoldeAbsenceContratService } from './solde-absence-contrat.service';

describe('SoldeAbsenceContrat Service', () => {
  let service: SoldeAbsenceContratService;
  let httpMock: HttpTestingController;
  let elemDefault: ISoldeAbsenceContrat;
  let expectedResult: ISoldeAbsenceContrat | ISoldeAbsenceContrat[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SoldeAbsenceContratService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      annee: 0,
      fullPayRight: 0,
      halfPayRight: 0,
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

    it('should create a SoldeAbsenceContrat', () => {
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

      service.create(new SoldeAbsenceContrat()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SoldeAbsenceContrat', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          annee: 1,
          fullPayRight: 1,
          halfPayRight: 1,
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

    it('should partial update a SoldeAbsenceContrat', () => {
      const patchObject = Object.assign(
        {
          fullPayRight: 1,
          util: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          op: 'BBBBBB',
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new SoldeAbsenceContrat()
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

    it('should return a list of SoldeAbsenceContrat', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          annee: 1,
          fullPayRight: 1,
          halfPayRight: 1,
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

    it('should delete a SoldeAbsenceContrat', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSoldeAbsenceContratToCollectionIfMissing', () => {
      it('should add a SoldeAbsenceContrat to an empty array', () => {
        const soldeAbsenceContrat: ISoldeAbsenceContrat = { id: 123 };
        expectedResult = service.addSoldeAbsenceContratToCollectionIfMissing([], soldeAbsenceContrat);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(soldeAbsenceContrat);
      });

      it('should not add a SoldeAbsenceContrat to an array that contains it', () => {
        const soldeAbsenceContrat: ISoldeAbsenceContrat = { id: 123 };
        const soldeAbsenceContratCollection: ISoldeAbsenceContrat[] = [
          {
            ...soldeAbsenceContrat,
          },
          { id: 456 },
        ];
        expectedResult = service.addSoldeAbsenceContratToCollectionIfMissing(soldeAbsenceContratCollection, soldeAbsenceContrat);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SoldeAbsenceContrat to an array that doesn't contain it", () => {
        const soldeAbsenceContrat: ISoldeAbsenceContrat = { id: 123 };
        const soldeAbsenceContratCollection: ISoldeAbsenceContrat[] = [{ id: 456 }];
        expectedResult = service.addSoldeAbsenceContratToCollectionIfMissing(soldeAbsenceContratCollection, soldeAbsenceContrat);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(soldeAbsenceContrat);
      });

      it('should add only unique SoldeAbsenceContrat to an array', () => {
        const soldeAbsenceContratArray: ISoldeAbsenceContrat[] = [{ id: 123 }, { id: 456 }, { id: 34245 }];
        const soldeAbsenceContratCollection: ISoldeAbsenceContrat[] = [{ id: 123 }];
        expectedResult = service.addSoldeAbsenceContratToCollectionIfMissing(soldeAbsenceContratCollection, ...soldeAbsenceContratArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const soldeAbsenceContrat: ISoldeAbsenceContrat = { id: 123 };
        const soldeAbsenceContrat2: ISoldeAbsenceContrat = { id: 456 };
        expectedResult = service.addSoldeAbsenceContratToCollectionIfMissing([], soldeAbsenceContrat, soldeAbsenceContrat2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(soldeAbsenceContrat);
        expect(expectedResult).toContain(soldeAbsenceContrat2);
      });

      it('should accept null and undefined values', () => {
        const soldeAbsenceContrat: ISoldeAbsenceContrat = { id: 123 };
        expectedResult = service.addSoldeAbsenceContratToCollectionIfMissing([], null, soldeAbsenceContrat, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(soldeAbsenceContrat);
      });

      it('should return initial array if no SoldeAbsenceContrat is added', () => {
        const soldeAbsenceContratCollection: ISoldeAbsenceContrat[] = [{ id: 123 }];
        expectedResult = service.addSoldeAbsenceContratToCollectionIfMissing(soldeAbsenceContratCollection, undefined, null);
        expect(expectedResult).toEqual(soldeAbsenceContratCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
