package springAPIServer.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springAPIServer.entity.CalendarEntity;

/**
 * Calendarテーブルのリポジトリ
 */
@Repository
public interface CalendarRepository extends JpaRepository<CalendarEntity, Date>{
	
	// SELECT * FROM Calendar WHERE date BETWEEN since AND until;
    List<CalendarEntity> findByDateBetween(Date since, Date until);
}
