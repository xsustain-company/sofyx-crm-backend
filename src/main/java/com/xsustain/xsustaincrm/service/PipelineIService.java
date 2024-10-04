package com.xsustain.xsustaincrm.service;

import com.xsustain.xsustaincrm.dto.PipelineDto;
import com.xsustain.xsustaincrm.model.Pipeline;

import java.util.List;

public interface PipelineIService {

    public PipelineDto createPipeline(PipelineDto pipelineDto);

    public PipelineDto updatePipeline(PipelineDto pipelineDto, long idPipeline);

    public List<PipelineDto> getAllPipelinesNotDeleted();

    public PipelineDto getOnePipeline(long idPipeline);

    public PipelineDto deletePipeline(long idPipeline);

}
