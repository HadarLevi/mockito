package org.mockito.internal.verification;

import org.mockito.internal.verification.api.VerificationData;
import org.mockito.verification.VerificationCompletionMode;
import org.mockito.verification.VerificationMode;

public class AtCompletionWrapper implements VerificationMode {
    private final VerificationCompletionMode mode;
    private final boolean waitUntilCompletion;


    public AtCompletionWrapper(VerificationCompletionMode mode, boolean waitUntilCompletion) {
        this.mode = mode;
        this.waitUntilCompletion=waitUntilCompletion;
    }

    public void verify(VerificationData data) {
        if(waitUntilCompletion)
            mode.verifyAndWaitUntilCompletion(data);
        else
            mode.verifyCompletion(data);
    }

    @Override
    public VerificationMode description(String description) {
        return VerificationModeFactory.description(this, description);
    }
}
