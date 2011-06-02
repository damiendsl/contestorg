﻿package common;

import interfaces.IGeneration;
import interfaces.IGenerationListener;

public abstract class GenerationAbstract<T> implements IGeneration<T>
{
	
	// Runnable
	private GenerationRunnableAbstract<T> runnable;
	
	// Constructeur
	public GenerationAbstract() {
		this.runnable = this.getRunnable();
	}

	// Implémentation de IGeneration
	@Override
	public void generationStart () { 
		new Thread(this.runnable).start();
	}
	@Override
	public void generationStop () {
		this.runnable.stop();
	}
	@Override
	public void generationCancel () {
		this.runnable.cancel();
	}
	@Override
	public void addListener (IGenerationListener<T> listener) {
		this.runnable.addListener(listener);
	}
	@Override
	public void removeListener (IGenerationListener<T> listener) {
		this.runnable.removeListener(listener);
	}

	// Méthode à implémenter
	public abstract GenerationRunnableAbstract<T> getRunnable();
	
}
