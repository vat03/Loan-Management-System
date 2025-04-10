import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: 'admin', loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule) },
  { path: '', redirectTo: '/admin/dashboard', pathMatch: 'full' }, // Default to admin dashboard for now
  { path: '**', redirectTo: '/admin/dashboard' } // Fallback (can add NotFoundComponent later)
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
