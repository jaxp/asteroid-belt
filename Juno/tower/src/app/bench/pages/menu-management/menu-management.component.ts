import { Component, OnInit } from '@angular/core';
import { MenuService } from './menu.service';
import { NzFormatEmitEvent } from 'ng-zorro-antd/tree';
import { Observable } from 'rxjs';
import { Menu, TreeNode } from '@/app/shared/model';

@Component({
  selector: 'app-menu-management',
  templateUrl: './menu-management.component.html',
  styleUrls: ['./menu-management.component.less']
})
export class MenuManagementComponent implements OnInit {

  tree$: Observable<TreeNode<Menu>[]>;

  constructor(private menuService: MenuService) { }
  ngOnInit(): void {
    this.tree$ = this.menuService.getMenus();
  }

  nzEvent(event: NzFormatEmitEvent): void {
    console.log(event);
  }

}
