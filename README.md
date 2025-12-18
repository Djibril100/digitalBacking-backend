# Digital Banking – Backend

Projet académique backend de gestion de comptes bancaires, développé en Java avec Spring Boot.

## Description
API REST permettant la gestion :
- des clients
- des comptes bancaires (courant, épargne)
- des opérations bancaires (débit, crédit)

Le projet suit une **architecture modulaire et maintenable**, avec une séparation claire des responsabilités.

## Architecture
- `entities` : entités JPA (Client, Compte, Opération, etc.)
- `dtos` : objets de transfert de données
- `mappers` : conversion Entity ↔ DTO
- `repositories` : accès aux données (Spring Data JPA)
- `services` : logique métier
- `web` : contrôleurs REST

## Technologies
- Java
- Spring Boot
- Spring Data JPA
- API REST
- Swagger (documentation de l’API)

## Bonnes pratiques
- Architecture en couches
- Code modulaire et lisible
- Séparation métier / exposition API
- Documentation complète via Swagger

## Auteur
Abdoulaye Djibril BARRY
