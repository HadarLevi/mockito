package org.mockito.internal.verification;

import org.mockito.internal.verification.api.VerificationData;
import org.mockito.verification.VerificationCompletionMode;
import org.mockito.verification.VerificationMode;

import static org.mockito.internal.exceptions.Reporter.NotImplementingVerificationCompletionMode;

public class VerificationWrapperAtCompletion implements VerificationMode{
    private final VerificationMode delegate;

    public VerificationWrapperAtCompletion(VerificationWrapper<?> verificationWrapper, boolean waitUntilCompletion) {
        VerificationMode verificationMode = verificationWrapper.wrappedVerification;
        VerificationMode atCompletionWrappedVerificationMode = wrapAtCompletion(verificationWrapper, verificationMode, waitUntilCompletion);
        delegate = verificationWrapper.copySelfWithNewVerificationMode(atCompletionWrappedVerificationMode);
    }

    @Override
    public void verify(VerificationData data) {
        delegate.verify(data);
    }

    @Override
    public VerificationMode description(String description) {
        return VerificationModeFactory.description(this, description);
    }

    private VerificationMode wrapAtCompletion(VerificationWrapper<?> verificationWrapper, VerificationMode verificationMode, boolean waitUntilCompletion) {
        if (verificationMode instanceof VerificationCompletionMode) {
            final VerificationCompletionMode verificationCompletionMode = (VerificationCompletionMode) verificationMode;
            return new AtCompletionWrapper(verificationCompletionMode, waitUntilCompletion);
        }
        if (verificationMode instanceof VerificationOverTimeImpl) {
            final VerificationOverTimeImpl verificationOverTime = (VerificationOverTimeImpl) verificationMode;
            if (verificationOverTime.getDelegate() instanceof VerificationCompletionMode) {
                return new VerificationOverTimeImpl(verificationOverTime.getPollingPeriodMillis(),
                    verificationOverTime.getTimer().duration(),
                    wrapAtCompletion(verificationWrapper, verificationOverTime.getDelegate(),false),
                    verificationOverTime.isReturnOnSuccess());
            }
        }
        throw NotImplementingVerificationCompletionMode(verificationMode);
    }

}
