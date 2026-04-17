# 🎓 Academic Manager API

**Academic Manager** est une API REST développée avec **Spring Boot 3**. Elle permet la gestion des utilisateurs (étudiants, professeurs, administrateurs) au sein d'un établissement, avec un système de sécurité robuste et une validation de compte par un administrateur.

## 🚀 Fonctionnalités Clés

* **Authentification JWT** : Sécurisation des routes via JSON Web Tokens.
* **Système d'Inscription avec Validation** : 
    * Les nouveaux utilisateurs s'inscrivent mais leur compte est désactivé par défaut (`is_active: false`).
    * Un administrateur doit valider manuellement le compte pour permettre la connexion.
* **Architecture Propre** : Séparation nette entre les contrôleurs (générés via OpenAPI), les services et les dépôts (JPA).
* **Documentation Interactive** : Swagger UI intégré pour tester les endpoints.
* **Conteneurisation** : Déploiement simplifié via Docker et Docker Compose.
* **CI/CD** : Workflow GitHub Actions pour construire et publier l'image Docker sur GHCR.

---

## 🛠 Stack Technique

* **Backend** : Java 21, Spring Boot 3.3.13, Spring Security.
* **Base de données** : MySQL 8.0.
* **Documentation** : OpenAPI 3.0 (Swagger).
* **Outils** : Maven, Docker, Lombok, JJWT.

---

## 📥 Installation et Lancement

### Prérequis
* Docker et Docker Compose installés.
* (Optionnel) Maven 3.9+ et JDK 21 pour le développement local.

### Démarrage avec Docker (Recommandé)

1.  **Cloner le dépôt** :
    ```bash
    git clone [https://github.com/votre-compte/academic-manager.git](https://github.com/votre-compte/academic-manager.git)
    cd academic-manager
    ```

2.  **Lancer l'application** :
    ```bash
    docker compose up --build
    ```

L'API sera disponible sur `http://localhost:8080`.

---

## 📖 Documentation de l'API

Une fois l'application lancée, vous pouvez accéder à la documentation interactive pour tester les appels :

* **Swagger UI** : [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
* **Spécification OpenAPI (JSON)** : [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

### Endpoints Principaux

| Méthode | Endpoint | Accès | Description |
| :--- | :--- | :--- | :--- |
| `POST` | `/auth/register` | Public | Créer un compte (statut inactif par défaut). |
| `POST` | `/auth/login` | Public | Obtenir un token JWT (échoue si le compte est inactif). |
| `GET` | `/me` | Authentifié | Récupérer les informations de son propre profil (via Token). |
| `PATCH` | `/users/{email}/activate` | **Admin** | Activer un compte utilisateur en attente. |
| `GET` | `/users` | **Admin** | Lister les utilisateurs avec filtres (rôle, isActive). |

---

## 🔒 Flux d'Authentification et d'Activation

1.  **Inscription** : L'utilisateur s'enregistre. Le compte est stocké en base avec le flag `is_active = false`.
2.  **Tentative de Login** : Si l'utilisateur tente de se connecter, l'API renvoie une erreur `403 Forbidden`.
3.  **Validation Admin** : L'administrateur utilise la route `/activate` pour passer le compte à `true`.
4.  **Connexion réussie** : L'utilisateur peut se connecter et reçoit son token JWT pour accéder aux autres ressources.

---

## ⚙️ Développement Local

Pour modifier l'API :
1. Modifiez le fichier `swagger.yaml`.
2. Générez le code des interfaces : `mvn clean compile`.
3. Implémentez la logique dans les services Java.
4. Relancez avec Docker pour tester l'intégration.

---
*Projet réalisé dans le cadre du module Architecture Logicielle - ESEO.*
