package com.xsustain.xsustaincrm.service.Impl;

import com.xsustain.xsustaincrm.dao.mapper.StageMapper;
import com.xsustain.xsustaincrm.dto.StageDto;
import com.xsustain.xsustaincrm.model.Stage;
import com.xsustain.xsustaincrm.repository.StageRepository;
import com.xsustain.xsustaincrm.service.StageIService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StageService implements StageIService {

    @Autowired
    StageRepository stageRepository;

    @Autowired
    StageMapper stageMapper;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public StageDto createStage(StageDto stageDto) {
        Stage stage = modelMapper.map(stageDto, Stage.class);
        stage.setDeleted(false);
        Stage stagesave = stageRepository.save(stage);
        // get the saved stage in dtabase

        return stageMapper.mapToStageDto(stagesave);
    }

    @Override
    public StageDto updateStage(StageDto stageDto, long idStage) {
        Stage stage = stageRepository.findById(idStage).orElseThrow(() -> new RuntimeException("Stage not found"));
        stage.setTitle(stageDto.getTitle());
        stage.setDescription(stageDto.getDescription());
        stageRepository.save(stage);
        return stageDto;
    }

    @Override
    public List<StageDto> getAllStages() {
        return stageMapper.mapToStagesDto(stageRepository.findAllStages());
    }

    @Override
    public StageDto getOneStage(long idStage) {
        Stage stage = stageRepository.findById(idStage).orElseThrow(() -> new RuntimeException("Stage not found"));
        return stageMapper.mapToStageDto(stage);
    }

    @Override
    public StageDto deleteStage(long idStage) {
        Stage stage = stageRepository.findById(idStage).orElseThrow(() -> new RuntimeException("Stage not found"));
        stage.setDeleted(true);
        stageRepository.save(stage);
        return stageMapper.mapToStageDto(stage);
    }
}
