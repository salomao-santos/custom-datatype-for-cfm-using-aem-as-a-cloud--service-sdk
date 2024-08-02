package com.aem.custom_datatypes_for_cfm.core.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

/**
 * Abstract model for custom field properties in AEM.
 */
public class AbstractCfModelFieldProperty {

    @JsonIgnore
    @SlingObject
    private final Resource resource;

    @JsonIgnore
    @SlingObject
    private final ResourceResolver resolver;

    @JsonIgnore
    @SlingObject
    private final SlingHttpServletRequest request;

    /**
     * Constructor for dependency injection.
     *
     * @param resource the resource
     * @param resolver the resource resolver
     * @param request the Sling HTTP servlet request
     */
    public AbstractCfModelFieldProperty(@SlingObject Resource resource,
                                        @SlingObject ResourceResolver resolver,
                                        @SlingObject SlingHttpServletRequest request) {
        this.resource = resource;
        this.resolver = resolver;
        this.request = request;
    }

    /**
     * Gets the resource.
     *
     * @return the resource
     */
    public Resource getResource() {
        return resource;
    }

    /**
     * Gets the Sling HTTP servlet request.
     *
     * @return the request
     */
    public SlingHttpServletRequest getRequest() {
        return request;
    }

    /**
     * Gets the resource resolver.
     *
     * @return the resolver
     */
    public ResourceResolver getResolver() {
        return resolver;
    }

    /**
     * Gets the base name for the resource.
     *
     * @return the base name
     */
    public String getNameBase() {
        return "./content/items/" + resource.getName();
    }
}