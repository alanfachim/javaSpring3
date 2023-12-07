import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TesteaceComponent } from './testeace.component';

describe('TesteaceComponent', () => {
  let component: TesteaceComponent;
  let fixture: ComponentFixture<TesteaceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TesteaceComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TesteaceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
