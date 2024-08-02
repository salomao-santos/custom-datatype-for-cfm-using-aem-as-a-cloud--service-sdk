package com.aem.custom_datatypes_for_cfm.core.models;

import org.osgi.annotation.versioning.ConsumerType;

/**
 * Interface representing a picture field model in AEM.
 */
@ConsumerType
public interface CfxPictureField extends CfxModelField {

    /**
     * Checks if the picture field is disabled.
     *
     * @return true if the picture field is disabled, false otherwise
     */
    Boolean getDisabled();

    /**
     * Gets the validation string for the picture field.
     *
     * @return the validation string
     */
    String getValidation();

    /**
     * Gets the thumbnail URL for the picture field.
     *
     * @return the thumbnail URL
     */
    String getThumbnail();
}