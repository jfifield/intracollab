package org.programmerplanet.intracollab.util;

import java.util.Date;

/**
 * A date range, with a start date and end date.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class DateRange {

	private Date start;
	private Date end;

	public DateRange(Date start, Date end) {
		if (start == null) {
			throw new IllegalArgumentException("start cannot be null");
		}
		if (end == null) {
			throw new IllegalArgumentException("end cannot be null");
		}
		if (start.after(end)) {
			throw new IllegalArgumentException("start cannot be after end");
		}
		this.start = start;
		this.end = end;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}

}
