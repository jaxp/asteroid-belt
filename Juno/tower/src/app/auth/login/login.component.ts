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
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }
    let params = this.validateForm.value;
    this.encryptService.getEncrypted(params.password)
      .subscribe(e => {
        if (this.validateForm.valid) {
          this.authService.login({ ...params, password: e })
            .subscribe(
              e => {
                console.log(e.data);
                this.router.navigate(['/page']);
              },
              e => this.message.error(e.msg)
            );
        }
      })
  }

  constructor(private fb: FormBuilder, private authService: AuthService,
    private message: NzMessageService, private encryptService: EncryptService,
    private router: Router) {
  }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      username: [null, [Validators.required, TowerValidators.username]],
      password: [null, [Validators.required]]
    });
  }

}
