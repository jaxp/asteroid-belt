import { id_ID } from 'ng-zorro-antd/i18n';
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
  addTime?: string;
  addUser?: string;
  updTime?: string;
  updUser?: string;
}
