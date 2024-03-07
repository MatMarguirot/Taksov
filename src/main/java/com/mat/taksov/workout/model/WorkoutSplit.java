package com.mat.taksov.workout.model;

import com.mat.taksov.user.model.User;
//import com.mat.taksov.workout.model.enums.SplitType;

public interface WorkoutSplit {
    public String getId();
//    public SplitType getSplitType();
    public User getUser();
}
