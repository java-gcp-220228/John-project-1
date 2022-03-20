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
  constructor(private auth: AuthService,
    private dialog: MatDialog,
    private router: Router) {
    this.token = localStorage.getItem('jwt');
  }

  login() {
    const dialogRef = this.dialog.open(LoginDialog, {data: {username: "", password: ""}});

    dialogRef.afterClosed().subscribe(res => {
      this.auth.authenticate(res.username, res.password).subscribe(
        response => {
          if (response.status == 200) {
            let token = response.headers.get('Token')!;
            localStorage.setItem('jwt', token);
            this.token = token;
            let user = response.body!;
            localStorage.setItem('user_id', "" + user.id);
            this.router.navigate(['dashboard']);
          }
        }
      )
    });
  }

  logout() {
    this.token = null;
    localStorage.removeItem('jwt');
    localStorage.removeItem('user_id');
    this.router.navigate(['']);
  }

}
