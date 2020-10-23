import { Component, Input, OnInit } from '@angular/core';
import { Menu } from '@shared/model/menu';
import { AuthService } from '../../auth/auth.service';
import { TreeNode } from '@shared/model/tree';
import TreeService from '@shared/services/tree.service';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {

  menuTree: TreeNode<Menu>[];
  level: number;
  @Input() nzInlineCollapsed: boolean;

  constructor(private authService: AuthService, private router: Router) {
  }
  ngOnInit(): void {
    this.authService.getMenus().subscribe(treeData => {
      this.level = treeData.level;
      this.menuTree = treeData.tree;
    });
  }

}
