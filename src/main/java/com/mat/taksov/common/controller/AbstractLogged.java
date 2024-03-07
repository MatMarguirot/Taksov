package com.mat.taksov.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class AbstractLogged {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
//    public final static String BASE_API_PATH = "api/v1/";

    public AbstractLogged(){}
}
