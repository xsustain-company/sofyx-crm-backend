package com.xsustain.xsustaincrm.controller;

import com.xsustain.xsustaincrm.dto.StageDto;
import com.xsustain.xsustaincrm.model.Stage;
import com.xsustain.xsustaincrm.service.StageIService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stage/")
public class StageController {

    @Autowired
    StageIService stageIService;

    @PostMapping("/create")
    public StageDto createStage(@Valid @RequestBody StageDto stageDto) {
        return stageIService.createStage(stageDto);
    }

    @PutMapping("/update/{idStage}")
    public StageDto updateStage(@Valid @RequestBody StageDto stageDto, @PathVariable long idStage) {
        return stageIService.updateStage(stageDto, idStage);
    }

    @GetMapping("/all")
    public List<StageDto> getAllStages() {
        return stageIService.getAllStages();
    }

    @GetMapping("/get/{idStage}")
    public StageDto getOneStage(@PathVariable long idStage) {
        return stageIService.getOneStage(idStage);
    }

    @DeleteMapping("/delete/{idStage}")
    public StageDto deleteStage(@PathVariable long idStage) {
        return stageIService.deleteStage(idStage);
    }
}
