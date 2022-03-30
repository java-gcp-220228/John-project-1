import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { User } from '../model/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  authenticate(username: string, password: string) {
    return this.http.post<User>(`${environment.apiUrl}/login`, {username, password}, {observe: 'response'});
  }
}
