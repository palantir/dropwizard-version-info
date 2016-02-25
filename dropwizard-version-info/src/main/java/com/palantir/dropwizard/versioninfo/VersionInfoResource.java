/*
 * Copyright 2015 Palantir Technologies, Inc. All rights reserved.
 */

package com.palantir.dropwizard.versioninfo;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.Strings;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
* Resource to display version information.
*
* @author sfan
*/
@Path("/version")
@Produces(MediaType.TEXT_PLAIN)
public final class VersionInfoResource {

    private final String version;

    public VersionInfoResource(String version) {
        checkArgument(!Strings.isNullOrEmpty(version));

        this.version = version;
    }

    @GET
    public String getVersion() {
        return version;
    }
}
