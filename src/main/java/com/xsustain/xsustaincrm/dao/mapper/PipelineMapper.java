package com.xsustain.xsustaincrm.dao.mapper;

import com.xsustain.xsustaincrm.dto.*;
import com.xsustain.xsustaincrm.model.Pipeline;
import com.xsustain.xsustaincrm.model.Ticket;
import com.xsustain.xsustaincrm.model.User;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class PipelineMapper {

    @Autowired
    private ModelMapper modelMapper;

    // Convert Pipeline entity into PipelineDto
    public PipelineDto mapToPipelineDto(Pipeline pipeline) {
        return modelMapper.map(pipeline, PipelineDto.class);
    }

    // Convert PipelineDto into Pipeline entity
    public Pipeline mapToPipeline(PipelineDto pipelineDto) {
        return modelMapper.map(pipelineDto, Pipeline.class);
    }

    // Convert list of Pipeline entities into list of PipelineDtos
    public List<PipelineDto> mapToPipelinesDto(List<Pipeline> pipelines) {
        List<PipelineDto> tempList = new ArrayList<>();

        for (Pipeline pipeline : pipelines) {
            tempList.add(this.mapToPipelineDto(pipeline));
        }
        return tempList;
    }

    // Convert list of PipelineDtos into list of Pipeline entities
    public List<Pipeline> mapToPipelines(List<PipelineDto> pipelineDtos) {
        List<Pipeline> tempList = new ArrayList<>();

        for (PipelineDto pipelineDto : pipelineDtos) {
            tempList.add(this.mapToPipeline(pipelineDto));
        }
        return tempList;
    }
}
