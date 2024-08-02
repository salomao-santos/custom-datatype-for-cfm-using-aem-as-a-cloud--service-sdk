package com.aem.custom_datatypes_for_cfm.core.models.impl;

import com.aem.custom_datatypes_for_cfm.core.models.CfxPictureField;
import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Implementation of the CfxPictureField interface.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class},
        adapters = {CfxPictureField.class, ComponentExporter.class},
        resourceType = {CfxPictureFieldImpl.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class CfxPictureFieldImpl extends AbstractCfxModelField implements CfxPictureField {

    static final String RESOURCE_TYPE = "custom_datatypes_for_cfm/editor/components/form/cfx-picture-field";

    @ValueMapValue
    @Default(booleanValues = false)
    private Boolean disabled;

    @ValueMapValue
    @Default(values = "")
    private String validation;

    @JsonIgnore
    @SlingObject
    private Resource resource;

    /**
     * Gets the disabled status of the picture field.
     *
     * @return true if the picture field is disabled, false otherwise
     */
    @Override
    public Boolean getDisabled() {
        return disabled;
    }

    /**
     * Gets the validation string for the picture field.
     *
     * @return the validation string
     */
    @Override
    public String getValidation() {
        return validation;
    }

    /**
     * Gets the thumbnail URL for the picture field.
     *
     * @return the thumbnail URL
     */
    @Override
    public String getThumbnail() {
        if (StringUtils.isBlank(getStringValue())) {
            return null;
        }
        Resource resource = getResolver().getResource(getStringValue());
        if (resource != null) {
            Asset asset = resource.adaptTo(Asset.class);
            if (asset != null) {
                Rendition rendition = asset.getRendition("cq5dam.thumbnail.140.100.png");
                if (rendition != null) {
                    return rendition.getPath();
                }
            }
        }
        return getStringValue();
    }

    /**
     * Gets the resource resolver.
     *
     * @return the resource resolver
     */
    @Override
    protected ResourceResolver getResolver() {
        return super.getResolver();
    }

    /**
     * Gets the resource.
     *
     * @return the resource
     */
    @Override
    protected Resource getResource() {
        return super.getResource();
    }

    /**
     * Gets the Sling HTTP servlet request.
     *
     * @return the Sling HTTP servlet request
     */
    @Override
    protected SlingHttpServletRequest getRequest() {
        return super.getRequest();
    }

    /**
     * Gets the base name for the resource.
     *
     * @return the base name for the resource
     */
    @Override
    public String getNameBase() {
        return super.getNameBase();
    }

    /**
     * Gets the exported type of the component.
     *
     * @return the exported type of the component
     */
    @Override
    public String getExportedType() {
        return RESOURCE_TYPE;
    }
}