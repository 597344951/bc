package com.zltel.broadcast.example.logout;



import org.junit.Test;

public class GetLoggerTest {


    private GetLogger createTestSubject() {
        return new GetLogger();
    }

    @Test
    public void testTest() throws Exception {
        GetLogger testSubject;
 
        testSubject = createTestSubject();
        testSubject.test();
    }
}
