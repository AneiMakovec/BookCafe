import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { NgModule } from '@angular/core';
import { CustomMaterialModule } from './core/material.module';
import { AppRoutingModule } from './core/app.routing.module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { DashboardCommentsComponent } from './dashboard/dashboard-comments.component';
import { AppService } from './services/app.service';
import {MatSortModule} from '@angular/material';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardComponent,
    DashboardCommentsComponent
  ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        CustomMaterialModule,
        HttpClientModule,
        FormsModule,
        AppRoutingModule,
        MatSortModule
    ],
  providers: [AppService],
  bootstrap: [AppComponent]
})

export class AppModule { }
