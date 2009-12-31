package org.programmerplanet.intracollab.model;

/**
 * Base class for entity classes. Contains a standard primary key property,
 * and default <code>equals</code>, <code>hashCode</code> and <code>toString</code>
 * implementations based on the primary key value.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public abstract class BaseEntity {

	private Long id;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof BaseEntity) {
			if (this.getClass().isAssignableFrom(obj.getClass()) || obj.getClass().isAssignableFrom(this.getClass())) {
				BaseEntity other = (BaseEntity)obj;
				if (this.id == null && other.getId() == null) {
					return super.equals(obj);
				} else if (this.id == null || other.getId() == null) {
					return false;
				} else {
					return this.id.equals(other.getId());
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		if (this.id != null) {
			return this.id.hashCode();
		} else {
			return super.hashCode();
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.getClass().getName() + (this.id != null ? ": " + this.id.toString() : "");
	}

}
