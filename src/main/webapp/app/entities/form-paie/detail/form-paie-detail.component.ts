import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFormPaie } from '../form-paie.model';

@Component({
  selector: 'jhi-form-paie-detail',
  templateUrl: './form-paie-detail.component.html',
})
export class FormPaieDetailComponent implements OnInit {
  formPaie: IFormPaie | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ formPaie }) => {
      this.formPaie = formPaie;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
