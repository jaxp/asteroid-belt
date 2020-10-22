import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { BenchRoutingModule } from './bench-routing.module';
import { HomeComponent } from './pages/home/home.component';
import { LayoutComponent } from './layout/layout.component';
import { MenuComponent } from './menu/menu.component';
import { MenuManagementComponent } from './pages/menu-management/menu-management.component';

@NgModule({
  declarations: [HomeComponent, LayoutComponent, MenuComponent, MenuManagementComponent],
  imports: [
    SharedModule,
    BenchRoutingModule
  ]
})
export class BenchModule { }
