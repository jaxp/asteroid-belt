import { Directive, ElementRef, Input, OnInit } from '@angular/core';
import { fromEvent, interval } from 'rxjs';
import { debounceTime } from 'rxjs/operators';

@Directive({
  selector: '[appGraph]'
})
export class GraphDirective implements OnInit {

  echarts: any;
  target: any;

  @Input() width: string;
  @Input() height: string;

  constructor(private el: ElementRef) {
    this.el.nativeElement.style.width = this.width || '100%';
    this.el.nativeElement.style.height = this.height || '100%';
  }
  ngOnInit(): void {
    const intervalSubscription = interval(200).subscribe(num => {
      if (this.el.nativeElement.offsetWidth) {
        this.target = echarts.init(this.el.nativeElement);
        this.refresh();
        fromEvent(window, 'resize')
          .pipe(
            debounceTime(300)
          ).subscribe(e => this.refresh());
        intervalSubscription.unsubscribe();
      }
    });
  }

  refresh(): void {
    this.target.resize();
    this.target.setOption({
      xAxis: {
        type: 'category',
        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
      },
      yAxis: {
        type: 'value'
      },
      series: [{
        data: [820, 932, 901, 934, 1290, 1330, 1320],
        type: 'line'
      }]
    });
  }

}
