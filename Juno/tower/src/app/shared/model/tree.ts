export class TreeNode<T> {
  id: string;
  pid: string;
  title: string;
  open: boolean;
  disabled: boolean;
  selected: boolean;
  level?: number;
  raw: T;
  children: TreeNode<T>[];
}
