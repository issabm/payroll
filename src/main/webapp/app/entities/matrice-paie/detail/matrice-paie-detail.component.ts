import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMatricePaie } from '../matrice-paie.model';

@Component({
  selector: 'jhi-matrice-paie-detail',
  templateUrl: './matrice-paie-detail.component.html',
})
export class MatricePaieDetailComponent implements OnInit {
  matricePaie: IMatricePaie | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matricePaie }) => {
      this.matricePaie = matricePaie;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
