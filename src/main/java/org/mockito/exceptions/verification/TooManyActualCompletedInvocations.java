package org.mockito.exceptions.verification;

import org.mockito.exceptions.base.MockitoAssertionError;

public class TooManyActualCompletedInvocations extends MockitoAssertionError {

    public TooManyActualCompletedInvocations(String message) {
        super(message);
    }
}
