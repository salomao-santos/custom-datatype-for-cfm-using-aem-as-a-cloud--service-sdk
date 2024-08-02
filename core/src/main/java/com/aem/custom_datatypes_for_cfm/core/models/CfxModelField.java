package com.aem.custom_datatypes_for_cfm.core.models;

import com.adobe.cq.export.json.ComponentExporter;
import com.drew.lang.annotations.NotNull;
import org.osgi.annotation.versioning.ConsumerType;

@ConsumerType
public interface CfxModelField extends ComponentExporter {

    Object getValue();

    String getStringValue();

    @NotNull
    @Override
    default String getExportedType() {
        return "";
    }

}