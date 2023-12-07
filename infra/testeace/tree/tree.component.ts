

import { Component, Inject, ViewEncapsulation } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { debounce } from '@agentepsilon/decko';
import { NestedTreeControl } from '@angular/cdk/tree';
import { MatTreeNestedDataSource } from '@angular/material/tree';


export interface TreeNode {
  id: string;
  tipo: string;
  valor: string | undefined;
  operacao: string | undefined;
  propriedade: string | undefined;
  children: TreeNode[];
  isExpanded?: boolean;
}

export interface DropInfo {
  targetId: string;
  action?: string;
}




const demoData: TreeNode[] = [
  {
    id: 'item 1',
    tipo: 'condicao',
    valor: undefined,
    operacao: undefined,
    propriedade: undefined,
    children: []
  },
  {
    id: 'item 2',
    valor: undefined,
    operacao: undefined,
    propriedade: undefined,
    tipo: 'condicao',
    children: []
  }
]

@Component({
  selector: 'app-tree',
  templateUrl: './tree.component.html',
  styleUrls: ['./tree.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class TreeComponent {
  nodes: TreeNode[] = [
    {
      id: 'item 1',
      valor: undefined,
      operacao: undefined,
      propriedade: undefined,
      tipo: 'condicao',
      children: []
    },
    {
      id: 'item 2',
      valor: undefined,
      operacao: undefined,
      propriedade: undefined,
      tipo: 'condicao',
      children: []
    }
  ]

  // ids for connected drop lists
  dropTargetIds: any = [];
  nodeLookup: any = {};
  dropActionTodo: DropInfo | any = null;

  treeControl = new NestedTreeControl<TreeNode>(node => node.children);
  dataSource = new MatTreeNestedDataSource<TreeNode>();

  constructor(@Inject(DOCUMENT) private document: Document) {
    this.prepareDragDrop(this.nodes);
    this.dataSource.data = demoData;
  }
  hasChild = (_: number, node: TreeNode) =>
    node.children && node.children.length > 0;

  prepareDragDrop(nodes: TreeNode[]) {
    nodes.forEach(node => {
      this.dropTargetIds.push(node.id);
      this.nodeLookup[node.id] = node;
      this.prepareDragDrop(node.children);
    });
  }
  addNode(event: TreeNode) {
    this.nodeLookup[event.id] = event
  }
  @debounce(50)
  dragMoved(event: { pointerPosition: { x: number; y: number; }; }): any {
    let e = this.document.elementFromPoint(
      event.pointerPosition.x,
      event.pointerPosition.y
    );

    if (!e) {
      this.clearDragInfo();
      return;
    }
    let container = e.classList.contains('node-item')
      ? e
      : e.closest('.node-item');
    if (!container) {
      this.clearDragInfo();
      return;
    }
    this.dropActionTodo = {
      targetId: container.getAttribute('data-id')
    };
    const targetRect = container.getBoundingClientRect();
    const oneThird = targetRect.height / 3;

    if (event.pointerPosition.y - targetRect.top < oneThird) {
      // before
      this.dropActionTodo['action'] = 'before';
    } else if (event.pointerPosition.y - targetRect.top > 2 * oneThird) {
      // after
      this.dropActionTodo['action'] = 'after';
    } else {
      // inside
      this.dropActionTodo['action'] = 'inside';
    }
    this.showDragInfo();
  }

  drop(event: { item: { data: any; }; previousContainer: { id: any; }; }) {
    if (!this.dropActionTodo) return;

    const draggedItemId = event.item.data;
    const parentItemId = event.previousContainer.id;
    const targetListId = this.getParentNodeId(
      this.dropActionTodo.targetId,
      this.nodes,
      'main'
    );


    const draggedItem = this.nodeLookup[draggedItemId];

    const oldItemContainer = parentItemId != 'main' ? this.nodeLookup[parentItemId].children
      : this.nodes;
    const newContainer = targetListId != 'main' ? this.nodeLookup[targetListId].children
      : this.nodes;

    let i = oldItemContainer.findIndex((c: { id: any; }) => c.id === draggedItemId);
    oldItemContainer.splice(i, 1);

    switch (this.dropActionTodo.action) {
      case 'before':
      case 'after':
        const targetIndex = newContainer.findIndex(
          (c: { id: any; }) => c.id === this.dropActionTodo.targetId
        );
        if (this.dropActionTodo.action == 'before') {
          newContainer.splice(targetIndex, 0, draggedItem);
        } else {
          newContainer.splice(targetIndex + 1, 0, draggedItem);
        }
        break;

      case 'inside':
        this.nodeLookup[this.dropActionTodo.targetId]['tipo'] = 'operador'
        this.nodeLookup[this.dropActionTodo.targetId]['valor'] = 'and'
        this.nodeLookup[this.dropActionTodo.targetId].children.push(
          draggedItem
        );
        this.nodeLookup[this.dropActionTodo.targetId].isExpanded = true;
        break;
    }
    setTimeout(() => {
      this.clearDragInfo(true);
    }, 50);

  }
  getParentNodeId(
    id: string,
    nodesToSearch: TreeNode[],
    parentId: string
  ): string | any {
    for (let node of nodesToSearch) {
      if (node.id == id) return parentId;
      let ret = this.getParentNodeId(id, node.children, node.id);
      if (ret) return ret;
    }
    return null;
  }
  showDragInfo() {
    this.clearDragInfo();
    if (this.dropActionTodo) {
      this.document
        .getElementById('node-' + this.dropActionTodo!.targetId)!
        .classList.add('drop-' + this.dropActionTodo!.action);
    }
  }
  addNewNode() {
    const data: TreeNode = {
      children: [],
      id: Date.now() + '',
      operacao: "eq",
      tipo: "condicao",
      propriedade: undefined,
      valor: ""
    }
    this.nodes.push(data);
    this.nodeLookup[data.id] = data;
  }
  onNodeDelete(event: any) {
    const parentNode = this.getParentNodeId(
      event.id,
      this.nodes,
      'main'
    )
    const node = parentNode == 'main' ? this.nodes : this.nodeLookup[parentNode].children;
    this.recursiveDelete(event);
    const index = node.indexOf(event);
    if (index > -1) { // only splice array when item is found
      node.splice(index, 1); // 2nd parameter means remove one item only
    }
  }
  recursiveDelete(parent: TreeNode) {
    delete this.nodeLookup[parent.id];
    for (let node of parent.children) {

      let ret = this.recursiveDelete(node);
    }
  }


  clearDragInfo(dropped = false) {
    if (dropped) {
      this.dropActionTodo = null;
    }
    this.document
      .querySelectorAll('.drop-before')
      .forEach(element => element.classList.remove('drop-before'));
    this.document
      .querySelectorAll('.drop-after')
      .forEach(element => element.classList.remove('drop-after'));
    this.document
      .querySelectorAll('.drop-inside')
      .forEach(element => element.classList.remove('drop-inside'));

  }
}
