import { EntityState } from "@ngrx/entity";
import { User } from "src/app/model/user.model";
import { adapter } from "./user-manager.adapters";

export interface State extends EntityState<User> {
  selectedUserId: string | number | null;
}

export const initialState: State = adapter.getInitialState({ selectedUserId: null });
