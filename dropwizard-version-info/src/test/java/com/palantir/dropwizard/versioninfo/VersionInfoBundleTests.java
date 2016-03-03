/*
 * Copyright 2015 Palantir Technologies, Inc. All rights reserved.
 */

package com.palantir.versioninfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.google.common.io.Resources;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.testing.junit.DropwizardAppRule;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.hamcrest.core.IsInstanceOf;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * Tests for {@link VersionInfoBundle}.
 */
public final class VersionInfoBundleTests {

    @ClassRule
    public static final DropwizardAppRule<Configuration> RULE =
            new DropwizardAppRule<Configuration>(TestApp.class, Resources.getResource("test-app-config.yml").getPath());

    @Test
    public void testAddsVersionInfoResource() {
        Environment environment = mock(Environment.class, RETURNS_DEEP_STUBS);

        VersionInfoBundle versionInfoBundle = new VersionInfoBundle();
        versionInfoBundle.initialize(mock(Bootstrap.class));
        versionInfoBundle.run(environment);
        verify(environment.jersey()).register(isA(VersionInfoResource.class));
    }

    @Test
    public void testVersionPropertiesFileDoesNotExist() {
        try {
            new VersionInfoBundle("filedoesntexist.properties");
            fail();
        } catch (RuntimeException e) {
            assertThat(e.getCause(), IsInstanceOf.instanceOf(IllegalArgumentException.class));
            assertEquals(e.getMessage(), "Could not read properties file 'filedoesntexist.properties'.");
        }
    }

    @Test
    public void testReturnsVersionFromDefaultPropertiesFile() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(String.format("http://localhost:%d/version", RULE.getLocalPort()));
        String response = target.request().get(String.class);
        assertEquals(response, "2.0.0");
    }

    @SuppressFBWarnings("NP_NULL_PARAM_DEREF_NONVIRTUAL")
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPath() {
        new VersionInfoBundle(null);
    }
}
