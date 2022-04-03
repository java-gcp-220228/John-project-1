import { createReducer, on } from "@ngrx/store";
import * as fromState from "./user-manager.state";
import * as fromActions from "./user-manager.actions";
import { adapter } from "./user-manager.adapters";

export const userManagerReducer = createReducer(
  fromState.initialState,
  on(fromActions.loadUsersSuccess, (state, { employees }) => {
    return adapter.setAll(employees, state);
  }),
);
