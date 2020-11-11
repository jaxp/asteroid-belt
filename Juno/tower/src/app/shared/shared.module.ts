import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzCheckboxModule } from 'ng-zorro-antd/checkbox';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzTreeModule } from 'ng-zorro-antd/tree';
import { IconsProviderModule } from '../icons-provider.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NzGridModule } from 'ng-zorro-antd/grid';
import { NzPageHeaderModule } from 'ng-zorro-antd/page-header';
import { NzTagModule } from 'ng-zorro-antd/tag';
import { NzBreadCrumbModule } from 'ng-zorro-antd/breadcrumb';
import { NzSpaceModule } from 'ng-zorro-antd/space';
import { NzDrawerModule } from 'ng-zorro-antd/drawer';
import { NzModalModule } from 'ng-zorro-antd/modal';
import { NzToolTipModule } from 'ng-zorro-antd/tooltip';
import { NzSpinModule } from 'ng-zorro-antd/spin';
import { NzDescriptionsModule } from 'ng-zorro-antd/descriptions';
import { NzBadgeModule } from 'ng-zorro-antd/badge';
import { NgxEchartsModule } from 'ngx-echarts';
import { GeometricElfDirective } from './directives/geometric-elf.directive';
import { GraphDirective } from './directives/graph.directive';



@NgModule({
  declarations: [GeometricElfDirective, GraphDirective],
  imports: [
    CommonModule,
    IconsProviderModule,
    NzLayoutModule,
    NzFormModule,
    NzMenuModule,
    NzButtonModule,
    NzCardModule,
    NzCheckboxModule,
    NzGridModule,
    NzInputModule,
    NzTreeModule,
    NzPageHeaderModule,
    NzTagModule,
    NzBreadCrumbModule,
    NzSpaceModule,
    NzDrawerModule,
    NzModalModule,
    NzToolTipModule,
    NzSpinModule,
    NzDescriptionsModule,
    NzBadgeModule,
    NgxEchartsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  exports: [
    CommonModule,
    IconsProviderModule,
    NzLayoutModule,
    NzFormModule,
    NzMenuModule,
    NzButtonModule,
    NzCardModule,
    NzCheckboxModule,
    NzGridModule,
    NzInputModule,
    NzTreeModule,
    NzPageHeaderModule,
    NzTagModule,
    NzBreadCrumbModule,
    NzSpaceModule,
    NzDrawerModule,
    NzModalModule,
    NzToolTipModule,
    NzSpinModule,
    NzDescriptionsModule,
    NzBadgeModule,
    NgxEchartsModule,
    ReactiveFormsModule,
    FormsModule,
    GeometricElfDirective,
    GraphDirective,
    HttpClientModule
  ]
})
export class SharedModule { }
