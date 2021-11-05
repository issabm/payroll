import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFormPaieLigneReb } from '../form-paie-ligne-reb.model';

@Component({
  selector: 'jhi-form-paie-ligne-reb-detail',
  templateUrl: './form-paie-ligne-reb-detail.component.html',
})
export class FormPaieLigneRebDetailComponent implements OnInit {
  formPaieLigneReb: IFormPaieLigneReb | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ formPaieLigneReb }) => {
      this.formPaieLigneReb = formPaieLigneReb;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
