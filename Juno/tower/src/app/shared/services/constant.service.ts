import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConstantService {

  constants = {
    menu: {
      enabled: {
        true: '可用',
        false: '不可用'
      },
      type: {
        0: '菜单',
        1: '菜单夹'
      }
    }
  };

  getConstants(): any {
    return this.constants;
  }

  constructor() { }
}
