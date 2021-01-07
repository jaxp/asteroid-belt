import { HttpEvent } from '@angular/common/http';
import { HttpErrorResponse } from '@angular/common/http';
import { HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError } from 'rxjs';
import { Observable } from 'rxjs/internal/Observable';
import { of } from 'rxjs/internal/observable/of';
import { catchError, finalize, mergeMap } from 'rxjs/operators';
import { AuthService } from '../auth/auth.service';
import { HttpStatus } from '@/constants/http-status';
import { NzMessageService } from 'ng-zorro-antd/message';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService, private messageService: NzMessageService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const started = Date.now();
    let result: string;
    let code: number;
    let authReq = req;
    if (req.url.indexOf('/api/anon/') < 0) {
      const authToken = this.authService.getAuthorizationToken();
      if (authToken) {
        authReq = req.clone({
          headers: req.headers.set('Authorization', 'Bearer ' + authToken)
        });
      }
    }
    return next.handle(authReq).pipe(
      mergeMap((event: any) => {
        if (event instanceof HttpResponse) {
          if (event.headers.get('content-type') === 'application/json') {
            code = event.body.code;
            if (code !== 200) {
              if (code >= 10100 && code < 10200) {
                this.authService.clearAuthorizationToken();
              }
              result = 'failed';
              return throwError(event.body);
            } else {
              result = 'succeeded';
            }
            const token = event.headers.get('authorization');
            if (token) {
              this.authService.setAuthorizationToken(token);
            }
          } else {
            result = 'succeeded';
          }
        }
        return of(event);
      }),
      catchError((err: HttpErrorResponse) => {
        const error = err.error || err;
        code = error.code;
        error.msg = error.msg || HttpStatus[code] && HttpStatus[code].zh || '服务器异常';
        this.messageService.error(error.msg);
        return throwError(error);
      }),
      finalize(() => {
        const elapsed = Date.now() - started;
        const msg = `${req.method} "${req.urlWithParams}"
           ${result} in ${elapsed} ms.`;
        // console.log(msg)
      })
    );
  }
}
