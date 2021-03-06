package com.gmail.ivanytskyy.vitaliy.service;

import java.util.List;
import com.gmail.ivanytskyy.vitaliy.domain.Group;

public interface GroupService {

	public abstract Group create(String groupName);

	public abstract Group findById(long groupId);

	public abstract List<Group> findByName(String groupName);

	public abstract List<Group> findAll();
	
	public abstract boolean isExists(long groupId);

	public abstract void update(long groupId, String newGroupName);

	public abstract void deleteById(long groupId);

}