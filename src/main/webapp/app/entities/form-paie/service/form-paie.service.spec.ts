import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IFormPaie, FormPaie } from '../form-paie.model';

import { FormPaieService } from './form-paie.service';

describe('FormPaie Service', () => {
  let service: FormPaieService;
  let httpMock: HttpTestingController;
  let elemDefault: IFormPaie;
  let expectedResult: IFormPaie | IFormPaie[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FormPaieService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      code: 'AAAAAAA',
      libEn: 'AAAAAAA',
      libAr: 'AAAAAAA',
      anneDebut: 0,
      anneeFin: 0,
      moisDebut: 0,
      moisFin: 0,
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

    it('should create a FormPaie', () => {
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

      service.create(new FormPaie()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FormPaie', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          libEn: 'BBBBBB',
          libAr: 'BBBBBB',
          anneDebut: 1,
          anneeFin: 1,
          moisDebut: 1,
          moisFin: 1,
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

    it('should partial update a FormPaie', () => {
      const patchObject = Object.assign(
        {
          libEn: 'BBBBBB',
          libAr: 'BBBBBB',
          anneeFin: 1,
          moisDebut: 1,
          moisFin: 1,
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          util: 'BBBBBB',
          op: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          modifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new FormPaie()
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

    it('should return a list of FormPaie', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          code: 'BBBBBB',
          libEn: 'BBBBBB',
          libAr: 'BBBBBB',
          anneDebut: 1,
          anneeFin: 1,
          moisDebut: 1,
          moisFin: 1,
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

    it('should delete a FormPaie', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addFormPaieToCollectionIfMissing', () => {
      it('should add a FormPaie to an empty array', () => {
        const formPaie: IFormPaie = { id: 123 };
        expectedResult = service.addFormPaieToCollectionIfMissing([], formPaie);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(formPaie);
      });

      it('should not add a FormPaie to an array that contains it', () => {
        const formPaie: IFormPaie = { id: 123 };
        const formPaieCollection: IFormPaie[] = [
          {
            ...formPaie,
          },
          { id: 456 },
        ];
        expectedResult = service.addFormPaieToCollectionIfMissing(formPaieCollection, formPaie);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FormPaie to an array that doesn't contain it", () => {
        const formPaie: IFormPaie = { id: 123 };
        const formPaieCollection: IFormPaie[] = [{ id: 456 }];
        expectedResult = service.addFormPaieToCollectionIfMissing(formPaieCollection, formPaie);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(formPaie);
      });

      it('should add only unique FormPaie to an array', () => {
        const formPaieArray: IFormPaie[] = [{ id: 123 }, { id: 456 }, { id: 11064 }];
        const formPaieCollection: IFormPaie[] = [{ id: 123 }];
        expectedResult = service.addFormPaieToCollectionIfMissing(formPaieCollection, ...formPaieArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const formPaie: IFormPaie = { id: 123 };
        const formPaie2: IFormPaie = { id: 456 };
        expectedResult = service.addFormPaieToCollectionIfMissing([], formPaie, formPaie2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(formPaie);
        expect(expectedResult).toContain(formPaie2);
      });

      it('should accept null and undefined values', () => {
        const formPaie: IFormPaie = { id: 123 };
        expectedResult = service.addFormPaieToCollectionIfMissing([], null, formPaie, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(formPaie);
      });

      it('should return initial array if no FormPaie is added', () => {
        const formPaieCollection: IFormPaie[] = [{ id: 123 }];
        expectedResult = service.addFormPaieToCollectionIfMissing(formPaieCollection, undefined, null);
        expect(expectedResult).toEqual(formPaieCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
