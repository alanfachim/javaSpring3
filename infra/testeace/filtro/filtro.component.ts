import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl } from '@angular/forms';
import { FloatLabelType } from '@angular/material/form-field';
import { Options } from 'ngx-slider-v2';

@Component({
  selector: 'app-filtro',
  templateUrl: './filtro.component.html',
  styleUrls: ['./filtro.component.css']
})
export class FiltroComponent implements OnInit {
  value: number = 100;
  highValue: number = 100;
  @Output() onPesquisarClick: EventEmitter<any> = new EventEmitter();
  optionss: Options = {
    floor: 0,
    ceil: 200
  };
  hideRequiredControl = new FormControl(false);
  floatLabelControl = new FormControl('auto' as FloatLabelType);
  options = this._formBuilder.group({
    hideRequired: this.hideRequiredControl,
    floatLabel: this.floatLabelControl,
  });
  Override: any;
  constructor(private _formBuilder: FormBuilder) { }
  ngOnInit(): void {
  }
  getPedidos() {
    this.onPesquisarClick.emit()
  }
  formatLabel(value: number) {
    if (value >= 1000) {
      return Math.round(value / 1000) + 'k';
    }

    return value;
  }
  getFloatLabelValue(): FloatLabelType {
    return this.floatLabelControl.value || 'auto';
  }
}
