package com.gmail.ivanytskyy.vitaliy.domain;
/*
 * Task #3/2015/12/11 (web project #3)
 * Group class
 * @version 1.01 2015.12.11
 * @author Vitaliy Ivanytskyy
 */
public class Group {
	private long groupId;
	private String groupName;
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
}