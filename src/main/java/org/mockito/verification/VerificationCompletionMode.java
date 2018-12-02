/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.mockito.verification;

import org.mockito.internal.verification.api.VerificationData;

public interface VerificationCompletionMode extends VerificationMode {

    void verifyCompletion(VerificationData data);
}
