import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFormPaieLigne } from '../form-paie-ligne.model';

@Component({
  selector: 'jhi-form-paie-ligne-detail',
  templateUrl: './form-paie-ligne-detail.component.html',
})
export class FormPaieLigneDetailComponent implements OnInit {
  formPaieLigne: IFormPaieLigne | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ formPaieLigne }) => {
      this.formPaieLigne = formPaieLigne;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
