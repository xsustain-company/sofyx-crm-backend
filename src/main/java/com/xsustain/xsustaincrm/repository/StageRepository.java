package com.xsustain.xsustaincrm.repository;

import com.xsustain.xsustaincrm.model.Stage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StageRepository extends CrudRepository<Stage, Long> {

    @Query("SELECT s FROM Stage s WHERE s.deleted = false")
    List<Stage> findAllStages();

    @Query("SELECT s FROM Stage s WHERE s.id = :stageId AND s.deleted = false")
    Stage findStageById(@Param("stageId") long stageId);
}