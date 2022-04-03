import { createFeatureSelector } from "@ngrx/store";
import { adapter } from "./user-manager.adapters";
import * as fromState from "./user-manager.state";

export const getEmployeesState = createFeatureSelector<fromState.State>('userManagerState');

export const {
    selectIds: selectEmployeeIds,
    selectEntities: selectEmployeeEntities,
    selectAll: selectAllEmployees,
    selectTotal: selectTotalEmployees,
} = adapter.getSelectors(getEmployeesState);
