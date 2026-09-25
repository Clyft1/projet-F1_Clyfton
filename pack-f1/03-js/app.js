/* =========================================================================
   MAILLON 3 — JAVASCRIPT : l'interface
   Les données arrivent du maillon Java, dans donnees.js :
     PILOTES = [{nom, ecurie, points, victoires}, ...]
     ECURIES = [{nom, points, victoires}, ...]
   Complétez les trois fonctions, puis ouvrez index.html dans le navigateur.
   ========================================================================= */

// 1. trierParPoints(liste) : renvoie une NOUVELLE liste triée par points
//    DÉCROISSANTS. La liste reçue ne doit pas être modifiée.
//    À points égaux, celui qui a le plus de victoires passe devant.
function trierParPoints(liste) {
  // je commence par créer une vraie copie de ma liste en utilisant le spread operator,
  // pour ne surtout pas modifier la liste originale passée en paramètre
  const copie = [...liste];
  
  // je trie cette copie. Je checke d'abord les points, et si c'est pareil, je départage avec les victoires !
  copie.sort((a, b) => {
    if (a.points !== b.points) {
      return b.points - a.points; // tri décroissant sur les points
    }
    return b.victoires - a.victoires; // en cas d'égalité, tri décroissant sur les victoires
  });
  
  return copie;
}

// 2. remplirTableau(idCorps, liste) : remplit le <tbody> dont l'id est fourni.
//    Une ligne <tr> par entrée, avec dans l'ordre les cellules <td> :
//      rang (1, 2, 3...) | nom | écurie (chaîne vide si absente) | points | victoires
//    Chaque <tr> porte l'attribut data-nom. Un nouvel appel REMPLACE le contenu.
function remplirTableau(idCorps, liste) {
  const tbody = document.getElementById(idCorps);
  
  // je nettoie le contenu existant du tableau, sinon ça va juste empiler les données à l'infini
  tbody.innerHTML = "";
  
  // je boucle sur tous les éléments de la liste que j'ai reçue
  liste.forEach((item, index) => {
    const tr = document.createElement("tr");
    
    // j'ajoute l'attribut spécial data-nom comme demandé dans l'énoncé
    tr.dataset.nom = item.nom;
    
    // je prépare mon écurie (car pour le tableau des écuries, cette propriété n'existe pas)
    const ecurie = item.ecurie || "";
    
    // je construis la ligne HTML en injectant les données proprement. L'index + 1 donne le rang.
    tr.innerHTML = `
      <td>${index + 1}</t>
      <td>${item.nom}</td>
      <td>${ecurie}</td>
      <td>${item.points}</td>
      <td>${item.victoires}</td>
    `;
    
    // j'ajoute enfin ma ligne générée au tableau
    tbody.appendChild(tr);
  });
}

// 3. marquerPodium(idCorps) : ajoute la classe CSS "podium" aux TROIS PREMIÈRES
//    lignes du tableau, et la retire de toutes les autres.
function marquerPodium(idCorps) {
  const tbody = document.getElementById(idCorps);
  const lignes = tbody.querySelectorAll("tr");
  
  // je parcours toutes les lignes et je leur ajoute la classe CSS 'podium' 
  // seulement si elles font partie du top 3 (index 0, 1, et 2)
  lignes.forEach((tr, index) => {
    if (index < 3) {
      tr.classList.add("podium");
    } else {
      tr.classList.remove("podium");
    }
  });
}

/* --- FOURNI — NE PAS MODIFIER : affichage de la saison ------------------- */
function afficherSaison() {
  if (typeof PILOTES === "undefined") {
    return;
  }
  remplirTableau("corps-pilotes", trierParPoints(PILOTES));
  marquerPodium("corps-pilotes");
  remplirTableau("corps-ecuries", trierParPoints(ECURIES));
  marquerPodium("corps-ecuries");
}
