import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { TesteaceComponent } from './testeace.component';
import { MainComponent } from './main/main.component';
import { MatAccordion, MatExpansionModule, MatExpansionPanel } from '@angular/material/expansion';
import { MatSlideToggle, MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIcon, MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatOptionModule } from '@angular/material/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { FiltroComponent } from './filtro/filtro.component';
import { NgxSliderModule } from 'ngx-slider-v2';
import { TreeComponent } from './tree/tree.component';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTree, MatTreeModule } from '@angular/material/tree';
import {DragDropModule} from '@angular/cdk/drag-drop';
import { ConditionComponent } from './tree/condition/condition.component';

const routes: Routes = [
  { path: '', component: TesteaceComponent }
];


@NgModule({
  declarations: [
    TesteaceComponent,
    MainComponent,
    FiltroComponent,
    TreeComponent,
    ConditionComponent,
  ],
  imports: [
    CommonModule,
    MatExpansionModule,
    MatCheckboxModule,
    MatToolbarModule,
    MatTreeModule,
    MatSlideToggleModule,
    MatPaginatorModule,
    MatButtonToggleModule,
    MatOptionModule,
    MatInputModule,
    MatFormFieldModule,
    NgxSliderModule ,
    ReactiveFormsModule,
    MatSelectModule,
    MatInputModule,
    FormsModule,
    MatRadioModule,
    MatButtonModule,
    MatIconModule,
    DragDropModule,
    MatSidenavModule,
    RouterModule.forChild(routes)
  ],
  exports: [MatExpansionModule]
})
export class TesteaceModule { }
