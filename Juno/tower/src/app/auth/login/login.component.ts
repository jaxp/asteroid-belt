import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TowerValidators } from '@/constants/validators';
import { AuthService } from '../auth.service';
import { SlideCaptchaComponent } from '@/app/shared/components/slide-captcha/slide-captcha.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.less']
})
export class LoginComponent implements OnInit {

  @ViewChild('slideCaptcha')
  private slideCaptcha: SlideCaptchaComponent;

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
    if (this.validateForm.valid) {
      this.loginLoading = true;
      this.slideCaptcha.refresh();
    }
  }

  login(certificate): void {
    const params = this.validateForm.value;
    this.loginLoading = true;
    this.authService.login(params, certificate)
      .subscribe(e => {
        this.loginLoading = false;
      });
  }

  constructor(private fb: FormBuilder, private authService: AuthService) {
  }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      username: [null, [Validators.required, TowerValidators.username]],
      password: [null, [Validators.required]]
    });
  }

}
