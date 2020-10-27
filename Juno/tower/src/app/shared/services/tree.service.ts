import { TransitiveCompileNgModuleMetadata } from '@angular/compiler';
import { Injectable } from '@angular/core';
import {TreeNode} from '../model';

@Injectable({
  providedIn: 'root'
})
export default class TreeService {

  getTree(data: TreeNode<any>[]): {level: number, tree: TreeNode<any>[]} {
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
      e.isLeaf = !e.children || e.children.length === 0;
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
