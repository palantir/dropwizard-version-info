/*
 * Copyright 2015 Palantir Technologies, Inc. All rights reserved.
 */

package com.palantir.versioninfo;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.Strings;
import com.palantir.dropwizard.versioninfo.api.VersionInfoService;

/**
* Resource to display version information.
*
* @author sfan
*/
public final class VersionInfoResource implements VersionInfoService {

    private final String version;

    public VersionInfoResource(String version) {
        checkArgument(!Strings.isNullOrEmpty(version));

        this.version = version;
    }

    @Override
    public String getVersion() {
        return version;
    }
}
