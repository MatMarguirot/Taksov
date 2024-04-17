package com.mat.taksov.workout.dto.mapper;

import com.mat.taksov.workout.dto.ExerciseSet.ExerciseSetCreateRequest;
import com.mat.taksov.workout.dto.ExerciseSet.ExerciseSetResponse;
import com.mat.taksov.workout.model.Exercise;
import com.mat.taksov.workout.model.ExerciseSet;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ExerciseSetMapper {
    private final ModelMapper modelMapper;

    public ExerciseSet toExerciseSet(ExerciseSetCreateRequest exerciseSetCreateRequest) {
        ExerciseSet exerciseSet = modelMapper.map(exerciseSetCreateRequest, ExerciseSet.class);
//        exerciseSet.setExercise(exercise);
//        exerciseSet.updateMuscleGroup();
        return exerciseSet;
    }

    // para actualizacion de WorkoutSession
    public ExerciseSet toExerciseSet(ExerciseSetCreateRequest exerciseSetCreateRequest, Exercise exercise) {
        ExerciseSet exerciseSet = modelMapper.map(exerciseSetCreateRequest, ExerciseSet.class);
        exerciseSet.setExercise(exercise);
//        exerciseSet.updateMuscleGroup();
        return exerciseSet;
    }

//    public ExerciseSet toExerciseBlock(
//            ExerciseSetCreateRequest exerciseBlockCreateRequest,
//            String workoutId,
//            String userId
//    ) {
//        ExerciseSet exerciseBlock = modelMapper.map(exerciseBlockCreateRequest, ExerciseSet.class);
//        return exerciseBlock;
//    }

    public ExerciseSetResponse toGetExerciseSetResponse(ExerciseSet exerciseSet){
        ExerciseSetResponse exerciseSetResponse = modelMapper.map(exerciseSet, ExerciseSetResponse.class);
        return exerciseSetResponse;
    }

}
