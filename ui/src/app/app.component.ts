import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { AuthService } from './auth.service';
import { LoginDialog } from './dialog/login.dialog';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ERS';
  token;
  errorMsg;
  constructor(private auth: AuthService,
    private dialog: MatDialog,
    private router: Router) {
    this.token = localStorage.getItem('jwt');
    this.errorMsg = '';
  }

  login() {
    const dialogRef = this.dialog.open(LoginDialog, {data: {username: "", password: ""}});

    dialogRef.afterClosed().subscribe(res => {
      this.auth.authenticate(res.username, res.password).subscribe({
        next: response => {
          if (response.ok) {
            let token = response.headers.get('Token')!;
            localStorage.setItem('jwt', token);
            this.token = token;
            let user = response.body!;
            localStorage.setItem('user_id', "" + user.id);
            localStorage.setItem('username', user.username);
            localStorage.setItem('firstName', user.firstName);
            localStorage.setItem('lastName', user.lastName);
            localStorage.setItem('email', user.email);
            localStorage.setItem('user_role', user.userRole);
            this.router.navigate(['dashboard']);
            this.errorMsg = '';
          }
        },
        error: error => {
          this.errorMsg = error.error.title;
        }
      })
    });
  }

  logout() {
    this.token = null;
    localStorage.removeItem('jwt');
    localStorage.removeItem('user_id');
    localStorage.removeItem('username');
    localStorage.removeItem('firstName');
    localStorage.removeItem('lastName');
    localStorage.removeItem('email');
    localStorage.removeItem('user_role');
    this.router.navigate(['']);
  }

}
