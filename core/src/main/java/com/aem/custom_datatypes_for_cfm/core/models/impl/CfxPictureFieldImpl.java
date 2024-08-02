package com.aem.custom_datatypes_for_cfm.core.models.impl;

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

    @SlingObject
    private Resource resource;

    public Boolean getDisabled() {
        return disabled;
    }

    public String getValidation() {
        return validation;
    }

    public String getThumbnail() {
        if (StringUtils.isBlank(getStringValue())) {
            return null;
        }
        org.apache.sling.api.resource.Resource resource = getResolver().getResource(getStringValue());
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

}