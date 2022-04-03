import { ActionReducerMap } from "@ngrx/store";
import { DashboardReducers, DashboardState } from "./dashboard/state";
import { AuthReducers, AuthState } from "./shared/state";
import { UserManagerReducers, UserManagerState } from "./user-manager/state";

export interface AppState {
  authState: AuthState.State,
  dashboardState: DashboardState.State,
  userManagerState: UserManagerState.State,
}

export const reducers: ActionReducerMap<AppState> = {
  authState: AuthReducers.authReducer,
  dashboardState: DashboardReducers.dashboardReducer,
  userManagerState: UserManagerReducers.userManagerReducer,
}
