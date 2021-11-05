import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOperatorForm } from '../operator-form.model';

@Component({
  selector: 'jhi-operator-form-detail',
  templateUrl: './operator-form-detail.component.html',
})
export class OperatorFormDetailComponent implements OnInit {
  operatorForm: IOperatorForm | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ operatorForm }) => {
      this.operatorForm = operatorForm;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
