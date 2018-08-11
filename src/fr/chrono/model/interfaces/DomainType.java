package fr.chrono.model.interfaces;

/**
 * en fonction du DomainType
 * les fonctions sont diffférentes
 * les configurations possibles sont les suivantes:
 *   - ARRIVAL
 *   - ARRIVAL + START
 *   - ARRIVAL + START + SERVER
 * si il n'y a pas des serveur c'est l'arrivé qui joue
 * le role du serveur
 * @author C.B
 *
 */
//TODO faire en sorte d'avoir plusieur arrivé
//TODO ajouter un domaine pour les temps intermedière.
public enum DomainType {
	
	START,
	ARRIVAL,
	SERVER;

}
