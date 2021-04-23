//package com.rex.boot.activiti;
//
//import org.activiti.engine.ProcessEngine;
//import org.activiti.engine.ProcessEngineConfiguration;
//import org.activiti.engine.repository.Deployment;
//import org.activiti.engine.repository.DeploymentBuilder;
//import org.junit.jupiter.api.Test;
//
//public class processes {
//    /**
//     * 部署流程文件
//     */
//    @Test
//    public void deploy() {
//        ProcessEngineConfiguration pec = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
//        pec.setJdbcDriver("com.mysql.cj.jdbc.Driver");
//        pec.setJdbcUrl("jdbc:mysql://localhost:3306/zrex2?characterEncoding=utf8&useUnicode=true&useSSL=false&serverTimezone=GMT%2B8");
//        pec.setJdbcUsername("root");
//        pec.setJdbcPassword("123456");
//
//
//        ProcessEngine processEngine = pec.createStandaloneProcessEngineConfiguration()
//                .buildProcessEngine();
//        DeploymentBuilder builder = processEngine.getRepositoryService().createDeployment();
//        Deployment deploy = builder.name("test")
//                // org.activiti.bpmn.exceptions.XMLException: cvc-complex-type.2.4.a: 发现了以元素 'process' 开头的无效内容。
//                .disableSchemaValidation() //禁用架构验证
//                .addClasspathResource("processes/holiday.bpmn")
//                .deploy();
//    }
//}
