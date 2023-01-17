package com.xu;

public class TestService {

    private TestProperties testProperties;

    public TestProperties getTestProperties() {
        return testProperties;
    }

    public void setTestProperties(TestProperties testProperties) {
        this.testProperties = testProperties;
    }

    public String sayHello(){
        return testProperties.getTestName()+":"+testProperties.getPrefix()+"Hello World"+testProperties.getSuffix();
    }
}
