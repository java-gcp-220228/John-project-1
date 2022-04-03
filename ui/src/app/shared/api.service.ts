import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Ticket } from '../model/ticket.model';
import { User } from '../model/user.model';

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

  addNewTicket(formData: FormData, id: number) {
    return this.http.post<Ticket>(`${environment.apiUrl}/employees/${id}/tickets`, formData, this.getOptions());
  }

  patchTicket(ticket_id: number, status: string): Observable<Ticket> {
    return this.http.patch<Ticket>(`${environment.apiUrl}/tickets/${ticket_id}`, status, this.getOptions());
  }

  getEmployees(): Observable<User[]> {
    return this.http.get<User[]>(`${environment.apiUrl}/employees`, this.getOptions());
  }

  getOptions() {
    return {
      headers: new HttpHeaders({
        Authorization: `Bearer ${localStorage.getItem("jwt")!}`
      }),
      params: new HttpParams()
    };
  }
}
