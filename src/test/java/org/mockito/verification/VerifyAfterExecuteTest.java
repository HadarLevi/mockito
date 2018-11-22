package org.mockito.verification;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.exceptions.verification.InvokedButNotCompleted;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.timeoutToCompletion;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyAndWaitUntilCompletion;
import static org.mockito.Mockito.verifyCompletion;


public class VerifyAfterExecuteTest {

    @Test(expected = InvokedButNotCompleted.class)
    public void InvokedButNotCompletedTest() {
        TestMock testMock = Mockito.mock(TestMock.class);
        doReturn(true).when(testMock).getTestMockMethod(any(String.class));
        final TestSpy testSpy = spy(new TestSpy(testMock));
        Thread methodRunner = new Thread(new Runnable() {
            @Override
            public void run() {
                testSpy.useTheTestMock();
            }
        });
        methodRunner.start();
        verify(testSpy, timeoutToCompletion(10)).useTheTestMock();// 0.1 second timeout
        verify(testMock).getTestMockMethod(any(String.class)); //need to fail here
    }

    @Test
    public void TimeoutToCompletionTest() {
        TestMock testMock = Mockito.mock(TestMock.class);
        doReturn(true).when(testMock).getTestMockMethod(any(String.class));
        final TestSpy testSpy = spy(new TestSpy(testMock));
        Thread methodRunner = new Thread(new Runnable() {
            @Override
            public void run() {
                testSpy.useTheTestMock();
            }
        });
        methodRunner.start();
        verify(testSpy, timeoutToCompletion(10000)).useTheTestMock();// 10 second timeout, need to pass
        verify(testMock).getTestMockMethod(any(String.class));
    }

    @Test
    public void VerifyAndWaitUntilCompletionTest() {
        TestMock testMock = Mockito.mock(TestMock.class);
        doReturn(true).when(testMock).getTestMockMethod(any(String.class));
        final TestSpy testSpy = spy(new TestSpy(testMock));
        Thread methodRunner = new Thread(new Runnable() {
            @Override
            public void run() {
                testSpy.useTheTestMock();
            }
        });
        methodRunner.start();
        verifyAndWaitUntilCompletion(testSpy).useTheTestMock();
        verify(testMock).getTestMockMethod(any(String.class));
    }

    @Test
    public void VerifyAndWaitUntilCompletionWithTimesTest() {
        TestMock testMock = Mockito.mock(TestMock.class);
        doReturn(true).when(testMock).getTestMockMethod(any(String.class));
        final TestSpy testSpy = spy(new TestSpy(testMock));
        Thread methodRunner = new Thread(new Runnable() {
            @Override
            public void run() {
                testSpy.callUseTheTestMockTwice();
            }
        });
        methodRunner.start();
        Thread.yield();//current thread give up the cpu time so the methodRunner thread will start
        verifyAndWaitUntilCompletion(testSpy).callUseTheTestMockTwice();
        verifyCompletion(testSpy, times(2)).useTheTestMock();
        verifyCompletion(testMock, times(2)).getTestMockMethod(any(String.class));
    }

    @Test(expected = InvokedButNotCompleted.class)
    public void shouldReturnAfterCompleteExecute() {//TODO: ask what verifyCompletion need to do
        TestMock testMock = Mockito.mock(TestMock.class);
        doReturn(true).when(testMock).getTestMockMethod(any(String.class));
        final TestSpy testSpy = spy(new TestSpy(testMock));
        Thread methodRunner = new Thread(new Runnable() {
            @Override
            public void run() {
                testSpy.useTheTestMock();
            }
        });
        methodRunner.start();
        verifyCompletion(testSpy).useTheTestMock(); //need to fail here
    }


    interface TestMock {
        boolean getTestMockMethod(String message);
    }

    public class TestSpy {
        TestMock someone;

        public TestSpy(TestMock someone) {
            this.someone = someone;
        }

        public void useTheTestMock() {
            try {
                Thread.sleep(1000);// sleep for 1 seconds
                someone.getTestMockMethod("FAKE MESSAGE");
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        public void callUseTheTestMockTwice() {
            useTheTestMock();
            useTheTestMock();
        }

    }
}
