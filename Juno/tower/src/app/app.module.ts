import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NZ_I18N } from 'ng-zorro-antd/i18n';
import { zh_CN } from 'ng-zorro-antd/i18n';
import { registerLocaleData } from '@angular/common';
import zh from '@angular/common/locales/zh';

import { SharedModule } from './shared/shared.module';
import { AuthModule } from './auth/auth.module';
import { BenchModule } from './bench/bench.module';
import { NzMessageService } from 'ng-zorro-antd/message';
import { httpInterceptorProviders } from './interceptors';

registerLocaleData(zh);

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    SharedModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    AuthModule,
    BenchModule
  ],
  providers: [
    httpInterceptorProviders,
    NzMessageService,
    { provide: NZ_I18N, useValue: zh_CN }
  ],
  exports: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
