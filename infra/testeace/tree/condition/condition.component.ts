import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { TreeNode } from '../tree.component';


interface Propriedade {
  valor: string;
  nome: string;
}

@Component({
  selector: 'app-condition',
  templateUrl: './condition.component.html',
  styleUrls: ['./condition.component.css']
})
export class ConditionComponent implements OnInit, OnChanges {

  @Input()
  public node: any;
  @Output() onNodeAdd = new EventEmitter<TreeNode>();
  public tipo: string | undefined = "and";
  public propriedade: string | undefined;
  public operacao: string | undefined;
  public valor: string | undefined;
  propriedades: Propriedade[] = [
    { nome: "Segmento", valor: "segmento" },
    { nome: "Path", valor: "path" },
    { nome: "Produto", valor: "produto" }
  ]
  @Output() onNodeDelete = new EventEmitter<TreeNode>();;
  constructor() {


  }
  ngOnChanges(changes: SimpleChanges): void {
    if (this.node.tipo == "operador") {
      //existe fil
      this.tipo = this.node.valor;
    }
    else {
      this.propriedade = this.node.propriedade;
      this.operacao = this.node.operacao;
      this.valor = this.node.valor;
    }
  }

  ngOnInit(): void {
    if (this.node.tipo == "operador") {
      //existe fil
      this.tipo = this.node.valor;
    }
    else {
      this.propriedade = this.node.propriedade;
      this.operacao = this.node.operacao;
      this.valor = this.node.valor;
    }
  }

  selected(event: any) {
    if (['and', 'or'].includes(event.value) && this.node['tipo'] != 'operador') {
      const data: TreeNode = {
        children: [],
        id: Date.now()+'',
        operacao: "eq",
        tipo: "condicao",
        propriedade: undefined,
        valor: ""
      }
      this.node['children'].push(data)
      this.node['tipo'] = 'operador'
      this.onNodeAdd.emit(data);
    } else {
      if (this.node['tipo'] != 'operador') {
        this.node['propriedade'] = event.value;
        this.propriedade = event.value;
        this.node['tipo'] = 'condicao'
        this.node['children'] = []
      }
    }
  }
  add(event: any) {
    const data: TreeNode = {
      children: [],
      id: Date.now()+'',
      operacao: "eq",
      tipo: "condicao",
      propriedade: undefined,
      valor: ""
    }
    event.stopPropagation();
    this.node['children'].push(data)
    this.onNodeAdd.emit(data);
  }

  delete(event: any) {
    event.stopPropagation();
    //this.node['children'].push(data)
    this.onNodeDelete.emit(this.node);
  }
}
