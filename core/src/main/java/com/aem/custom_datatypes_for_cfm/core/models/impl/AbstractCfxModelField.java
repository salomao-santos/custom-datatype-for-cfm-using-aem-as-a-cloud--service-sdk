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

/**
 * Abstract class representing a custom field model in AEM.
 */
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

    @ValueMapValue
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

    /**
     * Gets the field label.
     *
     * @return the field label
     */
    public String getFieldLabel() {
        return fieldLabel;
    }

    /**
     * Gets the field description.
     *
     * @return the field description
     */
    public String getFieldDescription() {
        return fieldDescription;
    }

    /**
     * Gets the name of the field.
     *
     * @return the name of the field
     */
    public String getName() {
        return name == null ? "" : name;
    }

    /**
     * Gets the label of the field.
     *
     * @return the label of the field
     */
    public String getLabel() {
        return fieldLabel == null ? "" : (required ? fieldLabel + " *" : fieldLabel);
    }

    /**
     * Gets the string value of the field.
     *
     * @return the string value of the field
     */
    public String getStringValue() {
        final Object valueObject = getValue();
        return valueObject == null ? "" : valueObject.toString();
    }

    /**
     * Gets the value of the field.
     *
     * @return the value of the field
     */
    @Override
    public String getValue() {
        return value == null ? "" : value;
    }

    /**
     * Checks if the field is required.
     *
     * @return true if the field is required, false otherwise
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Checks if the field is multiple.
     *
     * @return true if the field is multiple, false otherwise
     */
    public boolean isMultiple() {
        return valueType.contains("[]");
    }

    /**
     * Gets the containing content fragment resource.
     *
     * @return the containing content fragment resource
     */
    protected Resource getContainingContentFragmentResource() {
        return resolver.getResource(getContainingContentFragmentPath());
    }

    /**
     * Gets the containing content fragment path.
     *
     * @return the containing content fragment path
     */
    protected String getContainingContentFragmentPath() {
        SlingHttpServletRequest request = this.request;
        if (request != null) {
            return request.getRequestPathInfo().getSuffix();
        } else {
            return null;
        }
    }

    /**
     * Gets the resource resolver.
     *
     * @return the resource resolver
     */
    protected ResourceResolver getResolver() {
        return resolver;
    }

    /**
     * Gets the resource.
     *
     * @return the resource
     */
    protected Resource getResource() {
        return resource;
    }

    /**
     * Gets the Sling HTTP servlet request.
     *
     * @return the Sling HTTP servlet request
     */
    protected SlingHttpServletRequest getRequest() {
        return request;
    }

    /**
     * Gets the base name for the resource.
     *
     * @return the base name for the resource
     */
    public String getNameBase() {
        return "./content/items/" + getResource().getName();
    }

    /**
     * Gets the exported type of the component.
     *
     * @return the exported type of the component
     */
    @NotNull
    @Override
    public String getExportedType() {
        return resource.getResourceType();
    }
}