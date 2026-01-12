# Guide GitLab – Projet Déca (GL24)

Ce document définit **les règles de bonne conduite** pour travailler sur GitLab pendant le projet (branches, issues, labels, MR, CI, releases).

---

## 1) Objectif
- Garder **`master` toujours intégrable** (pas de push direct, MR obligatoires).
- Avoir une traçabilité claire : **1 issue ↔ 1 branche ↔ 1 MR**.
- Faciliter le suivi Agile : board par **Status**, owners explicites, milestones (sprints).

---

## 2) Règles essentielles
1. **Interdiction de push sur `master`** (merge uniquement via MR).
2. Toute issue doit avoir **exactement 1** label de chaque famille :
   - `Status::…` (colonne du board)
   - `Owner::…` (responsable)
   - `Sprint::…` (période)
   - `Priority::P0/P1/P2`
   - `Type::…`
   - `Stage::A/B/C/X`
3. Toute MR doit :
   - référencer l’issue (`Closes #123` ou lien)
   - passer la CI (pipeline verte)
   - avoir **au moins 1 reviewer**

---

## 3) Labels (comment les utiliser)

### 3.1 `Status::` (flux du board)
- `Status::Todo` : à faire (issue définie, pas commencée)
- `Status::Doing` : en cours (une branche existe, travail actif)
- `Status::Review` : MR ouverte, attente review/corrections
- `Status::Blocked` : bloqué (raison écrite en commentaire)
- `Status::Done` : terminé (mergé + vérifié)

**Règle : 1 seul status à la fois.**

### 3.2 `Owner::` (responsable)
- L’Owner **pilote** l’issue jusqu’au Done (coordonne, intègre, débloque).
- L’assignee GitLab peut être aligné sur l’Owner (recommandé).

### 3.3 `Sprint::` (planification)
- Mettre le sprint dès la création de l’issue.
- Si la tâche déborde : changer le label sprint + commentaire “pourquoi”.

### 3.4 `Priority::`
- `P0` : bloque une deadline / casse la base / empêche d’avancer
- `P1` : important, à faire dans le sprint
- `P2` : utile mais peut glisser

### 3.5 `Type::` (nature)
- `Type::UserStory` : fonctionnalité visible
- `Type::Task` : technique interne
- `Type::Bug` : correction
- `Type::Refactor` : nettoyage sans changement fonctionnel
- `Type::Spike` : exploration timebox
- `Type::Doc` : documentation

### 3.6 `Stage::`
- `Stage::A` : lexing/parsing/AST
- `Stage::B` : vérifications contextuelles
- `Stage::C` : génération de code
- `Stage::X` : transverse (CI, tests, docs, planning)

---

## 4) Workflow branches

### 4.1 Branches
- Branches personnelles : `dev/<login>` (bac à sable / essais)
- Branches livrables :
  - `feature/USx.y-titre-court`
  - `bugfix/BUGx.y-titre-court`
  - `refactor/zone-objectif`
  - `doc/objectif`
  - `ci/objectif`

**Règle : on ne merge pas `dev/*` vers `master`.**

### 4.2 Créer une branche “feature”
1) Créer une issue (avec labels complets)
2) Depuis `master` à jour :
```bash
git checkout master
git pull origin master
git checkout -b feature/US2.1-parser-sans-objet
git push -u origin feature/US2.1-parser-sans-objet
```

---

## 5) Merge Requests (MR)

### 5.1 Règles MR
- Une MR = une idée (petite et reviewable)
- Lien avec l’issue : dans la description, ajouter `Closes #123`
- 1 reviewer minimum
- Pas de merge si : CI rouge, threads non résolus

### 5.2 Checklist MR (à faire avant Review)
- `mvn clean verify` en local si possible
- pas de debug `println`
- tests ajoutés/ajustés si besoin
- doc mise à jour si l’interface utilisateur change

### 5.3 Changer de status pendant la MR
- MR ouverte → passer l’issue en `Status::Review`
- corrections demandées → rester en Review
- une fois ok → `Status::QA`
- après merge → `Status::Done`

---

## 6) CI/CD (règles)
- La CI doit tourner sur branches + MR.
- Pour l’instant : pipeline **non bloquante** sur compile/tests/verify (selon config actuelle).
- Plus tard : on rend `verify` bloquant (suppression `allow_failure`).

---

## 7) Milestones (Sprints)
- Chaque issue doit être assignée à un **milestone Sprint**.
- Sprint Planning (lundi) : on remplit le milestone du sprint.
- Review (vendredi) : on vérifie “Done” et on ré-estime si nécessaire.

---

## 8) Daily / Review
- **Daily** : 9h30–9h45
  - chaque Owner annonce : fait / à faire / blocage
  - met à jour le board (Status) immédiatement
- **Review + rétro** : vendredi 16h00–16h45

---

## 9) Releases / Freeze (rendus)
- Avant un rendu important : créer un **tag** sur `master` (ex: `intermediate-freeze`, `final-freeze`).
- Faire une vérification “clone propre” + build (évite “ça marche chez moi”).

---

## 10) Règle “Blocked”
Si vous mettez `Status::Blocked`, ajoutez un commentaire :
- **cause** (technique / spec / dépendance)
- **action** (qui contacte qui / quelle piste)
- **deadline** (quand on re-check)

