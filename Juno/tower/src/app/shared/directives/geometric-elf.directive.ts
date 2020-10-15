import { Directive, ElementRef, OnInit, OnChanges } from '@angular/core';
import { interval, fromEvent } from 'rxjs';
import { debounceTime } from 'rxjs/operators';

@Directive({
  selector: '[appGeometricElf]'
})
export class GeometricElfDirective implements OnInit {

  private canvasEl: HTMLCanvasElement;
  private ctx: CanvasRenderingContext2D;
  private seeds: Triangle[];
  private triangles: Triangle[];
  private bornTime = 400;
  private showTime = 800;
  private existTime = 4000;
  private disappearTime = 400;
  private disappearHeight = 5;
  private singleTime = 10;
  private alpha = 0.5;
  private heightDelta = 50;
  private widthDelta = 150;

  constructor(private el: ElementRef) {
    const canvasEl = document.createElement('canvas');
    canvasEl.style.position = 'fixed';
    el.nativeElement.appendChild(canvasEl);
    this.canvasEl = canvasEl;
    this.ctx = canvasEl.getContext('2d');
  }

  ngOnInit(): void {
    this.init();
    this.start();
    fromEvent(window, 'resize').pipe(debounceTime(300)).subscribe(e => this.init());
  }

  init(): void {
    this.canvasEl.width = this.el.nativeElement.clientWidth;
    this.canvasEl.height = this.el.nativeElement.clientHeight;
    this.seeds = [this.generateTriangle(true), this.generateTriangle(false)];
    this.triangles = [];
  }

  start(): void {
    const itv = interval(this.singleTime).subscribe(n => {
      this.ctx.clearRect(0, 0, this.canvasEl.clientWidth, this.canvasEl.clientHeight);
      if (n * this.singleTime % this.bornTime === 0) {
        this.seeds = this.seeds.map(e => {
          const newTri = this.generateTriangle(e);
          this.triangles = this.triangles.filter(triangle => triangle.rest > 0);
          this.triangles.push(newTri);
          return newTri;
        });
      }
      for (const triangle of this.triangles) {
        this.drawTriangle(triangle);
      }
    });
  }

  drawTriangle(triangle: Triangle): void {
    this.ctx.beginPath();
    this.ctx.fillStyle = `rgba(${triangle.color.r}, ${triangle.color.g}, ${triangle.color.b}, ${triangle.color.a})`;
    this.ctx.moveTo(triangle.p1.x, triangle.p1.y);
    this.ctx.lineTo(triangle.p2.x, triangle.p2.y);
    this.ctx.lineTo(triangle.p3.x, triangle.p3.y);
    this.ctx.fill();
    this.ctx.closePath();
    this.changeTriangle(triangle);
  }

  changeTriangle(triangle: Triangle): void {
    triangle.rest -= this.singleTime;
    if (triangle.rest < this.disappearTime && triangle.rest % (this.disappearTime / this.disappearHeight) === 0) {
      triangle.p1.y -= 1;
      triangle.p2.y -= 1;
      triangle.p3.y -= 1;
      triangle.color.a = this.alpha * triangle.rest / this.disappearTime;
    } else {
      const showTime = this.showTime + this.existTime + this.disappearTime - triangle.rest;
      if (showTime < this.showTime) {
        triangle.color.a = this.alpha * showTime / this.showTime;
      }
    }
  }

  generateTriangle(param: Triangle | boolean): Triangle{
    const triangle = (typeof param === 'boolean' ? null : param);
    let right = triangle ? triangle.right : !!param;
    const p1 = {
      x: triangle ? triangle.p2.x : (right ? -50 : this.canvasEl.clientWidth + 50),
      y: triangle ? triangle.p2.y : this.generateY(0)
    };
    const p2 = {
      x: triangle ? triangle.p3.x : this.generateX(p1.x, right),
      y: triangle ? triangle.p3.y : this.generateY(p1.y)
    };
    const p3 = {
      x: this.generateX(p2.x, right),
      y: this.generateY(p2.y)
    };
    if (right && p3.x > this.canvasEl.clientWidth || !right && p3.x < 0) {
      right = !right;
    }
    return {
      p1,
      p2,
      p3,
      color: {
        r: this.generateColor(triangle ? triangle.color.r : null),
        g: this.generateColor(triangle ? triangle.color.g : null),
        b: this.generateColor(triangle ? triangle.color.b : null),
        a: 0
      },
      rest: this.showTime + this.existTime + this.disappearTime,
      right
    };
  }

  generateColor(base: number): number {
    let random = Math.random();
    if (base) {
      random = random * 80 - 40;
      const value = base + random;
      if (value > 255 || value < 0) {
        return base - random;
      }
      return value;
    }
    return 255 * random;
  }

  generateX(base: number, right: boolean): number {
    return base + (10 + parseInt(Math.random() * this.widthDelta + '', 10)) * (right ? 1 : -1);
  }

  generateY(base: number): number {
    if (base === 0) {
      base = this.canvasEl.clientHeight / 2;
    }
    const value = parseInt(Math.random() * this.heightDelta * 2 + '', 10);
    if (base < 200) {
      return base + parseInt(Math.random() * this.heightDelta + '', 10);
    } else if (base > this.canvasEl.clientHeight - 200) {
      return base - parseInt(Math.random() * this.heightDelta + '', 10);
    }
    return base + (value > this.heightDelta ? (value - this.heightDelta + 10) : (value - this.heightDelta - 10));
  }

}
class Triangle {
  p1: Point;
  p2: Point;
  p3: Point;
  color: Color;
  rest: number;
  right: boolean;
}
class Point {
  x: number;
  y: number;
}
class Color {
  r: number;
  g: number;
  b: number;
  a: number;
}
