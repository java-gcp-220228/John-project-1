import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Observable} from 'rxjs';
import { TicketDialog } from '../dialog/ticket.dialog';
import { Ticket } from '../model/ticket.model';
import { User } from '../model/user.model';
import { Store } from '@ngrx/store';
import { AppState } from '../app.state';
import { AuthSelectors } from '../shared/state';
import { DashboardActions, DashboardSelectors } from './state';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  getUser;
  user!: User;
  cards!: Observable<Ticket[]>;
  filter: string = 'ALL';
  statuses: string[] = ['ALL', 'PENDING', 'APPROVED', 'DENIED'];

  constructor(
    private store: Store<AppState>,
    private dialog: MatDialog,
    ) {
      this.getUser = this.store.select(AuthSelectors.selectAuthState);
      this.getUser.subscribe((state) => {
        this.user = state.user!;
      });
    }
  ngOnInit(): void {
    if (this.user.userRole.toUpperCase().includes('MANAGER')) {
      this.store.dispatch(DashboardActions.loadTickets());
      this.cards = this.filterTickets(this.filter);
    } else if (this.user.userRole.toUpperCase().includes('EMPLOYEE')) {
      this.store.dispatch(DashboardActions.loadEmployeeTickets({employee_id: this.user.id}));
      this.cards = this.store.select(DashboardSelectors.selectAllTickets);
    }
  }

    filterTickets(status: string) {
      if (status == 'ALL') return this.store.select(DashboardSelectors.selectAllTickets);
      return this.store.select(DashboardSelectors.selectFilteredTickets(status));
    }

    newTicket() {
      const dialogRef = this.dialog.open(TicketDialog, { data: { amount: 0, author: this.user, description: "", image: null, type: "OTHER" } });
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

        this.store.dispatch(DashboardActions.addNewTicket({ employee_id: this.user.id, formData: formData}));
      });
    }

    serveTicket(ticket_id: number, status: string) {
      this.store.dispatch(DashboardActions.serveTicket({ ticket_id: ticket_id, status: status }));
    }
}
