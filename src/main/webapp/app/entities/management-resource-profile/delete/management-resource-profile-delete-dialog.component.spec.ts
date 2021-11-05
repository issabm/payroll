jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ManagementResourceProfileService } from '../service/management-resource-profile.service';

import { ManagementResourceProfileDeleteDialogComponent } from './management-resource-profile-delete-dialog.component';

describe('ManagementResourceProfile Management Delete Component', () => {
  let comp: ManagementResourceProfileDeleteDialogComponent;
  let fixture: ComponentFixture<ManagementResourceProfileDeleteDialogComponent>;
  let service: ManagementResourceProfileService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ManagementResourceProfileDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(ManagementResourceProfileDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ManagementResourceProfileDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ManagementResourceProfileService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({})));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
