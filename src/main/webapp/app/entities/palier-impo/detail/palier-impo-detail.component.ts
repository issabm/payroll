import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPalierImpo } from '../palier-impo.model';

@Component({
  selector: 'jhi-palier-impo-detail',
  templateUrl: './palier-impo-detail.component.html',
})
export class PalierImpoDetailComponent implements OnInit {
  palierImpo: IPalierImpo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ palierImpo }) => {
      this.palierImpo = palierImpo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
