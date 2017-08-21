import { Http, Headers } from '@angular/http';
import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';

@Injectable()
export class AuthenticationService {

  private authenticated: boolean;

  constructor(private http: Http) { }

  login(credentials) {
    console.log(credentials)
    var h = new Headers();
    h.append('Content-Type', 'application/x-www-form-urlencoded');
    return this.http.post('/myblog/login', 'username=' + credentials.username + 
    '&password=' + credentials.password, {
      headers: h
      })
      .map(response => {
        if (response.status === 200) {
          // localStorage.setItem('token', result.token)
          this.authenticated = true;
          return true;
        }
        this.authenticated = false;
        return false;
      });
  }

  logout() {
    console.log("logoutserv");
    return this.http.get("/myblog/logout")
      .subscribe(response => {},
      (error: Response) => {
        if (error.status === 404) {
          this.authenticated = false;
        }
      });
  }

  isLogedIn() {
    return this.authenticated;
  }

}
