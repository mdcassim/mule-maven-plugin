/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.tools.maven.plugin.mule;


import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.maven.plugin.logging.Log;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

public abstract class AbstractApi
{

    protected final Log log;

    public AbstractApi(Log log)
    {
        this.log = log;
    }

    protected WebTarget getTarget(String uri, String path)
    {
        ClientBuilder builder = ClientBuilder.newBuilder();
        configureSecurityContext(builder);
        Client client = builder.build().register(MultiPartFeature.class);
        if (log != null && log.isDebugEnabled())
        {
            client.register(new ApiLoggingFilter(log));
        }

        return client.target(uri).path(path);
    }

    protected void configureSecurityContext(ClientBuilder builder)
    {
        // Implemented in concrete classes
    }

    protected Response post(String uri, String path, Entity entity)
    {
        return builder(uri, path).post(entity);
    }

    protected Response post(String uri, String path, Object entity)
    {
        return post(uri, path, Entity.entity(entity, APPLICATION_JSON_TYPE));
    }

    protected Response put(String uri, String path, Entity entity)
    {
        return builder(uri, path).put(entity);
    }

    protected Response put(String uri, String path, Object entity)
    {
        return put(uri, path, Entity.entity(entity, APPLICATION_JSON_TYPE));
    }

    protected Response delete(String uri, String path)
    {
        return builder(uri, path).delete();
    }

    protected Response get(String uri, String path)
    {
        return builder(uri, path).get();
    }

    protected <T> T get(String uri, String path, Class<T> clazz)
    {
        return get(uri, path).readEntity(clazz);
    }

    private Invocation.Builder builder(String uri, String path)
    {
        WebTarget target = getTarget(uri, path);
        Invocation.Builder builder = target.request(APPLICATION_JSON_TYPE);
        configureRequest(builder);
        return builder;
    }

    /**
     * Template method to allow subclasses to configure the request (adding headers for example).
     */
    protected void configureRequest(Invocation.Builder builder)
    {

    }

}
