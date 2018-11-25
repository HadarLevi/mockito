package org.mockito.verification;

import org.mockito.internal.verification.api.VerificationData;

public interface VerificationCompletionMode extends VerificationMode {

    void verifyCompletion(VerificationData data);
}
