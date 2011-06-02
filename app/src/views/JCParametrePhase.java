﻿package views;

import infos.InfosModelCategorie;
import infos.InfosModelPhaseQualificative;
import infos.InfosModelPoule;
import infos.Parametre;
import interfaces.IChangeableListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JComboBox;

import common.Pair;

import controlers.ContestOrg;

@SuppressWarnings("serial")
public class JCParametrePhase extends JCParametreAbstract implements IChangeableListener<Pair<Integer,Integer>>
{
	// Liste associée au composant
	private JComboBox jcb_phase = new JComboBox();
	
	// Couleur par défaut de la liste
	private Color color;
	
	// Booléen indiquant si le composant est dépendant d'un autre
	private boolean dependant = false;
	
	// Id de la catégorie
	private Integer idCategorie = null;
	
	// Id de la poule
	private Integer idPoule = null;
	
	// Ids des phases
	private ArrayList<Integer> idsPhases = new  ArrayList<Integer>();
	
	// Constructeur
	public JCParametrePhase(Parametre parametre) {
		// Appel du constructeur parent
		super(parametre);
		
		// Ajouter la liste dans le panel
		this.setLayout(new BorderLayout());
		this.add(this.jcb_phase);
		
		// Retenir la couleur par défaut de la liste
		this.color = this.jcb_phase.getBackground();
		
		// Remplir la liste
		if(ContestOrg.get().is(ContestOrg.STATE_OPEN)) {
			this.jcb_phase.addItem("Veuillez séléctionner une poule");
			this.jcb_phase.setBackground(new Color(250, 180, 76));
		} else {
			this.jcb_phase.addItem("Pas encore de phases");
			this.jcb_phase.setBackground(new Color(250, 90, 90));
		}
	}

	// Implémentation de getValeur
	@Override
	public String getValeur () {
		// Vérifier s'il ne s'agit pas du premier item qui est séléctionné
		if(this.jcb_phase.getSelectedIndex() == 0) {
			return null;
		}
		
		// Vérifier s'il ne s'agit pas du dernier item qui est séléctionné
		if(this.jcb_phase.getSelectedIndex() == this.jcb_phase.getItemCount()-1) {
			return "%idDernierePhase("+this.idCategorie+","+this.idPoule+")%";
		}
		
		// Retourner l'id de la phase séléctionnée
		return String.valueOf(this.idsPhases.get(this.jcb_phase.getSelectedIndex()-1));
	}
	
	// Implémentation de setValeur
	@Override
	public void setValeur (String valeur) {
		try {
			this.jcb_phase.setSelectedIndex(valeur == null || valeur.isEmpty() ? 0 : this.idsPhases.indexOf(Integer.parseInt(valeur))+1);
		} catch(NumberFormatException e) {
			this.jcb_phase.setSelectedIndex(this.jcb_phase.getItemCount()-1);
		}
	}
	
	// Implémentation de getError
	@Override
	public String getError () {
		// Vérifier si le paramètre est optionnel
		if(!this.parametre.isOptionnel()) {
			// Vérifier si le concours est bien ouvert
			if(!ContestOrg.get().is(ContestOrg.STATE_OPEN)) {
				return "Le paramètre \""+this.parametre.getNom()+"\" ne peut pas être défini tant que le concours n'est pas créé.";
			}
			
			// Vérifier si le composant est dépendant d'un autre
			if(!this.dependant) {
				return "Le paramètre \""+this.parametre.getNom()+"\" doit être dépendant d'une poule.";
			}
			
			// Vérifier si la valeur n'est pas vide
			if(this.getValeur() == null || this.getValeur().isEmpty()) {
				return "Le paramètre \""+this.parametre.getNom()+"\" est obligatoire.";
			}
		}
		
		// Retourner null
		return null;
	}

	// Implémentation de link
	@Override
	public void link (JCParametreAbstract[] composants) {
		if(ContestOrg.get().is(ContestOrg.STATE_OPEN)) {	
			// Chercher le composant de la catégorie
			for(JCParametreAbstract composant : composants) {
				if(composant instanceof JCParametrePoule && this.isDependant(composant)) {
					// Ecouter le composant
					((JCParametrePoule)composant).addListener(this);
					
					// Retenir la dépendance
					this.dependant = true;
				}
			}
			
			// Erreur si le composant courant n'est pas dépendant d'un autre
			if(!this.dependant) {
				this.jcb_phase.removeAllItems();
				this.jcb_phase.addItem("Dépendance avec une poule manquante");
				this.jcb_phase.setBackground(new Color(250, 90, 90));
			}
		}
	}
	
	// Implémentation de IChangeableListener
	@Override
	public void change (Pair<Integer,Integer> idsCategoriePoule) {
		// Vider la liste des poules
		this.jcb_phase.removeAllItems();
		this.idsPhases.clear();
		
		// Retenir l'id de la catégorie
		this.idCategorie = idsCategoriePoule.getFirst();
		this.idPoule = idsCategoriePoule.getSecond();

		// Vérifier si une poule a été choisie
		if(this.idCategorie == null || this.idPoule == null) {
			this.jcb_phase.addItem("Veuillez séléctionner une poule");
			this.jcb_phase.setBackground(new Color(250, 180, 76));
			return;
		}
		
		// Ajouter les phases de la poule
		boolean trouvee = false;
		ArrayList<Pair<InfosModelCategorie, ArrayList<Pair<InfosModelPoule, ArrayList<InfosModelPhaseQualificative>>>>> categories = ContestOrg.get().getCtrlPhasesQualificatives().getListeCategoriesPoulesPhases();
		for(Pair<InfosModelCategorie, ArrayList<Pair<InfosModelPoule, ArrayList<InfosModelPhaseQualificative>>>> categorie : categories) {
			if(this.idCategorie.equals(categorie.getFirst().getId())) {
				for(Pair<InfosModelPoule, ArrayList<InfosModelPhaseQualificative>> poule : categorie.getSecond()) {
					if(this.idPoule.equals(poule.getFirst().getId())) {
						// Poule trouvée
						trouvee = true;
						
						// Remplir la liste
						if(poule.getSecond().size() != 0) {
							this.jcb_phase.addItem("Veuillez séléctionner une phase");
							this.jcb_phase.setBackground(this.color);
							int i = 1;
							for(InfosModelPhaseQualificative phase : poule.getSecond()) {
								this.jcb_phase.addItem("Phase qualificative "+i);
								this.idsPhases.add(phase.getId());
								i++;
							}
							if(this.jcb_phase.getItemCount() == 3) {
								this.jcb_phase.setSelectedIndex(1);
							}
						} else {
							this.jcb_phase.addItem("Pas encore de phases");
							this.jcb_phase.setBackground(new Color(250, 180, 76));
						}
						this.jcb_phase.addItem("Dernière phase de la poule");
					}
				}
			}
		}
		if(!trouvee) {
			this.jcb_phase.addItem("Poule correspondante non trouvée");
			this.jcb_phase.setBackground(new Color(250, 90, 90));
		}
	}
}
