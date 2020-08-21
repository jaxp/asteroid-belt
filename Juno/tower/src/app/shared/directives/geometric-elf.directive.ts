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
  private bornTime: number = 400;
  private showTime: number = 800;
  private existTime: number = 4000
  private disappearTime: number = 400;
  private disappearHeight: number = 5;
  private singleTime: number = 10;
  private alpha: number = 0.5;
  private heightDelta: number = 50;
  private widthDelta: number = 150;

  constructor(private el: ElementRef) {
    let canvasEl = document.createElement('canvas');
    canvasEl.style.position = 'fixed';
    el.nativeElement.appendChild(canvasEl);
    this.canvasEl = canvasEl;
    this.ctx = canvasEl.getContext('2d');
  }

  ngOnInit() {
    this.init();
    this.start();
    fromEvent(window, 'resize').pipe(debounceTime(300)).subscribe(e => this.init())
  }

  init() {
    this.canvasEl.width = this.el.nativeElement.clientWidth;
    this.canvasEl.height = this.el.nativeElement.clientHeight;
    this.seeds = [this.generateTriangle(true), this.generateTriangle(false)];
    this.triangles = []
  }

  start() {
    let itv = interval(this.singleTime).subscribe(n => {
      this.ctx.clearRect(0, 0, this.canvasEl.clientWidth, this.canvasEl.clientHeight)
      if (n * this.singleTime % this.bornTime == 0) {
        this.seeds = this.seeds.map(e => {
          let newTri = this.generateTriangle(e);
          this.triangles = this.triangles.filter(e => e.rest > 0)
          this.triangles.push(newTri);
          return newTri;
        })
      }
      for (let triangle of this.triangles) {
        this.drawTriangle(triangle)
      }
      
    })
  }

  drawTriangle (triangle: Triangle) {
    this.ctx.beginPath()
    this.ctx.fillStyle = `rgba(${triangle.color.r}, ${triangle.color.g}, ${triangle.color.b}, ${triangle.color.a})`;
    this.ctx.moveTo(triangle.p1.x, triangle.p1.y);
    this.ctx.lineTo(triangle.p2.x, triangle.p2.y);
    this.ctx.lineTo(triangle.p3.x, triangle.p3.y);
    this.ctx.fill()
    this.ctx.closePath()
    this.changeTriangle(triangle)
  }

  changeTriangle (triangle: Triangle) {
    triangle.rest -= this.singleTime
    if (triangle.rest < this.disappearTime && triangle.rest % (this.disappearTime / this.disappearHeight) == 0) {
      triangle.p1.y -= 1
      triangle.p2.y -= 1
      triangle.p3.y -= 1
      triangle.color.a = this.alpha * triangle.rest / this.disappearTime
    } else {
      let showTime = this.showTime + this.existTime + this.disappearTime - triangle.rest
      if (showTime < this.showTime) {
        triangle.color.a = this.alpha * showTime / this.showTime
      }
    }
  }

  generateTriangle(param: Triangle | boolean) {
    let triangle = (typeof param == 'boolean' ? null : param)
    let right = triangle ? triangle.right : !!param;
    let p1 = {
      x: triangle ? triangle.p2.x : (right ? -50 : this.canvasEl.clientWidth + 50),
      y: triangle ? triangle.p2.y : this.generateY(0)
    }
    let p2 = {
      x: triangle ? triangle.p3.x : this.generateX(p1.x, right),
      y: triangle ? triangle.p3.y : this.generateY(p1.y)
    }
    let p3 = {
      x: this.generateX(p2.x, right),
      y: this.generateY(p2.y)
    }
    if (right && p3.x > this.canvasEl.clientWidth || !right && p3.x < 0) {
      right = !right
    }
    return {
      p1: p1,
      p2: p2,
      p3: p3,
      color: {
        r: this.generateColor(triangle ? triangle.color.r : null),
        g: this.generateColor(triangle ? triangle.color.g : null),
        b: this.generateColor(triangle ? triangle.color.b : null),
        a: 0
      },
      rest: this.showTime + this.existTime + this.disappearTime,
      right: right
    }
  }

  generateColor (base: number) {
    let random = Math.random();
    if (base) {
      random = random * 80 - 40;
      let value = base + random
      if (value > 255 || value < 0) {
        return base - random;
      }
      return value;
    }
    return 255 * random
  }

  generateX(base: number, right: boolean) {
    return base + (10 + parseInt(Math.random() * this.widthDelta + "")) * (right ? 1 : -1)
  }

  generateY(base: number) {
    if (base == 0) {
      base = this.canvasEl.clientHeight / 2
    }
    let value = parseInt(Math.random() * this.heightDelta * 2 + "")
    if (base < 200) {
      return base + parseInt(Math.random() * this.heightDelta + "")
    } else if (base > this.canvasEl.clientHeight - 200) {
      return base - parseInt(Math.random() * this.heightDelta + "") 
    }
    return base + (value > this.heightDelta ? (value - this.heightDelta + 10) : (value - this.heightDelta - 10))
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