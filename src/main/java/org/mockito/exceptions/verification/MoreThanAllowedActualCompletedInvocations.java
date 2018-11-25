package org.mockito.exceptions.verification;

import org.mockito.exceptions.base.MockitoAssertionError;

public class MoreThanAllowedActualCompletedInvocations extends MockitoAssertionError {
    public MoreThanAllowedActualCompletedInvocations(String message) {
        super(message);
    }
}
