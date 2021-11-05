import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMatricePaieEmp } from '../matrice-paie-emp.model';

@Component({
  selector: 'jhi-matrice-paie-emp-detail',
  templateUrl: './matrice-paie-emp-detail.component.html',
})
export class MatricePaieEmpDetailComponent implements OnInit {
  matricePaieEmp: IMatricePaieEmp | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matricePaieEmp }) => {
      this.matricePaieEmp = matricePaieEmp;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
