import { createFeatureSelector, createSelector } from "@ngrx/store";
import { adapter } from "./dashboard.adapters";
import * as fromState from "./dashboard.state";

export const getDashboardState = createFeatureSelector<fromState.State>('dashboardState');

export const {
  selectIds: selectTicketIds,
  selectEntities: selectTicketEntities,
  selectAll: selectAllTickets,
  selectTotal: selectTotalTickets,
} = adapter.getSelectors(getDashboardState);

export const selectFilteredTickets = (status: string) =>
  createSelector(selectAllTickets, (tickets) => tickets.filter(t => t.status == status));
