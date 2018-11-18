package org.mockito.verification;

import org.mockito.internal.util.Timer;
import org.mockito.internal.verification.VerificationAtCompletionOverTime;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.internal.verification.VerificationWrapper;

import static org.mockito.internal.exceptions.Reporter.atMostAndNeverShouldNotBeUsedWithTimeout;

public class TimeoutToCompletion extends VerificationWrapper<VerificationAtCompletionOverTime> implements VerificationWithTimeout{

    public TimeoutToCompletion(long millis, VerificationCompletionMode delegate) {
        this(10, millis, delegate);
    }

    TimeoutToCompletion(long pollingPeriodMillis, long millis, VerificationCompletionMode delegate) {
        this(new VerificationAtCompletionOverTime(pollingPeriodMillis, millis, delegate, true));
    }

    TimeoutToCompletion(long pollingPeriodMillis, VerificationCompletionMode delegate, Timer timer) {
        this(new VerificationAtCompletionOverTime(pollingPeriodMillis, delegate, true, timer));
    }

    TimeoutToCompletion(VerificationAtCompletionOverTime verificationAtCompletionOverTime) {
        super(verificationAtCompletionOverTime);
    }

    @Override
    protected VerificationMode copySelfWithNewVerificationMode(VerificationMode newVerificationMode) {
        return new TimeoutToCompletion(wrappedVerification.copyWithVerificationMode(newVerificationMode));
    }

    public VerificationMode atMost(int maxNumberOfInvocations) {
        throw atMostAndNeverShouldNotBeUsedWithTimeout();
    }

    public VerificationMode never() {
        throw atMostAndNeverShouldNotBeUsedWithTimeout();
    }

    @Override
    public VerificationMode description(String description) {
        return VerificationModeFactory.description(this, description);
    }
}
