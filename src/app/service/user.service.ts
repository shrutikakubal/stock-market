import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs/index';
import { User } from '../model/user.model';
import { AuthenticationRequest } from '../model/auth.request';
import { StorageService } from './storage.service';
import {AuthenticationResponse} from '../model/auth.response'

@Injectable({
  providedIn: 'root'
})
export class UserService {
    constructor(private http: HttpClient, private storageSrv: StorageService) { }

    baseUrl: string = environment.getBaseUrl('usr');
  
    signUp(user: User): Observable<null> {
      return this.http.post<null>(this.baseUrl + 'user/signup', user);
    }
  
    signIn(authReq: AuthenticationRequest): Observable<AuthenticationResponse> {
      return this.http.post<AuthenticationResponse>(this.baseUrl + 'authenticate/signin', authReq);
    }
  
    updateProfile(user: User): Observable<null> {
      return this.http.post<null>(this.baseUrl + 'user/profile', user);
    }
  
    authAdmin(): Observable<any> {
      return this.http.get<any>(this.baseUrl + 'authenticate/admin');
    }
  
    authenticated(): Observable<any> {
      return this.http.get<any>(this.baseUrl + 'authenticate/authenticated');
    }
  
    getCurrentUser(): User {
      const userStr = this.storageSrv.getItem('currentUser');
      return userStr ? JSON.parse(userStr) : null;
    }
  
    getCurrentUserRole(): string {
      const currentUser: User = this.getCurrentUser();
      return currentUser ? currentUser.role.split('_')[1] : '';
    }
  
    hasRole(role: string): boolean {
      const currentUser: User = this.getCurrentUser();
      if (!currentUser) {
        return false;
      }
      const currentUserRole = currentUser.role;
      return currentUserRole.indexOf('ROLE_' + role.toUpperCase()) !== -1;
    }
  
    isLoggedIn(): boolean {
      const token = this.storageSrv.getItem('token');
      const currentUser = this.getCurrentUser();
  
      if (token && token.length > 0 && currentUser && currentUser.username) {
        return true;
      } else {
        return false;
      }
    }
  
    signOut(): void {
      this.storageSrv.removeItem('token');
      this.storageSrv.removeItem('currentUser');
      this.storageSrv.clear();
    }
  
}
