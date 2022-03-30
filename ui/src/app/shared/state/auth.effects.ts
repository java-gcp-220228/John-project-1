import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { catchError, map, mergeMap, of, tap } from "rxjs";
import { AuthService } from "../auth.service";
import * as fromActions from "./auth.actions";

@Injectable()
export class AuthEffects {
  constructor(
    private authService: AuthService,
    private actions$: Actions,
    private router: Router,
  ){}

  login$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromActions.login),
      mergeMap((data) =>
        this.authService.authenticate(data.username, data.password).pipe(
          map((response) => {
            let token = response.headers.get('Token')!;
            let user = response.body!;
            localStorage.setItem('jwt', token);
            return fromActions.loginSuccess({user: user, token: token});
          }),
          catchError((error) => {
            return of(fromActions.loginFail({message: error.error.title}))
          })
        )
      )
    )
  );

  loginSuccess$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromActions.loginSuccess),
      tap(() =>
        this.router.navigate(['dashboard'])
      )
    ),
    { dispatch: false }
  );

  logout$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromActions.logout),
      tap(() => {
        localStorage.removeItem('jwt');
        this.router.navigate(['']);
      })
    ),
    { dispatch: false }
  );
}
