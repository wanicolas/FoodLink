import { Routes } from '@angular/router';
import { OrderSummaryComponent } from './orders/components/order-summary/order-summary.component';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'orders/1',
  },
  {
    path: 'orders/:orderId',
    component: OrderSummaryComponent,
  },
  {
    path: '**',
    redirectTo: 'orders/1',
  },
];
