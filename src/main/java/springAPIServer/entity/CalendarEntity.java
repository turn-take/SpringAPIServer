package springAPIServer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Calendarテーブルのエンティティクラス
 *
 */
@Entity
@Table(name="calendar")
public class CalendarEntity {
	
	@Id
	@Column(nullable = false)
	private Date date;
	
	@Column(nullable = false)
	private boolean holidayflag;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isHolidayflag() {
		return holidayflag;
	}

	public void setHolidayflag(boolean holidayflag) {
		this.holidayflag = holidayflag;
	}
}
