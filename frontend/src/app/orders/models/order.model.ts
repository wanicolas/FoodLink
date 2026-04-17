export type OrderStatus = 'EN_ATTENTE' | 'EN_PREPARATION' | 'PRETE' | 'SERVIE';

export interface OrderItem {
  name: string;
  price: number;
  quantity: number;
}

export interface Order {
  id: number;
  status: OrderStatus;
  totalPrice: number;
  items: OrderItem[];
}
