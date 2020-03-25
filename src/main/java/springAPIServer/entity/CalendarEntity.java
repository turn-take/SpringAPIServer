package springAPIServer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Calendarテーブルのエンティティクラス
 *
 */
@Entity
@Table(name="calendar")
public class CalendarEntity {
	
	@Id
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@Column(nullable = false)
	private boolean holidayflag;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean getHolidayflag() {
		return holidayflag;
	}

	public void setHolidayflag(boolean holidayflag) {
		this.holidayflag = holidayflag;
	}

	@Override
	public String toString() {
		return "CalendarEntity [date=" + date + ", holidayflag=" + holidayflag + "]";
	}
}
