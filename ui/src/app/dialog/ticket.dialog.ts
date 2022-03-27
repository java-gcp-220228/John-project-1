import { Component, Inject } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { User } from "../model/user.model";

@Component({
  selector: 'ticket-dialog',
  templateUrl: 'ticket-dialog.html',
})
export class TicketDialog {
  constructor(
    public dialogRef: MatDialogRef<TicketDialog>,
    @Inject(MAT_DIALOG_DATA) public data: {amount: number, description: string, image: File | null, author: User, type: string},
  ) {}
  onNoClick(): void {
    this.dialogRef.close();
  }
  upload(e: Event) {
    let files = (e.target as HTMLInputElement).files;
    this.data.image = files?.item(0)!;
  }
}
