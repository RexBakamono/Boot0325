//package com.rex.boot.activiti;
//
//import org.activiti.engine.*;
//import org.activiti.engine.history.HistoricActivityInstance;
//import org.activiti.engine.repository.Deployment;
//import org.activiti.engine.repository.ProcessDefinition;
//import org.activiti.engine.runtime.ProcessInstance;
//import org.activiti.engine.task.Task;
//import org.junit.jupiter.api.Test;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.zip.ZipInputStream;
//
///**
// * @param
// * @author rex
// * @description //TODO 流程部署
// * @date
// * @return $return
// */
//public class ActivitiDeployment {
//
//
//    @Test
//    public void deploy() {
//        ProcessEngineConfiguration processEngine = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
//        processEngine.setJdbcDriver("com.mysql.cj.jdbc.Driver");
//        processEngine.setJdbcUrl("jdbc:mysql://localhost:3306/zrex2?characterEncoding=utf8&useUnicode=true&useSSL=false&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true");
//        processEngine.setJdbcUsername("root");
//        processEngine.setJdbcPassword("123456");
//        processEngine.setDatabaseSchemaUpdate("true");
//        processEngine.buildProcessEngine();
//
//
////        RepositoryService repositoryService = processEngine.getRepositoryService();
//
//
////        Deployment deployment = repositoryService.createDeployment()
////                .addClasspathResource("processes/holiday.bpmn")
////                .addClasspathResource("processes/holiday.png")
////                .name("请假流程")
////                .deploy();
////
////
////        System.out.println(deployment.getName());
////        System.out.println(deployment.getId());
//
//
//    }
//
//
//    /**
//     * @param path holiday.zip  processes 文件夹下
//     * @return $return
//     * @author rex
//     * @description //TODO zip文件部署
//     * @date
//     */
//    @Test
//    public void deployByZip(String path, String name) {
////        获取流
//        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
//        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
//
////        正常部署 addZipInputStream
//        RepositoryService repositoryService = getEngine().getRepositoryService();
//        Deployment deployment = repositoryService
//                .createDeployment()
//                .addZipInputStream(zipInputStream)
//                .name(name)
//                .deploy();
//        System.out.println(deployment.getName());
//        System.out.println(deployment.getId());
//    }
//
//    /**
//     * @param
//     * @return $return
//     * @author rex
//     * @description //TODO 获取engine
//     * @date
//     */
//    @Test
//    private static ProcessEngine getEngine() {
//        ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
//        configuration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
//        configuration.setJdbcUrl("jdbc:mysql://localhost:3306/zrex2?characterEncoding=utf8&useUnicode=true&useSSL=false&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true");
//        configuration.setJdbcUsername("root");
//        configuration.setJdbcPassword("123456");
//        configuration.setDatabaseSchemaUpdate("true");
//        ProcessEngine processEngine = configuration.buildProcessEngine();
//        return processEngine;
//    }
//
//
//    /**
//     * @param
//     * @param key 流程唯一id key holiday
//     * @return $return
//     * @author rex
//     * @description //TODO 启动流程实例
//     * @date
//     */
//    @Test
//    public void startFlow(String key) {
//        ProcessEngine processEngine = getEngine();
//        RuntimeService runtimeService = processEngine.getRuntimeService();
//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key);
//        System.out.println("流程部署id" + processInstance.getDeploymentId());
//        System.out.println("流程实例id" + processInstance.getId());
//        System.out.println("流程活动id" + processInstance.getActivityId());
//    }
//
//    /**
//     * @param key holiday
//     *            businessKey 业务数据id
//     * @return $return
//     * @author rex
//     * @description //TODO 根据业务id启动流程
//     * @date
//     */
//    @Test
//    public void startFlow(String key, String businessKey) {
//        ProcessEngine processEngine = getEngine();
//        RuntimeService runtimeService = processEngine.getRuntimeService();
//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, businessKey);
//        System.out.println("流程部署id" + processInstance.getDeploymentId());
//        System.out.println("流程实例id" + processInstance.getId());
//        System.out.println("流程活动id" + processInstance.getActivityId());
//    }
//
//    /**
//     * @param
//     * @param key  流程唯一id key
//     * @param user 用户名称
//     * @return $return
//     * @author rex
//     * @description //TODO 任务查询
//     * @date
//     */
//    @Test
//    public void queryFlows(String key, String user) {
//        ProcessEngine processEngine = getEngine();
//        TaskService taskService = processEngine.getTaskService();
//        List<Task> tasks = taskService.createTaskQuery().processDefinitionKey(key).taskAssignee(user).list();
//
//        // taskAssignee(user) 方式
//        // taskCandidateUser(user)组任务方式
//        // taskCandidateOrAssigned() 两种方式
//        // 任务拾取 任务id 任务负责人
//        // taskService.claim(tasks.get(0).getId(), cadidateUsers);
//        // 归还组任务 通过tasks.get(0).getAssignee() 查询任务负责人，判断是否是当前用户
//        // taskService.setAssignee(taskId, null);
//
//
//        for (Task task :
//                tasks) {
//            System.out.println("流程实例id" + task.getProcessInstanceId());
//            System.out.println("任务id" + task.getId());
//            System.out.println("任务负责人" + task.getAssignee());
//            System.out.println("任务名称" + task.getName());
//        }
//        // 删除流程实例    InstanceId 实例id  user 相关信息
//        RuntimeService runtimeService = processEngine.getRuntimeService();
//        runtimeService.deleteProcessInstance(tasks.get(0).getProcessInstanceId(), user + "删除流程");
//    }
//
//    /**
//     * @param taskId act_hi_taskinst.ID_ task.getID()
//     * @return $return
//     * @author rex
//     * @description //TODO 发送下一步任务
//     * @date
//     */
//    public void toNextFlow(String taskId) {
//        ProcessEngine processEngine = getEngine();
//        TaskService taskService = processEngine.getTaskService();
//        taskService.complete(taskId);
//
////        map 为传入的流程变量 assign
////        Map<String, Object> map = new HashMap<>();
////        List<String> userlist = new ArrayList<String>();
////        userlist.add("a");
////        userlist.add("b");
////        userlist.add("c");
////        map.put("users", userlist);
////        taskService.complete(taskId, map);
//    }
//
//
//    /**
//     * @param processDefinitionKey 流程定义key  holiday
//     * @return $return
//     * @author rex
//     * @description //TODO 查询流程定义信息
//     * @date
//     */
//    public void selectProcessDefinition(String processDefinitionKey) {
//        ProcessEngine processEngine = getEngine();
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//        List<ProcessDefinition> list = repositoryService
//                .createProcessDefinitionQuery()
//                .processDefinitionKey(processDefinitionKey)
//                .orderByProcessDefinitionVersion() // 根据流程定义的版本号排序
//                .desc() // 降序
//                .list();
//        for (ProcessDefinition processDefinition :
//                list) {
//            System.out.println("流程定义id" + processDefinition.getId());
//            System.out.println("流程定义名称" + processDefinition.getName());
//            System.out.println("流程定义key" + processDefinition.getKey());
//            System.out.println("流程定义版本号" + processDefinition.getVersion());
//        }
//    }
//
//    /**
//     * @param deploymentId processDefinition.getDeploymentId()    通过流程定义信息获取
//     * @return $return
//     * @author rex
//     * @description //TODO 删除流程定义
//     * @date
//     */
//    public void deleteProcessDefinition(String deploymentId) {
//        ProcessEngine processEngine = getEngine();
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//        // 删除流程已经有流程在运行 报错
//        repositoryService.deleteDeployment(deploymentId);
//        // 设置true 级联删除，删除已经启动的流程实例
//        repositoryService.deleteDeployment(deploymentId, true);
//    }
//
//    /**
//     * @param
//     * @return $return
//     * @author rex
//     * @description //TODO 文件复制
//     * @date
//     */
//    private static void copy(InputStream in, OutputStream out) throws IOException {
//        byte[] buffer = new byte[1024];
//        int i = in.read(buffer);
//        while (i != -1) {
//            out.write(buffer, 0, i);
//            i = in.read(buffer);
//        }
//    }
//
//    /**
//     * @param key holiday
//     * @return $return
//     * @author rex
//     * @description //TODO 操作流程部署文件（从act_ge_bytearray 表中读取）下载资源文件
//     * 一个是用activiti api  操作
//     * 二是直接读取表中 blob字段读取流转换成文件
//     * @date
//     */
//    public void queryBpmnFile(String key) throws IOException {
//        ProcessEngine processEngine = getEngine();
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//        ProcessDefinition processDefinition = repositoryService
//                .createProcessDefinitionQuery()
//                .processDefinitionKey(key)
//                .singleResult();
//
////        第一个参数为部署id 第二个为资源名称   png bpmn 文件
//        InputStream pngIs = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
//                processDefinition.getDiagramResourceName());
//
//        InputStream bpmnIs = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
//                processDefinition.getResourceName());
//
//        OutputStream pngOs = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\" + processDefinition.getDiagramResourceName());
//
//        OutputStream bpmnOs = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\" + processDefinition.getResourceName());
//
//        copy(pngIs, pngOs);
//        copy(bpmnIs, bpmnOs);
//
//        pngIs.close();
//        bpmnIs.close();
//        pngOs.close();
//        bpmnOs.close();
//    }
//
//
//    /**
//     * @param processInstanceId
//     * @return $return
//     * @author rex
//     * @description //TODO 查询历史记录
//     * @date
//     */
//    public void queryHistory(String processInstanceId) {
//        ProcessEngine engine = getEngine();
//        HistoryService historyService = engine.getHistoryService();
//        List<HistoricActivityInstance> list = historyService
//                .createHistoricActivityInstanceQuery()
//                .processInstanceId(processInstanceId)
//                .orderByHistoricActivityInstanceStartTime() // 排序
//                .asc()
//                .list();
//
//        for (HistoricActivityInstance instance :
//                list) {
//            System.out.println(instance.getActivityId());
//            System.out.println(instance.getActivityName());
//            System.out.println(instance.getProcessDefinitionId());
//            System.out.println(instance.getProcessInstanceId());
//        }
//    }
//
//    /**
//     * @param key holiday
//     * @return $return
//     * @author rex
//     * @description //TODO 流程挂起
//     * @date
//     */
//    public void pauseProcessDefinition(String key) {
//        ProcessEngine engine = getEngine();
//        RepositoryService repositoryService = engine.getRepositoryService();
//        ProcessDefinition processDefinition = repositoryService
//                .createProcessDefinitionQuery()
//                .processDefinitionKey(key)
//                .singleResult();
//        boolean isSuspended = processDefinition.isSuspended();
//        if (isSuspended) {
//            // 是暂停状态, 激活操作
//            repositoryService.activateProcessDefinitionById(processDefinition.getId(), true, null);
//            System.out.println("实例激活");
//        } else {
//            repositoryService.suspendProcessDefinitionById(processDefinition.getId(), true, null);
//            System.out.println("实例挂起");
//        }
//    }
//
//    /**
//     * @param
//     * @return $return
//     * @author rex
//     * @description //TODO 单个流程实例挂起
//     * @date
//     */
//    public void pauseProcessDefinitionByInstance(String processInstanceId) {
//        ProcessEngine engine = getEngine();
//        RuntimeService runtimeService = engine.getRuntimeService();
//        ProcessInstance processInstance = runtimeService
//                .createProcessInstanceQuery()
//                .processInstanceId(processInstanceId)
//                .singleResult();
//        boolean isSuspended = processInstance.isSuspended();
//        if (isSuspended) {
//            // 是暂停状态, 激活操作
//            runtimeService.activateProcessInstanceById(processInstance.getId());
//            System.out.println("实例激活");
//        } else {
//            runtimeService.suspendProcessInstanceById(processInstance.getId());
//            System.out.println("实例挂起");
//        }
//    }
//
//
//}
