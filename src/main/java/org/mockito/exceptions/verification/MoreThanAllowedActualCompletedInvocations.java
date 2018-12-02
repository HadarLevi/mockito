/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.mockito.exceptions.verification;

import org.mockito.exceptions.base.MockitoAssertionError;

public class MoreThanAllowedActualCompletedInvocations extends MockitoAssertionError {
    public MoreThanAllowedActualCompletedInvocations(String message) {
        super(message);
    }
}

