export class Menu {
  id: string;
  pid?: string;
  disabled: boolean;
  title: string;
  icon: string;
  enabled?: boolean;
  grade?: number;
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

export interface Captcha {
  cid: string;
  images: string[];
  data: { rate: number, y: number };
}

export class Dashboard {
  id: string;
  label: string;
  sort: number;
  height: string;
  width: string;
  templateId: string;
  content: string;
  addUser: string;
  updUser: string;
  addTime: string;
  updTime: string;
  template?: DashboardTemplate;
  data?: DashboardData[];
}
export class DashboardTemplate {
  id: string;
  type: number;
  name: string;
  content: string;
  remark: string;
  addUser: string;
  updUser: string;
  addTime: string;
  updTime: string;
  data?: DashboardData[];
}
export class DashboardData {
  id: string;
  templateId: string;
  label: string;
  uri: string;
  params: string;
}
