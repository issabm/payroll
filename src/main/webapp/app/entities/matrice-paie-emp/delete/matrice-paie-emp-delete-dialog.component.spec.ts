jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { MatricePaieEmpService } from '../service/matrice-paie-emp.service';

import { MatricePaieEmpDeleteDialogComponent } from './matrice-paie-emp-delete-dialog.component';

describe('MatricePaieEmp Management Delete Component', () => {
  let comp: MatricePaieEmpDeleteDialogComponent;
  let fixture: ComponentFixture<MatricePaieEmpDeleteDialogComponent>;
  let service: MatricePaieEmpService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [MatricePaieEmpDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(MatricePaieEmpDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MatricePaieEmpDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(MatricePaieEmpService);
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
