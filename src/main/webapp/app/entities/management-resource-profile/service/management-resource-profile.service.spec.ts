import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IManagementResourceProfile, ManagementResourceProfile } from '../management-resource-profile.model';

import { ManagementResourceProfileService } from './management-resource-profile.service';

describe('ManagementResourceProfile Service', () => {
  let service: ManagementResourceProfileService;
  let httpMock: HttpTestingController;
  let elemDefault: IManagementResourceProfile;
  let expectedResult: IManagementResourceProfile | IManagementResourceProfile[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ManagementResourceProfileService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      profile: 'AAAAAAA',
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

    it('should create a ManagementResourceProfile', () => {
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

      service.create(new ManagementResourceProfile()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ManagementResourceProfile', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          profile: 'BBBBBB',
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

    it('should partial update a ManagementResourceProfile', () => {
      const patchObject = Object.assign(
        {
          profile: 'BBBBBB',
          dateop: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: 'BBBBBB',
          createdBy: 'BBBBBB',
          isDeleted: true,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new ManagementResourceProfile()
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

    it('should return a list of ManagementResourceProfile', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          profile: 'BBBBBB',
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

    it('should delete a ManagementResourceProfile', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addManagementResourceProfileToCollectionIfMissing', () => {
      it('should add a ManagementResourceProfile to an empty array', () => {
        const managementResourceProfile: IManagementResourceProfile = { id: 123 };
        expectedResult = service.addManagementResourceProfileToCollectionIfMissing([], managementResourceProfile);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(managementResourceProfile);
      });

      it('should not add a ManagementResourceProfile to an array that contains it', () => {
        const managementResourceProfile: IManagementResourceProfile = { id: 123 };
        const managementResourceProfileCollection: IManagementResourceProfile[] = [
          {
            ...managementResourceProfile,
          },
          { id: 456 },
        ];
        expectedResult = service.addManagementResourceProfileToCollectionIfMissing(
          managementResourceProfileCollection,
          managementResourceProfile
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ManagementResourceProfile to an array that doesn't contain it", () => {
        const managementResourceProfile: IManagementResourceProfile = { id: 123 };
        const managementResourceProfileCollection: IManagementResourceProfile[] = [{ id: 456 }];
        expectedResult = service.addManagementResourceProfileToCollectionIfMissing(
          managementResourceProfileCollection,
          managementResourceProfile
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(managementResourceProfile);
      });

      it('should add only unique ManagementResourceProfile to an array', () => {
        const managementResourceProfileArray: IManagementResourceProfile[] = [{ id: 123 }, { id: 456 }, { id: 64426 }];
        const managementResourceProfileCollection: IManagementResourceProfile[] = [{ id: 123 }];
        expectedResult = service.addManagementResourceProfileToCollectionIfMissing(
          managementResourceProfileCollection,
          ...managementResourceProfileArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const managementResourceProfile: IManagementResourceProfile = { id: 123 };
        const managementResourceProfile2: IManagementResourceProfile = { id: 456 };
        expectedResult = service.addManagementResourceProfileToCollectionIfMissing(
          [],
          managementResourceProfile,
          managementResourceProfile2
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(managementResourceProfile);
        expect(expectedResult).toContain(managementResourceProfile2);
      });

      it('should accept null and undefined values', () => {
        const managementResourceProfile: IManagementResourceProfile = { id: 123 };
        expectedResult = service.addManagementResourceProfileToCollectionIfMissing([], null, managementResourceProfile, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(managementResourceProfile);
      });

      it('should return initial array if no ManagementResourceProfile is added', () => {
        const managementResourceProfileCollection: IManagementResourceProfile[] = [{ id: 123 }];
        expectedResult = service.addManagementResourceProfileToCollectionIfMissing(managementResourceProfileCollection, undefined, null);
        expect(expectedResult).toEqual(managementResourceProfileCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
