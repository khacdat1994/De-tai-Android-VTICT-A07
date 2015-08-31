package com.vtict.eng4kid.database;

public class Topic {

	private int topicID;
	private String topicName;
	private String topicIcon;

	public Topic(int _topicID, String _topicName, String _topicIcon) {
		topicIcon = _topicIcon;
		topicID = _topicID;
		topicName = _topicName;
	}

	public String getTopicIcon() {
		return topicIcon;
	}

	public void setTopicIcon(String topicIcon) {
		this.topicIcon = topicIcon;
	}

	public int getTopicID() {
		return topicID;
	}

	public void setTopicID(int topicID) {
		this.topicID = topicID;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

}
