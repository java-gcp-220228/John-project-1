import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AuthService } from '../auth.service';
import { LoginDialog } from '../dialog/login.dialog';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private auth: AuthService,
    private dialog: MatDialog
    ) { }

  ngOnInit(): void {
  }

  portal() {
    const dialogRef = this.dialog.open(LoginDialog, {data: {username: "", password: ""}});

    dialogRef.afterClosed().subscribe(res => {
      this.auth.authenticate(res.username, res.password).subscribe(
        response => {
          if (response.status == 200) {
            let token = response.headers.get('Token')!;
            localStorage.setItem('jwt', token);
            let user = response.body!;
            localStorage.setItem('user_id', "" + user.id);
          }
        }
      )
    });
  }
}
