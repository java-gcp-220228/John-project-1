import { createReducer, on } from '@ngrx/store'
import * as fromState from './auth.state'
import * as fromActions from './auth.actions'

export const authReducer = createReducer(
  fromState.initialState,
  on(fromActions.loginSuccess, (state, { user, token }) => {
    return {
      ...state,
      isAuthenticated: true,
      token: token,
      user: user,
      errorMessage: null,
    }
  }),
  on(fromActions.loginFail, (state, { message }) => {
    return {
      ...state,
      errorMessage: message,
    }
  }),
  on(fromActions.logout, () => fromState.initialState),
  on(fromActions.autoLogin, (state) => {
    return {
      ...state,
      isAuthenticated: true,
      token: localStorage.getItem('jwt'),
      user: JSON.parse(localStorage.getItem('user')!),
      errorMessage: null,
    }
  }),
);
