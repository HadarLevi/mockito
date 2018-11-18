package org.mockito.exceptions.verification;

import org.mockito.exceptions.base.MockitoAssertionError;

public class InvokedButNotCompleted extends MockitoAssertionError {
    public InvokedButNotCompleted(String message) {
        super(message);
    }
}
