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
  options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${localStorage.getItem("jwt")!}`
      })
    };


  getTickets(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(`${environment.apiUrl}/tickets`, this.options);
  }
  getEmployeeTickets(id: number): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(`${environment.apiUrl}/employees/${id}/tickets`, this.options);
  }

  addNewTicket(ticket: Ticket) {
    return this.http.post<Ticket>(`${environment.apiUrl}/employees/${ticket.author.id}/tickets`, ticket, this.options);
  }
}
