# GL24 — Déca Compiler (Java) — Local Setup (Ubuntu)

Ce document explique comment installer, compiler et lancer le projet **GL24 (Déca)** en local sur **Ubuntu**, et comment configurer **VS Code** (extensions de l’équipe).

Repo GitLab ENSIMAG (SSH) : `git@gitlab.ensimag.fr:gl2026/gr4/gl24.git`  
Branche principale : `master` (développement sur branches perso, ex: `dev/arzelr`, MR → `master`)

---

## 0) TL;DR (si tu veux juste que ça marche)
```bash
sudo apt update
sudo apt install -y git openjdk-21-jdk maven

mkdir -p ~/ensimag/GL && cd ~/ensimag/GL
git clone git@gitlab.ensimag.fr:gl2026/gr4/gl24.git
cd gl24

mvn -q -DskipTests compile
./src/main/bin/decac -b

1) Prérequis

    Ubuntu + droits sudo

    Git, Java 21, Maven

    Accès GitLab (SSH recommandé)

2) Installation (Ubuntu)

sudo apt update
sudo apt install -y git openjdk-21-jdk maven

Vérification :

git --version
java -version
javac -version
mvn -v

Si Java 21 n’est pas sélectionné par défaut :

sudo update-alternatives --config java
sudo update-alternatives --config javac
java -version

3) SSH GitLab (recommandé)
3.1 Générer une clé (si besoin)

ls -la ~/.ssh
ssh-keygen -t ed25519 -C "gl24" -f ~/.ssh/id_ed25519

(Entrée, Entrée, Entrée)
3.2 Ajouter la clé à l’agent SSH

eval "$(ssh-agent -s)"
ssh-add ~/.ssh/id_ed25519

3.3 Ajouter la clé publique dans GitLab

Copier la clé publique :

cat ~/.ssh/id_ed25519.pub

Puis GitLab → Avatar → Preferences → SSH Keys → coller → Add key
3.4 Tester

ssh -T git@gitlab.ensimag.fr

4) Cloner le dépôt

mkdir -p ~/ensimag/GL
cd ~/ensimag/GL
git clone git@gitlab.ensimag.fr:gl2026/gr4/gl24.git
cd gl24

Vérifier que pom.xml est bien à la racine :

ls pom.xml

5) Build Maven (commande “safe” au début)

mvn -q -DskipTests compile
echo $?

    echo $? doit renvoyer 0

    Des warnings peuvent apparaître : au début ce n’est pas bloquant si la compilation passe.

6) Scripts projet : decac, test_lex, test_synt
6.1 Ajouter au PATH

Éditer ~/.bashrc :

nano ~/.bashrc

Ajouter en fin de fichier :

export PATH="$HOME/ensimag/GL/gl24/src/main/bin:$PATH"
export PATH="$HOME/ensimag/GL/gl24/src/test/script:$PATH"
export PATH="$HOME/ensimag/GL/gl24/src/test/script/launchers:$PATH"

Recharger :

source ~/.bashrc

Vérifier :

which decac
which test_lex
which test_synt

6.2 Tests rapides

Bannière (option -b seule) :

decac -b

Lexer sur stdin :

echo -n | test_lex

Syntaxe sur un programme fourni :

ls src/test/deca/syntax/valid/provided
test_synt src/test/deca/syntax/valid/provided/<fichier>.deca

7) VS Code (extensions + réglages)
7.1 Ouvrir le repo

code ~/ensimag/GL/gl24

7.2 Extensions utilisées par l’équipe

Installer / vérifier :

    Extension Pack for Java (Microsoft)

    Language Support for Java™ by Red Hat

    ANTLR4 grammar syntax support (Mike Lischke)

    Error Lens

    Checkstyle for Java

    Prettier - Code formatter

    GitLens

    GitLab Workflow

    Todo Tree

7.3 Forcer VS Code à utiliser Java 21

Trouver le chemin du JDK 21 :

readlink -f $(which javac)

(ex: /usr/lib/jvm/java-21-openjdk-amd64/bin/javac → home = /usr/lib/jvm/java-21-openjdk-amd64)

Dans VS Code :

    Ctrl+Shift+P → Java: Configure Java Runtime → choisir Java 21 par défaut

Option “béton” :

    Ctrl+Shift+P → Preferences: Open Settings (JSON)
    Ajouter (adapter le chemin si besoin) :

{
  "java.jdt.ls.java.home": "/usr/lib/jvm/java-21-openjdk-amd64",
  "editor.formatOnSave": true
}

Puis redémarrer VS Code.
7.4 Commandes VS Code utiles (tirer parti des extensions)

    Maven : Ctrl+Shift+P → Maven: Execute Commands → clean, compile, test, verify

    Java : Ctrl+Shift+P → Java: Reload Project

    Problems : Ctrl+Shift+M (voir erreurs/warnings, Error Lens les affiche inline)

    Prettier : Shift+Alt+F (format document, utile pour Markdown)

    GitLens : clic droit fichier → Show File History

    GitLab Workflow : Ctrl+Shift+P → taper “GitLab…”

    Todo Tree : vue TODO/FIXME dans la sidebar

8) Workflow Git (branche perso + MR)

⚠️ Ne pas commiter directement sur master.
8.1 Se placer sur une branche existante (ex: dev/arzelr)

git fetch origin --prune
git checkout dev/arzelr
git status -sb

Si la branche existe sur origin mais pas en local :

git checkout -b dev/arzelr origin/dev/arzelr

8.2 Mettre à jour sa branche avec master

git checkout master
git pull origin master
git checkout dev/arzelr
git merge master

8.3 Commit / push

git add <fichiers>
git commit -m "message clair"
git push

8.4 Merge Request

    Source : branche perso

    Target : master

    Préfixer le titre par Draft: si en cours

9) Troubleshooting
VS Code propose “Install JDK”

    Si java -version = 21 : ne pas installer un autre JDK, configurer Java 21 dans VS Code (7.3).

Permission denied (publickey) au clone

    Clé SSH non ajoutée au bon compte GitLab → refaire 3.3/3.4.

decac / test_lex / test_synt introuvables

    Vérifier le PATH dans ~/.bashrc + source ~/.bashrc.

10) Checklist finale (2 minutes)

cd ~/ensimag/GL/gl24
mvn -q -DskipTests compile
decac -b
echo -n | test_lex
ls src/test/deca/syntax/valid/provided
test_synt src/test/deca/syntax/valid/provided/<fichier>.deca

Si tout passe : setup opérationnel ✅

::contentReference[oaicite:0]{index=0}


