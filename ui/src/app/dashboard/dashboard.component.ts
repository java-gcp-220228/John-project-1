import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Observable, map } from 'rxjs';
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
  filter: string = 'ALL';
  statuses: string[] = ['ALL', 'PENDING', 'APPROVED', 'DENIED'];

  constructor(
    private api: ApiService,
    private dialog: MatDialog,
    ) {
      if (this.role?.toUpperCase().includes('MANAGER')) {
        this.cards = this.filterTickets(this.filter);
      } else if (this.role?.toUpperCase().includes('EMPLOYEE')) {
        let userId = localStorage.getItem('user_id');
        this.cards = this.api.getEmployeeTickets(Number(userId));
      }
    }

    filterTickets(status: string) {
      if (status == 'ALL') return this.api.getTickets();
      return this.api.getTickets().pipe(map(tickets => tickets.filter(t => t.status == status)));
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
      const dialogRef = this.dialog.open(TicketDialog, {data: {amount: 0, author: user, description: "", image: null, type: "OTHER"}})

      dialogRef.afterClosed().subscribe(data => {
        let formData = new FormData();
        let ticket: Ticket = {
          id: 0,
          amount: data.amount,
          submitted: data.submitted,
          description: data.description,
          receiptLink: "",
          author: data.author,
          status: data.status,
          type: data.type
        };
        let id = data.author.id;
        formData.append('ticket-amount', '' + ticket.amount);
        formData.append('ticket-description', '' + ticket.description);
        formData.append('ticket-author-username', ticket.author.username);
        formData.append('ticket-author-first', ticket.author.firstName);
        formData.append('ticket-author-last', ticket.author.lastName);
        formData.append('ticket-author-email', ticket.author.email);
        formData.append('ticket-author-role', ticket.author.userRole);
        formData.append('ticket-status', ticket.status);
        formData.append('ticket-type', ticket.type);
        formData.append('image', data.image);

        this.api.addNewTicket(formData, id).subscribe({
          next: addedTicket => {
            this.cards = this.api.getEmployeeTickets(+localStorage.getItem('user_id')!);
          }
        })
      });
    }

    serveTicket(ticket_id: number, status: string) {
      this.api.patchTicket(ticket_id, status).subscribe({
        next: ticket => {
          this.cards = this.filterTickets(this.filter);
        }
      });
    }
}
