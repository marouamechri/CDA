# 📁 Classeur Médical – Projet CDA

Classeur Médical est une application web développée dans le cadre d’une formation CDA (Concepteur Développeur d'Applications). Elle a pour objectif d'aider les utilisateurs à organiser, suivre et gérer leurs informations médicales personnelles.

## 🎯 Fonctionnalités principales

- **Authentification sécurisée** (inscription, connexion, vérification email, reset du mot de passe)
- **Espace personnel** pour chaque utilisateur
- **Gestion des événements médicaux** (consultations, analyses, traitements, etc.)
- **Organisation par sujets et sous-sujets**
- **Gestion des documents médicaux**
- **Tableau de bord administrateur**
- **Système de rôles (Admin, Utilisateur, etc.)**

## 🛠️ Technologies utilisées

- **Back-end** : Java, Spring Boot, Spring Security, JWT
- **Base de données** : (MySQL)
- **Architecture** : MVC, API RESTful
- **Déploiement** : Docker

## 📁 Structure du projet

```
CDA-main/
├── src/main/java/com/example/cda/
│   ├── controllers/         → Logique de contrôle (admin, user, sécurité)
│   ├── services/            → Services métier
│   ├── models/              → Entités JPA
│   ├── repositories/        → Requêtes en base de données (JPA)
│   ├── config/              → Configuration de sécurité
│   └── CdaApplication.java  → Point d’entrée
├── pom.xml                  → Configuration Maven
```

## 🚀 Lancement du projet

1. Cloner le dépôt :
   ```bash
   git clone https://github.com/votre-utilisateur/CDA-main.git
   cd CDA-main
   ```

2. Configurer le fichier `application.properties` :
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/cda
   spring.datasource.username=root
   spring.datasource.password=your_password
   jwt.secret=your_secret
   ```

3. Lancer l’application :
   ```bash
   ./mvnw spring-boot:run
   ```

## 🔐 Sécurité

- Authentification avec JWT
- Rôles définis pour sécuriser les routes (admin / utilisateur)

## 📬 Contact

Développé par **Mechri Maroua**  
📧 marwa.mechri@gmail.com  

