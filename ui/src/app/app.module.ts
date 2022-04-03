import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { MatButtonModule } from '@angular/material/button'
import { MatCardModule } from '@angular/material/card'
import { MatDialogModule } from '@angular/material/dialog'
import { MatFormFieldModule } from '@angular/material/form-field'
import { MatSelectModule } from '@angular/material/select'
import { MatToolbarModule } from '@angular/material/toolbar'
import { MatIconModule } from '@angular/material/icon'
import { MatInputModule } from '@angular/material/input'
import { MatRadioModule } from '@angular/material/radio'
import { MatSnackBarModule } from '@angular/material/snack-bar'

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { LoginDialog } from './dialog/login.dialog';
import { HttpClientModule } from '@angular/common/http';
import { HomeComponent } from './home/home.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatMenuModule } from '@angular/material/menu';
import { LayoutModule } from '@angular/cdk/layout';
import { TicketDialog } from './dialog/ticket.dialog';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { environment } from '../environments/environment';
import { reducers } from './app.state';
import { UserManagerComponent } from './user-manager/user-manager.component';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { AuthEffects } from './shared/state';
import { DashboardEffects } from './dashboard/state';
import { UserManagerEffects } from './user-manager/state';

@NgModule({
  declarations: [
    AppComponent,
    LoginDialog,
    HomeComponent,
    DashboardComponent,
    TicketDialog,
    UserManagerComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    MatButtonModule,
    MatCardModule,
    MatDialogModule,
    MatFormFieldModule,
    MatSelectModule,
    MatToolbarModule,
    MatIconModule,
    MatInputModule,
    MatRadioModule,
    MatGridListModule,
    MatMenuModule,
    MatSnackBarModule,
    LayoutModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    StoreModule.forRoot(reducers, {}),
    EffectsModule.forRoot([
      AuthEffects.AuthEffects,
      DashboardEffects.DashboardEffects,
      UserManagerEffects.UserManagerEffects,
    ]),
    StoreDevtoolsModule.instrument({ maxAge: 25, logOnly: environment.production }),
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
