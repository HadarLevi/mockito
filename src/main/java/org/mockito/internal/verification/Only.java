/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.mockito.internal.verification;

import java.util.List;
import org.mockito.internal.verification.api.VerificationData;
import org.mockito.invocation.Invocation;
import org.mockito.invocation.MatchableInvocation;
import org.mockito.verification.VerificationCompletionMode;
import org.mockito.verification.VerificationMode;

import static org.mockito.internal.exceptions.Reporter.invokedButNotCompleted;
import static org.mockito.internal.exceptions.Reporter.noMoreInteractionsWanted;
import static org.mockito.internal.exceptions.Reporter.wantedButNotInvoked;
import static org.mockito.internal.invocation.InvocationMarker.markVerified;
import static org.mockito.internal.invocation.InvocationsFinder.findCompletedInvocations;
import static org.mockito.internal.invocation.InvocationsFinder.findFirstUnverified;
import static org.mockito.internal.invocation.InvocationsFinder.findInvocations;

public class Only implements VerificationMode, VerificationCompletionMode {

    @SuppressWarnings("unchecked")
    public void verify(VerificationData data) {
        MatchableInvocation target = data.getTarget();
        List<Invocation> invocations = data.getAllInvocations();
        List<Invocation> chunk = findInvocations(invocations,target);
        if (invocations.size() != 1 && !chunk.isEmpty()) {
            Invocation unverified = findFirstUnverified(invocations);
            throw noMoreInteractionsWanted(unverified, (List) invocations);
        }
        if (invocations.size() != 1 || chunk.isEmpty()) {
            throw wantedButNotInvoked(target);
        }
        markVerified(chunk.get(0), target);
    }

    public VerificationMode description(String description) {
        return VerificationModeFactory.description(this, description);
    }

    @Override
    public void verifyCompletion(VerificationData data) {
        verify(data);
        MatchableInvocation target = data.getTarget();
        List<Invocation> completedInvocations= findCompletedInvocations(data.getAllInvocations());
        List<Invocation> chunk = findInvocations(completedInvocations,target);
        if (completedInvocations.size() != 1 && !chunk.isEmpty()) {
            Invocation unverified = findFirstUnverified(completedInvocations);
            throw noMoreInteractionsWanted(unverified, (List) completedInvocations);
        }
        if (completedInvocations.size() != 1 || chunk.isEmpty()) {
            throw invokedButNotCompleted(target, chunk);
        }
        markVerified(chunk.get(0), target);
    }
}
