import { Component, OnInit, ViewChild } from '@angular/core';
import { MenuService } from './menu.service';
import { NzFormatEmitEvent, NzTreeComponent, NzTreeNode } from 'ng-zorro-antd/tree';
import { Observable } from 'rxjs';
import { Menu, TreeNode } from '@/app/shared/model';

@Component({
  selector: 'app-menu-management',
  templateUrl: './menu-management.component.html',
  styleUrls: ['./menu-management.component.less']
})
export class MenuManagementComponent implements OnInit {

  @ViewChild(NzTreeComponent)
  private tree: NzTreeComponent;

  tree$: Observable<TreeNode<Menu>[]>;
  visible = false;
  checked: [];
  selected: NzTreeNode;

  constructor(private menuService: MenuService) { }
  ngOnInit(): void {
    this.refresh();
  }

  nzEvent(event: NzFormatEmitEvent): void {
    if (event.eventName === 'click') {
      if (event.selectedKeys && event.selectedKeys.length === 1) {
        this.selected = event.selectedKeys[0];
      } else {
        this.selected = null;
      }
    }
  }

  newMenu(event: MouseEvent, node: NzTreeNode): void {
    node.isSelected = true;
    this.selected = node;
    console.log(node);
    event.stopPropagation();
    this.visible = true;
  }
  editMenu(event: MouseEvent, node: NzTreeNode): void {
    event.stopPropagation();
    this.visible = true;
  }
  close(): void {
    this.visible = false;
  }

  refresh(key?: string): void {
    this.selected = null;
    this.tree$ = this.menuService.getMenus();
  }

  expandAll(flag: boolean): void {
    let nodes = this.tree.getTreeNodes().filter(e => e.children && e.children.length > 0);
    while (nodes.length > 0) {
      let children = [];
      nodes.forEach(node => {
        node.isExpanded = flag;
        children = [...children, ...node.children];
      });
      nodes = children.filter(e => e.children && e.children.length > 0);
    }
  }

}
