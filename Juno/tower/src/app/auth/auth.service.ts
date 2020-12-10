import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { of, Observable } from 'rxjs';
import { Router } from '@angular/router';
import { Api } from '@constants/api';
import { Menu, User, Result, MenuTreeNode, TreeNode } from '@shared/model';
import { catchError, flatMap, map, tap } from 'rxjs/operators';
import TreeService from '../shared/services/tree.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { EncryptService } from '../shared/services/encrypt.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  nodeMap: Map<any, MenuTreeNode<Menu>>;
  user: User;
  authorities: string[];
  currentUrl: string;
  redirectUrl: string;

  getUser(): Observable<User> {
    if (this.user) {
      return of(this.user);
    } else {
      return this.http.get<Result<User>>(Api.auth.getUser).pipe(
        tap(res => this.user = res.data),
        map(res => res.data)
      );
    }
  }

  getMenus(): Observable<{ level: number, tree: TreeNode<any>[] }> {
    return this.http.get<Result<Menu[]>>(Api.auth.getMenus).pipe(
      map(res => this.buildMenuTree(res.data))
    );
  }

  buildMenuTree(data: Menu[]): { level: number, tree: TreeNode<any>[] } {
    const nodes = data.map(e => {
      const node = new MenuTreeNode<Menu>();
      node.id = e.id;
      node.pid = e.pid;
      node.title = e.title;
      node.icon = e.icon;
      node.disabled = !!e.disabled;
      node.selected = false;
      node.expanded = false;
      node.origin = e;
      return node;
    });
    this.nodeMap = new Map<any, MenuTreeNode<Menu>>();
    nodes.forEach(node => this.nodeMap[node.id] = node);
    this.openMenu(this.currentUrl);
    return this.treeService.getTree(nodes);
  }

  openMenu(url: string): void {
    this.currentUrl = url;
    if (this.nodeMap) {
      const target = Object.values(this.nodeMap).filter(e => '/page' + e.origin.url === url);
      if (target.length > 0) {
        target[0].selected = true;
        let id = target[0].pid;
        while (this.nodeMap[id]) {
          this.nodeMap[id].expanded = true;
          id = this.nodeMap[id].pid;
        }
      }
    }
  }

  getAuthorities(type: string): void {
    this.http.get<Result<string[]>>(Api.auth.getAuthorities + type)
      .subscribe(e => this.authorities = e.data);
  }

  login(data: { username: string, password: string }, certificate: string): Observable<Result<User>> {
    return this.encryptService.getEncrypted(data.password).pipe(
      flatMap(encryptedPwd =>
        this.http.post<Result<User>>(Api.auth.login, { username: data.username, password: encryptedPwd }, {
          headers: { captcha: certificate }
        }).pipe(
          tap(res => {
            this.user = res.data;
            this.router.navigate(['/page']);
          }),
          catchError(err => {
            this.message.error(err.msg);
            if (err.code === 10001) {
              this.encryptService.clearPublicKey();
            }
            return of(null);
          })
        )));
  }

  logout(): void {
    this.clearAuthorizationToken();
    this.http.get(Api.auth.logout).subscribe();
  }

  isLogin(): boolean {
    return !!this.getAuthorizationToken();
  }

  clearAuthorizationToken(): void {
    this.user = null;
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  getAuthorizationToken(): string {
    return localStorage.getItem('token');
  }

  setAuthorizationToken(token: string): void {
    localStorage.setItem('token', token);
  }

  constructor(private http: HttpClient, private router: Router, private treeService: TreeService,
    private message: NzMessageService, private encryptService: EncryptService) { }
}
