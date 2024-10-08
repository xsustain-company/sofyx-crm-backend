package com.xsustain.xsustaincrm.repository;

import com.xsustain.xsustaincrm.model.Pipeline;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PipelineRepository extends CrudRepository<Pipeline, Long> {

    // Fetch all pipelines that are not deleted
    @Query("SELECT p FROM Pipeline p WHERE p.deleted = false")
    List<Pipeline> findAllPipelinesNotDeleted();

    // Fetch a pipeline by its ID, only if it's not deleted
    @Query("SELECT p FROM Pipeline p WHERE p.id = :pipelineId AND p.deleted = false")
    Pipeline findPipelineByIdAndNotDeleted(@Param("pipelineId") long pipelineId);

}