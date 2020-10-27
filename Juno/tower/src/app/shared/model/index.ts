export class Menu {
  id: string;
  pid?: string;
  disabled: boolean;
  title: string;
  icon: string;
  enabled?: boolean;
  rank?: number;
  url?: string;
  type?: number;
  permission?: number;
  addTime?: string;
  addUser?: string;
  updTime?: string;
  updUser?: string;
}

export interface Result<T> {
  msg: string;
  code: number;
  data: T;
}

export class TreeNode<T> {
  id: string;
  pid: string;
  title: string;
  icon?: string;
  disabled: boolean;
  selected: boolean;
  isLeaf: boolean;
  expanded: boolean;
  origin: T;
  children: TreeNode<T>[];
}

export class MenuTreeNode<T> extends TreeNode<T> {
  level?: number;
}

export class NormalTreeNode<T> extends TreeNode<T> {
  key: string;
  checked: boolean;
  selectable: boolean;
  disableCheckbox: boolean;
}

export interface User {
  id: string;
  username: string;
  email: string;
  telephone: string;
  authorities: string[];
  accountExpired: boolean;
  accountLocked: boolean;
  enabled: boolean;
  pwdExpired: boolean;
  addTime: string;
  updTime: string;
}

