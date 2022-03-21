import { User } from "./user.model";

export interface Ticket {
  id: number;
  amount: number;
  submitted: Date;
  resolved?: Date;
  description?: string;
  receiptLink?: string;
  author: User;
  resolver?: User;
  status: string;
  type: string;
}
