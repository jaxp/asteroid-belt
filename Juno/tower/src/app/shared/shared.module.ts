import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzCheckboxModule } from 'ng-zorro-antd/checkbox';
import { NzFormModule } from 'ng-zorro-antd/form';
import { IconsProviderModule } from '../icons-provider.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NzGridModule } from 'ng-zorro-antd/grid';



@NgModule({
  declarations: [],
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
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule
  ]
})
export class SharedModule { }
