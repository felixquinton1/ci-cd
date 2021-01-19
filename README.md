# Menu Server

Cette application est une API REST(-ish) pour gérer les menus de la Cantina.

Je vous dirais bien comment la lancer, mais le précédent employé est parti sans laisser de documentation.

Il disait : "il suffit de lancer le script run.sh".

## Utilisation

Les personnes du département "Quality Assurance" disent qu'on peut tester l'application manuellement avec ces requêtes HTTP :

```shell
# Afficher tous les menus
$ curl -XGET localhost:8080/menus

# Créer un nouveau menu "Menu de Noel" avec un plat "Céréales sur lait"
$ curl -H "Content-Type: application/json"  -d '{"name": "Menu de Noel", "dishes": [{"name": "Céréales sur lait"}]}' http://localhost:8080/menus
```
