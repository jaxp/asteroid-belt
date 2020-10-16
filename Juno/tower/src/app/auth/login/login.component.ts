import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd/message';
import { TowerValidators } from '@/constants/validators';
import { AuthService } from '../auth.service';
import { EncryptService } from '@shared/services/encrypt.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  validateForm!: FormGroup;
  loginLoading = false;

  autoTips: Record<string, Record<string, string>> = {
    'zh-cn': {
      required: '必填项'
    },
    en: {
      required: 'Input is required'
    }
  };

  submitForm(): void {
    for (const i in this.validateForm.controls) {
      if (this.validateForm.controls.hasOwnProperty(i)) {
        this.validateForm.controls[i].markAsDirty();
        this.validateForm.controls[i].updateValueAndValidity();
      }
    }
    const params = this.validateForm.value;
    this.loginLoading = true;
    this.encryptService.getEncrypted(params.password)
      .subscribe(e => {
        if (this.validateForm.valid) {
          this.authService.login({ ...params, password: e })
            .subscribe(
              res => {
                this.loginLoading = false;
                this.router.navigate(['/page']);
              },
              res => {
                this.loginLoading = false;
                this.message.error(res.msg);
                if (res.code === 10001) {
                  this.encryptService.clearPublicKey();
                }
              }
            );
        } else {
          this.loginLoading = false;
        }
      });
  }

  constructor(private fb: FormBuilder, private authService: AuthService,
              private message: NzMessageService, private encryptService: EncryptService, private router: Router) {
  }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      username: [null, [Validators.required, TowerValidators.username]],
      password: [null, [Validators.required]]
    });
  }

}
