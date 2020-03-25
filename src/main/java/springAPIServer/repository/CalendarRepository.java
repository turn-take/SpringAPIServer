package springAPIServer.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springAPIServer.entity.CalendarEntity;

/**
 * Calendarテーブルのリポジトリ
 */
@Repository
public interface CalendarRepository extends JpaRepository<CalendarEntity, Date>{

}
