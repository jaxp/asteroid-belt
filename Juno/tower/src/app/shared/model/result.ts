export interface Result<T> {
  msg: string;
  code: number;
  data: T;
}
