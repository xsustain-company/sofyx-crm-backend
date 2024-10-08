package com.xsustain.xsustaincrm.service.Impl;

import com.xsustain.xsustaincrm.dao.mapper.PipelineMapper;
import com.xsustain.xsustaincrm.dao.mapper.StageMapper;
import com.xsustain.xsustaincrm.dto.PipelineDto;
import com.xsustain.xsustaincrm.model.Pipeline;
import com.xsustain.xsustaincrm.repository.PipelineRepository;
import com.xsustain.xsustaincrm.service.PipelineIService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PipelineService implements PipelineIService {

    @Autowired
    PipelineRepository pipelineRepository;

    @Autowired
    PipelineMapper pipelineMapper;

    @Autowired
    StageMapper stageMapper;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public PipelineDto createPipeline(PipelineDto pipelineDto) {
        Pipeline pipeline = modelMapper.map(pipelineDto, Pipeline.class);
        pipeline.setDeleted(false);
        pipelineRepository.save(pipeline);
        return pipelineDto;
    }

    @Override
    public PipelineDto updatePipeline(PipelineDto pipelineDto, long idPipeline) {
        Pipeline pipeline = pipelineRepository.findById(idPipeline)
                .orElseThrow(() -> new RuntimeException("Pipeline not found"));
        pipeline.setPipelineName(pipelineDto.getPipelineName());
        pipeline.setNumberOfDeals(pipelineDto.getNumberOfDeals());
        pipeline.setStatus(pipelineDto.getStatus());
        pipeline.setStages(stageMapper.mapToStages(pipelineDto.getStages()));
        pipeline.setCurrentStage(stageMapper.mapToStage(pipelineDto.getCurrentStage()));
        pipeline.setTotalDealValue(pipelineDto.getTotalDealValue());
        pipelineRepository.save(pipeline);
        return pipelineDto;
    }

    @Override
    public List<PipelineDto> getAllPipelinesNotDeleted() {
        return pipelineMapper.mapToPipelinesDto(pipelineRepository.findAllPipelinesNotDeleted());
    }

    @Override
    public PipelineDto getOnePipeline(long idPipeline) {
        Pipeline pipeline = pipelineRepository.findById(idPipeline)
                .orElseThrow(() -> new RuntimeException("Pipeline not found"));
        return pipelineMapper.mapToPipelineDto(pipeline);
    }

    @Override
    public PipelineDto deletePipeline(long idPipeline) {
        Pipeline pipeline = pipelineRepository.findById(idPipeline)
                .orElseThrow(() -> new RuntimeException("Pipeline not found"));
        pipeline.setDeleted(true);
        pipelineRepository.save(pipeline);
        return pipelineMapper.mapToPipelineDto(pipeline);
    }
}
