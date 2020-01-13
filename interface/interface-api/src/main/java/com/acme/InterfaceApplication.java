package com.acme;

import com.kumuluz.ee.cors.annotations.CrossOrigin;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("v1")
@CrossOrigin
public class InterfaceApplication extends Application {}
