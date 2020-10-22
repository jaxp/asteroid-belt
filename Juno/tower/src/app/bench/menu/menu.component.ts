import { Component, Input } from '@angular/core';
import { Menu } from '@shared/model/menu';
import { AuthService } from '../../auth/auth.service';
import { TreeNode } from '@shared/model/tree';
import TreeService from '@shared/services/tree.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent {

  menuTree: TreeNode<Menu>[];
  level: number;
  @Input() nzInlineCollapsed: boolean;

  constructor(authService: AuthService, private treeService: TreeService) {
    authService.getMenus().subscribe(e => this.buildTree(e));
    authService.getAuthorities();
  }

  buildTree(data: Menu[]): void {
    const nodes = data.map(e => {
      const node = new TreeNode<Menu>();
      node.id = e.id;
      node.pid = e.pid;
      node.title = e.title;
      node.disabled = !!e.disabled;
      node.selected = false;
      node.open = false;
      node.raw = e;
      return node;
    });
    const treeData = this.treeService.getTree(nodes);
    console.log(treeData);
    this.level = treeData.level;
    this.menuTree = treeData.tree;
  }

}
