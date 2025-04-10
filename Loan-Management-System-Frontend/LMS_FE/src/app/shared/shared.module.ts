// import { NgModule } from '@angular/core';
// import { CommonModule } from '@angular/common';



// @NgModule({
//   declarations: [],
//   imports: [
//     CommonModule
//   ]
// })
// export class SharedModule { }


import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { ButtonComponent } from './button/button.component';
import { CardComponent } from './card/card.component';

@NgModule({
  declarations: [ButtonComponent, CardComponent],
  imports: [
    CommonModule,
    MatButtonModule,
    MatCardModule
  ],
  exports: [ButtonComponent, CardComponent]
})
export class SharedModule { }