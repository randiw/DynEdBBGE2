package com.randi.dyned2.tools;

/**
 * A listener to notify SaveFileConnection status.
 * @author Randi Waranugraha
 *
 */
public interface SaveFileConnectionListener {

	/**
	 * Invoked when SaveFileConnection is completed.
	 */
	public void onDone();

	/**
	 * Invoked when error occured.
	 * @param message
	 */
	public void onError(String message);
	
}