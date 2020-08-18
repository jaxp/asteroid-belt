import { Component, Input } from '@angular/core';
import { Menu } from './model/menu'

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent {

  @Input() menus: Menu[];
  @Input() nzInlineCollapsed: boolean;

  constructor() { }

}
