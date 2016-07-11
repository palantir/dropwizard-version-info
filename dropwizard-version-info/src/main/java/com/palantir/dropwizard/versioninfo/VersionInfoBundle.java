/*
 * Copyright 2015 Palantir Technologies, Inc. All rights reserved.
 */

package com.palantir.versioninfo;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Bundle to read product version from a file and expose it as a resource.
 *
 * @author sfan
 */
public final class VersionInfoBundle implements Bundle {

    private static final Logger LOGGER = LoggerFactory.getLogger(VersionInfoBundle.class);
    private static final String DEFAULT_PATH = "version.properties";
    private static final String UNKNOWN = "unknown";

    private final String version;

    public VersionInfoBundle() {
        this(DEFAULT_PATH);
    }

    public VersionInfoBundle(String path) {
        checkArgument(!Strings.isNullOrEmpty(path));

        this.version = readVersion(path);
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
        // do nothing
    }

    @Override
    public void run(Environment environment) {
        checkNotNull(environment);

        environment.jersey().register(new VersionInfoResource(this.version));
    }

    public static String readVersion(String resourcePath) {
        Properties properties = new Properties();
        try {
            URL url = Resources.getResource(resourcePath);
            InputStream versionProperties = Resources.asByteSource(url).openStream();
            properties.load(versionProperties);
        } catch (IOException | IllegalArgumentException e) {
            LOGGER.warn("Could not read properties file '" + resourcePath + "'.", e);
        }
        return properties.getProperty("productVersion", UNKNOWN);
    }
}
