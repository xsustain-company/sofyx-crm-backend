package com.xsustain.xsustaincrm.controller;

import com.xsustain.xsustaincrm.dto.PipelineDto;
import com.xsustain.xsustaincrm.model.Pipeline;
import com.xsustain.xsustaincrm.service.PipelineIService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pipeline/")
public class PipelineController {

    @Autowired
    PipelineIService pipelineIService;

    @PostMapping("/create")
    public PipelineDto createPipeline(@Valid @RequestBody PipelineDto pipelineDto) {
        return pipelineIService.createPipeline(pipelineDto);
    }

    @PutMapping("/update/{idPipeline}")
    public PipelineDto updatePipeline(@Valid @RequestBody PipelineDto pipelineDto, @PathVariable long idPipeline) {
        return pipelineIService.updatePipeline(pipelineDto, idPipeline);
    }

    @GetMapping("/all")
    public List<PipelineDto> getAllPipelinesNotDeleted() {
        return pipelineIService.getAllPipelinesNotDeleted();
    }

    @GetMapping("/get/{idPipeline}")
    public PipelineDto getOnePipeline(@PathVariable long idPipeline) {
        return pipelineIService.getOnePipeline(idPipeline);
    }

    @DeleteMapping("/delete/{idPipeline}")
    public PipelineDto deletePipeline(@PathVariable long idPipeline) {
        return pipelineIService.deletePipeline(idPipeline);
    }
}
