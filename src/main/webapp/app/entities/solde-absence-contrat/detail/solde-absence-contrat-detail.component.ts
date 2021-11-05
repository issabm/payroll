import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISoldeAbsenceContrat } from '../solde-absence-contrat.model';

@Component({
  selector: 'jhi-solde-absence-contrat-detail',
  templateUrl: './solde-absence-contrat-detail.component.html',
})
export class SoldeAbsenceContratDetailComponent implements OnInit {
  soldeAbsenceContrat: ISoldeAbsenceContrat | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ soldeAbsenceContrat }) => {
      this.soldeAbsenceContrat = soldeAbsenceContrat;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
