import { Pipe, PipeTransform } from '@angular/core';
import { ConstantService } from '../services/constant.service';

@Pipe({name: 'constant'})
export class ConstantPipe implements PipeTransform {
  transform(value: any, keyStr?: string): string {
    const keys = keyStr.split('.');
    keys.push(value);
    let result = this.constantService.getConstants();
    keys.forEach(key => {
      if (!result) {
        return null;
      }
      result = result[key];
    });
    return result;
  }

  constructor(private constantService: ConstantService) {}
}
