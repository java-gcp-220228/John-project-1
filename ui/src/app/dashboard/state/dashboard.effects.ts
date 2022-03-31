import { Injectable } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { catchError, map, mergeMap, of, tap } from "rxjs";
import { ApiService } from "src/app/shared/api.service";
import * as fromActions from "./dashboard.actions";

@Injectable()
export class DashboardEffects {
  constructor(
    private actions$: Actions,
    private api: ApiService,
    private snackbar: MatSnackBar,
  ){}

  loadTickets$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromActions.loadTickets),
      mergeMap(() =>
        this.api.getTickets().pipe(
          map((data) => fromActions.loadTicketsSuccess({ tickets: data })),
          catchError((error) => of(fromActions.loadTicketsFail(error.error.title)))
        )
      )
    )
  );

  loadTicketsFail$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromActions.loadTicketsFail),
      tap((error) =>
        this.snackbar.open(error.message, 'Error', { duration: 2500 })
      )
    ),
    { dispatch: false }
  );

  loadEmployeeTickets$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromActions.loadEmployeeTickets),
      mergeMap((id) =>
        this.api.getEmployeeTickets(id.employee_id).pipe(
          map((data) => fromActions.loadEmployeeTicketsSuccess({ tickets: data })),
          catchError((error) => of(fromActions.loadEmployeeTicketsFail(error.error.title)))
        )
      )
    )
  );

  loadEmployeeTicketsFail$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromActions.loadEmployeeTicketsFail),
      tap((error) =>
        this.snackbar.open(error.message, 'Error', { duration: 2500 })
      )
    ),
    { dispatch: false }
  );

  addNewTicket$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromActions.addNewTicket),
      mergeMap((res) =>
        this.api.addNewTicket(res.formData, res.employee_id).pipe(
          map((data) => fromActions.addNewTicketSuccess({ ticket: data })),
          catchError((error) => of(fromActions.addNewTicketFail(error.error.title)))
        )
      )
    )
  );

  addNewTicketFail$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromActions.addNewTicketFail),
      tap((error) =>
        this.snackbar.open(error.message, 'Error', { duration: 2500 })
      )
    ),
    { dispatch: false }
  );

  serveTicket$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromActions.serveTicket),
      mergeMap((res) =>
        this.api.patchTicket(res.ticket_id, res.status).pipe(
          map((data) => fromActions.serveTicketSuccess({ ticket: data })),
          catchError((error) => of(fromActions.serveTicketFail(error.error.title)))
        )
      )
    )
  );

  serveTicketFail$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromActions.serveTicketFail),
      tap((error) =>
        this.snackbar.open(error.message, 'Error', { duration: 2500 })
      )
    ),
    { dispatch: false }
  );

}
