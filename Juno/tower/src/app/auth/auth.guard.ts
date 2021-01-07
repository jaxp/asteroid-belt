import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, CanActivateChild, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate, CanActivateChild {

  constructor(private authService: AuthService, private router: Router) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.checkLogin(state.url);
  }

  canActivateChild(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    return this.canActivate(route, state);
  }

  checkLogin(url: string): Observable<true | UrlTree> {
    const online = this.authService.online();
    if (online) {
      return this.authService.getUser().pipe(
        map(e => {
          if (url === '/login') {
            return this.router.parseUrl('/page');
          }
          this.authService.openMenu(url);
          this.authService.redirectUrl = url;
          return true;
        })
      );
    } else {
      return of(url === '/login' || this.router.parseUrl('/login'));
    }
  }
}
