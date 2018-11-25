package org.mockito.internal.verification;

import org.mockito.internal.util.Timer;
import org.mockito.verification.VerificationCompletionMode;
import org.mockito.verification.VerificationMode;

import static org.mockito.internal.exceptions.Reporter.NotImplementingVerificationCompletionMode;

public class VerificationAtCompletionOverTime extends VerificationOverTimeImpl{

    public VerificationAtCompletionOverTime (VerificationOverTimeImpl verificationOverTime){
        super(verificationOverTime.getPollingPeriodMillis(), verificationOverTime.getDelegate(), verificationOverTime.isReturnOnSuccess(), verificationOverTime.getTimer());
    }

    public VerificationAtCompletionOverTime(long pollingPeriodMillis, long durationMillis, VerificationMode delegate, boolean returnOnSuccess) {
        super(pollingPeriodMillis, durationMillis, delegate, returnOnSuccess);
    }

    public VerificationAtCompletionOverTime(long pollingPeriodMillis, VerificationMode delegate, boolean returnOnSuccess, Timer timer) {
        super(pollingPeriodMillis, delegate, returnOnSuccess, timer);
    }

    @Override
    public VerificationOverTimeImpl copyWithVerificationMode(VerificationMode verificationMode) {
        if(verificationMode instanceof VerificationCompletionMode)
            return new VerificationAtCompletionOverTime(getPollingPeriodMillis(), getTimer().duration(), new AtCompletionWrapper((VerificationCompletionMode)verificationMode), isReturnOnSuccess());
        throw NotImplementingVerificationCompletionMode(verificationMode);
    }

}
