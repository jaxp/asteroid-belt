import { Component, ElementRef, EventEmitter, Input, Output, TemplateRef, ViewChild } from '@angular/core';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';
import { Captcha } from '../../model';
import { CaptchaService } from '../../services/captcha.service';
import { FileService } from '../../services/file.service';
import { Api } from '@constants/api';

const statusMap = {
  loading: {
    key: 0,
    tip: '加载中'
  },
  inited: {
    key: 1,
    tip: '向右拖动滑块填充拼图'
  },
  sliding: {
    key: 2,
    color: '#00A1E6',
    bg: '#7BC3E1'
  },
  validating: {
    key: 3,
    tip: '校验中',
    bg: '#DFBCA3'
  },
  success: {
    key: 4,
    tip: '成功',
    color: '#93FA10',
    bg: '#C1DF9B'
  },
  fail: {
    key: 5,
    tip: '失败',
    color: '#FF6800',
    bg: '#EF3636'
  }
};

@Component({
  selector: 'app-slide-captcha',
  templateUrl: './slide-captcha.component.html',
  styleUrls: ['./slide-captcha.component.less']
})
export class SlideCaptchaComponent {

  imgUrl: string;
  @ViewChild('tplSlideCaptcha')
  slideCaptcha: TemplateRef<{}>;
  @ViewChild('tplImgBase')
  imgBase: ElementRef;
  @ViewChild('tplImgCutout')
  imgCutout: ElementRef;

  @Input() width: number = 400;
  @Output() succeed = new EventEmitter<string>();
  @Output() closed = new EventEmitter();

  ref: NzModalRef;
  captcha: Captcha;
  cutoutWidth: number;
  status: { key: number, tip?: string, color?: string, bg?: string };
  start: { x: number, y: number };
  length: number = 0;

  constructor(private modalService: NzModalService, private captchaService: CaptchaService,
              private fileService: FileService) {
    this.imgUrl = Api.file.download;
  }

  refresh(): void {
    this.status = statusMap.loading;
    this.length = 0;
    this.captchaService.slide().subscribe(e => {
      this.captcha = e;
      this.cutoutWidth = this.width * this.captcha.data.rate;
      if (!this.ref) {
        this.ref = this.modalService.create({
          nzContent: this.slideCaptcha,
          nzWidth: this.width + 10,
          nzBodyStyle: {
            padding: '5px'
          },
          nzMaskClosable: true,
          nzClosable: false,
          nzFooter: null
        });
        this.ref.afterClose.subscribe(() => {
          if (this.status.key !== statusMap.success.key) {
            this.closed.emit();
          }
          this.ref = null;
        });
        this.ref.afterOpen.subscribe(() => {
          this.loadImgs();
        });
      } else {
        this.loadImgs();
      }
    });
  }

  loadImgs(): void {
    this.fileService.downloadImg(this.imgCutout.nativeElement, this.captcha.images[1]).subscribe();
    this.fileService.downloadImg(this.imgBase.nativeElement, this.captcha.images[0]).subscribe(
      () => this.status = statusMap.inited
    );
  }

  begin(event: MouseEvent): void {
    if (this.status.key === statusMap.inited.key) {
      this.status = statusMap.sliding;
      this.start = {
        x: event.x,
        y: event.y
      };
    }
  }

  slide(event: MouseEvent): void {
    if (this.status.key === statusMap.sliding.key) {
      this.length = Math.min(Math.max(0, event.x - this.start.x), this.width * (1 - this.captcha.data.rate));
    }
  }

  end(event: MouseEvent): void {
    this.status = statusMap.validating;
    this.captchaService.validate({
      cid: this.captcha.cid,
      code: event.x - this.start.x + ':' + this.width
    }).subscribe(
      e => {
        this.status = statusMap.success;
        this.ref.close();
        this.succeed.emit(e);
      },
      () => {
        this.status = statusMap.fail;
        setTimeout(() => {
          this.refresh();
        }, 300);
      }
    );
  }

}
