import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPalierImpo, PalierImpo } from '../palier-impo.model';

import { PalierImpoService } from './palier-impo.service';

describe('PalierImpo Service', () => {
  let service: PalierImpoService;
  let httpMock: HttpTestingController;
  let elemDefault: IPalierImpo;
  let expectedResult: IPalierImpo | IPalierImpo[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PalierImpoService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      annee: 0,
      payrollMin: 0,
      payrollMax: 0,
      impoValue: 0,
      util: 'AAAAAAA',
      dateop: currentDate,
      dateModif: currentDate,
      modifiedBy: 'AAAAAAA',
      op: 'AAAAAAA',
      isDeleted: false,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateop: currentDate.format(DATE_TIME_FORMAT),
          dateModif: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a PalierImpo', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateop: currentDate.format(DATE_TIME_FORMAT),
          dateModif: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateop: currentDate,
          dateModif: currentDate,
        },
        returnedFromService
      );

      service.create(new PalierImpo()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PalierImpo', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          annee: 1,
          payrollMin: 1,
          payrollMax: 1,
          impoValue: 1,
          util: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          dateModif: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          op: 'BBBBBB',
          isDeleted: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateop: currentDate,
          dateModif: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PalierImpo', () => {
      const patchObject = Object.assign(
        {
          payrollMax: 1,
          util: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          dateModif: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          isDeleted: true,
        },
        new PalierImpo()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateop: currentDate,
          dateModif: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PalierImpo', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          annee: 1,
          payrollMin: 1,
          payrollMax: 1,
          impoValue: 1,
          util: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          dateModif: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          op: 'BBBBBB',
          isDeleted: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateop: currentDate,
          dateModif: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a PalierImpo', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPalierImpoToCollectionIfMissing', () => {
      it('should add a PalierImpo to an empty array', () => {
        const palierImpo: IPalierImpo = { id: 123 };
        expectedResult = service.addPalierImpoToCollectionIfMissing([], palierImpo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(palierImpo);
      });

      it('should not add a PalierImpo to an array that contains it', () => {
        const palierImpo: IPalierImpo = { id: 123 };
        const palierImpoCollection: IPalierImpo[] = [
          {
            ...palierImpo,
          },
          { id: 456 },
        ];
        expectedResult = service.addPalierImpoToCollectionIfMissing(palierImpoCollection, palierImpo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PalierImpo to an array that doesn't contain it", () => {
        const palierImpo: IPalierImpo = { id: 123 };
        const palierImpoCollection: IPalierImpo[] = [{ id: 456 }];
        expectedResult = service.addPalierImpoToCollectionIfMissing(palierImpoCollection, palierImpo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(palierImpo);
      });

      it('should add only unique PalierImpo to an array', () => {
        const palierImpoArray: IPalierImpo[] = [{ id: 123 }, { id: 456 }, { id: 54947 }];
        const palierImpoCollection: IPalierImpo[] = [{ id: 123 }];
        expectedResult = service.addPalierImpoToCollectionIfMissing(palierImpoCollection, ...palierImpoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const palierImpo: IPalierImpo = { id: 123 };
        const palierImpo2: IPalierImpo = { id: 456 };
        expectedResult = service.addPalierImpoToCollectionIfMissing([], palierImpo, palierImpo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(palierImpo);
        expect(expectedResult).toContain(palierImpo2);
      });

      it('should accept null and undefined values', () => {
        const palierImpo: IPalierImpo = { id: 123 };
        expectedResult = service.addPalierImpoToCollectionIfMissing([], null, palierImpo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(palierImpo);
      });

      it('should return initial array if no PalierImpo is added', () => {
        const palierImpoCollection: IPalierImpo[] = [{ id: 123 }];
        expectedResult = service.addPalierImpoToCollectionIfMissing(palierImpoCollection, undefined, null);
        expect(expectedResult).toEqual(palierImpoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
