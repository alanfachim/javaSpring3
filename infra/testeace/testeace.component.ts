import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { FloatLabelType } from '@angular/material/form-field';
import { PedidosService } from '../shared/sevice/pedidosService';
import { RegraService } from '../shared/sevice/regrasService';
import { TreeComponent } from './tree/tree.component';

@Component({
  selector: 'app-testeace',
  templateUrl: './testeace.component.html',
  styleUrls: ['./testeace.component.css']
})
export class TesteaceComponent implements OnInit {
  panelOpenState = false;
  showFiller = false;
  hideRequiredControl = new FormControl(false);
  floatLabelControl = new FormControl('auto' as FloatLabelType);
  options = this._formBuilder.group({
    hideRequired: this.hideRequiredControl,
    floatLabel: this.floatLabelControl,
  });
  @ViewChild("tree") private tree: TreeComponent | undefined;
  addNode(event: any) {
    event.stopPropagation();
    this.tree?.addNewNode();
  }
  getFloatLabelValue(): FloatLabelType {
    return this.floatLabelControl.value || 'auto';
  }
  getpedidos() {
    this.pedidosService.listPedidos()
  }
  regras = [
    { nome: "[1] Regra consulta apontamento", disparos: 347, habilitado: true },
    { nome: "[2] Regra consulta apontamento", disparos: 346, habilitado: true },]
  constructor(private _formBuilder: FormBuilder, public pedidosService: PedidosService, public regraService: RegraService) { }
  form: FormGroup = new FormGroup({
    tel: new FormControl('procurar regra'),
  });
  ngOnInit(): void {
  }

}
