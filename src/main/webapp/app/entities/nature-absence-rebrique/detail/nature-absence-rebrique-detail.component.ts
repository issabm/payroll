import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INatureAbsenceRebrique } from '../nature-absence-rebrique.model';

@Component({
  selector: 'jhi-nature-absence-rebrique-detail',
  templateUrl: './nature-absence-rebrique-detail.component.html',
})
export class NatureAbsenceRebriqueDetailComponent implements OnInit {
  natureAbsenceRebrique: INatureAbsenceRebrique | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natureAbsenceRebrique }) => {
      this.natureAbsenceRebrique = natureAbsenceRebrique;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
