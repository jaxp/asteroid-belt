import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Dashboard } from '../../model';
import { DashboardService } from '../../services/dashboard.service';

const dashboardOps = {
  new: {
    title: '新增图表'
  },
  update: {
    title: '修改图表'
  },
  delete: {
    title: '删除图表'
  }
};

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.less']
})
export class DashboardComponent implements OnInit {

  @Input() label: string;
  @Input() width: string = '100%';
  @Input() height: string = '100%';

  canDev$: Observable<boolean>;
  data: Dashboard[];
  current: Dashboard;
  op = null;
  ifTemplate: boolean = false;
  formData!: FormGroup;
  loading: boolean = false;

  constructor(private dashboardService: DashboardService, private fb: FormBuilder) { }

  ngOnInit(): void {
    this.canDev$ = this.dashboardService.canDev();
    if (this.label) {
      this.getDashboards();
    }
    this.formData = this.fb.group({
      name: [null, [Validators.required]],
      height: [null, [Validators.required]],
      width: [null, [Validators.required]],
      sort: [null, [Validators.required]],
      templateName: [null],
      templateRemark: [null]
    });
  }

  closeEditor(): void {
    this.current = null;
  }

  openEditor(id: string): void {
    this.current = this.data.filter(e => e.id === id)[0];
  }

  getDashboards(): void {
    this.dashboardService.getDashboards(this.label).subscribe(
      data => {
        this.data = data;
      }
    );
  }

  newDashboard(): void {
    const newDashboard: Dashboard = new Dashboard();
    newDashboard.content = `const option = {};`;
    this.current = newDashboard;
  }

  saveDashboard(content): void {
    this.op = { ...dashboardOps.new, content };
  }

  backToEditor(): void {
    this.op = null;
  }

  save(): void {
    // tslint:disable-next-line: forin
    for (const i in this.formData.controls) {
      this.formData.controls[i].markAsDirty();
      this.formData.controls[i].updateValueAndValidity();
    }
    if (this.formData.valid) {
      const data = {
        label: this.label,
        content: this.op.content,
        ...this.formData.value
      };
      this.loading = true;
      this.dashboardService.saveDashboard(data).pipe(
        catchError(err => {
          return of(err);
        })
      ).subscribe(e => {
        this.loading = false;
        this.op = null;
        this.closeEditor();
        this.getDashboards();
      });
    }
  }

}
