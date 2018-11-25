package org.mockito.internal.verification;

import org.mockito.verification.VerificationMode;

public class VerificationCompletionModeFactory {

    public static VerificationMode atLeastOnce() {
        return new AtCompletionWrapper( new AtLeast(1));
    }

    public static VerificationMode atLeast(int minNumberOfInvocations) {
        return new AtCompletionWrapper( new AtLeast(minNumberOfInvocations));
    }

    public static VerificationMode only() {
        return new AtCompletionWrapper(new Only());
    }

    public static VerificationMode times(int wantedNumberOfInvocations) {
        return new AtCompletionWrapper(new Times(wantedNumberOfInvocations));
    }

    public static VerificationMode calls(int wantedNumberOfInvocations) {
        return new AtCompletionWrapper(new Calls( wantedNumberOfInvocations ));
    }

    public static VerificationMode noMoreInteractions() {
        return new AtCompletionWrapper(new NoMoreInteractions());
    }

    public static VerificationMode atMost(int maxNumberOfInvocations) {
        return new AtCompletionWrapper(new AtMost(maxNumberOfInvocations));
    }

    public static VerificationMode description(VerificationMode mode, String description) {
        return new Description(mode, description);
    }
}
