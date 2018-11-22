/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */

package org.mockito.internal.verification;

import java.util.List;
import org.mockito.exceptions.base.MockitoAssertionError;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.internal.verification.api.VerificationData;
import org.mockito.internal.verification.api.VerificationDataInOrder;
import org.mockito.internal.verification.api.VerificationInOrderMode;
import org.mockito.invocation.Invocation;
import org.mockito.invocation.MatchableInvocation;
import org.mockito.verification.TimeoutToCompletion;
import org.mockito.verification.VerificationCompletionMode;
import org.mockito.verification.VerificationMode;

import static org.mockito.internal.verification.checkers.MissingInvocationChecker.checkMissingCompletedInvocation;
import static org.mockito.internal.verification.checkers.MissingInvocationChecker.checkMissingInvocation;
import static org.mockito.internal.verification.checkers.NumberOfInvocationsChecker.checkNumberOfCompletedInvocations;
import static org.mockito.internal.verification.checkers.NumberOfInvocationsChecker.checkNumberOfInvocations;

public class Times implements VerificationInOrderMode, VerificationMode, VerificationCompletionMode {

    final int wantedCount;

    public Times(int wantedNumberOfInvocations) {
        if (wantedNumberOfInvocations < 0) {
            throw new MockitoException("Negative value is not allowed here");
        }
        this.wantedCount = wantedNumberOfInvocations;
    }

    @Override
    public void verify(VerificationData data) {
        List<Invocation> invocations = data.getAllInvocations();
        MatchableInvocation wanted = data.getTarget();

        if (wantedCount > 0) {
             checkMissingInvocation(data.getAllInvocations(), data.getTarget());
        }
        checkNumberOfInvocations(invocations, wanted, wantedCount);
    }
    @Override
    public void verifyInOrder(VerificationDataInOrder data) {
        List<Invocation> allInvocations = data.getAllInvocations();
        MatchableInvocation wanted = data.getWanted();

        if (wantedCount > 0) {
            checkMissingInvocation(allInvocations, wanted, data.getOrderingContext());
        }
        checkNumberOfInvocations(allInvocations, wanted, wantedCount, data.getOrderingContext());
    }

    @Override
    public String toString() {
        return "Wanted invocations count: " + wantedCount;
    }

    @Override
    public VerificationMode description(String description) {
        return VerificationModeFactory.description(this, description);
    }

    @Override
    public void verifyCompletion(VerificationData data){
        verify(data);
        List<Invocation> invocations =data.getAllInvocations();
        MatchableInvocation wanted = data.getTarget();
        if (wantedCount > 0) {
            checkMissingCompletedInvocation(data.getAllInvocations(), data.getTarget());
        }
        checkNumberOfCompletedInvocations(invocations, wanted, wantedCount);
    }

    /*
    verify that the wanted function is called,
    if the wanted function hasn't been called the wanted number of time, this function will throw exception immediately
    if the function has been called the number of times that wanted
    but at least one of the calls hasn't been completed yet
    the function will wait until those missing calls will be completed.
     */
    public void verifyAndWaitUntilCompletion(VerificationData data){
        verify(data);
        boolean completed=false;
        while(!completed) {
            try {
                new TimeoutToCompletion(Long.MAX_VALUE, this).verify(data);
                completed=true;
            }catch (MockitoAssertionError e){}
        }
    }

}
