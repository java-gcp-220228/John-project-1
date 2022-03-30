import { createAction, props } from "@ngrx/store";
import { User } from "src/app/model/user.model";

export const login = createAction('[Auth] Login', props<{ username: string, password: string }>());
export const loginSuccess = createAction('[Auth] Login Success', props<{ user: User, token: string }>());
export const loginFail = createAction('[Auth] Login Failure', props<{ message: string }>());
export const logout = createAction('[Auth] Logout');
