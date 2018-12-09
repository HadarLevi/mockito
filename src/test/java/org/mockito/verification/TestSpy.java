package org.mockito.verification;

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
