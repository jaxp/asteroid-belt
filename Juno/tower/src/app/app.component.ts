import { Component } from '@angular/core';
import { NzIconService } from 'ng-zorro-antd/icon';
import { AuthService } from './auth/auth.service';
import { Menu } from './menu/model/menu'
import { Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  isCollapsed = false;
  menus: Menu[]
  constructor(private iconService: NzIconService, authService: AuthService) {
    authService.getMenus().subscribe(e => {
      console.log(e)
      this.menus = e
    })
    this.iconService.fetchFromIconfont({
      scriptUrl: 'https://at.alicdn.com/t/font_2013394_am6l4btljq7.js'
    });
  }
}
