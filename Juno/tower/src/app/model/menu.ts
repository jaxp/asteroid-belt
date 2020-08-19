export interface Menu {
  title: string;
  icon: string;
  open?: boolean;
  disabled?: boolean;
  selected?: boolean;
  level?: number;
  url?: string;
  children?: Menu[];
}