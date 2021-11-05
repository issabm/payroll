import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAssiete } from '../assiete.model';

@Component({
  selector: 'jhi-assiete-detail',
  templateUrl: './assiete-detail.component.html',
})
export class AssieteDetailComponent implements OnInit {
  assiete: IAssiete | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ assiete }) => {
      this.assiete = assiete;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
