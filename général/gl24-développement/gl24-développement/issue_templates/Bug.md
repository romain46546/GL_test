<!--
TEMPLATE BUG — Projet GL Déca

But : diagnostiquer vite, reproduire, fixer, et ajouter un test NON-RÉGRESSION correctement classé.
-->

## 🐞 Résumé du bug
<Une phrase claire : "decac accepte un programme invalide", "format d’erreur incorrect", "crash NullPointer", "codegen incorrect"...>

---

## 📍 Contexte / impact
- Gravité :
  - [ ] P0 (bloque un jalon / rendu / beaucoup de tests)
  - [ ] P1 (gênant mais contournable)
  - [ ] P2 (mineur)
- Type :
  - [ ] Régression (marchait avant)
  - [ ] Bug ancien (jamais marché)
- Impact sur :
  - [ ] Étape A (lex/synt/AST)
  - [ ] Étape B (context)
  - [ ] Étape C (codegen)
  - [ ] Tests / CI

---

## 🧪 Reproduction (obligatoire)
<!-- Donnez des commandes copiables/collables, sinon le bug est difficile à partager. -->
### Commande(s)
```bash
# depuis la racine du repo
mvn clean verify
# ou
./src/main/bin/decac <options> <fichier.deca>
