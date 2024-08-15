package com.aem.custom_datatypes_for_cfm.core.models;

import com.adobe.cq.export.json.ComponentExporter;
import com.drew.lang.annotations.NotNull;
import org.osgi.annotation.versioning.ConsumerType;

/**
 * Interface representing a custom field model in AEM.
 */
@ConsumerType
public interface CfxModelField extends ComponentExporter {

    /**
     * Gets the value of the custom field.
     *
     * @return the value of the custom field
     */
    Object getValue();

    /**
     * Gets the string representation of the custom field value.
     *
     * @return the string value of the custom field
     */
    String getStringValue();

    /**
     * Gets the exported type of the component.
     *
     * @return the exported type
     */
    @NotNull
    @Override
    default String getExportedType() {
        return "";
    }
}