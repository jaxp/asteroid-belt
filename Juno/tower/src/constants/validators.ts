import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { AbstractControl, Validators } from '@angular/forms';

export type MyErrorsOptions = { 'zh-cn': string; en: string } & Record<string, NzSafeAny>;
export type MyValidationErrors = Record<string, MyErrorsOptions>;
export class TowerValidators extends Validators {
  static mobile(control: AbstractControl): MyValidationErrors | null {
    const value = control.value;
    if (isEmptyInputValue(value)) {
      return null;
    }
    return isMobile(value) ? null : { mobile: { 'zh-cn': `手机号码不正确`, en: `Mobile phone number is not valid` } };
  }
  static username(control: AbstractControl): MyValidationErrors | null {
    const value = control.value;
    if (isEmptyInputValue(value)) {
      return null;
    }
    return validUsername(value) ? null : { mobile: { 'zh-cn': `用户名格式错误`, en: `username is not valid` } };
  }
}

function isEmptyInputValue(value: NzSafeAny): boolean {
  return value == null || value.length === 0;
}

function isMobile(value: string): boolean {
  return typeof value === 'string' && /(^1\d{10}$)/.test(value);
}

function validUsername(value: string): boolean {
  return typeof value === 'string' && /^(?![0-9]+$)[0-9A-Za-z]{6,20}$/.test(value)
}