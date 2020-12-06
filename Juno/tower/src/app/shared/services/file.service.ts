import { Api } from '@/constants/api';
import { HttpClient } from '@angular/common/http';
import { ElementRef, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class FileService {

  constructor(private http: HttpClient) { }

  downloadImg(img: HTMLImageElement, id: string): Observable<Blob> {
    return this.http.get(Api.file.download + id, {
      responseType: 'blob'
    }).pipe(
      tap(data => {
        img.onload = () => window.URL.revokeObjectURL(img.src);
        img.src = window.URL.createObjectURL(data);
      })
    );
  }

  downloadFile(responseData): void {
    const data = new Blob([responseData], {
      type: 'application/pdf;charset=UTF-8'
    });
    const downloadUrl = window.URL.createObjectURL(data);
    const anchor = document.createElement('a');
    anchor.href = downloadUrl;
    // anchor.download = decodeURI(realFileName);
    anchor.click();
    // window.URL.revokeObjectURL(data);
  }
}
