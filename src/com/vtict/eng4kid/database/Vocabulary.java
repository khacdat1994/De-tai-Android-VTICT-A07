package com.vtict.eng4kid.database;

public class Vocabulary {

	private String voName;
	private String voImage;
	private String voSound;
	private String voMeaning;

	public Vocabulary(String _voName, String _voImage, String _voSound,
			String _voMeaning) {

		voName = _voName;
		voImage = _voImage;
		voSound = _voSound;
		voMeaning = _voMeaning;
	}

	public String getVoName() {
		return voName;
	}

	public void setVoName(String voName) {
		this.voName = voName;
	}

	public String getVoImage() {
		return voImage;
	}

	public void setVoImage(String voImage) {
		this.voImage = voImage;
	}

	public String getVoSound() {
		return voSound;
	}

	public void setVoSound(String voSound) {
		this.voSound = voSound;
	}

	public String getVoMeaning() {
		return voMeaning;
	}

	public void setVoMeaning(String voMeaning) {
		this.voMeaning = voMeaning;
	}

}
