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
  isLoginMode = true;
  msgFromServer: string;

  constructor(private http: HttpClient) { }

  onSwitchMode() {
    this.isLoginMode = !this.isLoginMode;
  }

  onSubmit(authForm: NgForm) {
    if (!this.isLoginMode) {
      return;
    }
    const email = authForm.value.nm_email;
    const password = authForm.value['nm_password'];

    if (!this.isLoginMode && authForm.valid) {
      //Signup
      //authObs = this.authSvc.signup(email, password, true);
      console.log(authForm.value['nm_password']);
    }
    else if (authForm.valid) {
      //Login

      this.http.get(environment.helloBeanURL + '/Text Sent and Recived from server.', {
        headers: new HttpHeaders({
          Authorization: 'basic ' + window.btoa(authForm.value['nm_email'] + ':' + authForm.value['nm_password'])
        })
      })
        .subscribe((response) => {
          console.log(response);
          this.msgFromServer = response['message'];
        },
          error => {
            console.log(error.error);
          })

      console.log(authForm.value['nm_password']);
    }

    authForm.reset();
  }

  ngOnDestroy() { }


}
