// import { NgModule } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { HeaderComponent } from './components/header/header.component';
// import { FooterComponent } from './components/footer/footer.component';
// import { DashboardCardComponent } from './components/dashboard-card/dashboard-card.component';



// @NgModule({
//   declarations: [
//     HeaderComponent,
//     FooterComponent,
//     DashboardCardComponent
//   ],
//   imports: [
//     CommonModule
//   ]
// })
// export class SharedModule { }


// import { NgModule } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { MaterialModule } from './material/material.module';
// import { HeaderComponent } from './components/header/header.component';
// import { FooterComponent } from './components/footer/footer.component';
// import { DashboardCardComponent } from './components/dashboard-card/dashboard-card.component';
// import { RouterModule } from '@angular/router';

// @NgModule({
//   declarations: [HeaderComponent, FooterComponent, DashboardCardComponent],
//   imports: [CommonModule, MaterialModule, RouterModule],
//   exports: [MaterialModule, HeaderComponent, FooterComponent, DashboardCardComponent]
// })
// export class SharedModule { }




import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from './material/material.module';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { DashboardCardComponent } from './components/dashboard-card/dashboard-card.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    DashboardCardComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    RouterModule
  ],
  exports: [
    MaterialModule,
    HeaderComponent,
    FooterComponent,
    DashboardCardComponent
  ]
})
export class SharedModule { }