/* =========================================================================
   MAILLON 2 — JAVA : le moteur de calcul
   Complétez les quatre méthodes. Les classes Ligne, Resultat et Chargeur
   sont fournies : ne les modifiez pas.
       javac -encoding UTF-8 -d out src/*.java
       java -Dstdout.encoding=UTF-8 -cp out Tests     (les tests)
       java -Dstdout.encoding=UTF-8 -cp out Main      (la production)
   ========================================================================= */

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class Classement {

    /** Barème officiel des dix premiers. FOURNI — NE PAS MODIFIER. */
    public static final int[] BAREME = {25, 18, 15, 12, 10, 8, 6, 4, 2, 1};

    // 1. pointsPourPosition(position) : points marqués pour cette position.
    //    1 -> 25, 2 -> 18, ..., 10 -> 1. Au-delà de la 10e place : 0.
    //    Un abandon vaut la position 0, donc 0 point.
    public static int pointsPourPosition(int position) {
        // je gère l'erreur d'un pilote hors du top 10 ou qui a abandonné (position 0)
        // dans ce cas, c'est directement 0 point.
        if (position <= 0 || position > 10) return 0;
        // sinon, je récupère les points dans le barème. Je fais -1 car l'index des tableaux commence à 0 en Java
        return BAREME[position - 1];
    }

    // 2. classementPilotes(lignes) : un Resultat par pilote, avec ses points,
    //    ses victoires (position 1) et ses 2e places, trié par :
    //    points décroissants, puis victoires, puis 2e places, puis nom (A→Z).
    public static List<Resultat> classementPilotes(List<Ligne> lignes) {
        // j'utilise un Map pour regrouper les résultats de chaque pilote facilement
        Map<String, Resultat> map = new HashMap<>();
        
        for (Ligne ligne : lignes) {
            // si je n'ai pas encore vu ce pilote, je le crée dans ma liste
            map.putIfAbsent(ligne.pilote(), new Resultat(ligne.pilote(), ligne.ecurie()));
            Resultat r = map.get(ligne.pilote());
            
            // j'additionne ses points avec ma fonction du dessus
            r.points += pointsPourPosition(ligne.position());
            // je compte s'il a eu une victoire
            if (ligne.position() == 1) r.victoires++;
            // ou une deuxième place
            if (ligne.position() == 2) r.deuxiemes++;
        }
        
        // je transforme mon Map en une simple liste pour pouvoir la trier
        List<Resultat> pilotes = new ArrayList<>(map.values());
        
        // je trie la liste selon toutes les conditions demandées dans l'énoncé !
        pilotes.sort((a, b) -> {
            if (a.points != b.points) return Integer.compare(b.points, a.points);
            if (a.victoires != b.victoires) return Integer.compare(b.victoires, a.victoires);
            if (a.deuxiemes != b.deuxiemes) return Integer.compare(b.deuxiemes, a.deuxiemes);
            return a.nom.compareTo(b.nom);
        });
        
        return pilotes;
    }

    // 3. classementEcuries(pilotes) : additionne les points, victoires et
    //    2e places des pilotes de chaque écurie. Même ordre de tri.
    public static List<Resultat> classementEcuries(List<Resultat> pilotes) {
        // je refais un peu la même technique du Map mais cette fois regroupé par écurie
        Map<String, Resultat> map = new HashMap<>();
        
        for (Resultat p : pilotes) {
            // je l'initialise si c'est la première fois que je croise l'écurie
            map.putIfAbsent(p.ecurie, new Resultat(p.ecurie, ""));
            Resultat r = map.get(p.ecurie);
            
            // j'additionne simplement les points de ses pilotes
            r.points += p.points;
            r.victoires += p.victoires;
            r.deuxiemes += p.deuxiemes;
        }
        
        List<Resultat> ecuries = new ArrayList<>(map.values());
        
        // je recycle ma petite logique de tri qui marche bien
        ecuries.sort((a, b) -> {
            if (a.points != b.points) return Integer.compare(b.points, a.points);
            if (a.victoires != b.victoires) return Integer.compare(b.victoires, a.victoires);
            if (a.deuxiemes != b.deuxiemes) return Integer.compare(b.deuxiemes, a.deuxiemes);
            return a.nom.compareTo(b.nom);
        });
        
        return ecuries;
    }

    // 4. positionMoyenne(lignes, pilote) : moyenne des positions de ce pilote,
    //    ABANDONS EXCLUS, arrondie à 2 décimales. 0 s'il n'a jamais terminé.
    //    Ex. positions 1, 2 et un abandon -> 1.5
    public static double positionMoyenne(List<Ligne> lignes, String pilote) {
        double somme = 0;
        int compte = 0;
        
        for (Ligne ligne : lignes) {
            // je m'assure qu'on regarde bien le bon pilote et qu'il n'a pas abandonné (donc position > 0)
            if (ligne.pilote().equals(pilote) && ligne.position() > 0) {
                somme += ligne.position();
                compte++;
            }
        }
        
        // petite protection : si le pilote n'a fait que des abandons (ou n'a pas couru), je renvoie 0 pour pas faire planter la division
        if (compte == 0) return 0.0;
        
        // je calcule la moyenne et je l'arrondis à deux décimales avec Math.round
        return Math.round((somme / compte) * 100.0) / 100.0;
    }
}
