import { Menu, NormalTreeNode, Result, TreeNode } from '@/app/shared/model';
import TreeService from '@/app/shared/services/tree.service';
import { Api } from '@/constants/api';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { id_ID } from 'ng-zorro-antd/i18n';
import { Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  constructor(private http: HttpClient, private treeService: TreeService) { }

  getMenus(): Observable<TreeNode<any>[]> {
    return this.http.get<Result<Menu[]>>(Api.menu.getMenus).pipe(
      map(res => this.buildMenuTree(res.data)),
      tap(e => console.log(e))
    );
  }

  buildMenuTree(data: Menu[]): TreeNode<any>[] {
    const nodes = data.map(e => {
      const node = new NormalTreeNode<Menu>();
      node.id = e.id;
      node.pid = e.pid;
      node.key = e.id;
      node.title = e.title;
      node.icon = e.icon;
      node.selected = false;
      node.expanded = false;
      node.origin = e;
      node.checked = false;
      node.selectable = true;
      node.disableCheckbox = true;
      return node;
    });
    return this.treeService.getTree(nodes).tree;
  }
}
