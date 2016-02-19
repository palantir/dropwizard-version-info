/*
 * Copyright 2015 Palantir Technologies, Inc. All rights reserved.
 */

package com.palantir.dropwizard.versioninfo;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public final class TestApp extends Application<TestConfig> {

    @Override
    public void initialize(Bootstrap<TestConfig> bootstrap) {
        bootstrap.addBundle(new VersionInfoBundle());
    }

    @Override
    public void run(TestConfig configuration, Environment environment) throws Exception {
        // do nothing
    }
}
