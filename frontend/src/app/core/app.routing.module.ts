import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from '../login/login.component';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { DashboardCommentsComponent } from '../dashboard/dashboard-comments.component';

const routes: Routes = [
  {path: 'dashboard/:id/:username', component: DashboardComponent},
  {path: 'login', component: LoginComponent},
  {path: '', component: LoginComponent},
  {path: 'dashboard/comments/:userId/:bookId/:title/:author/:publisher/:description/:numPages/:publishDate',
    component: DashboardCommentsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
