import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IFormPaieLigne, FormPaieLigne } from '../form-paie-ligne.model';

import { FormPaieLigneService } from './form-paie-ligne.service';

describe('FormPaieLigne Service', () => {
  let service: FormPaieLigneService;
  let httpMock: HttpTestingController;
  let elemDefault: IFormPaieLigne;
  let expectedResult: IFormPaieLigne | IFormPaieLigne[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FormPaieLigneService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      priorite: 0,
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

    it('should create a FormPaieLigne', () => {
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

      service.create(new FormPaieLigne()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FormPaieLigne', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          priorite: 1,
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

    it('should partial update a FormPaieLigne', () => {
      const patchObject = Object.assign(
        {
          code: 'BBBBBB',
          libEn: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          createdDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new FormPaieLigne()
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

    it('should return a list of FormPaieLigne', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          priorite: 1,
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

    it('should delete a FormPaieLigne', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addFormPaieLigneToCollectionIfMissing', () => {
      it('should add a FormPaieLigne to an empty array', () => {
        const formPaieLigne: IFormPaieLigne = { id: 123 };
        expectedResult = service.addFormPaieLigneToCollectionIfMissing([], formPaieLigne);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(formPaieLigne);
      });

      it('should not add a FormPaieLigne to an array that contains it', () => {
        const formPaieLigne: IFormPaieLigne = { id: 123 };
        const formPaieLigneCollection: IFormPaieLigne[] = [
          {
            ...formPaieLigne,
          },
          { id: 456 },
        ];
        expectedResult = service.addFormPaieLigneToCollectionIfMissing(formPaieLigneCollection, formPaieLigne);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FormPaieLigne to an array that doesn't contain it", () => {
        const formPaieLigne: IFormPaieLigne = { id: 123 };
        const formPaieLigneCollection: IFormPaieLigne[] = [{ id: 456 }];
        expectedResult = service.addFormPaieLigneToCollectionIfMissing(formPaieLigneCollection, formPaieLigne);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(formPaieLigne);
      });

      it('should add only unique FormPaieLigne to an array', () => {
        const formPaieLigneArray: IFormPaieLigne[] = [{ id: 123 }, { id: 456 }, { id: 95968 }];
        const formPaieLigneCollection: IFormPaieLigne[] = [{ id: 123 }];
        expectedResult = service.addFormPaieLigneToCollectionIfMissing(formPaieLigneCollection, ...formPaieLigneArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const formPaieLigne: IFormPaieLigne = { id: 123 };
        const formPaieLigne2: IFormPaieLigne = { id: 456 };
        expectedResult = service.addFormPaieLigneToCollectionIfMissing([], formPaieLigne, formPaieLigne2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(formPaieLigne);
        expect(expectedResult).toContain(formPaieLigne2);
      });

      it('should accept null and undefined values', () => {
        const formPaieLigne: IFormPaieLigne = { id: 123 };
        expectedResult = service.addFormPaieLigneToCollectionIfMissing([], null, formPaieLigne, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(formPaieLigne);
      });

      it('should return initial array if no FormPaieLigne is added', () => {
        const formPaieLigneCollection: IFormPaieLigne[] = [{ id: 123 }];
        expectedResult = service.addFormPaieLigneToCollectionIfMissing(formPaieLigneCollection, undefined, null);
        expect(expectedResult).toEqual(formPaieLigneCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
