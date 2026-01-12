<!--
TEMPLATE USER STORY — Projet GL Déca

But : une US = un incrément "valeur" (comportement compilateur / sous-ensemble de langage / option CLI),
idéalement couvrant la chaîne A+B+C quand c’est pertinent (approche recommandée par le poly).
-->

## 🎯 User Story
**En tant que** <type d’utilisateur: étudiant / évaluateur / dev du compilateur>  
**Je veux** <fonctionnalité observable>  
**Afin de** <bénéfice: compile / erreur claire / tests automatiques / etc.>

<!-- Exemples :
- "En tant qu’évaluateur, je veux que `decac -b` fonctionne strictement comme spécifié."
- "En tant qu’utilisateur, je veux compiler un `if/else` sans-objet et obtenir la sortie attendue."
-->

---

## 🧭 Portée / Sous-langage
<!-- Indiquez précisément la partie du langage ou de l’interface qui est visée. -->
- Sous-ensemble Déca : `<sans-objet | essentiel | complet>`
- Fonctionnalité : `<ex: affectation, if/while, print/println, appels de méthode, new, etc.>`
- Options CLI concernées : `<-b, -p, -v, -w, ...>` (si applicable)

---

## 🧩 Étapes du compilateur touchées
<!-- Gardez 1 Stage principal (A/B/C/X) dans les labels, mais ici décrivez l’impact réel. -->
- [ ] Étape A (lexing/parsing/AST)
- [ ] Étape B (vérifications contextuelles / décorations)
- [ ] Étape C (génération de code IMA / exécution)

---

## ✅ Critères d’acceptation (obligatoires)
<!-- Le compilateur est testé automatiquement : soyez concrets et vérifiables. -->
- [ ] **Comportement externe** conforme (sortie / erreurs / fichiers générés).
- [ ] En cas d’erreur : **format strict** `fichier.deca:ligne:colonne: message` (aucun espace parasite). 
- [ ] Le compilateur **s’arrête à la première erreur** détectée (pas de récupération). :contentReference[oaicite:2]{index=2}
- [ ] `mvn clean verify` passe (tests + couverture Jacoco). 
- [ ] **Tests `.deca` ajoutés** et **rangés dans la bonne classification** (valid/invalid/interactive/perf). 
- [ ] **En-tête de test** ajouté en début de fichier `.deca` (Description / Résultats / Historique). 

---

## 🧪 Plan de tests (à remplir AVANT de coder)
<!-- IMPORTANT : tests "boîte noire" dans src/test/deca/... ; on évite les tests dépendants de l’implémentation. -->
### Tests `.deca` à ajouter / mettre à jour
- [ ] Étape A — syntaxe :
  - `src/test/deca/syntax/valid/...` : `<nom_test.deca>`
  - `src/test/deca/syntax/invalid/...` : `<nom_test.deca>`
- [ ] Étape B — contexte :
  - `src/test/deca/context/valid/...` : `<nom_test.deca>`
  - `src/test/deca/context/invalid/...` : `<nom_test.deca>`
- [ ] Étape C — codegen :
  - `src/test/deca/codegen/valid/...` : `<nom_test.deca>`
  - `src/test/deca/codegen/invalid/...` : `<nom_test.deca>` (si pertinent)
  - `src/test/deca/codegen/interactive/...` : seulement si `readInt/readFloat` :contentReference[oaicite:6]{index=6}
