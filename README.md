
# CookBot - Backend

## À propos
Ce projet est une application Java (**Java 17**) construite autour d’une architecture backend combinant :
- **Spring boot** (pour le bean context) pour les standards d’API côté serveur,
- **Spring Data JPA** pour l’accès aux données et la persistance,
- **JPA / Hibernate** (selon la configuration) pour le mapping ORM,
- **Lombok** pour réduire le boilerplate (getters/setters, builders, etc.).

L’objectif est de fournir une base robuste, maintenable et testable pour développer des fonctionnalités métier exposées via une couche API et persistées en base de données relationnelle.

---

## Stack technique
- **Java** : 17
-- **Spring boot** : version 3
- **Jakarta EE** : APIs `jakarta.*`
- **Persistance** : Spring Data JPA (JPA + provider ORM, souvent Hibernate)
- **Lombok** : annotations pour simplifier le code
- **Base de données** : **PostgreSQL**
- **Docker** : *docker run --name postgres-cookbot -e POSTGRES_DB=<NOM_DB> -e POSTGRES_USER=<NOM_USER> -e POSTGRES_PASSWORD=<PASSWORD_BD> -p 5432:5432 -d postgres*
- **Tests** :
  - **H2" (database in memory de test)
  - **JUnit 5** (tests unitaires et d’intégration)
  - **Mockito** (mocking — essentiel pour les tests de services)
  - **Spring Test** 

---

## Architecture
Le projet est généralement organisé selon une séparation claire des responsabilités :
- **Domain / Model** : entités JPA et logique métier
- **Repository** : interfaces Spring Data JPA (CRUD, requêtes dérivées, requêtes custom)
- **Service** : orchestration métier, transactions, validations
- **Web / API** : contrôleurs (REST), DTO, mapping, gestion des erreurs
- **Config** : configuration (datasource, JPA, sécurité, etc.)

---

## Base de données (DB)
### Choix & environnements
- **PostgreSQL**

### Paramètres de connexion
La configuration se fait via plusieurs fichiers de configuration adapté en fonction de l'environnement : `application-dev.properties`, `application-prod.properties` et `application-test.properties`

Exemples :
- `DB_URL=jdbc:postgresql://<host>:<port>/<db>`
- `DB_USERNAME=<username>`
- `DB_PASSWORD=<password>`

Paramètres JPA courants  :
- `JPA_DDL_AUTO=validate` 
- `JPA_DDL_AUTO=update` : mettre à jours la db sans drop l'existant

---

## Build & exécution
### Build (Maven — exemple)
- Lancer les tests :
  - `mvn clean test`
- Construire le package :
  - `mvn clean package`


### Lancer le projet (local)
#### Via IntelliJ IDEA
1. Ouvrir le projet dans IntelliJ IDEA
2. Importer le projet (Maven)
3. Configurer la datasource (DB_URL, DB_USERNAME, DB_PASSWORD)
4. Ajouter une clé Open router dans `OPENROUTER`
5. Ajouter un token assez solide (minimum 30 caractères) `JWT_TOKEN` et une durée de validité pour pour ledit token `JWT_EXPIRATION`
6. Créer une variable d'environnement ENV=dev

---

## Tests
### Tests unitaires (services)
Les services sont testés de manière isolée avec **JUnit 5** + **Mockito** :
- on **mock** les repositories/clients externes
- on vérifie la logique métier, les validations et les cas d’erreur

### Tests d’intégration (repositories / DB)
Selon votre stratégie :
- **H2** pour des tests rapides

---

## Licence
Propriétaire
