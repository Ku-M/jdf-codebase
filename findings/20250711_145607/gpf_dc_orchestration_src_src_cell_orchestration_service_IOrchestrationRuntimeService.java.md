# Analysis for: gpf_dc_orchestration\src\src\cell\orchestration\service\IOrchestrationRuntimeService.java

## Original File Path
`D:\TMP\CLASS_BUS_PROJECT\gpf_dc_orchestration\src\src\cell\orchestration\service\IOrchestrationRuntimeService.java`

## Extracted Snippets & Analysis
好的，我将作为资深软件架构师，严格遵循你给出的核心规则，从提供的代码中提炼出高质量、可复用的API调用模式样例。

我将重点关注 `IOrchestrationRuntimeService` 接口中，**能够通过通用Java类型或该接口自身提供的静态方法可靠获取到实例，并执行具体“动作”的方法**。对于需要依赖无法可靠获取的私有库实例（例如 `IDao`, `PDCForm`, `Form`, `IDCRuntimeContext`, `ApplicationSetting`, `Cnd` 等）作为参数的方法，即使它们是公开的，也会被排除，因为它们不符合“确保样例的绝对可靠性”原则。

---

### 提炼出的高质量代码样例

以下是根据您的严格规则从 `IOrchestrationRuntimeService` 接口中提取的核心代码模式：

**前置准备：获取服务实例**
*注意：所有后续样例都需要先获取服务实例。*

```java
import cell.orchestration.service.IOrchestrationRuntimeService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import gpt.dto.openai.GptMessageDto; // 假设此DTO有公共构造函数和/或setter
import fe.orchestration.component.dto.LLMConfigDto; // 假设此DTO有公共构造函数和/或setter
import gpf.enums.OperateLogStatus;
// import gpf.adur.data.Form; // Form 类型在此上下文中无法可靠实例化，故只作为返回值类型出现
// import fe.cmn.widget.WidgetDto; // WidgetDto 类型无法可靠实例化，故只作为返回值类型出现

/**
 * 样例1: 获取Orchestration运行时服务实例
 * 描述: 通过静态方法获取IOrchestrationRuntimeService的单例实例。这是所有后续API调用的入口。
 * 可靠性: 极高，依赖于公共静态方法。
 */
public class OrchestrationServiceUsageExample {

    public static void main(String[] args) throws Exception {
        IOrchestrationRuntimeService serviceInstance = IOrchestrationRuntimeService.get();
        System.out.println("Orchestration 运行时服务实例获取成功.");

        // 以下是具体的API调用模式样例
        // 请根据需要取消注释并使用
        // buildLastRunBatch_Example(serviceInstance);
        // queryEffectNodeName2KeyMap_Example(serviceInstance);
        // resetNode_Single_Example(serviceInstance);
        // resetNode_Batch_Example(serviceInstance);
        // updateNodeChat_Example(serviceInstance);
        // queryPdfUuid_Example(serviceInstance);
        // getParamMap_ByUuid_Example(serviceInstance);
        // getInstanceLlmConfig_Example(serviceInstance);
        // updateParamMap_Example(serviceInstance);
        // queryInstance_ByUuid_Example(serviceInstance);
        // updateInstanceInitWord_Example(serviceInstance);
        // deleteInstance_Example(serviceInstance);
        // queryInstanceStatus_Example(serviceInstance);
        // regain2NodeChat_Example(serviceInstance);
    }

    /**
     * 样例2: 调用 buildLastRunBatch 方法
     * 描述: 构建并获取上一个运行批次的ID。
     * 任务: 获取批次信息。
     */
    public static void buildLastRunBatch_Example(IOrchestrationRuntimeService serviceInstance) throws Exception {
        String lastRunBatchId = serviceInstance.buildLastRunBatch();
        System.out.println("获取到的上一个运行批次ID: " + lastRunBatchId);
    }

    /**
     * 样例3: 调用 queryEffectNodeName2KeyMap 方法
     * 描述: 根据流程实例UUID和节点名称查询有效节点名称到键的映射。
     * 任务: 查询特定节点的映射信息。
     */
    public static void queryEffectNodeName2KeyMap_Example(IOrchestrationRuntimeService serviceInstance) throws Exception {
        String yourPdfInstanceUuid = "your_pdf_instance_uuid_here"; // 您的流程实例UUID
        String yourNodeName = "your_node_name_here";                 // 您的节点名称
        Map<String, String> nodeMap = serviceInstance.queryEffectNodeName2KeyMap(yourPdfInstanceUuid, yourNodeName);
        System.out.println("查询到的节点映射: " + nodeMap);
    }

    /**
     * 样例4: 调用 resetNode (单个节点) 方法
     * 描述: 重置指定流程实例的单个节点。
     * 任务: 重置流程节点。
     */
    public static void resetNode_Single_Example(IOrchestrationRuntimeService serviceInstance) throws Exception {
        String yourPdfInstanceUuid = "your_pdf_instance_uuid_here"; // 您的流程实例UUID
        String yourNodeName = "node_to_reset_name_here";             // 要重置的节点名称
        boolean includeSelfNode = true;                               // 是否包含自身节点 (true/false)
        Set<String> resetNodes = serviceInstance.resetNode(yourPdfInstanceUuid, yourNodeName, includeSelfNode);
        System.out.println("已重置的节点列表 (单个): " + resetNodes);
    }

    /**
     * 样例5: 调用 resetNode (批量节点) 方法
     * 描述: 重置指定流程实例的多个节点。
     * 任务: 批量重置流程节点。
     */
    public static void resetNode_Batch_Example(IOrchestrationRuntimeService serviceInstance) throws Exception {
        String yourPdfInstanceUuid = "your_pdf_instance_uuid_here"; // 您的流程实例UUID
        List<String> yourNodeKeys = Arrays.asList("node_key_1", "node_key_2", "node_key_3"); // 要重置的节点键列表
        serviceInstance.resetNode(yourPdfInstanceUuid, yourNodeKeys);
        System.out.println("已重置指定节点列表 (批量): " + yourNodeKeys);
    }

    /**
     * 样例6: 调用 updateNodeChat 方法
     * 描述: 更新指定节点的聊天记录。
     * 任务: 更新节点关联的对话信息。
     */
    public static void updateNodeChat_Example(IOrchestrationRuntimeService serviceInstance) throws Exception {
        String yourPdfInstanceUuid = "your_pdf_instance_uuid_here"; // 您的流程实例UUID
        String yourNodeName = "node_with_chat_history";             // 具有聊天记录的节点名称
        List<GptMessageDto> messages = new ArrayList<>();
        // 假设 GptMessageDto 有可访问的构造函数或公共 setter
        GptMessageDto message1 = new GptMessageDto();
        // message1.setRole("user"); // 示例属性设置
        // message1.setContent("Hello, this is a test message."); // 示例属性设置
        messages.add(message1);
        // 可以添加更多消息
        // GptMessageDto message2 = new GptMessageDto();
        // message2.setRole("assistant");
        // message2.setContent("Hi there! How can I help you?");
        // messages.add(message2);

        serviceInstance.updateNodeChat(yourPdfInstanceUuid, yourNodeName, messages);
        System.out.println("已更新节点 '" + yourNodeName + "' 的聊天记录。");
    }

    /**
     * 样例7: 调用 queryPdfUuid 方法
     * 描述: 通过实例UUID查询对应的流程UUID。
     * 任务: 获取流程UUID。
     */
    public static void queryPdfUuid_Example(IOrchestrationRuntimeService serviceInstance) throws Exception {
        String yourPdfInstanceUuid = "your_pdf_instance_uuid_here"; // 您的流程实例UUID
        String pdfUuid = serviceInstance.queryPdfUuid(yourPdfInstanceUuid);
        System.out.println("根据实例UUID '" + yourPdfInstanceUuid + "' 查询到的流程UUID: " + pdfUuid);
    }

    /**
     * 样例8: 调用 getParamMap (通过实例UUID) 方法
     * 描述: 根据流程实例UUID查询已配置的参数表。
     * 任务: 获取实例参数。
     */
    public static void getParamMap_ByUuid_Example(IOrchestrationRuntimeService serviceInstance) throws Exception {
        String yourPdfInstanceUuid = "your_pdf_instance_uuid_here"; // 您的流程实例UUID
        boolean mergeParams = true; // 是否合并参数 (true/false)
        Map<String, String> paramMap = serviceInstance.getParamMap(yourPdfInstanceUuid, mergeParams);
        System.out.println("根据实例UUID '" + yourPdfInstanceUuid + "' 查询到的参数表: " + paramMap);
    }

    /**
     * 样例9: 调用 getInstanceLlmConfig 方法
     * 描述: 根据实例UUID查询该实例的LLM配置。
     * 任务: 获取LLM配置。
     */
    public static void getInstanceLlmConfig_Example(IOrchestrationRuntimeService serviceInstance) throws Exception {
        String yourPdfInstanceUuid = "your_pdf_instance_uuid_here"; // 您的流程实例UUID
        LLMConfigDto llmConfig = serviceInstance.getInstanceLlmConfig(yourPdfInstanceUuid);
        System.out.println("根据实例UUID '" + yourPdfInstanceUuid + "' 查询到的LLM配置对象: " + llmConfig);
        // 您可以进一步访问 llmConfig 的属性，例如 llmConfig.getModelName(), llmConfig.getApiKey() 等
    }

    /**
     * 样例10: 调用 updateParamMap 方法
     * 描述: 根据实例UUID更新其参数表。
     * 任务: 更新实例参数。
     */
    public static void updateParamMap_Example(IOrchestrationRuntimeService serviceInstance) throws Exception {
        String yourPdfInstanceUuid = "your_pdf_instance_uuid_here"; // 您的流程实例UUID
        Map<String, String> newParamMap = new HashMap<>();
        newParamMap.put("newParamKey1", "newParamValue1");
        newParamMap.put("newParamKey2", "newParamValue2");
        // 根据业务需求添加更多参数

        serviceInstance.updateParamMap(yourPdfInstanceUuid, newParamMap);
        System.out.println("已更新实例 '" + yourPdfInstanceUuid + "' 的参数表。");
    }

    /**
     * 样例11: 调用 queryInstance (通过实例UUID) 方法
     * 描述: 根据实例UUID查询实例对象。
     * 任务: 获取完整的实例对象。
     */
    public static void queryInstance_ByUuid_Example(IOrchestrationRuntimeService serviceInstance) throws Exception {
        String yourPdfInstanceUuid = "your_pdf_instance_uuid_here"; // 您的流程实例UUID
        gpf.adur.data.Form instanceObject = serviceInstance.queryInstance(yourPdfInstanceUuid); // 返回值类型为私有库类型，但作为方法签名一部分是可接受的
        System.out.println("根据实例UUID '" + yourPdfInstanceUuid + "' 查询到的实例对象: " + instanceObject);
        // 您可以进一步访问 instanceObject 的属性，例如 instanceObject.getUuid(), instanceObject.getCode() 等
    }

    /**
     * 样例12: 调用 updateInstanceInitWord 方法
     * 描述: 更新实例的初始语句，并可选择是否重新运行。
     * 任务: 修改实例初始化信息。
     */
    public static void updateInstanceInitWord_Example(IOrchestrationRuntimeService serviceInstance) throws Exception {
        String yourPdfInstanceUuid = "your_pdf_instance_uuid_here"; // 您的流程实例UUID
        String newInitWord = "此处填写新的初始语句，例如：请帮我处理... "; // 新的初始语句
        boolean shouldRerun = true; // 是否在更新后重新运行实例 (true/false)

        serviceInstance.updateInstanceInitWord(yourPdfInstanceUuid, newInitWord, shouldRerun);
        System.out.println("已更新实例 '" + yourPdfInstanceUuid + "' 的初始语句并设置是否重新运行。");
    }

    /**
     * 样例13: 调用 deleteInstance 方法
     * 描述: 删除指定实例。
     * 任务: 删除实例。
     */
    public static void deleteInstance_Example(IOrchestrationRuntimeService serviceInstance) throws Exception {
        String yourPdfInstanceUuid = "your_pdf_instance_uuid_here"; // 您的流程实例UUID
        serviceInstance.deleteInstance(yourPdfInstanceUuid);
        System.out.println("已删除实例: " + yourPdfInstanceUuid);
    }

    /**
     * 样例14: 调用 queryInstanceStatus 方法
     * 描述: 查询指定流程实例的所有节点状态。
     * 任务: 获取实例的运行状态。
     */
    public static void queryInstanceStatus_Example(IOrchestrationRuntimeService serviceInstance) throws Exception {
        String yourPdfInstanceUuid = "your_pdf_instance_uuid_here"; // 您的流程实例UUID
        Map<String, OperateLogStatus> instanceStatuses = serviceInstance.queryInstanceStatus(yourPdfInstanceUuid);
        System.out.println("查询到的实例 '" + yourPdfInstanceUuid + "' 的节点状态: " + instanceStatuses);
        // 示例：遍历并打印状态
        // instanceStatuses.forEach((nodeName, status) -> System.out.println("  节点: " + nodeName + ", 状态: " + status));
    }

    /**
     * 样例15: 调用 regain2NodeChat 方法
     * 描述: 回溯到指定节点并更新其聊天记录。
     * 任务: 回溯并修正聊天记录。
     */
    public static void regain2NodeChat_Example(IOrchestrationRuntimeService serviceInstance) throws Exception {
        String yourInstanceUuid = "your_instance_uuid_here";         // 您的流程实例UUID
        String yourNodeName = "node_to_regain_chat";                 // 要回溯到的节点名称
        String newMessageContent = "这是回溯后添加或修改的聊天内容。"; // 新的聊天内容

        serviceInstance.regain2NodeChat(yourInstanceUuid, yourNodeName, newMessageContent);
        System.out.println("已回溯实例 '" + yourInstanceUuid + "' 的节点 '" + yourNodeName + "' 并更新聊天内容。");
    }
}
```