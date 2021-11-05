jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { SoldeAbsenceContratService } from '../service/solde-absence-contrat.service';

import { SoldeAbsenceContratDeleteDialogComponent } from './solde-absence-contrat-delete-dialog.component';

describe('SoldeAbsenceContrat Management Delete Component', () => {
  let comp: SoldeAbsenceContratDeleteDialogComponent;
  let fixture: ComponentFixture<SoldeAbsenceContratDeleteDialogComponent>;
  let service: SoldeAbsenceContratService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SoldeAbsenceContratDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(SoldeAbsenceContratDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SoldeAbsenceContratDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SoldeAbsenceContratService);
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
