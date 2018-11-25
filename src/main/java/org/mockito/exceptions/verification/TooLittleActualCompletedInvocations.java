package org.mockito.exceptions.verification;

import org.mockito.exceptions.base.MockitoAssertionError;

public class TooLittleActualCompletedInvocations extends MockitoAssertionError {

    private static final long serialVersionUID = 1L;

    public TooLittleActualCompletedInvocations(String message) {
        super(message);
    }
}
