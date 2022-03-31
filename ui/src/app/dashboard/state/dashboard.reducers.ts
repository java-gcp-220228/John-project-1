import { createReducer, on } from "@ngrx/store";
import * as fromActions from "./dashboard.actions";
import * as fromState from "./dashboard.state";
import { adapter } from "./dashboard.adapters";

export const dashboardReducer = createReducer(
  fromState.initialState,
  on(fromActions.loadTicketsSuccess, (state, { tickets }) => {
    return adapter.setAll(tickets, state);
  }),
  on(fromActions.loadEmployeeTicketsSuccess, (state, { tickets }) => {
    return adapter.setAll(tickets, state);
  }),
  on(fromActions.addNewTicketSuccess, (state, { ticket }) =>{
    return adapter.addOne(ticket, state);
  }),
  on(fromActions.serveTicketSuccess, (state, { ticket }) => {
    return adapter.upsertOne(ticket, state);
  }),
);
