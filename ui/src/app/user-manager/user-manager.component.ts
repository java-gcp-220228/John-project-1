import { Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { AppState } from '../app.state';
import { User } from '../model/user.model';
import { UserManagerActions, UserManagerSelectors } from './state';
import { UserManagerDataSource } from './user-manager-datasource';

@Component({
  selector: 'app-user-manager',
  templateUrl: './user-manager.component.html',
  styleUrls: ['./user-manager.component.scss']
})
export class UserManagerComponent {
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatTable) table!: MatTable<User>;
  dataSource!: UserManagerDataSource;
  data$!: Observable<User[]>;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['id', 'username', 'firstName', 'lastName', 'email', 'userRole'];

  constructor(
    private store: Store<AppState>,
  ) {
    this.data$ = this.store.select(UserManagerSelectors.selectAllEmployees);
    this.store.dispatch(UserManagerActions.loadUsers());
    this.data$.subscribe({
      next: data => {
        if (data.length > 0) {
          this.dataSource = new UserManagerDataSource(data);
          this.createTable();
        }
      }
    });
  }

  createTable(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
  }
}
