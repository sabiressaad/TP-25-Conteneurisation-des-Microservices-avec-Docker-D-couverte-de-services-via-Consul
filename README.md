# TP 25 : Containerisation des Microservices avec Docker + Consul

## 📋 Description
Architecture microservices avec Spring Boot, Docker et Consul pour la découverte de services.

### Services disponibles :
- **Client Service** (port 8088) - Gestion des clients
- **Voiture Service** (port 8089) - Gestion des voitures
- **Gateway Service** (port 8888) - API Gateway
- **Consul** (port 8500) - Découverte de services
- **MySQL** (port 3306) - Base de données
- **phpMyAdmin** (port 8081) - Interface MySQL

## ✅ Prérequis validés
- ✅ Docker version 28.5.1
- ✅ Docker Compose version v2.40.0
- ✅ Maven version 3.9.11
- ✅ Java 17

## 🚀 Démarrage rapide

### Construire et démarrer tous les services :
```bash
docker compose up -d --build
```

### Vérifier l'état des conteneurs :
```bash
docker compose ps
```

### Voir les logs :
```bash
# Tous les services
docker compose logs -f

# Un service spécifique
docker compose logs -f client-service
docker compose logs -f voiture-service
docker compose logs -f gateway-service
docker compose logs -f consul
```

### Arrêter les services :
```bash
docker compose down
```

## 🔗 URLs d'accès

- **Consul UI** : http://localhost:8500
- **phpMyAdmin** : http://localhost:8081
  - Host: `mysql`
  - User: `root`
  - Password: `root`
- **Gateway** : http://localhost:8888
- **Client Service** : http://localhost:8088
- **Voiture Service** : http://localhost:8089

## 📡 Endpoints API

### Client Service (via Gateway ou directement)
```bash
# Via Gateway
GET    http://localhost:8888/clients
POST   http://localhost:8888/clients
GET    http://localhost:8888/clients/{id}
PUT    http://localhost:8888/clients/{id}
DELETE http://localhost:8888/clients/{id}

# Directement
GET    http://localhost:8088/clients
POST   http://localhost:8088/clients
```

### Voiture Service (via Gateway ou directement)
```bash
# Via Gateway
GET    http://localhost:8888/voitures
POST   http://localhost:8888/voitures
GET    http://localhost:8888/voitures/{id}
GET    http://localhost:8888/voitures/client/{clientId}
PUT    http://localhost:8888/voitures/{id}
DELETE http://localhost:8888/voitures/{id}

# Directement
GET    http://localhost:8089/voitures
POST   http://localhost:8089/voitures
```

## 🧪 Tests

### Créer un client :
```bash
curl -X POST http://localhost:8088/clients \
  -H "Content-Type: application/json" \
  -d '{"nom":"Dupont","age":30}'
```

### Créer une voiture :
```bash
curl -X POST http://localhost:8089/voitures \
  -H "Content-Type: application/json" \
  -d '{"marque":"Renault","matricule":"ABC123","model":"Clio","id_client":1}'
```

### Lister tous les clients :
```bash
curl http://localhost:8088/clients
```

### Lister toutes les voitures :
```bash
curl http://localhost:8089/voitures
```

## 🔍 Vérifications

### 1. Vérifier l'enregistrement dans Consul
- Aller sur http://localhost:8500
- Cliquer sur "Services"
- Vous devriez voir : `gateway`, `service-client`, `service-voiture`

### 2. Vérifier les bases de données dans phpMyAdmin
- Aller sur http://localhost:8081
- Connexion avec `root` / `root`
- Vérifier les bases : `Micro_ClientDB` et `Micro_VoitureDB`

### 3. Vérifier les ports exposés
```bash
docker compose ps
```

## 🐛 Dépannage

### Redémarrer un service spécifique :
```bash
docker compose restart client-service
```

### Voir les logs d'erreur :
```bash
docker compose logs --tail=50 client-service
```

### Vérifier les ports utilisés :
```bash
netstat -ano | findstr "8088"
netstat -ano | findstr "8089"
netstat -ano | findstr "8888"
```

### Reconstruire un service :
```bash
docker compose build client-service
docker compose up -d client-service
```

### Nettoyer complètement :
```bash
docker compose down -v
docker compose up -d --build
```

## 🎓 Points clés du TP

### Pourquoi Docker aide en microservices ?
- **Isolation** : chaque service a son environnement
- **Reproductibilité** : même config partout
- **Démarrage global** : un seul fichier Compose
- **Réseau interne** : communication entre conteneurs

### Différence localhost vs noms DNS Docker
❌ **Faux** : `jdbc:mysql://localhost:3306/...`
✅ **Correct** : `jdbc:mysql://mysql:3306/...`

Dans Docker Compose, `mysql` est le nom DNS du conteneur MySQL, pas `localhost`.

### Dockerfile multi-stage
1. **Stage builder** : compile avec Maven
2. **Stage runtime** : exécute le JAR avec Java léger

### Architecture réseau
Tous les services sont sur le réseau `microservices-network` et peuvent communiquer via leurs noms de services.

## 📁 Structure du projet
```
tp25/
├── docker-compose.yml
├── clientService/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
├── voitureService/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
└── gatewayService/
    ├── Dockerfile
    ├── pom.xml
    └── src/
```

## 🎯 Objectifs atteints

✅ Expliquer pourquoi Docker est utile en microservices  
✅ Créer un Dockerfile multi-stage pour un microservice Spring Boot  
✅ Orchestrer plusieurs conteneurs via Docker Compose  
✅ Comprendre la différence entre localhost et les noms DNS Docker  
✅ Vérifier l'enregistrement automatique des services dans Consul  
✅ Diagnostiquer les problèmes classiques (ports, réseau, base, dépendances)
