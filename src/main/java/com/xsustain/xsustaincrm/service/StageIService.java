package com.xsustain.xsustaincrm.service;

import com.xsustain.xsustaincrm.dto.StageDto;
import com.xsustain.xsustaincrm.model.Stage;

import java.util.List;

public interface StageIService {

    public StageDto createStage(StageDto stageDto);

    public StageDto updateStage(StageDto stageDto, long idStage);

    public List<StageDto> getAllStages();

    public StageDto getOneStage(long idStage);

    public StageDto deleteStage(long idStage);

}
