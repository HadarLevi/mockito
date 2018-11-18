package org.mockito.internal.verification;

import org.mockito.exceptions.base.MockitoAssertionError;
import org.mockito.internal.util.Timer;
import org.mockito.internal.verification.api.VerificationData;
import org.mockito.verification.VerificationCompletionMode;
import org.mockito.verification.VerificationMode;

import static org.mockito.internal.exceptions.Reporter.NotImplementingVerificationCompletionMode;

public class VerificationAtCompletionOverTime extends VerificationOverTimeImpl{

    public VerificationAtCompletionOverTime(long pollingPeriodMillis, long durationMillis, VerificationCompletionMode delegate, boolean returnOnSuccess) {
        super(pollingPeriodMillis, durationMillis, delegate, returnOnSuccess);
    }

    public VerificationAtCompletionOverTime(long pollingPeriodMillis, VerificationCompletionMode delegate, boolean returnOnSuccess, Timer timer){
        super(pollingPeriodMillis, delegate, returnOnSuccess, timer);
    }

    @Override
    public void verify(VerificationData data) {
        AssertionError error = null;
        final Timer timer=getTimer();
        timer.start();
        while (timer.isCounting()) {
            try {
                if(!(getDelegate() instanceof VerificationCompletionMode))
                    throw NotImplementingVerificationCompletionMode(getDelegate());
                ((VerificationCompletionMode)getDelegate()).verifyCompletion(data);
                if (isReturnOnSuccess()) {
                    return;
                } else {
                    error = null;
                }
            } catch (MockitoAssertionError e) {
                error = handleVerifyException(e);
            }
            catch (AssertionError e) {
                error = handleVerifyException(e);
            }
        }
        if (error != null) {
            throw error;
        }
    }

    @Override
    public VerificationAtCompletionOverTime copyWithVerificationMode(VerificationMode verificationMode) {
        if(verificationMode instanceof VerificationCompletionMode)
            return new VerificationAtCompletionOverTime(getPollingPeriodMillis(), getTimer().duration(), (VerificationCompletionMode)verificationMode, isReturnOnSuccess());
        throw NotImplementingVerificationCompletionMode(verificationMode);
    }

    @Override
    public VerificationMode description(String description) {
        return VerificationModeFactory.description(this, description);
    }

}
