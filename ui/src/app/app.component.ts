import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { LoginDialog } from './dialog/login.dialog';
import { Store } from '@ngrx/store';
import { AuthActions, AuthSelectors } from './shared/state';
import { AppState } from './app.state';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ERS';
  isAuthenticated: boolean = false;
  errorMsg: string | null | undefined;
  getState;
  constructor(
    private store: Store<AppState>,
    private dialog: MatDialog,
    ) {
      this.getState = this.store.select(AuthSelectors.selectAuthState);
      this.getState.subscribe((state) => {
        this.errorMsg = state.errorMessage;
        this.isAuthenticated = state.isAuthenticated;
      });
  }

  login() {
    const dialogRef = this.dialog.open(LoginDialog, {data: {username: "", password: ""}});

    dialogRef.afterClosed().subscribe(res => {
      this.store.dispatch(AuthActions.login({username: res.username, password: res.password}));
    });
  }

  logout() {
    this.store.dispatch(AuthActions.logout());
  }

}
