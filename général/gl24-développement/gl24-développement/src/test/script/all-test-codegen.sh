#! /bin/sh
cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:./src/main/bin:"$PATH"

for cas_de_test in src/test/deca/codegen/valid/perso/*.deca
do
  decac "$cas_de_test" || exit 1
done

for cas_de_test in src/test/deca/codegen/valid/perso/*.ass
do
  resultat=$(ima "$cas_de_test")
  # shellcheck disable=SC2028
  echo "$cas_de_test\n$resultat"
done
rm -rf src/test/deca/codegen/valid/perso/*.ass