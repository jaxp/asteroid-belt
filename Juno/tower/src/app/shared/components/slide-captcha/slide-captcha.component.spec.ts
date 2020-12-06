import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SlideCaptchaComponent } from './slide-captcha.component';

describe('SlideCaptchaComponent', () => {
  let component: SlideCaptchaComponent;
  let fixture: ComponentFixture<SlideCaptchaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SlideCaptchaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SlideCaptchaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
