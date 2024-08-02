package com.aem.custom_datatypes_for_cfm.core.models;

import org.osgi.annotation.versioning.ConsumerType;

@ConsumerType
public interface CfxPictureField extends CfxModelField {

    Boolean getDisabled();

    String getValidation();

    String getThumbnail();
}