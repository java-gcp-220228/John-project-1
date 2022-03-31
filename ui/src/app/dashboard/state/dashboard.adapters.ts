import { createEntityAdapter, EntityAdapter } from "@ngrx/entity";
import { Ticket } from "src/app/model/ticket.model";

export const adapter: EntityAdapter<Ticket> = createEntityAdapter<Ticket>();
