package com.acme;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.kumuluz.ee.discovery.annotations.RegisterService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@RegisterService
@ApplicationPath("v1")
@CrossOrigin
public class CommentApplication extends Application {}
