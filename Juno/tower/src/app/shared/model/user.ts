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
