package org.contestorg.views;

import java.awt.Window;

import org.contestorg.infos.InfosModelObjectif;
import org.contestorg.infos.InfosModelObjectifNul;
import org.contestorg.infos.InfosModelObjectifPoints;
import org.contestorg.infos.InfosModelObjectifPourcentage;
import org.contestorg.interfaces.ICollector;

/**
 * Boîte de dialogue d'édition d'un objectif
 */
@SuppressWarnings("serial")
public class JDObjectifEditer extends JDObjectifAbstract
{
	/**
	 * Constructeur
	 * @param w_parent fenêtre parent
	 * @param collector collecteur des informations de l'objectif
	 * @param infos informations de l'objectif
	 */
	public JDObjectifEditer(Window w_parent, ICollector<InfosModelObjectif> collector, InfosModelObjectif infos) {
		// Appeller le constructeur du parent
		super(w_parent, "Editer un objectif", collector);

		// Remplir les champs avec les données l'objectif
		if (infos instanceof InfosModelObjectifPoints) {
			// Remplir les champs
			this.jtf_objectif_points_nom.setText(infos.getNom());
			this.jtf_objectif_points_points.setText(String.valueOf(((InfosModelObjectifPoints)infos).getPoints()));
			if (((InfosModelObjectifPoints)infos).getBorneParticipation() != null) {
				this.jtf_objectif_points_borneParticipation.setText(String.valueOf(((InfosModelObjectifPoints)infos).getBorneParticipation()));
			}

			// Séléctionner le bon item dans la liste
			this.jcb_types.setSelectedItem(JDObjectifAbstract.LABEL_OBJECTIF_POINTS);
		} else if (infos instanceof InfosModelObjectifPourcentage) {
			// Remplir les champs
			this.jtf_objectif_pourcentage_nom.setText(infos.getNom());
			this.jtf_objectif_pourcentage_pourcentage.setText(String.valueOf(((InfosModelObjectifPourcentage)infos).getPourcentage()));
			if (((InfosModelObjectifPourcentage)infos).getBorneParticipation() != null) {
				this.jtf_objectif_pourcentage_borneParticipation.setText(String.valueOf(((InfosModelObjectifPourcentage)infos).getBorneParticipation()));
			}
			if (((InfosModelObjectifPourcentage)infos).getBorneAugmentation() != null) {
				this.jtf_objectif_pourcentage_borneAugmentation.setText(String.valueOf(((InfosModelObjectifPourcentage)infos).getBorneAugmentation()));
			}

			// Séléctionner le bon item dans la liste
			this.jcb_types.setSelectedItem(JDObjectifAbstract.LABEL_OBJECTIF_POURCENTAGE);
		} else if (infos instanceof InfosModelObjectifNul) {
			// Remplir les champs
			this.jtf_objectif_nul_nom.setText(infos.getNom());

			// Séléctionner le bon item dans la liste
			this.jcb_types.setSelectedItem(JDObjectifAbstract.LABEL_OBJECTIF_NUL);
		}
	}

}
