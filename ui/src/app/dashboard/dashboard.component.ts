import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from '../api.service';
import { Ticket } from '../model/ticket.model';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {
  /** Based on the screen size, switch from standard to one column per row */
  cards!: Observable<Ticket[]>;
  role = localStorage.getItem('user_role');

  constructor(
    private api: ApiService
    ) {
      if (this.role?.toUpperCase().includes('MANAGER')) {
        this.cards = this.api.getTickets();
      } else if (this.role?.toUpperCase().includes('EMPLOYEE')) {
        let userId = localStorage.getItem('user_id');
        this.cards = this.api.getEmployeeTickets(Number(userId));
      }
    }
}
