package com.example.dedis.repositories;

import com.example.dedis.entities.Event;
import com.example.dedis.projections.EventProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(value = """
                    select e.id as id,
                        JSON_UNQUOTE(JSON_EXTRACT(e.content_blocks, '$.title')) as title
                    from event e
                    """, nativeQuery = true)
    List<EventProjection> getAllEventTitles();

    @Query(value = """
                    select
                        JSON_UNQUOTE(JSON_EXTRACT(e.content_blocks, '$.title'))
                    from event e where e.id = :id
                    """, nativeQuery = true)
    String eventTitle(@Param("id") Long id);

    @Query(value = """
        SELECT JSON_UNQUOTE(j.images)
        FROM event e,
             JSON_TABLE(e.content_blocks, '$.contentBlocks[*]'
                 COLUMNS (
                     type VARCHAR(50) PATH '$.type',
                     images JSON PATH '$.values'
                 )
             ) AS j
        WHERE e.id = :id AND j.type = 'image'
    """, nativeQuery = true)
    List<String> imageURLs(@Param("id") Long id);
}