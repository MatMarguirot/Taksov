package com.mat.taksov.workout.service;

import com.mat.taksov.workout.dto.ExerciseDto;
import com.mat.taksov.workout.exception.ExerciseNotFoundException;
import com.mat.taksov.workout.exception.MuscleGroupNotFoundException;
import com.mat.taksov.workout.model.MuscleGroup;
import com.mat.taksov.workout.repository.MuscleGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class MuscleGroupService {
    private final MuscleGroupRepository muscleGroupRepository;

    public MuscleGroup findById(String id) {
        return muscleGroupRepository.findById(id).orElseThrow();
    }

    public MuscleGroup save(MuscleGroup muscleGroup) {
        return muscleGroupRepository.save(muscleGroup);
    }

    public void deleteById(String id) {
        muscleGroupRepository.deleteById(id);
    }

    public List<MuscleGroup> findAll() {
        return muscleGroupRepository.findAll();
    }


    /*
    * MODEL RETURNING
     */
    public MuscleGroup findMuscleGroupModelById(String id) {
        return muscleGroupRepository.findById(id).orElseThrow();
    }

    public MuscleGroup findMuscleGroupModelByName(String name) {
        return muscleGroupRepository.findByName(name).orElseThrow(MuscleGroupNotFoundException::new);
    }

    public List<MuscleGroup> findAllMuscleGroupModel() {
        return muscleGroupRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public MuscleGroup findOrCreateMuscleGroupModelByName(String muscleGroupName) {
        // si lo encuentra lo obtiene
        if(muscleGroupRepository.existsByName(muscleGroupName)) return muscleGroupRepository.findByName(muscleGroupName).orElseThrow(ExerciseNotFoundException::new);

        //crea nuevo muscleGroup
        MuscleGroup muscleGroup = new MuscleGroup();
        muscleGroup.setName(muscleGroupName);
        MuscleGroup persistedMuscleGroup = muscleGroupRepository.save(muscleGroup); // deberia retornar lo correcto
        return persistedMuscleGroup;
//        return exerciseRepository.save(exercise);
    }


//    public MuscleGroup saveMuscleGroupModel(MuscleGroup muscleGroup) {
//        return muscleGroupRepository.save(muscleGroup);
//    }

//    public void deleteMuscleGroupModelById(String id) {
//        muscleGroupRepository.deleteById(id);
//    }

}
