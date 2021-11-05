import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IMatricePaie, MatricePaie } from '../matrice-paie.model';

import { MatricePaieService } from './matrice-paie.service';

describe('MatricePaie Service', () => {
  let service: MatricePaieService;
  let httpMock: HttpTestingController;
  let elemDefault: IMatricePaie;
  let expectedResult: IMatricePaie | IMatricePaie[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MatricePaieService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      code: 'AAAAAAA',
      libAr: 'AAAAAAA',
      libEn: 'AAAAAAA',
      isDisplay: false,
      anneeDebut: 0,
      moisDebut: 0,
      anneeFin: 0,
      moisFin: 0,
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

    it('should create a MatricePaie', () => {
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

      service.create(new MatricePaie()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MatricePaie', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          libAr: 'BBBBBB',
          libEn: 'BBBBBB',
          isDisplay: true,
          anneeDebut: 1,
          moisDebut: 1,
          anneeFin: 1,
          moisFin: 1,
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

    it('should partial update a MatricePaie', () => {
      const patchObject = Object.assign(
        {
          libEn: 'BBBBBB',
          isDisplay: true,
          anneeFin: 1,
          moisFin: 1,
          op: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new MatricePaie()
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

    it('should return a list of MatricePaie', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          libAr: 'BBBBBB',
          libEn: 'BBBBBB',
          isDisplay: true,
          anneeDebut: 1,
          moisDebut: 1,
          anneeFin: 1,
          moisFin: 1,
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

    it('should delete a MatricePaie', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addMatricePaieToCollectionIfMissing', () => {
      it('should add a MatricePaie to an empty array', () => {
        const matricePaie: IMatricePaie = { id: 123 };
        expectedResult = service.addMatricePaieToCollectionIfMissing([], matricePaie);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(matricePaie);
      });

      it('should not add a MatricePaie to an array that contains it', () => {
        const matricePaie: IMatricePaie = { id: 123 };
        const matricePaieCollection: IMatricePaie[] = [
          {
            ...matricePaie,
          },
          { id: 456 },
        ];
        expectedResult = service.addMatricePaieToCollectionIfMissing(matricePaieCollection, matricePaie);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MatricePaie to an array that doesn't contain it", () => {
        const matricePaie: IMatricePaie = { id: 123 };
        const matricePaieCollection: IMatricePaie[] = [{ id: 456 }];
        expectedResult = service.addMatricePaieToCollectionIfMissing(matricePaieCollection, matricePaie);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(matricePaie);
      });

      it('should add only unique MatricePaie to an array', () => {
        const matricePaieArray: IMatricePaie[] = [{ id: 123 }, { id: 456 }, { id: 1145 }];
        const matricePaieCollection: IMatricePaie[] = [{ id: 123 }];
        expectedResult = service.addMatricePaieToCollectionIfMissing(matricePaieCollection, ...matricePaieArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const matricePaie: IMatricePaie = { id: 123 };
        const matricePaie2: IMatricePaie = { id: 456 };
        expectedResult = service.addMatricePaieToCollectionIfMissing([], matricePaie, matricePaie2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(matricePaie);
        expect(expectedResult).toContain(matricePaie2);
      });

      it('should accept null and undefined values', () => {
        const matricePaie: IMatricePaie = { id: 123 };
        expectedResult = service.addMatricePaieToCollectionIfMissing([], null, matricePaie, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(matricePaie);
      });

      it('should return initial array if no MatricePaie is added', () => {
        const matricePaieCollection: IMatricePaie[] = [{ id: 123 }];
        expectedResult = service.addMatricePaieToCollectionIfMissing(matricePaieCollection, undefined, null);
        expect(expectedResult).toEqual(matricePaieCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
