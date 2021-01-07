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
import { NzDividerModule } from 'ng-zorro-antd/divider';
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
import { NzCarouselModule } from 'ng-zorro-antd/carousel';
import { NzPopconfirmModule } from 'ng-zorro-antd/popconfirm';
import { NzSwitchModule } from 'ng-zorro-antd/switch';
import { NzCodeEditorModule } from 'ng-zorro-antd/code-editor';
import { NgxEchartsModule } from 'ngx-echarts';

import { ConstantPipe } from './pipes/constant.pipe';
import { GeometricElfDirective } from './directives/geometric-elf.directive';
import { SlideCaptchaComponent } from './components/slide-captcha/slide-captcha.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { EchartsComponent } from './components/dashboard/echarts/echarts.component';
import { EditorComponent } from './components/dashboard/editor/editor.component';
import { NzCollapseModule } from 'ng-zorro-antd/collapse';



@NgModule({
  declarations: [GeometricElfDirective, ConstantPipe, SlideCaptchaComponent, DashboardComponent, EchartsComponent, EditorComponent],
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
    NzDividerModule,
    NzCollapseModule,
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
    NzCarouselModule,
    NzSwitchModule,
    NzPopconfirmModule,
    NzCodeEditorModule,
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
    NzDividerModule,
    NzCollapseModule,
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
    NzCarouselModule,
    NzSwitchModule,
    NzPopconfirmModule,
    NgxEchartsModule,
    ReactiveFormsModule,
    FormsModule,
    GeometricElfDirective,
    SlideCaptchaComponent,
    DashboardComponent,
    ConstantPipe,
    HttpClientModule
  ]
})
export class SharedModule { }
