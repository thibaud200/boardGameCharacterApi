# API BoardGameCharacterAPI pour le site UltraBoardGames

Ce document décrit l'API `BoardGameCharacterAPI`, 
qui est conçue pour gérer les personnages dans un jeu de société en ligne. 
L'API permet de créer, lire, mettre à jour et supprimer des personnages, 
ainsi que de gérer leurs attributs et compétences.

## Structure de l'API
L'API est construite en suivant les principes RESTful,
Respect des best practices de Spring Boot et Java.
Respect des conventions de nommage et structuration des endpoints.
respect des bonnes pratiques de gestion des erreurs et des codes HTTP.
respect des typages de données et validation des entrées.
respect des principes de sécurité (authentification, autorisation, chiffrement, masquage).=>uniquement via l'api
Respect des principes de documentation (Swagger/OpenAPI).
respect des principes de tests unitaires et d'intégration.
respect des principes de performance et d'optimisation (caching).  
Respect des principes de maintenance et d'évolutivité (modularité, séparation des préoccupations).
respect des principes de logging et de monitoring.
respect des principes de gestion des versions de l'API (versioning).
respect des principes de gestion des dépendances (Maven/Gradle).
respect des principes de gestion des configurations (profiles Spring).
respect des principes de gestion des erreurs globales (ControllerAdvice).
respect des principes de gestion des transactions (Spring Transaction Management).
respect des principes de gestion des exceptions personnalisées.
respect des principes de gestion des ressources (pagination, tri).
respect des principes de gestion des relations entre entités (JPA/Hibernate).
respect des principes de gestion des tâches asynchrones (Spring Async).
respect des principes de gestion des événements (Spring Events).
respect des principes de gestion des tests (JUnit, Mockito).
respect des principes de gestion des logs (SLF4J, Logback).
respect des principes de gestion des performances (profiling, optimisation).
respect des principes de gestion des ressources (ThreadPool, Connection Pool).
respect des principes de gestion des erreurs (Retry, Circuit Breaker).
respect des principes de gestion des configurations (Config Server, Vault).
respect des principes de gestion des dépendances (Dependency Injection, Inversion of Control).
respect des principes de gestion des versions (Git, CI/CD).
respect des principes de gestion des environnements (Docker, Kubernetes).
respect des principes de gestion des données (Data Migration, Data Seeding).
respect des principes de gestion des API (API Gateway, Rate Limiting).


## Structure du projet
Le projet est structuré de manière modulaire pour faciliter la maintenance et l'évolution.
boardGameCharacterApi/
├─ src/main/java/com/example/boardgamecharacterapi/
│   ├─ controller/   → REST controllers
│   ├─ service/      → logique métier (scraper + fusion BGG)
│   ├─ scraper/      → classes Jsoup dédiées au scraping
│   ├─ model/        → entités JPA / DTO
│   ├─ repository/   → Spring Data JPA pour persistance
│   └─ BoardGameCharacterApiApplication.java
└─ src/main/resources/
└─ application.yml → configuration DB / ports / logging

## Endpoints de l'API
a partir d'un jeu en entrée récupérer les personnages du jeu de société.(tenir compte de l'année
de sortie du jeu)
- `GET /games/{gameId}/characters` : Récupérer la liste des personnages d'un jeu spécifique. ?forceScrape=true pour déclencher un scraping si le jeu n’est pas encore en DB.
- `GET /characters/{characterId}` : Récupérer les détails d'un personnage spécifique.
- `POST /games/{gameId}/characters` : Créer un nouveau personnage pour un jeu spécifique.
- `PUT /characters/{characterId}` : Mettre à jour les informations d'un personnage spécifique.
- `DELETE /characters/{characterId}` : Supprimer un personnage spécifique.
- `GET /characters/{characterId}/skills` : Récupérer les compétences d'un personnage.
- `POST /characters/{characterId}/skills` : Ajouter ou mettre à jour les compétences d'un personnage.
- `DELETE /characters/{characterId}/skills` : Ajouter ou mettre à jour les compétences d'un personnage.
- `GET /characters/search` : Rechercher des personnages par nom, attributs ou compétences.
- `GET /characters/types` : Récupérer les types de personnages disponibles.
- `POST /characters/types` : Ajouter un nouveau type de personnage.

## Modèles de données
tableau des personnages avec leurs attributs et compétences.
- `Character` : Représente un personnage avec des champs tels que `id`, `name`, `description`, `gameId`, etc.

tableau des Game.
- `Game` : Représente un jeu de société avec des champs tels que `id`, `title`, `releaseYear`, etc.

tableau des Skills.
- `Skills` : Représente une compétence de personnage avec des champs tels que `id`, `characterId`, `name`, `level`, etc.

tableau des types de personnages.
- `CharacterType` : Représente un type de personnage avec des champs tels que `id`, `name`, `description`, etc.
- `CharacterTypeAssignment` : Représente l'affectation d'un type à un personnage avec des champs tels que `characterId`, `typeId`, etc.
- `CharacterTypeStats` : Représente des statistiques sur les types de personnages avec des champs tels que `typeId`, `characterCount`, etc.

tableau de requetes
- `SearchCriteria` : Représente les critères de recherche pour trouver des personnages.
- `Response` : Représente une réponse d'erreur avec des champs tels que `code`, `message`, etc.

## Authentification et Sécurité
L'API utilise OAuth 2.0 pour l'authentification et l'autorisation

## Gestion des erreurs
L'API renvoie des codes d'état HTTP appropriés pour indiquer le succès ou l'échec des opérations, 
ainsi que des messages d'erreur détaillés dans le corps de la réponse.
- `200 OK` : Opération réussie.
- `201 Created` : Ressource créée avec succès.
- `204 No Content` : Opération réussie, mais pas de contenu à renvoyer.
- `304 Not Modified` : Ressource non modifiée depuis la dernière requête.
- `400 Bad Request` : Requête invalide ou mal formée.
- `401 Unauthorized` : Authentification requise ou échouée.
- `403 Forbidden` : Accès refusé.
- `404 Not Found` : Ressource non trouvée.
- `409 Conflict` : Conflit lors de la création ou de la mise à jour d'une ressource.
- `422 Unprocessable Entity` : Entité non traitable, généralement en raison de données invalides.
- `429 Too Many Requests` : Limite de taux dépassée.
- `500 Internal Server Error` : Erreur serveur.
- `501 Not Implemented` : Fonctionnalité non implémentée.
- `502 Bad Gateway` : Mauvaise passerelle, généralement en raison d'un problème avec un serveur en amont.
- `503 Service Unavailable` : Service temporairement indisponible.
- `504 Gateway Timeout` : Délai d'attente de la passerelle dépassé.
- gestion des erreurs autres.

Endpoints

Les endpoints que tu as listés sont très complets. Quelques remarques :

/characters/{characterId}/skills : tu as mis DELETE avec la même description que POST → probablement tu voulais “supprimer les compétences d’un personnage” pour DELETE.

/characters/search : prévoir des paramètres query (name, typeId, skillId, gameId, releaseYear) pour filtrer facilement.

/games/{gameId}/characters (GET) : tu pourrais ajouter un paramètre ?forceScrape=true pour déclencher un scraping si le jeu n’est pas encore en DB.

2️⃣ Modèles de données

Pour Spring Boot / JPA, tu pourrais avoir :

a) Game
@Entity
public class Game {
@Id
private Long id;
private String title;
private Integer releaseYear;
}

b) Character
@Entity
public class Character {
@Id
private Long id;
private String name;
private String description;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL)
    private List<Skill> skills;

    @ManyToMany
    @JoinTable(
        name = "character_type_assignment",
        joinColumns = @JoinColumn(name = "character_id"),
        inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    private Set<CharacterType> types;
}

c) Skill
@Entity
public class Skill {
@Id
private Long id;
private String name;
private Integer level;

    @ManyToOne
    @JoinColumn(name = "character_id")
    private Character character;
}

d) CharacterType
@Entity
public class CharacterType {
@Id
private Long id;
private String name;
private String description;
}

3️⃣ Authentification / Sécurité

OAuth2 via Spring Security → spring-boot-starter-oauth2-resource-server si tu veux valider des JWT.
Sinon, pour un usage interne ou test, tu peux commencer avec Basic Auth ou un token statique.

4️⃣ Gestion des erreurs

Ton tableau est complet. Avec Spring Boot, tu peux centraliser via un @ControllerAdvice et un DTO ErrorResponse pour renvoyer un format standard.
Exemple de réponse d’erreur JSON :

{
"code": 404,
"message": "Character not found"
}

5️⃣ Scraping & cache

Endpoint /games/{gameId}/characters : si les personnages ne sont pas en DB → lancer Jsoup → insérer en DB → renvoyer la liste.
Ajouter un verrou par jeu pour éviter le double scraping si plusieurs utilisateurs font la même requête simultanément.
Ajouter lastScraped pour pouvoir rafraîchir si besoin.

6️⃣ Suggestions d’amélioration

Ajouter un DTO pour les réponses REST (CharacterDTO, SkillDTO) → éviter d’exposer directement les entités JPA.
Ajouter des paramètres query pour filtrer les personnages par type, compétence ou année.
Définir des tests unitaires et d’intégration pour valider le scraping + l’insertion en BDD.

Documenter l’API avec OpenAPI / Swagger (springdoc-openapi) pour que React puisse consommer facilement.
Si tu veux, je peux te faire une ébauche complète de projet Spring Boot :
avec entités JPA,repository,service scraping Jsoup + lock,
controller REST complet pour tous tes endpoints,
et gestion des erreurs centralisée.