import { TransitiveCompileNgModuleMetadata } from '@angular/compiler';
import { Injectable } from '@angular/core';
import { NzTreeComponent } from 'ng-zorro-antd/tree';
import {TreeNode} from '../model';

@Injectable({
  providedIn: 'root'
})
export default class TreeService {

  expandAll(tree: NzTreeComponent, flag: boolean): void {
    let nodes = tree.getTreeNodes().filter(e => e.children && e.children.length > 0);
    while (nodes.length > 0) {
      let children = [];
      nodes.forEach(node => {
        node.isExpanded = flag;
        children = [...children, ...node.children];
      });
      nodes = children.filter(e => e.children && e.children.length > 0);
    }
  }

  getTree(data: TreeNode<any>[], options?: {isLeaf}): {level: number, tree: TreeNode<any>[]} {
    const map = new Map<any, TreeNode<any>>();
    data.forEach(e => {
      e.children = [];
      map[e.id] = e;
    });
    data.forEach(e => {
      if (e.pid && map[e.pid]) {
        map[e.pid].children.push(e);
      }
    });
    data.forEach(e => {
      if (e.pid && map[e.pid]) {
        delete map[e.id];
      }
      if (options?.isLeaf) {
        e.isLeaf = options.isLeaf(e);
      } else {
        e.isLeaf = !e.children || e.children.length === 0;
      }
    });
    let level = 1;
    let nodes = Object.values(map);
    while (nodes.length > 0) {
      let newNodes = [];
      nodes.forEach(node => {
        node.level = level;
        if (node.children && node.children.length > 0) {
          newNodes = [...newNodes, ...node.children];
        }
      });
      nodes = newNodes;
      level++;
    }
    return {level: level - 1, tree: Object.values(map)};
  }

  constructor() { }
}
