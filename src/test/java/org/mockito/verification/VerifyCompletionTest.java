package org.mockito.verification;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.exceptions.verification.InvokedButNotCompleted;
import org.mockitoutil.TestBase;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.timeoutToCompletion;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyCompletion;


public class VerifyCompletionTest {

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
        Thread.yield();
        verify(testSpy, timeoutToCompletion(10)).useTheTestMock();
        try {
            verify(testMock).getTestMockMethod(any(String.class)); //need to fail here
        }finally {
            try{methodRunner.join();}
            catch (InterruptedException e){}
        }
    }

    @Test
    public void TimeoutToCompletionTest() {
        TestMock testMock1 = Mockito.mock(TestMock.class);
        doReturn(true).when(testMock1).getTestMockMethod(any(String.class));
        final TestSpy testSpy1 = spy(new TestSpy(testMock1));
        Thread methodRunner1 = new Thread(new Runnable() {
            @Override
            public void run() {
                testSpy1.useTheTestMock();
            }
        });
        methodRunner1.start();
        Thread.yield();
        verify(testSpy1, timeoutToCompletion(10000)).useTheTestMock();//need to pass
        verify(testMock1).getTestMockMethod(any(String.class));
        try{methodRunner1.join();}
        catch (InterruptedException e){}
    }

    @Test(expected = InvokedButNotCompleted.class)
    public void shouldReturnAfterCompleteExecute() {
        TestMock testMock2 = Mockito.mock(TestMock.class);
        doReturn(true).when(testMock2).getTestMockMethod(any(String.class));
        final TestSpy testSpy2 = spy(new TestSpy(testMock2));
        Thread methodRunner2 = new Thread(new Runnable() {
            @Override
            public void run() {
                testSpy2.useTheTestMock();
            }
        });
        methodRunner2.start();
        Thread.yield();
        verifyCompletion(testSpy2).useTheTestMock(); //need to fail here
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
            try{
                Thread.sleep(1000);
                someone.getTestMockMethod("FAKE MESSAGE");
            }catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
