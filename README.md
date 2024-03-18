# Subscriber-bc
## Project Organization
Le projet est organisé selon une approche modulaire, avec des modules (API, service pour la partie data et WS pour les services Web).

## Lancement du Projet
Pour lancer le projet, suivez ces étapes :
1. Importez le projet dans IntelliJ IDEA ou autre IDE.
2. Exécutez la commande `mvn compile` pour compiler le projet.
3. Configurez IntelliJ IDEA pour exécuter la classe principale située dans le module WS.

## API Documentation
- [Swagger UI](http://localhost:8097/swagger-ui/index.html#/)

## Database Access
- **H2 Console URL:** [H2 Console](http://localhost:8097/h2-console/login.jsp?jsessionid=9a1a9636431c2afef948a5c43fb09750)
- **JDBC URL:** jdbc:h2:mem:subscriberdb
- **User:** sa
- **Password:** root

## Other
- Un mapper est utilisé pour faire le mapping entre les entités et les DTO.
- Des tests unitaires sont inclus dans le projet. Les tests d'intégration ne sont pas encore réalisés.
## TODO
- Code Review
