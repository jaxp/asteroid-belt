import { Menu, NormalTreeNode, Result, TreeNode } from '@/app/shared/model';
import TreeService from '@/app/shared/services/tree.service';
import { Api } from '@/constants/api';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  constructor(private http: HttpClient, private treeService: TreeService) { }

  getMenus(): Observable<TreeNode<any>[]> {
    return this.http.get<Result<Menu[]>>(Api.menu.getMenus).pipe(
      map(res => this.buildMenuTree(res.data)),
      catchError(err => {
        console.log(err);
        return of(err);
      })
    );
  }

  buildMenuTree(data: Menu[]): TreeNode<Menu>[] {
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
      node.isLeaf = !e.type;
      return node;
    });
    const tree = this.treeService.getTree(nodes, {isLeaf: n => n.isLeaf}).tree;
    const root = new NormalTreeNode<Menu>();
    root.id = root.key = 'root';
    root.title = '菜单树';
    root.icon = 'folder';
    root.selected = false;
    root.expanded = true;
    root.origin = null;
    root.checked = false;
    root.selectable = true;
    root.disableCheckbox = true;
    root.children = tree;
    root.isLeaf = false;
    return [root];
  }
}
