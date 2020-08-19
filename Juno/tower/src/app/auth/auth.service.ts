import { Injectable } from '@angular/core';
import { of, Observable } from 'rxjs';
import { Menu } from '../model/menu';

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

  getMenus(): Observable<Menu[]> {
    return of(MENUS)
  }

  constructor() { }
}
