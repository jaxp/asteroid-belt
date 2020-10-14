import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Api } from '@constants/api';
import { Result } from '@shared/model/result';
import { of, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as JsEncryptModule from 'jsencrypt';

@Injectable({
  providedIn: "root"
})
export class EncryptService {

  publicKey: string;

  getPublicKey() {
    if (!this.publicKey) {
      return this.http.get(Api.auth.getKey).pipe(
        map((e: Result) => {
          this.publicKey = `-----BEGIN PUBLIC KEY-----${e.data}-----END PUBLIC KEY-----`;
          return this.publicKey;
        })
      );
    } else {
      return of(this.publicKey);
    }
  }

  getEncrypted(str: string): Observable<string> {
    return this.getPublicKey().pipe(
      map(publicKey => {
        let encrypt = new JsEncryptModule.JSEncrypt();
        encrypt.setPublicKey(publicKey);
        let signature = encrypt.encrypt(str);
        return signature;
      })
    );
  }

  constructor(private http: HttpClient) { }
}
