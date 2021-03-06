package org.contestorg.interfaces;

/**
 * Interface à implémenter si une classe souhaite être recherchée par mots clés
 */
public interface IMatchable
{
	/**
	 * Vérifier si l'objet correspont au moins à un des mots clés
	 * @param keywords mots clés
	 * @return niveau de correspondance avec les mots clés
	 */
	public int match (String keywords);
}
