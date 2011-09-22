﻿package models;

import infos.InfosModelCompPhasesQualifsVictoires;

import java.util.ArrayList;

public class ModelCompPhasesQualifsVictoires extends ModelCompPhasesQualifsAbstract
{
	// Constructeurs
	public ModelCompPhasesQualifsVictoires(ModelConcours concours, InfosModelCompPhasesQualifsVictoires infos) {
		// Appeller le constructeur parent
		super(concours);
		
		// Enregistrer les informations
		this.setInfos(infos);
	}
	
	// Setter
	protected void setInfos (InfosModelCompPhasesQualifsVictoires infos) {
		// Appeller le setInfos du parent
		super.setInfos(infos);
		
		// Fire update
		this.fireUpdate();
	}
	
	// Implémentation de toInformation
	@Override
	public InfosModelCompPhasesQualifsVictoires toInformation () {
		InfosModelCompPhasesQualifsVictoires infos = new InfosModelCompPhasesQualifsVictoires();
		infos.setId(this.getId());
		return infos;
	}
	
	// Implémentation de remove
	@Override
	protected void delete (ArrayList<ModelAbstract> removers) throws ContestOrgModelException {
		if (!removers.contains(this)) {
			// Appeller le remove parent
			super.delete(removers);
			
			// Fire delete
			this.fireDelete();
		}
	}
	
}