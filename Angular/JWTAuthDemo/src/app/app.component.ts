import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  msgFromServer: string;
  token: string;

  constructor(private http: HttpClient) { }

  onSubmit(authForm: NgForm) {

    const email = authForm.value.nm_email;
    const password = authForm.value['nm_password'];

    if (authForm.valid) {
      //Login
      this.http.post(environment.loginURL + 'abcd', {
        username: authForm.value['nm_email'],
        password: authForm.value['nm_password']
      })
        .subscribe((response) => {
          console.log('email ' + response['email']);
          console.log('localId ' + response['localId']);
          console.log('idToken ' + response['idToken']);
          console.log('expiresIn ' + response['expiresIn']);
          this.token = response['idToken'];
        },
          error => {
            console.log(error.error);
          })
    }

    authForm.reset();
  }

  onAddNewUser(authForm: NgForm) {

    if (authForm.valid) {
      //Signup
      let headers = new Headers();
      headers.append('Content-Type', 'application/json');

      this.http.post(environment.signUpURL + 'abcd', {
        username: authForm.value['nm_email'],
        password: authForm.value['nm_password']
      },
        {
          headers: new HttpHeaders({ 'Authorization': 'Bearer ' + this.token })
        })
        .subscribe((response) => {
          console.log(response);
          //this.token = response['token'];
        },
          error => {
            console.log(error.error);
          })

    }

  }

  onLogOut() {
    this.token = '';
  }
  ngOnDestroy() { }


}
