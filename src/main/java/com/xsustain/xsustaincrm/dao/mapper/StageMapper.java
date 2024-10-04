package com.xsustain.xsustaincrm.dao.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.xsustain.xsustaincrm.dto.StageDto;
import com.xsustain.xsustaincrm.model.Stage;
import com.xsustain.xsustaincrm.model.User;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Component
@Getter
public class StageMapper {

    @Autowired
    private ModelMapper modelMapper;

    // convert Stage Jpa Entity into StageDto
    public StageDto mapToStageDto(Stage stage) {
        return modelMapper.map(stage, StageDto.class);
    }

    // convert StageDto Entity into Stage
    public Stage mapToStage(StageDto stageDto) {
        return modelMapper.map(stageDto, Stage.class);
    }

    public List<StageDto> mapToStagesDto(List<Stage> stagesDto) {
        List<StageDto> tempList = new ArrayList<>();

        for (Stage stage : stagesDto) {
            tempList.add(this.mapToStageDto(stage));
        }
        return tempList;
    }

    public List<Stage> mapToStages(List<StageDto> stagesDto) {
        List<Stage> tempList = new ArrayList<>();

        for (StageDto stageDto : stagesDto) {
            tempList.add(this.mapToStage(stageDto));
        }
        return tempList;
    }

}
