import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Ticket } from './model/ticket.model';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) { }


  getTickets(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(`${environment.apiUrl}/tickets`, this.getOptions());
  }
  getEmployeeTickets(id: number): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(`${environment.apiUrl}/employees/${id}/tickets`, this.getOptions());
  }

  addNewTicket(ticket: Ticket) {
    return this.http.post<Ticket>(`${environment.apiUrl}/employees/${ticket.author.id}/tickets`, ticket, this.getOptions());
  }

  patchTicket(ticket_id: number, status: string): Observable<Ticket> {
    return this.http.patch<Ticket>(`${environment.apiUrl}/tickets/${ticket_id}`, status, this.getOptions());
  }

  getOptions() {
    return {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${localStorage.getItem("jwt")!}`
      })
    };
  }
}
