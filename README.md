# FoodLink

Monorepo full-stack pour la user story:

> En tant que client du restaurant, je veux consulter le recapitulatif de ma commande ainsi que son etat.

Le projet contient:

- un backend Spring Boot 3 + Maven + JPA + H2
- un frontend Angular 17 standalone + RxJS

## Structure du repo

```text
FoodLink/
  backend/
  frontend/
```

## Fonctionnalites implementees

- Endpoint backend: `GET /api/v1/orders/{orderId}`
- Statuts supportes: `EN_ATTENTE`, `EN_PREPARATION`, `PRETE`, `SERVIE`
- Calcul du `totalPrice` cote backend
- Seed auto au demarrage backend (commande id `1`)
- UI Angular de recap commande (route `/orders/:orderId`)
- Interceptor HTTP global qui ajoute:
  - `Authorization: Bearer mock_jwt_token_valide_123`

## Architecture

### Backend (clean layers)

- Controller: expose l API REST
- Service: logique applicative et mapping vers DTO
- Repository: acces donnees
- Entity: modele JPA

Packages principaux:

- `com.foodlink.orders.presentation`
- `com.foodlink.orders.application`
- `com.foodlink.orders.domain`
- `com.foodlink.orders.infrastructure`

### Frontend (Angular standalone)

- Components standalone
- Service `OrderService` avec `HttpClient`
- Interceptor global d authentification mock
- Models TypeScript alignes sur la reponse backend

## Prerequis

- Java 17+
- Maven 3.9+
- Node.js 18 ou 20 recommande
- npm 9+

Notes:

- Angular 17 fonctionne mieux avec Node 18/20.
- Node 22 peut afficher un warning de version non supportee.

## Lancer le projet

### 1) Lancer le backend

```bash
cd backend
mvn spring-boot:run
```

Backend disponible sur:

- `http://localhost:8080`

H2 console:

- `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:foodlinkdb`
- User: `sa`
- Password: (vide)

### 2) Lancer le frontend

```bash
cd frontend
npm install
npm start
```

Frontend disponible sur:

- `http://localhost:4200/orders/1`

## Verifications rapides

### Verifier l API backend

```bash
curl http://localhost:8080/api/v1/orders/1
```

Exemple de reponse (seed initial):

```json
{
  "id": 1,
  "status": "EN_PREPARATION",
  "totalPrice": 24.0,
  "items": [
    { "name": "Burger Maison", "price": 12.5, "quantity": 1 },
    { "name": "Frites Maison", "price": 4.0, "quantity": 2 },
    { "name": "Limonade", "price": 3.5, "quantity": 1 }
  ]
}
```

### Verifier le cas 404

```bash
curl -i http://localhost:8080/api/v1/orders/999
```

### Verifier le header Authorization cote frontend

- Ouvrir les DevTools navigateur
- Onglet Network
- Ouvrir une requete vers `/api/v1/orders/1`
- Verifier le header:
  - `Authorization: Bearer mock_jwt_token_valide_123`

## Contrat API

### GET `/api/v1/orders/{orderId}`

Response `200`:

```json
{
  "id": 1,
  "status": "EN_PREPARATION",
  "totalPrice": 24.0,
  "items": [
    {
      "name": "Burger Maison",
      "price": 12.5,
      "quantity": 1
    }
  ]
}
```

Response `404`:

```json
{
  "message": "Order with id 999 not found",
  "timestamp": "2026-04-17T00:00:00Z"
}
```

## Commandes utiles (depuis la racine)

Lancer backend sans changer de dossier:

```bash
mvn -f backend/pom.xml spring-boot:run
```

Installer et lancer frontend sans changer de dossier:

```bash
npm --prefix frontend install
npm --prefix frontend start
```

Build frontend:

```bash
npm --prefix frontend run build -- --configuration development
```

## Troubleshooting

- `mvn: command not found`
  - Installer Maven puis reouvrir le terminal.
- Erreur CORS
  - Verifier que le frontend tourne bien sur `http://localhost:4200`.
  - Le backend autorise cette origine via `@CrossOrigin`.
- Donnees H2 perdues apres restart
  - Normal: H2 est en memoire (`jdbc:h2:mem`).
- Warnings npm/audit
  - Typique en dev local. Evaluer au cas par cas avant `npm audit fix --force`.

## Limites actuelles

- Authentification reelle non implementee (token mock uniquement).
- Base in-memory uniquement.
- Un seul endpoint de consultation (`GET /api/v1/orders/{orderId}`).

## Ressources

- README Angular CLI genere: `frontend/README.md`
