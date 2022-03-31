import { EntityState } from "@ngrx/entity"
import { Ticket } from "src/app/model/ticket.model";
import { adapter } from "./dashboard.adapters";

export interface State extends EntityState<Ticket> {
  // no additional properties
}

export const initialState: State = adapter.getInitialState();
