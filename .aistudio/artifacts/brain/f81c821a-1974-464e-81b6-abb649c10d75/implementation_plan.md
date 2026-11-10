# Refonte du Catalogue : Recherche Épurée, Éléments Ultra-Compacts & Page Dédiée

Ce plan détaille la restructuration du catalogue et de la recherche pour maximiser la visibilité des livres à l'écran, épurer l'interface de recherche et offrir une navigation progressive (ligne compacte -> expansion rapide des détails essentiels -> page dédiée complète de l'ouvrage).

---

### Synthèse des Réponses Utilisateur (Turn 1)

> [!IMPORTANT]
> 1. **Affichage des filtres multicritères** : Masqués par défaut. Affichage via un **volet déroulant animé** sous la barre de recherche lorsqu'on clique sur le bouton de filtre.
> 2. **Densité visuelle des livres** : **Ligne compacte** (hauteur minimale avec Titre, Auteur, statut de disponibilité et chevron d'expansion) pour permettre à l'utilisateur de parcourir un maximum de livres en un coup d'œil.
> 3. **Navigation progressive (2 niveaux de détails)** :
>    - *Niveau 1 (Inline Expand)* : Clic sur le chevron/la ligne pour afficher l'essentiel (catégorie, rayon/emplacement, nombre d'exemplaires et aperçu court de description).
>    - *Niveau 2 (Page Dédiée Complète)* : Clic sur le bouton « Voir la fiche complète » pour ouvrir une page dédiée à l'ouvrage (avec gestion du retour arrière `BackHandler`, QR code grand format, ISBN, fiche technique détaillée et statut de disponibilité en temps réel).

---

## 1. Vue d'Ensemble & Nouveaux Parcours Utilisateur

```
┌─────────────────────────────────────────────────────────────┐
│ [Rechercher un livre...]                    [Filtres (Icon)]│
└─────────────────────────────────────────────────────────────┘
      │ (Clic sur bouton Filtres)
      ▼
┌─────────────────────────────────────────────────────────────┐
│ ── Volet déroulant animé (Catégories + Disponibilité + Tri) ──│
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│  Liste des Livres Ultra-Compacte                            │
│                                                             │
│  📘 Conception et Architecture... • A. Dupont  [Dispo]  ▼   │  <- Ligne compacte (48dp)
│  📘 Anthologie Poétique... • C. Fontaine     [Emprunté] ▼   │
│  ┌──────────────────────────────────────────────────────┐   │
│  │ 📘 Algorithmes Avancés • M. Lambert     [Dispo]   ▲  │   │  <- Ligne dépliée (Expand)
│  │    Rayon : Info - B1 | Année : 2021 | 3 dispo(s)     │   │
│  │    Aperçu : Graphes, arbres de recherche, complexité │   │
│  │    [ 📄 Consulter la fiche complète & QR Code  -> ]  │   │
│  └──────────────────────────────────────────────────────┘   │
│  📘 Chroniques et Récits... • P. Valmont     [Emprunté] ▼   │
└─────────────────────────────────────────────────────────────┘
      │ (Clic sur "Consulter la fiche complète")
      ▼
┌─────────────────────────────────────────────────────────────┐
│ ← Fiche de l'Ouvrage (Page Dédiée Plein Écran)              │
│   • Titre & Auteur complets                                 │
│   • QR Code haute définition généré                         │
│   • Disponibilité & Rayon précis                            │
│   • Fiche technique & Description intégrale                 │
└─────────────────────────────────────────────────────────────┘
```

---

## 2. Conception de l'Interface & Expérience Utilisateur

### A. Barre de Recherche Épurée avec Bouton de Filtre
- **Barre unique** : Champ de recherche plein format avec icône de recherche, bouton d'effacement et bouton d'action **Filtre** avec badge visuel indiquant si un filtre est actif.
- **Volet déroulant animé (`AnimatedVisibility`)** :
  - S'ouvre sous la barre en glissant vers le bas sans encombrer la vue.
  - Regroupe les puces de catégories (Rayons), le filtre de disponibilité (Tous, Disponible, Emprunté) et le choix du tri (Titre, Auteur, Année).
  - Bouton rapide « Réinitialiser les filtres ».

### B. Carte Ouvrage Ultra-Compacte (Hauteur minimale ~56dp)
- **Mode replié** :
  - Icône miniature du livre.
  - Titre (1 ligne avec `TextOverflow.Ellipsis`) et Auteur.
  - Pastille compacte de statut : `Disponible` (vert) ou `Emprunté` (rouge).
  - Chevron animé (bas/haut).
- **Mode déplié (Expand inline accordéon)** :
  - Animation fluide d'ouverture.
  - Détails essentiels : Rayon d'emplacement (ex: `Rayon Info - B1`), Année d'édition, Nombre d'exemplaires.
  - Description courte.
  - Bouton d'action proéminent : **« Fiche complète & QR Code »**.

### C. Page Dédiée de l'Ouvrage (`BookDetailScreen`)
- Transition fluide vers une nouvelle page dédiée.
- **TopAppBar** avec bouton retour `←` et intégration de `BackHandler` pour un retour naturel à la liste des livres.
- Affichage soigné :
  - Grande carte d'en-tête avec badge de disponibilité.
  - QR Code vectoriel haute résolution avec son code d'exemplaire (pour scan direct au comptoir du gérant).
  - Métadonnées complètes : ISBN, Catégorie, Rayon de rangement, Année de publication, Nombre total d'exemplaires vs disponibles.
  - Résumé et synopsis détaillé.

---

## 3. Architecture Technique

- **Gestion de Navigation** :
  - Ajout d'un état `selectedBookDetail: BookEntity?` dans l'état de l'écran ou le `LibraryViewModel`.
  - Quand `selectedBookDetail != null`, affichage de la page `BookDetailScreen` avec `BackHandler { selectedBookDetail = null }`.
- **Composants Modularisés** :
  - `BookCompactItem` : composant de ligne avec gestion d'état étendu (`expanded = remember { mutableStateOf(false) }`).
  - `FilterCollapsePanel` : volet déroulant pour la sélection multi-critères.
  - `BookDetailScreen` : page dédiée complète de l'ouvrage.

---

## 4. Plan de Déploiement

1. **Création du composant `BookDetailScreen.kt`** :
   - Écran complet avec gestion du `BackHandler`, grand QR code, fiche technique détaillée et bouton retour.
2. **Refonte de `UserSearchScreen.kt`** :
   - Remplacement de la structure précédente par la barre épurée + bouton filtre.
   - Intégration du volet accordéon déroulant des filtres.
   - Création de la liste d'éléments ultra-compacts avec expansion des détails essentiels.
   - Aiguillage vers la nouvelle page dédiée de l'ouvrage lors du clic.
3. **Vérification & Compilation** :
   - Test de compilation avec `compile_applet`.
   - Vérification de la fluidité des animations et de la compatibilité avec toutes les résolutions d'écran.
