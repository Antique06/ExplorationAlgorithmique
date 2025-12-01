# Compte Rendu - Projet IHM

## Membres du groupe

- **Chocraux Corentin**
- **Morenvillé Martin**
- **Brillant Romain**

## Capture d’écran de l’application finale

![Capture d’écran de l’application](./res/applicationFinal.png)  

## Lien vers les mockups Figma

[Accéder aux maquettes sur Figma](https://www.figma.com/design/y9owo0GbW2Ujw6ryxnUrg8/SAE-ihm?node-id=7574-480&t=FEUlwnf49H0u8FVm-1)

## Justification des choix de conception (critères ergonomiques)

Nos choix de conception respectent les principes ergonomiques vus en cours :

### 1. **Guidage**
- Les différentes parties de l'application sont bien délimitées grâce à des groupements logiques entre les items, notamment pour les filtres et les fonctionnalités, et la lisibilité est bonne.

### 2. **Charge de travail**
- Peu de boutons pour trouver facilement ce dont on a besoin, et peu d'actions à effectuer (maximum trois clics) pour réaliser une tâche.
- Les éléments sont regroupés logiquement pour limiter les efforts de recherche.

### 3. **Contrôle explicite**
- L’utilisateur a le contrôle de toutes les actions : il peut effectuer manuellement les appariements si les appariements automatiques ne lui conviennent pas, ainsi que les enregistrements, suppressions, etc.

### 4. **Homogénéité et cohérence**
- Styles et positionnement des composants homogènes partout dans l'application.
- Regroupement et placement cohérent des suites d'actions logiques, notamment les fonctionnalités à droite du tableau, les filtres en haut à droite, et le bouton pour changer les préférences en haut en bleu.

### 5. **Gestion des erreurs**
- Les actions à réaliser limitent fortement le nombre d'erreurs que l'utilisateur peut commettre.

### 6. **Compatibilité**
- Compatible avec différentes tailles d’écran.
- Le niveau technologique requis est faible, permettant à un maximum d’utilisateurs d’utiliser l’application facilement, sans avoir besoin de documentation.

### 8. **Règles d’or de Coutaz**
- **Lutter pour la cohérence** : il y a une cohérence entre les boutons qui sont regroupés logiquement.  
- **Lutter pour la concision** : les actions sont concises grâce à une bonne gestion de l’interface, qui optimise chaque action que l’utilisateur peut effectuer.  
- **Réduire la charge cognitive** : peu de texte à lire pour comprendre le fonctionnement de l’application.  
- **Mettre le contrôle entre les mains de l’utilisateur** : l’utilisateur a la possibilité de modifier les appariements générés automatiquement s’ils ne lui conviennent pas, grâce aux différents boutons regroupés dans les fonctionnalités.  
- **Souplesse d’utilisation** : l’utilisation est simple, avec peu de clics nécessaires pour effectuer une action.  
  Exemple : pour réaliser l'appariement complet de tous les adolescents, il suffit d'appuyer sur *Appariement automatique*. Pour modifier les valeurs prises en compte dans l'appariement, il suffit de cliquer sur le bouton *Préférences*, puis de faire glisser les curseurs selon ses préférences.  
- **Structurer le dialogue** : tous les dialogues sont structurés.  
- **Prédire les erreurs** : les erreurs possibles sont évitées grâce à l’utilisation de modules qui permettent d’identifier facilement les erreurs (exemple : `TableView`).

## Répartition des contributions

| Membre                 | Contributions principales                                      |
|------------------------|----------------------------------------------------------------|
| **Brillant Romain**    | Design Figma, interface                                        |
| **Morenvillé Martin**  | Design Java Scene Builder, codage en JavaFX                   |
| **Chocraux Corentin**  | Codage en JavaFX                                               |

Nous avons réparti les tâches en fonction des compétences de chacun :
- Romain avait de meilleures compétences pour créer l’aspect graphique de l’application et l’interface.
- Martin a fait le prototype haute fidélité et a codé une partie de l’application en JavaFX.
- Corentin maîtrise le mieux JavaFX, donc il a réalisé la majorité des parties techniques JavaFX.

Cette organisation permet une utilisation cohérente des compétences de chacun.

## Informations supplémentaires

Il peut rester des bugs que nous n'avons pas vus.

## Vidéo de présentation (2-3 min)

[👉 Voir la vidéo de présentation](https://youtu.be/PFescyY0b0g)