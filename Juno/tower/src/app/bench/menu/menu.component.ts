import { Component, Input } from '@angular/core';
import { Menu } from '@shared/model/menu';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent {

  menus: Menu[];
  @Input() nzInlineCollapsed: boolean;

  constructor(authService: AuthService) {
    authService.getMenus().subscribe(e => this.menus = e);
  }

}
