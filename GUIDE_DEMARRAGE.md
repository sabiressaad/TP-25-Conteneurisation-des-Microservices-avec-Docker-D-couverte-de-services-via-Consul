# 🚀 Guide de Démarrage - TP 25

## ⚠️ Avant de commencer

### 1. Démarrer Docker Desktop
- Lancez l'application **Docker Desktop** sur votre machine
- Attendez que le statut soit "Engine running" (icône verte)
- Vérifiez dans le terminal :
  ```bash
  docker --version
  ```

## 📝 Étapes du TP

### ✅ Étape 1 : Vérifier les prérequis

```bash
# Vérifier Docker
docker --version

# Vérifier Docker Compose
docker compose version

# Vérifier Maven (optionnel)
mvn -version
```

### ✅ Étape 2-3 : Projets Spring Boot et Dockerfiles
Les fichiers sont déjà créés ! Vous pouvez explorer :
- `clientService/` - Microservice Client
- `voitureService/` - Microservice Voiture  
- `gatewayService/` - API Gateway

### ✅ Étape 4 : Test rapide - Construire une image

```bash
# Depuis la racine du projet
docker build -t client-service:1.0 ./clientService
```

**Attendu** : L'image se construit sans erreur

### ✅ Étape 5-6 : Lancer toute l'architecture

```bash
# Construire et démarrer tous les services
docker compose up -d --build

# Vérifier l'état
docker compose ps

# Voir les logs
docker compose logs -f
```

### ✅ Étape 7 : Vérifications

#### Vérification 1 : Consul UI
1. Ouvrir http://localhost:8500
2. Aller dans "Services"
3. **Attendu** : voir `gateway`, `service-client`, `service-voiture`

#### Vérification 2 : phpMyAdmin
1. Ouvrir http://localhost:8081
2. Connexion :
   - Host: `mysql`
   - User: `root`
   - Password: `root`
3. **Attendu** : voir les bases `Micro_ClientDB` et `Micro_VoitureDB`

#### Vérification 3 : Ports exposés
Tester les URLs :
- Gateway : http://localhost:8888
- Client : http://localhost:8088/clients
- Voiture : http://localhost:8089/voitures

### ✅ Étape 8 : Mini défis

#### Défi 1 : Redémarrer un service et observer Consul
```bash
docker compose restart client-service
```
Puis regarder Consul UI pour voir le service se réenregistrer.

#### Défi 2 : Lire les logs du Gateway
```bash
docker compose logs -f gateway-service
```
Observer :
- Démarrage Spring Boot
- Connexion à Consul
- Enregistrement du service

## 🧪 Tester l'API

### Créer un client
```bash
curl -X POST http://localhost:8088/clients -H "Content-Type: application/json" -d "{\"nom\":\"Dupont\",\"age\":30}"
```

### Lister les clients
```bash
curl http://localhost:8088/clients
```

### Créer une voiture
```bash
curl -X POST http://localhost:8089/voitures -H "Content-Type: application/json" -d "{\"marque\":\"Renault\",\"matricule\":\"ABC123\",\"model\":\"Clio\",\"id_client\":1}"
```

### Lister les voitures
```bash
curl http://localhost:8089/voitures
```

### Via le Gateway
```bash
# Client via Gateway
curl http://localhost:8888/clients

# Voiture via Gateway  
curl http://localhost:8888/voitures
```

## 🔧 Commandes utiles

### Arrêter tous les services
```bash
docker compose down
```

### Redémarrer un service
```bash
docker compose restart client-service
```

### Voir les logs d'un service
```bash
docker compose logs -f client-service
```

### Reconstruire et redémarrer
```bash
docker compose up -d --build
```

### Nettoyer complètement (⚠️ supprime les données)
```bash
docker compose down -v
```

## 📊 Points clés à comprendre

### ✨ Pourquoi Docker aide en microservices ?
- **Isolation** : chaque service = 1 conteneur isolé
- **Reproductibilité** : même environnement partout
- **Démarrage simple** : 1 commande pour tout lancer
- **Réseau interne** : communication facile entre services

### 🔑 localhost vs noms DNS Docker
Dans docker-compose.yml :
- ❌ `jdbc:mysql://localhost:3306/...` - **FAUX**
- ✅ `jdbc:mysql://mysql:3306/...` - **CORRECT**

**Pourquoi ?** Dans Docker, `mysql` est le nom DNS du conteneur MySQL sur le réseau `microservices-network`.

### 🏗️ Dockerfile multi-stage
```dockerfile
# Stage 1 : Builder (compile)
FROM maven:3.8.4-openjdk-17 AS builder
# Compile le projet → génère le JAR

# Stage 2 : Runtime (exécute)
FROM openjdk:17-jdk-alpine
# Image légère qui exécute le JAR
```

**Avantage** : Image finale plus petite (sans Maven)

## ✅ Checklist de réussite

- [ ] Docker Desktop est démarré
- [ ] `docker compose up -d --build` réussit
- [ ] Consul UI affiche les 3 services
- [ ] phpMyAdmin affiche les 2 bases
- [ ] Les endpoints API répondent
- [ ] Je peux créer et lister des clients/voitures
- [ ] Je comprends localhost vs mysql (DNS Docker)
- [ ] Je peux redémarrer un service et voir Consul se mettre à jour

## 🎓 Objectifs du lab atteints

✅ Expliquer pourquoi Docker est utile en microservices  
✅ Créer un Dockerfile multi-stage pour un microservice Spring Boot  
✅ Orchestrer plusieurs conteneurs (MySQL, Consul, Gateway, Client, Voiture, phpMyAdmin) via Docker Compose  
✅ Comprendre la différence entre localhost (machine hôte) et les noms DNS Docker (mysql, consul)  
✅ Vérifier l'enregistrement automatique des services dans Consul  
✅ Diagnostiquer les problèmes classiques (ports, réseau, base, dépendances)

---

**Bon travail ! 🎉**
