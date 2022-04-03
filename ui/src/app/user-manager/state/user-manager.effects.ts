import { Injectable } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { catchError, map, mergeMap, of, tap } from "rxjs";
import { ApiService } from "src/app/shared/api.service";
import * as fromActions from "./user-manager.actions";

@Injectable()
export class UserManagerEffects {
  constructor(
    private actions$: Actions,
    private api: ApiService,
    private snackbar: MatSnackBar,
  ){}

  loadUser$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromActions.loadUsers),
      mergeMap(() =>
        this.api.getEmployees().pipe(
          map(data => fromActions.loadUsersSuccess({ employees: data })),
          catchError(error => of(fromActions.loadUsersFail({ message: error.error.title })))
        )
      )
    )
  );

  loadUsersFail$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromActions.loadUsersFail),
      tap(error =>
        this.snackbar.open(error.message, 'Error', { duration: 2500 })
      )
    )
  );

}
