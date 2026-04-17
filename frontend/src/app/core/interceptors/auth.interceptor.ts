import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private static readonly MOCK_BEARER_TOKEN = 'mock_jwt_token_valide_123';

  intercept(
    req: HttpRequest<unknown>,
    next: HttpHandler,
  ): Observable<HttpEvent<unknown>> {
    const authenticatedRequest = req.clone({
      setHeaders: {
        Authorization: `Bearer ${AuthInterceptor.MOCK_BEARER_TOKEN}`,
      },
    });

    return next.handle(authenticatedRequest);
  }
}
