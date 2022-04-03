import { createAction, props } from "@ngrx/store";
import { User } from "src/app/model/user.model";

export const loadUsers = createAction('[UserManager] Load all Users');
export const loadUsersSuccess = createAction('[UserManager] Load all Users Success', props<{ employees: User[]}>());
export const loadUsersFail = createAction('[UserManager] Load all Users Fail', props<{ message: string }>());
