# Gribouille 🎨

Gribouille est une application de dessin (mini "paint") réalisée en **Java avec JavaFX**, dans le cadre des TP de l'UE *R2.02 – Programmation des IHM* (BUT Informatique). Le projet a été développé progressivement, TP après TP, en appliquant à chaque étape une nouvelle notion vue en cours (gestion des événements, architecture MVC, data binding, etc.).

## 📖 Présentation

Gribouille permet à l'utilisateur de dessiner à main levée sur une zone de type "ardoise" (un `Canvas`), avec la possibilité de :
- choisir un outil de dessin (crayon ou étoile),
- changer la couleur et l'épaisseur du trait,
- sauvegarder et recharger un dessin,
- naviguer dans les menus et raccourcis clavier de l'application.

L'interface est décrite en **FXML** et éditée visuellement avec **SceneBuilder**, tandis que la logique est écrite en Java selon le patron d'architecture **MVC (Modèle-Vue-Contrôleur)**.

## 🖼️ Interface

La fenêtre est organisée autour d'un `BorderPane` :
- **Haut** : une barre de menus (`Dessin`, `Outils`, `Aide`) permettant de charger/sauvegarder un dessin, choisir l'outil et l'épaisseur du trait, quitter l'application, etc.
- **Centre** : la zone de dessin (`Canvas`), qui occupe tout l'espace disponible et se redimensionne avec la fenêtre.
- **Droite** : un panneau de sélection des couleurs (un `ColorPicker` et une palette de couleurs prédéfinies sous forme de rectangles cliquables).
- **Bas** : une barre d'état affichant en temps réel la position du curseur (X, Y), l'épaisseur du trait et l'outil/couleur sélectionnés.

## 🏗️ Architecture

Le projet suit une architecture **MVC** avec plusieurs contrôleurs "imbriqués" (nested controllers), chacun responsable d'une zone de l'interface :

| Fichier FXML | Contrôleur | Rôle |
|---|---|---|
| `Gribouille.fxml` | `Controleur` | Contrôleur central, fait le lien entre les autres |
| `menus.fxml` | `MenusController` | Barre de menus (fichier, outils, épaisseur…) |
| `dessin.fxml` | `DessinController` | Zone de dessin (Canvas) |
| `couleurs.fxml` | `CouleursController` | Palette de couleurs |
| `statut.fxml` | `StatutController` | Barre d'état en bas de fenêtre |

Le `Controleur` central centralise l'état de l'application (couleur courante, épaisseur, outil actif, position de la souris…) sous forme de **propriétés observables JavaFX** (`SimpleObjectProperty`, `SimpleIntegerProperty`…), ce qui permet de lier directement ces valeurs aux composants graphiques (data binding) sans avoir à rafraîchir manuellement l'affichage.

### Le modèle

Le modèle (package `modele`) représente un dessin de façon indépendante de l'affichage, afin de pouvoir le sauvegarder, le recharger, et le redessiner (par exemple lors d'un redimensionnement de la fenêtre) :

- **`Point`** : un point (x, y).
- **`Figure`** : classe abstraite représentant une figure dessinée (couleur, épaisseur, liste de points), avec les méthodes de sauvegarde/chargement au format texte.
- **`Trace`** : figure concrète représentant un tracé libre (dessin au crayon).
- **`Etoile`** : figure concrète représentant une étoile (rayons tracés depuis un centre vers plusieurs points).
- **`Dessin`** : conteneur regroupant l'ensemble des figures d'un dessin, avec les propriétés `nomDuFichier` (liée au titre de la fenêtre) et `estModifie` (permet d'afficher un `*` dans le titre quand le dessin n'a pas été sauvegardé).

### Les outils

Les outils de dessin héritent d'une classe abstraite commune `Outil` (patron **Stratégie**) :
- **`OutilCrayon`** : dessine un tracé libre au gré des mouvements de la souris.
- **`OutilEtoile`** : dessine une étoile en traçant des rayons depuis le point de clic initial vers chaque nouveau point survolé.

Changer d'outil (via le menu ou le clavier) change dynamiquement le comportement de la souris sur la zone de dessin, sans avoir à dupliquer le code de gestion des événements.

## ✨ Fonctionnalités

- **Dessin à main levée** avec le crayon (`MOUSE_PRESSED` / `MOUSE_DRAGGED` sur le `Canvas`).
- **Outil étoile**, dessinant des rayons vers les points survolés.
- **Choix de la couleur** du trait via une palette de rectangles cliquables ou un `ColorPicker`.
- **Choix de l'épaisseur** du trait (1 à 9), via le menu ou le clavier.
- **Raccourcis clavier** permettant de changer rapidement d'outil, de couleur ou d'épaisseur sans interrompre le tracé en cours.
- **Redimensionnement intelligent** : le dessin est mémorisé dans le modèle et intégralement redessiné si la fenêtre est agrandie ou réduite (le `Canvas` seul ne conserverait pas les portions cachées).
- **Barre d'état dynamique**, liée par data binding aux propriétés courantes (position de la souris, épaisseur, couleur, outil).
- **Sauvegarde / chargement** d'un dessin dans un fichier texte, via un `FileChooser` (chaque figure est sérialisée sur une ligne : type, épaisseur, couleur, points).
- **Titre de fenêtre intelligent** : affiche le nom du fichier courant, complété d'une étoile `*` si le dessin contient des modifications non sauvegardées.
- **Confirmation à la fermeture** : si le dessin a été modifié, l'utilisateur est invité à sauvegarder, quitter sans sauvegarder, ou annuler la fermeture.

## 🛠️ Technologies utilisées

- **Java** / **JavaFX** (interface graphique)
- **FXML** + **SceneBuilder** (description et édition visuelle de l'interface)
- **Maven** (gestion du projet et des dépendances)
- **Git** (gestion de versions, avec une branche stable `gribouille_stable` et une branche de développement par TP)

## 📂 Organisation du dépôt Git

Le projet a été développé selon un workflow Git structuré :
- une branche **`gribouille_stable`** contient la version stable et fonctionnelle du projet,
- chaque nouvelle fonctionnalité est développée sur une branche dédiée (`gribouille_tpX`), éventuellement accompagnée de branches temporaires pour les étapes intermédiaires,
- les fonctionnalités validées sont fusionnées (`merge --squash`) dans la branche stable afin de conserver un historique propre, avec un commit par fonctionnalité.

## 🚀 Lancement du projet

Le projet est un projet Maven utilisant JavaFX (version 21.0.2). Pour le lancer :

```bash
mvn compile
mvn javafx:run
```

> ⚠️ Un JDK 17 minimum est requis pour exécuter JavaFX 21.

## 🎓 Contexte

Ce projet a été réalisé dans le cadre de l'UE R2.02 (Programmation des IHM) du BUT Informatique de l'IUT de Caen, au fil de plusieurs séances de TP consacrées respectivement à :
1. la prise en main de Git, Maven et SceneBuilder,
2. la gestion des événements (souris, clavier, menus),
3. l'architecture MVC et le binding de propriétés,
4. les contrôleurs multiples et imbriqués,
5. une séance de révisions (clavier, couleur, épaisseur),
6. le data binding avancé et la persistance des dessins (sauvegarde/chargement).
