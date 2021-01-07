import { Dashboard } from '@/app/shared/model';
import { Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { fromEvent, interval } from 'rxjs';
import { debounceTime } from 'rxjs/operators';

@Component({
  selector: 'app-echarts',
  templateUrl: './echarts.component.html',
  styleUrls: ['./echarts.component.less']
})
export class EchartsComponent implements OnInit {


  @Output() edit = new EventEmitter<string>();
  @Input() data: Dashboard;
  @Input() editable: boolean = false;
  @ViewChild('tplDashboard') dashboard: ElementRef;

  echarts: any;
  target: any;
  content: string;

  constructor() { }

  ngOnInit(): void {
    const intervalSubscription = interval(200).subscribe(num => {
      if (this.dashboard.nativeElement.offsetWidth) {
        this.target = echarts.init(this.dashboard.nativeElement);
        this.content = this.data.content || this.data?.template?.content;
        this.setOption(this.content);
        fromEvent(window, 'resize')
          .pipe(
            debounceTime(300)
          ).subscribe(e => this.target.resize());
        intervalSubscription.unsubscribe();
      }
    });
  }

  setOption(value: string | null): any {
    // tslint:disable-next-line: no-eval
    const getOption = eval(`() => {
      ${value}
      return option;
    }`);
    const option = getOption();
    if (option && this.editable) {
      option.toolbox = option.toolbox || {};
      option.toolbox.feature = option.toolbox.feature || {};
      option.toolbox.feature.myTool = {
        show: true,
        title: '编辑',
        icon: 'path://M360.533333 765.866667c8.533333 0 17.066667-2.133333 23.466667-6.4 32-14.933333 177.066667-100.266667 192-117.333334l258.133333-307.2c25.6-27.733333 23.466667-72.533333-4.266666-98.133333l-59.733334-55.466667c-27.733333-25.6-72.533333-23.466667-98.133333 4.266667L413.866667 492.8c-14.933333 17.066667-87.466667 177.066667-98.133334 204.8-6.4 19.2-4.266667 38.4 8.533334 53.333333 8.533333 10.666667 21.333333 14.933333 36.266666 14.933334zM704 215.466667c4.266667-4.266667 10.666667-8.533333 19.2-8.533334 6.4 0 12.8 2.133333 19.2 6.4l59.733333 55.466667c10.666667 10.666667 10.666667 27.733333 0 38.4l-32 38.4c0-2.133333-2.133333-2.133333-2.133333-4.266667l-100.266667-78.933333s-2.133333 0-2.133333-2.133333l38.4-44.8zM354.133333 712.533333c14.933333-40.533333 81.066667-177.066667 91.733334-192l192-228.266666c0 2.133333 2.133333 2.133333 4.266666 4.266666l100.266667 78.933334s2.133333 0 2.133333 2.133333l-200.533333 236.8c-12.8 12.8-142.933333 91.733333-179.2 108.8-4.266667 2.133333-8.533333 2.133333-10.666667 0-2.133333-2.133333-2.133333-6.4 0-10.666667zM896 832c0 10.666667-10.666667 21.333333-21.333333 21.333333H149.333333c-10.666667 0-21.333333-10.666667-21.333333-21.333333s10.666667-21.333333 21.333333-21.333333h725.333334c10.666667 0 21.333333 10.666667 21.333333 21.333333z',
        onclick: () => this.editDashboard()
      };
    }
    this.target.setOption(option);
  }

  editDashboard(): void {
    this.edit.emit(this.data.id);
  }


}
