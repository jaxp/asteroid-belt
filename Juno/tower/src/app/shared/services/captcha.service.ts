import { Api } from '@/constants/api';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Captcha, Result } from '../model';

@Injectable({
  providedIn: 'root'
})
export class CaptchaService {

  constructor(private http: HttpClient) { }

  slide(): Observable<Captcha> {
    return this.http.get<Result<Captcha>>(Api.captcha.slide).pipe(
      map(e => e.data)
    );
  }

  validate(validation: {cid: string, code: string}): Observable<string> {
    return this.http.post<Result<string>>(Api.captcha.validate, validation).pipe(
      map(e => e.data)
    );
  }
}
