import { Directive, ElementRef, Input } from '@angular/core';

@Directive({
  selector: '[appGraph]'
})
export class GraphDirective {

  echarts: any;
  target: any;

  @Input() www: string;

  constructor(private el: ElementRef) {
    console.log(this.www);
    this.target = echarts.init(el.nativeElement);
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
