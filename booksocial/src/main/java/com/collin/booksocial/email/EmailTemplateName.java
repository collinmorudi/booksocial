package com.collin.booksocial.email;

import lombok.Getter;

/**
 * EmailTemplateName is an enumeration that holds a list of named constants
 * representing different email templates.
 *
 * The enumeration currently includes the following email templates:
 * - ACTIVATE_ACCOUNT: Represents the template for activating an account.
 *
 * This enum provides a constructor to initialize the template name and a
 * getter method to retrieve the assigned name for each template.
 */
@Getter
public enum EmailTemplateName {

    /**
     * Represents the template name for activating an account.
     */
    ACTIVATE_ACCOUNT("activate_account")
    ;


    /**
     * The name of the email template.
     *
     * This field holds the string value of the template name, used for
     * identifying and retrieving the specific template associated with
     * this enumeration constant.
     */
    private final String name;
    /**
     * Constructs an EmailTemplateName with the specified template name.
     *
     * @param name The name assigned to the email template.
     */
    EmailTemplateName(String name) {
        this.name = name;
    }
}
