import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRebriqueContrat } from '../rebrique-contrat.model';

@Component({
  selector: 'jhi-rebrique-contrat-detail',
  templateUrl: './rebrique-contrat-detail.component.html',
})
export class RebriqueContratDetailComponent implements OnInit {
  rebriqueContrat: IRebriqueContrat | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rebriqueContrat }) => {
      this.rebriqueContrat = rebriqueContrat;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
