import { Component, Input, OnInit } from '@angular/core';
import { Menu, TreeNode } from '@shared/model';
import { AuthService } from '../../auth/auth.service';
import { Router } from '@angular/router';

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
