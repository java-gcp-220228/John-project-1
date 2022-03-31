import { createAction, props } from "@ngrx/store";
import { Ticket } from "src/app/model/ticket.model";

export const loadTickets = createAction('[Dashboard] Load All Tickets');
export const loadTicketsSuccess = createAction('[Dashboard] Load All Tickets Success', props<{ tickets: Ticket[] }>());
export const loadTicketsFail = createAction('[Dashboard] Load All Tickets Fail', props<{ message: string }>());

export const loadEmployeeTickets = createAction('[Dashboard] Load Employee Tickets', props<{ employee_id: number }>());
export const loadEmployeeTicketsSuccess = createAction('[Dashboard] Load Employee Tickets Success', props<{ tickets: Ticket[] }>());
export const loadEmployeeTicketsFail = createAction('[Dashboard] Load Employee Tickets Fail', props<{ message: string }>());

export const addNewTicket = createAction('[Dashboard] Add New Ticket', props<{ employee_id: number, formData: FormData }>());
export const addNewTicketSuccess = createAction('[Dashboard] Add New Ticket Success', props<{ ticket: Ticket }>());
export const addNewTicketFail = createAction('[Dashboard] Add New Ticket Fail', props<{ message: string }>());

export const serveTicket = createAction('[Dashboard] Approve/Deny Ticket', props<{ ticket_id: number, status: string }>());
export const serveTicketSuccess = createAction('[Dashboard] Approve/Deny Ticket Success', props<{ ticket: Ticket }>());
export const serveTicketFail = createAction('[Dashboard] Approve/Deny Fail', props<{ message: string }>());
