/*
 * Copyright 2015 Palantir Technologies, Inc. All rights reserved.
 */

package com.palantir.dropwizard.versioninfo;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.common.io.Resources;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.testing.junit.DropwizardAppRule;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * Tests for {@link VersionInfoBundle}.
 */
public final class VersionInfoBundleTests {

    @ClassRule
    public static final DropwizardAppRule<TestConfig> RULE =
            new DropwizardAppRule<TestConfig>(TestApp.class, Resources.getResource("test-app-config.yml").getPath());

    private Environment environment;
    private JerseyEnvironment jerseyEnvironment;
    private Bootstrap<?> bootstrap;

    @Before
    public void setUp() {
        environment = mock(Environment.class);
        jerseyEnvironment = mock(JerseyEnvironment.class);
        when(environment.jersey()).thenReturn(jerseyEnvironment);
        bootstrap = mock(Bootstrap.class);
    }

    @Test
    public void testAddsVersionInfoResource() {
        VersionInfoBundle versionInfoBundle = new VersionInfoBundle();
        versionInfoBundle.initialize(bootstrap);
        versionInfoBundle.run(environment);
        verify(jerseyEnvironment).register(isA(VersionInfoResource.class));
    }

    @Test
    public void testVersionPropertiesFileDoesNotExist() {
        try {
            assertNull(new VersionInfoBundle("filedoesntexist.properties"));
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

    @Test
    public void testInvalidPath() {
        try {
            assertNull(new VersionInfoBundle(null));
            fail();
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
