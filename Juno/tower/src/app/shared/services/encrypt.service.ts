import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Api } from '../constants/api';
import { Result } from '../model';
import { of, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as JsEncryptModule from 'jsencrypt';

@Injectable({
  providedIn: 'root'
})
export class EncryptService {

  publicKey: string;

  getPublicKey(): Observable<string> {
    if (!this.publicKey) {
      return this.http.get(Api.auth.getKey).pipe(
        map((e: Result<string>) => {
          this.publicKey = `-----BEGIN PUBLIC KEY-----${e.data}-----END PUBLIC KEY-----`;
          return this.publicKey;
        })
      );
    } else {
      return of(this.publicKey);
    }
  }

  clearPublicKey(): void {
    this.publicKey = null;
  }

  getEncrypted(str: string): Observable<string> {
    return this.getPublicKey().pipe(
      map(publicKey => {
        const encrypt = new JsEncryptModule.JSEncrypt();
        encrypt.setPublicKey(publicKey);
        const signature = encrypt.encrypt(str);
        return signature;
      })
    );
  }

  constructor(private http: HttpClient) { }
}
