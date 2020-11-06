import { Component, OnInit, ViewChild } from '@angular/core';
import { MenuService } from './menu.service';
import { NzFormatEmitEvent, NzTreeComponent, NzTreeNode } from 'ng-zorro-antd/tree';
import { Observable, of } from 'rxjs';
import { Menu, TreeNode } from '@/app/shared/model';
import TreeService from '@/app/shared/services/tree.service';
import { tap } from 'rxjs/operators';
import { NzFormTooltipIcon } from 'ng-zorro-antd/form';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

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
        this.selected = event.selectedKeys[0];
      } else {
        this.selected = null;
      }
    }
  }

  newMenu(event: MouseEvent, node: NzTreeNode): void {
    node.isSelected = true;
    this.selected = node;
    event.stopPropagation();
    this.menuOp = this.menuOps.new;
  }
  editMenu(event: MouseEvent, node: NzTreeNode): void {
    node.isSelected = true;
    this.selected = node;
    event.stopPropagation();
    this.menuOp = this.menuOps.update;
  }
  close(): void {
    this.menuOp = null;
  }

  refresh(key?: string): void {
    this.selected = null;
    this.treeLoading = true;
    this.tree$ = this.menuService.getMenus().pipe(
      tap(() => this.treeLoading = false)
    );
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
