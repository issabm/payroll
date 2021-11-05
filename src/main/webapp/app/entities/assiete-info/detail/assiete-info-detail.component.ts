import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAssieteInfo } from '../assiete-info.model';

@Component({
  selector: 'jhi-assiete-info-detail',
  templateUrl: './assiete-info-detail.component.html',
})
export class AssieteInfoDetailComponent implements OnInit {
  assieteInfo: IAssieteInfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ assieteInfo }) => {
      this.assieteInfo = assieteInfo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
