/*
 * Copyright 2016 Palantir Technologies, Inc. All rights reserved.
 */

package com.palantir.dropwizard.versioninfo.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * interface for obtaining the version info.
 */
@Path("/")
public interface VersionInfoService {

    @GET
    @Path("version")
    @Produces(MediaType.TEXT_PLAIN)
    String getVersion();
}
