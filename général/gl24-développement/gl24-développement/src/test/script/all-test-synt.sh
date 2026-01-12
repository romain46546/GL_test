#! /bin/sh
#Script pour tester tous nos tests basé sur basic-lex
cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:"$PATH"
test_synt_invalide () {
    # $1 = premier argument.
    if test_synt "$1" 2>&1 | grep -q -e "$1:[0-9][0-9]*:"
    then
        echo "Echec attendu pour test_synt sur $1."
        cat "$cas_de_test"
        test_synt "$1"
        echo ""
    else
        echo "Succes inattendu de test_synt sur $1."
        cat "$cas_de_test"
        exit 1
    fi
}
test_synt_valide () {
    # $1 = premier argument.
    if test_synt "$1" 2>&1 | grep -q -e "$1:[0-9][0-9]*:"
    then
        echo "Echec inattendu pour test_synt sur $1."
        cat "$cas_de_test"
        test_synt "$1"
        exit 1
    else
        echo "Succes attendu de test_synt sur $1."
        cat "$cas_de_test"
        echo ""
    fi
}

echo "Teste tous les fichers de test pour le parseur :"
echo "-----"
echo "Test les fichiers devant lever une erreur :"
for cas_de_test in src/test/deca/syntax/invalid/perso/*.deca
do
    test_synt_invalide "$cas_de_test"
done
echo "-----"
echo "Teste les fichiers devant pas lever une erreur :"
for cas_de_test in src/test/deca/syntax/valid/perso/*.deca
do
    test_synt_valide "$cas_de_test"
done
echo "-----"
echo "Teste fournis par les enseignants :"
basic-synt.sh
basic-lex.sh
