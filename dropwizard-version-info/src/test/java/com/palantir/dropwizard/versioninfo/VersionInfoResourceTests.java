/*
 * Copyright 2015 Palantir Technologies, Inc. All rights reserved.
 */

package com.palantir.dropwizard.versioninfo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests for {@link VersionInfoResource}.
 *
 * @author sfan
 */
public final class VersionInfoResourceTests {

    @Test
    public void testVersion() {
        String version = "test-version";
        VersionInfoResource versionInfoResource = new VersionInfoResource(version);
        assertEquals(version, versionInfoResource.getVersion());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidVersion() {
        new VersionInfoResource(null);
    }
}
