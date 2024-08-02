package com.aem.custom_datatypes_for_cfm.core.models.impl;


import com.aem.custom_datatypes_for_cfm.core.models.CfxModelField;

import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


public abstract class AbstractCfxModelField implements CfxModelField {

    @JsonIgnore
    @SlingObject
    private Resource resource;

    @JsonIgnore
    @SlingObject
    private ResourceResolver resolver;

    @JsonIgnore
    @Self(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Nullable
    private SlingHttpServletRequest request;

    @ValueMapValue()
    @Default(values = "")
    private String fieldLabel;

    @ValueMapValue
    @Default(values = "")
    private String fieldDescription;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Nullable
    protected String name;

    @ValueMapValue
    @Default(values = "")
    protected String value;

    @ValueMapValue
    private String valueType;

    @ValueMapValue
    @Default(booleanValues = false)
    private boolean required;

    public String getFieldLabel() {
        return fieldLabel;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }


    public String getName() {

        return name == null ? "" : name;

    }

    public String getLabel() {
        //return fieldLabel == null ? text : fieldLabel;
        return fieldLabel == null ? "" :
            (required ? fieldLabel + " *" : fieldLabel);
    }

    public String getStringValue() {
        final Object valueObject = getValue();
        return valueObject == null ? "" : valueObject.toString();
    }

    @Override
    public String getValue() {
        return value == null ? "" : value;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isMultiple() {

        return valueType.contains("[]");
    }

    protected Resource getContainingContentFragmentResource() {
        return resolver.getResource(getContainingContentFragmentPath());
    }

   
    protected String getContainingContentFragmentPath() {
        SlingHttpServletRequest request = this.request;

        if (request != null) {
            return request.getRequestPathInfo().getSuffix();
        } else {
            return null;
        }
    }

    protected ResourceResolver getResolver() {
        return resolver;
    }

    protected Resource getResource() {
        return resource;
    }

    protected SlingHttpServletRequest getRequest() {
        return request;
    }

    public String getNameBase() {
        return "./content/items/" + getResource().getName();
    }

    @NotNull
    @Override
    public String getExportedType() {
        return resource.getResourceType();
    }

}