package org.programmerplanet.intracollab.model;

import java.util.Set;

/**
 * Represents a project.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class Project {

	private Long id;
	private String name;
	private String description;
	private Set<Component> components;
	private Set<Milestone> milestones;
	private SourceRepository sourceRepository;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setComponents(Set<Component> components) {
		this.components = components;
	}

	public Set<Component> getComponents() {
		return components;
	}

	public void setMilestones(Set<Milestone> milestones) {
		this.milestones = milestones;
	}

	public Set<Milestone> getMilestones() {
		return milestones;
	}

	public void setSourceRepository(SourceRepository sourceRepository) {
		this.sourceRepository = sourceRepository;
	}

	public SourceRepository getSourceRepository() {
		return sourceRepository;
	}

}
