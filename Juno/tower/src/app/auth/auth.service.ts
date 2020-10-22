import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { of, Observable } from 'rxjs';
import { Api } from '@constants/api';
import { Menu } from '@shared/model/menu';
import { User } from '@shared/model/user';
import { map, tap } from 'rxjs/operators';
import { Result } from '@shared/model/result';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  menus: Menu[];
  user: User;
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

  getMenus(): Observable<Menu[]> {
    return this.http.get<Result<Menu[]>>(Api.auth.getMenus).pipe(
      map(res => res.data)
    );
  }

  getAuthorities(): void {
    this.http.get(Api.auth.getAuthorities).subscribe(e => console.log(e));
  }

  login(data: { username: string, password: string }): Observable<any> {
    return this.http.post(Api.auth.login, data).pipe(
      tap(e => this.user = e.data)
    );
  }

  logout(): void {
    this.user = null;
    localStorage.removeItem('token');
    this.http.get(Api.auth.logout).subscribe();
  }

  isLogin(): boolean {
    return !!this.getAuthorizationToken();
  }

  getAuthorizationToken(): string {
    return localStorage.getItem('token');
  }

  setAuthorizationToken(token: string): void {
    localStorage.setItem('token', token);
  }

  constructor(private http: HttpClient) { }
}
