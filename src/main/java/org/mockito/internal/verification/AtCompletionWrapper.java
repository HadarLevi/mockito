package org.mockito.internal.verification;

import org.mockito.internal.verification.api.VerificationData;
import org.mockito.verification.VerificationCompletionMode;
import org.mockito.verification.VerificationMode;

public class AtCompletionWrapper implements VerificationMode, VerificationCompletionMode {
    private final VerificationCompletionMode mode;



    public AtCompletionWrapper(VerificationCompletionMode mode) {
        this.mode = mode;
    }

    public void verify(VerificationData data) {
            mode.verifyCompletion(data);
    }

    @Override
    public VerificationMode description(String description) {
        return VerificationModeFactory.description(this, description);
    }

    @Override
    public void verifyCompletion(VerificationData data) {
        mode.verifyCompletion(data);
    }
}
