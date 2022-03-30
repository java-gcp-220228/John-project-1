import * as fromState from "./auth.state"
import { createFeatureSelector } from "@ngrx/store";

export const selectAuthState = createFeatureSelector<fromState.State>('authState');
