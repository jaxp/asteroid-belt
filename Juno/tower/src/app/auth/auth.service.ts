import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { of, Observable } from 'rxjs';
import { Api } from '@constants/api';
import { Menu } from '@shared/model/menu';
import { User } from '@shared/model/user';
import { tap } from 'rxjs/operators';

const MENUS: Menu[] = [{
  title: 'dashboard',
  icon: 'dashboard',
  level: 1,
  children: [{
    title: 'menu1',
    url: '/welcome',
    icon: 'dashboard',
    level: 2,
    children: []
  }, {
    title: 'menu2',
    url: '/menu2',
    icon: 'dashboard',
    level: 2,
    children: []
  }, {
    title: 'menu3',
    url: '/menu3',
    icon: 'dashboard',
    level: 2,
    children: [{
      title: 'menu4',
      url: '/menu4',
      icon: 'dashboard',
      level: 3,
      children: []
    }, {
      title: 'menu5',
      url: '/menu5',
      icon: 'dashboard',
      level: 3,
      children: []
    }, {
      title: 'menu6',
      url: '/menu6',
      icon: 'dashboard',
      level: 3,
      children: []
    }]
  }]
}, {
  title: 'dashboard1',
  icon: 'dashboard',
  level: 1,
  children: []
}]

@Injectable({
  providedIn: "root"
})
export class AuthService {

  menus: Menu[];
  user: User;
  redirectUrl: string;

  getMenus(): Observable<Menu[]> {
    return of(MENUS);
  }

  login(data: { username: string, password: string }): Observable<any> {
    return this.http.post(Api.auth.login, data).pipe(
      tap(e => this.user = e.data)
    )
  }

  logout() {
    return this.http.get(Api.auth.logout).pipe(
      tap(e => {
        this.user = null;
        localStorage.removeItem("token");
      })
    )
  }

  isLogin(): boolean {
    return !!this.getAuthorizationToken();
  }

  getAuthorizationToken() {
    return localStorage.getItem("token");
  }

  setAuthorizationToken(token: string) {
    localStorage.setItem("token", token);
  }

  constructor(private http: HttpClient) { }
}
