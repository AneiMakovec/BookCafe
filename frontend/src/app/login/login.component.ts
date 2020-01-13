import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import {User, UserInfo} from '../models/User';
import {AppService} from '../services/app.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: User = new User();

  constructor(private appService: AppService,
              private router: Router) { }

  ngOnInit() {
    this.user.user = new UserInfo();
  }

  login(): void {
      this.appService.login(this.user).subscribe(user => this.router.navigate(['dashboard', user.user.userId, user.user.username]));
  }
}
