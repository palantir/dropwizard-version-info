/*
 * Copyright 2015 Palantir Technologies, Inc. All rights reserved.
 */

package com.palantir.dropwizard.versioninfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link VersionInfoResource}.
 *
 * @author sfan
 */
public final class VersionInfoResourceTests {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testVersion() {
        String version = "test-version";
        VersionInfoResource versionInfoResource = new VersionInfoResource(version);
        assertEquals(version, versionInfoResource.getVersion());
    }

    @Test
    public void testInvalidVersion() {
        thrown.expect(IllegalArgumentException.class);
        assertNull(new VersionInfoResource(null));
    }
}
