import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { ApiService } from '../api.service';
import { TicketDialog } from '../dialog/ticket.dialog';
import { Ticket } from '../model/ticket.model';
import { User } from '../model/user.model';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {
  cards!: Observable<Ticket[]>;
  role = localStorage.getItem('user_role');

  constructor(
    private api: ApiService,
    private dialog: MatDialog,
    ) {
      if (this.role?.toUpperCase().includes('MANAGER')) {
        this.cards = this.api.getTickets();
      } else if (this.role?.toUpperCase().includes('EMPLOYEE')) {
        let userId = localStorage.getItem('user_id');
        this.cards = this.api.getEmployeeTickets(Number(userId));
      }
    }

    newTicket() {
      let user: User = {
        id: +localStorage.getItem('user_id')!,
        username: localStorage.getItem('username')!,
        firstName: localStorage.getItem('firstName')!,
        lastName: localStorage.getItem('lastName')!,
        email: localStorage.getItem('email')!,
        userRole: localStorage.getItem('user_role')!
      }
      const dialogRef = this.dialog.open(TicketDialog, {data: {amount: 0, author: user, description: "", type: "OTHER"}})

      dialogRef.afterClosed().subscribe(ticket => {
        this.api.addNewTicket(ticket).subscribe({
          next: addedTicket => {
            this.cards = this.api.getEmployeeTickets(+localStorage.getItem('user_id')!);
          }
        })
      });
    }

    serveTicket(ticket_id: number, status: string) {
      this.api.patchTicket(ticket_id, status).subscribe({
        next: ticket => {
          this.cards = this.api.getTickets();
        }
      });
    }
}
