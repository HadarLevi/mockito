/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.mockito.internal.verification;

import org.mockito.verification.VerificationMode;

public abstract class VerificationCompletionFactoryWrapper<WrapperType extends VerificationMode> extends VerificationWrapper implements VerificationMode {

    public VerificationCompletionFactoryWrapper(WrapperType wrappedVerification) {
        super(wrappedVerification);
    }

    @Override
    public VerificationMode times(int wantedNumberOfInvocations) {
        return copySelfWithNewVerificationMode(VerificationCompletionModeFactory.times(wantedNumberOfInvocations));
    }

    @Override
    public VerificationMode never() {
        return copySelfWithNewVerificationMode(VerificationCompletionModeFactory.atMost(0));
    }

    @Override
    public VerificationMode atLeastOnce() {
        return copySelfWithNewVerificationMode(VerificationCompletionModeFactory.atLeastOnce());
    }

    @Override
    public VerificationMode atLeast(int minNumberOfInvocations) {
        return copySelfWithNewVerificationMode(VerificationCompletionModeFactory.atLeast(minNumberOfInvocations));
    }

    @Override
    public VerificationMode atMost(int maxNumberOfInvocations) {
        return copySelfWithNewVerificationMode(VerificationCompletionModeFactory.atMost(maxNumberOfInvocations));
    }

    @Override
    public VerificationMode only() {
        return copySelfWithNewVerificationMode(VerificationCompletionModeFactory.only());
    }


}
