import { Dashboard } from '@/app/shared/model';
import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { editor } from 'monaco-editor';
import { EchartsComponent } from '../echarts/echarts.component';

// tslint:disable-next-line no-any
declare const monaco: any;

@Component({
  selector: 'app-editor',
  templateUrl: './editor.component.html',
  styleUrls: ['./editor.component.less']
})
export class EditorComponent implements OnInit {

  @Input() data: Dashboard;
  @Output() editorClose = new EventEmitter();
  @Output() editorSave = new EventEmitter<string>();
  @ViewChild('tplEcharts') echarts: EchartsComponent;

  editor: editor.ICodeEditor | editor.IEditor;
  model: editor.ITextModel;
  content: string;

  constructor() { }

  ngOnInit(): void {
    this.content = this.data.content || this.data.template?.content;
  }

  onEditorInit(e: editor.ICodeEditor | editor.IEditor): void {
    this.editor = e;
    this.editor.updateOptions({
      fontSize: 12,
      wordWrap: 'on',
      minimap: {
        enabled: false,
        side: 'left'
      }
    });
    if (this.model) {
      this.model.setValue(this.content);
    } else {
      this.model = monaco.editor.createModel(this.content, 'typescript');
    }
    this.editor.setModel(this.model);
  }

  preview(): void {
    const content = this.model.getValue();
    this.echarts.setOption(content);
  }

  save(): void {
    const content = this.model.getValue();
    this.editorSave.emit(content);
  }

  close(): void {
    this.model.dispose();
    this.editorClose.emit();
  }

}
