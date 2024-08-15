(function($) {
    /**
     * Selector for the picture field container.
     * @constant {string}
     */
    const PICTUREFIELD_SELECTOR = ".htl-picturefield";

    /**
     * Selector for the picture field autocomplete.
     * @constant {string}
     */
    const PICTUREFIELD_AUTOCOMPLETE_SELECTOR = PICTUREFIELD_SELECTOR + " foundation-autocomplete";

    /**
     * Allowed picture file extensions.
     * @constant {Array<string>}
     */
    const PICTURE_EXTENSIONS = [".png", ".jpg", ".jpeg", ".svg"];

    let registry = $(window).adaptTo("foundation-registry");

    // Register validation selector
    registry.register("foundation.validation.selector", {
        submittable: PICTUREFIELD_AUTOCOMPLETE_SELECTOR,
        candidate: PICTUREFIELD_AUTOCOMPLETE_SELECTOR,
        exclusion: PICTUREFIELD_AUTOCOMPLETE_SELECTOR + " *"
    });

    // Register validation validator
    registry.register("foundation.validation.validator", {
        selector: PICTUREFIELD_AUTOCOMPLETE_SELECTOR,
        validate: function(el) {
            updateThumbnail(el);

            if (el.required) {
                if (el.multiple && el.values.length === 0) {
                    return Granite.I18n.get("Please fill out this field.");
                } else if (!el.multiple && el.value.length === 0) {
                    return Granite.I18n.get("Please fill out this field.");
                }
            }

            if (!checkExtension(el.value)) {
                return Granite.I18n.get("Please select a valid picture file.");
            }
        }
    });

    // Ensure change events trigger validation
    $(PICTUREFIELD_AUTOCOMPLETE_SELECTOR).change(function() {
        $(this).checkValidity();
    });

    /**
     * Updates the thumbnail image based on the selected picture path.
     * @param {HTMLElement} pictureField - The picture field element.
     */
    function updateThumbnail(pictureField) {
        let picturePath = pictureField.value;
        if (picturePath === undefined) {
            return;
        }
        let $parentHtlpictureField = $(pictureField).parents(PICTUREFIELD_SELECTOR);
        let $pictureElement = $parentHtlpictureField.find('img');

        if (picturePath) {
            $pictureElement.attr('src', picturePath.replace(/&amp;/g, "&"));
        } else {
            $pictureElement.removeAttr('src');
        }
    }

    /**
     * Checks if the given picture path has a valid extension.
     * @param {string} picturePath - The picture path to check.
     * @returns {boolean} True if the extension is valid, false otherwise.
     */
    function checkExtension(picturePath) {
        if (!picturePath) {
            return true;
        }

        for (let i = 0; i < PICTURE_EXTENSIONS.length; i++) {
            if (picturePath.toLowerCase().endsWith(PICTURE_EXTENSIONS[i])) {
                return true;
            }
        }

        return false;
    }

}(jQuery));