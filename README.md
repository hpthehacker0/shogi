# 🏯 Shogi Engine & Multiplayer API

A robust, full-stack Shogi (Japanese Chess) engine built with **Java and Spring Boot**. This backend serves as the authoritative "single source of truth" for game logic, enforcing complex rules, detecting check/checkmate, and broadcasting real-time game states to connected clients via WebSockets.

---
## Deployment link : https://shogi-frontend-sigma.vercel.app/
---
## ✨ Core Features

- **Complete Rules Engine** — Enforces all piece movements, capturing, and turn-taking.
- **Advanced Drop System** — Fully implements the Komadai (Hand), including strict validation for **Nifu** (Double Pawn) and the **Zero-Move Rule**.
- **Smart Promotions** — Calculates Mandatory, Optional, and None promotion states based on board geometry.
- **The "Time Machine" Algorithm** — Simulates moves locally to prevent players from moving into Check.
- **Checkmate Detection** — Automatically calculates when a player has zero safe moves remaining and locks the game.
- **Real-Time Multiplayer** — Uses Spring WebSockets and STOMP to broadcast game state and live Kifu (game logs) to all connected clients instantly.

---
## 🏛️ Architecture (Domain-Driven Design)
This backend strictly follows **Domain-Driven Design (DDD)** and a 3-tier architecture to ensure maximum separation of concerns, security, and scalability.

* **The Domain Layer (`/engine`):** The pure Java Shogi engine. This layer has zero dependencies on Spring Boot, WebSockets, or databases. It is a completely isolated environment that acts as the single source of truth for game rules, piece movement, checkmate calculations, and drop validation.
* **The Application Layer (`/service` & `/controller`):** Acts as the "Traffic Cop." It exposes the REST endpoints, handles WebSocket broadcasting, and translates (serializes/deserializes) the living Java `ShogiBoard` objects into flat JSON DTOs for the frontend.
* **The Data/Infrastructure Layer (`/repository` & `/model`):** Manages persistence using Spring Data JPA. It stores the complex board state as a JSON string and maintains the append-only Kifu (Game Logs) array.
---
## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 26 |
| Framework | Spring Boot 6 |
| Database | Spring Data JPA (PostgreSQL / H2) |
| Real-time | Spring WebSockets & STOMP Message Broker |
| Serialization | Jackson (JSON) |

---

## 🚀 Getting Started

### Prerequisites

- Java JDK 17 or higher
- Maven

### Installation & Running

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/shogi-engine.git
   cd shogi-engine
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```

The server will start on `http://localhost:8080`.

---

## 📡 API Reference

### REST Endpoints

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/games/start` | Initializes a new Shogi board and returns a Game ID |
| `GET` | `/api/games/{id}` | Retrieves the current state and logs of a specific game |
| `POST` | `/api/games/{id}/move` | Submits a move or drop request |

### WebSocket Connection

- **Endpoint:** `ws://localhost:8080/shogi-websocket`
- **Topic:** Subscribe to `/topic/game/{id}` to receive real-time JSON payloads whenever a move is made.
## 👨‍💻 Author

## Contact Info

For questions, issues, or collaboration, please contact:

> **HARI PRASANNA M**
> *E-mail: m.hariprasanna.hp@gmail.com*
> *GitHub: hpthehacker0*

