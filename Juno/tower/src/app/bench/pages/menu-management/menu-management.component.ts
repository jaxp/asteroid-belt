import { Component, EventEmitter, OnInit, ViewChild } from '@angular/core';
import { MenuService } from './menu.service';
import { NzFormatEmitEvent, NzTreeComponent, NzTreeNode } from 'ng-zorro-antd/tree';
import { Observable, of } from 'rxjs';
import { Menu, TreeNode } from '@/app/shared/model';
import TreeService from '@/app/shared/services/tree.service';
import { tap } from 'rxjs/operators';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NzModalService } from 'ng-zorro-antd/modal';

@Component({
  selector: 'app-menu-management',
  templateUrl: './menu-management.component.html',
  styleUrls: ['./menu-management.component.less']
})
export class MenuManagementComponent implements OnInit {

  @ViewChild(NzTreeComponent)
  private tree: NzTreeComponent;

  menuOps = {
    new: {
      title: '新增子菜单'
    },
    update: {
      title: '修改菜单'
    },
    delete: {
      title: '删除菜单'
    }
  };
  menuOp = null;

  tree$: Observable<TreeNode<Menu>[]>;
  treeLoading: boolean;
  checked: [];
  selected: NzTreeNode;

  showMenuInfoPane: boolean;
  menuInfoNode: Menu;

  validateForm!: FormGroup;

  constructor(private menuService: MenuService, private treeService: TreeService, private fb: FormBuilder) { }

  ngOnInit(): void {
    this.refresh();
    this.validateForm = this.fb.group({
      title: [null, [Validators.required]],
      icon: [null, [Validators.required]]
    });
  }

  nzEvent(event: NzFormatEmitEvent): void {
    if (event.eventName === 'click') {
      if (event.selectedKeys && event.selectedKeys.length === 1) {
        this.setSelected(event.selectedKeys[0]);
      } else {
        this.setSelected(null);
      }
    }
  }

  enableMenu(id: string, enabled: boolean): void {
    this.menuService.enableMenu(id, enabled).subscribe();
  }

  newMenu(event: MouseEvent, node: NzTreeNode): void {
    this.setSelected(node);
    event.stopPropagation();
    this.menuOp = this.menuOps.new;
  }
  editMenu(event: MouseEvent, node: NzTreeNode): void {
    this.setSelected(node);
    event.stopPropagation();
    this.menuOp = this.menuOps.update;
  }
  close(): void {
    this.menuOp = null;
  }

  refresh(key?: string): void {
    this.setSelected(null);
    this.treeLoading = true;
    this.tree$ = this.menuService.getMenus().pipe(
      tap(() => this.treeLoading = false)
    );
  }

  setSelected(node: NzTreeNode | null): void {
    if (node) {
      if (node.key === this.selected?.key) {
        return;
      }
      this.selected = node;
      node.isSelected = true;
      if (this.menuInfoNode) {
        this.showMenuInfoPane = false;
        setTimeout(() => {
          this.showMenuInfoPane = true;
          this.menuInfoNode = JSON.parse(JSON.stringify(node.origin.origin));
        }, 200);
      } else {
        this.showMenuInfoPane = true;
        this.menuInfoNode = JSON.parse(JSON.stringify(node.origin.origin));
      }
    } else {
      this.showMenuInfoPane = false;
      this.menuInfoNode = null;
    }
  }

  expandAll(flag: boolean): void {
    this.treeService.expandAll(this.tree, flag);
  }

  submitForm(): void {
    // tslint:disable-next-line: forin
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }
  }
}
