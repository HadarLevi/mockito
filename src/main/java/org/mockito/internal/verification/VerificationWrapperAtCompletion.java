/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.mockito.internal.verification;

import org.mockito.internal.verification.api.VerificationData;
import org.mockito.verification.VerificationCompletionMode;
import org.mockito.verification.VerificationMode;

import static org.mockito.internal.exceptions.Reporter.NotImplementingVerificationCompletionMode;

public class VerificationWrapperAtCompletion implements VerificationMode{
    private final VerificationMode delegate;

    public VerificationWrapperAtCompletion(VerificationWrapper<?> verificationWrapper) {
        VerificationMode verificationMode = verificationWrapper.wrappedVerification;
        delegate = wrapAtCompletion( verificationMode);
    }

    @Override
    public void verify(VerificationData data) {
        delegate.verify(data);
    }

    @Override
    public VerificationMode description(String description) {
        return VerificationModeFactory.description(this, description);
    }

    public static VerificationMode wrapAtCompletion( VerificationMode verificationMode) {
        if (verificationMode instanceof VerificationCompletionMode) {
            return new AtCompletionWrapper((VerificationCompletionMode)verificationMode);
        }
        if (verificationMode instanceof VerificationOverTimeImpl) {
            final VerificationOverTimeImpl verificationOverTime = (VerificationOverTimeImpl) verificationMode;
             return new VerificationAtCompletionOverTime(verificationOverTime.copyWithVerificationMode(wrapAtCompletion(verificationOverTime.getDelegate())));
        }
        if (verificationMode instanceof VerificationAtCompletionOverTime)
            return verificationMode;

        throw NotImplementingVerificationCompletionMode(verificationMode);
    }

}
