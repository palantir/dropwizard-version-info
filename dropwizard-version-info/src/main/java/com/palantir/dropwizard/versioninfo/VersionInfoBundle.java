/*
 * Copyright 2015 Palantir Technologies, Inc. All rights reserved.
 */

package com.palantir.dropwizard.versioninfo;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Strings;
import com.google.common.io.Resources;
import io.dropwizard.Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Bundle to read product version from a .properties and expose it as a resource.
 *
 * @author sfan
 */
public final class VersionInfoBundle implements Bundle {
    private static final String DEFAULT_PATH = "version.properties";
    private static final String UNKNOWN = "unknown";

    private final String version;

    public VersionInfoBundle() {
        this(DEFAULT_PATH);
    }

    public VersionInfoBundle(String path) {
        checkArgument(!Strings.isNullOrEmpty(path));
        this.version = readResource(path);
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
    }

    @Override
    public void run(Environment environment) {
        checkNotNull(environment);

        environment.jersey().register(new VersionInfoResource(this.version));
    }

    private static String readResource(String resourcePath) {
        Properties properties = new Properties();
        String result;
        try {
            URL url = Resources.getResource(resourcePath);
            InputStream versionProperties = Resources.asByteSource(url).openStream();
            properties.load(versionProperties);
            result = properties.getProperty("productVersion", UNKNOWN);
        } catch (IOException | IllegalArgumentException e) {
            throw new RuntimeException("Could not read properties file '" + resourcePath + "'.", e);
        }
        return result;
    }
}
