import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EMPTY, catchError, filter, finalize, map, switchMap, tap } from 'rxjs';
import { Order, OrderItem } from '../../models/order.model';
import { OrderService } from '../../services/order.service';

@Component({
  selector: 'app-order-summary',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './order-summary.component.html',
  styleUrl: './order-summary.component.css',
})
export class OrderSummaryComponent implements OnInit {
  order: Order | null = null;
  isLoading = true;
  errorMessage = '';
  currentOrderId: number | null = null;

  constructor(
    private readonly route: ActivatedRoute,
    private readonly orderService: OrderService,
  ) {}

  ngOnInit(): void {
    this.route.paramMap
      .pipe(
        map((params) => Number(params.get('orderId'))),
        tap((orderId) => {
          this.order = null;
          this.errorMessage = '';

          if (Number.isNaN(orderId)) {
            this.currentOrderId = null;
            this.isLoading = false;
            this.errorMessage = 'Identifiant de commande invalide.';
            return;
          }

          this.currentOrderId = orderId;
          this.isLoading = true;
        }),
        filter((orderId) => !Number.isNaN(orderId)),
        switchMap((orderId) =>
          this.orderService.getOrder(orderId).pipe(
            catchError((error: HttpErrorResponse) => {
              if (error.status === 404) {
                this.errorMessage = 'Commande introuvable.';
              } else {
                this.errorMessage = 'Impossible de charger la commande.';
              }
              return EMPTY;
            }),
            finalize(() => {
              this.isLoading = false;
            }),
          ),
        ),
      )
      .subscribe((order) => {
        this.order = order;
      });
  }

  trackByItem(index: number, item: OrderItem): string {
    return `${item.name}-${index}`;
  }

  getStatusLabel(status: Order['status']): string {
    switch (status) {
      case 'EN_ATTENTE':
        return 'En attente';
      case 'EN_PREPARATION':
        return 'En preparation';
      case 'PRETE':
        return 'Prete';
      case 'SERVIE':
        return 'Servie';
      default:
        return status;
    }
  }

  lineTotal(item: OrderItem): number {
    return item.price * item.quantity;
  }
}
