import { Http, Headers } from '@angular/http';
import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';

@Injectable()
export class AuthenticationService {

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
        if (response.status == 200) {
          // localStorage.setItem('token', result.token)
          return true;
        }
        return false;
      });
  }

  logout() {
    
  }

}
