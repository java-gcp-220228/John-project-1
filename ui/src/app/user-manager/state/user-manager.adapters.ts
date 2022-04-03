import { createEntityAdapter, EntityAdapter } from "@ngrx/entity";
import { User } from "src/app/model/user.model";

export const adapter: EntityAdapter<User> = createEntityAdapter<User>();
