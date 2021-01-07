import { Api } from '@/constants/api';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Dashboard, Result } from '../model';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  constructor(private http: HttpClient, private messageService: NzMessageService) { }

  canDev(): Observable<boolean> {
    return this.http.get<Result<number>>(Api.dashboard.hasDevAuth).pipe(
      map(e => e.data !== 0)
    );
  }

  getDashboards(label: string): Observable<Dashboard[]> {
    return this.http.get<Result<Dashboard[]>>(Api.dashboard.list + label).pipe(
      map(e => e.data)
    );
  }

  saveDashboard(data: any): Observable<Result<string>> {
    return this.http.post<Result<string>>(Api.dashboard.save, data);
  }
}
