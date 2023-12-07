import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TesteaceComponent } from './testeace.component';

const routes: Routes = [{ path: '', component: TesteaceComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TesteaceRoutingModule { }
