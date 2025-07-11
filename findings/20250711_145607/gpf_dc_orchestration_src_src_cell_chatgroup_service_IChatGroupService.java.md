# Analysis for: gpf_dc_orchestration\src\src\cell\chatgroup\service\IChatGroupService.java

## Original File Path
`D:\TMP\CLASS_BUS_PROJECT\gpf_dc_orchestration\src\src\cell\chatgroup\service\IChatGroupService.java`

## Extracted Snippets & Analysis
作为一名资深的软件架构师，我已仔细分析了您提供的 `IChatGroupService` 接口代码，并严格遵循了您提出的核心规则。

以下是我为您提炼出的、符合所有严格标准的API使用样例：

---

**提取的API使用样例**

```java
// 样例1: 获取 IChatGroupService 的实例
// 这是一个可靠的静态方法调用，是使用服务入口点。
IChatGroupService service = IChatGroupService.get();

// 样例2: 创建一个新的聊天群组
// 该样例展示了如何通过提供基本信息创建群组。
// 请将占位符替换为您实际的业务值。
IChatGroupService service = IChatGroupService.get();
gpf.adur.data.Form createdChatGroupForm = service.createChatGroup(
    "your_chat_group_code_placeholder", // 群组编码
    "your_group_name_placeholder",      // 群组名称
    "your_group_description_placeholder", // 群组描述
    "your_group_prompt_placeholder",    // 群组提示语
    "your_default_llm_code_placeholder",// 默认LLM编码
    java.util.Arrays.asList("your_role_code_1", "your_role_code_2"), // 角色编码列表
    java.util.Collections.emptyList() // 自定义规则列表，可为空列表或包含 ChatGroupChatRuleDto 实例
);
// createdChatGroupForm 返回了新创建的群组表单数据，您可以对其进行后续操作。


// 样例3: 创建一个新的聊天角色
// 该样例展示了如何通过提供基本信息创建一个角色。
// 请将占位符替换为您实际的业务值。
IChatGroupService service = IChatGroupService.get();
gpf.adur.data.Form createdChatRoleForm = service.createChatRole(
    "your_chat_role_code_placeholder",  // 角色编码
    "your_role_name_placeholder",       // 角色名称
    "your_role_description_placeholder",// 角色描述
    "your_role_prompt_placeholder",     // 角色提示语
    "your_default_llm_code_placeholder" // 默认LLM编码
);
// createdChatRoleForm 返回了新创建的角色表单数据，您可以对其进行后续操作。


// 样例4: 更新一个聊天角色的提示语
// 该样例展示了如何修改现有角色的提示语。
// 请将占位符替换为您实际的业务值。
IChatGroupService service = IChatGroupService.get();
service.updateRolePrompt(
    "chat_role_code_to_update_placeholder", // 需要更新的聊天角色编码
    "new_prompt_content_placeholder"        // 新的提示语内容
);


// 样例5: 更新一个聊天角色的LLM配置
// 该样例展示了如何修改现有角色的LLM（大型语言模型）配置。
// 请将占位符替换为您实际的业务值。
IChatGroupService service = IChatGroupService.get();
service.updateLlmConfig(
    "chat_role_code_to_update_placeholder", // 需要更新的聊天角色编码
    "new_llm_code_placeholder"              // 新的LLM编码
);


// 样例6: 根据编码查询聊天群组信息
// 该样例展示了如何获取特定聊天群组的表单数据。
// 请将占位符替换为您实际的业务值。
IChatGroupService service = IChatGroupService.get();
gpf.adur.data.Form chatGroupForm = service.queryChatGroup(
    "chat_group_code_to_query_placeholder" // 要查询的群组编码
);
// 您可以处理返回的 chatGroupForm 对象。


// 样例7: 根据编码查询聊天角色信息
// 该样例展示了如何获取特定聊天角色的表单数据。
// 请将占位符替换为您实际的业务值。
IChatGroupService service = IChatGroupService.get();
gpf.adur.data.Form chatRoleForm = service.queryChatRole(
    "chat_role_code_to_query_placeholder" // 要查询的角色编码
);
// 您可以处理返回的 chatRoleForm 对象。


// 样例8: 构建组合的角色提示语
// 该样例展示了如何将群组提示语和角色提示语组合成一个字符串。
// 请将占位符替换为您实际的业务值。
IChatGroupService service = IChatGroupService.get();
String combinedPrompt = service.buildRolePrompt(
    "your_chat_group_base_prompt_placeholder",   // 群组基础提示语
    "your_chat_role_specific_prompt_placeholder" // 角色专属提示语
);
// 您可以使用 combinedPrompt 字符串。


// 样例9: 向群组追加一个角色，并可附带自定义规则
// 该样例展示了如何将一个现有角色关联到群组。
// 请将占位符替换为您实际的业务值。
IChatGroupService service = IChatGroupService.get();
chatgroup.dto.ChatGroupChatRuleDto customRule = new chatgroup.dto.ChatGroupChatRuleDto();
// 如果需要，您可以设置 customRule 的属性，例如：
// customRule.setRuleType("YOUR_RULE_TYPE");
// customRule.setRuleContent("YOUR_RULE_CONTENT");

service.addRole2Group(
    "your_chat_group_code_placeholder", // 目标群组编码
    "your_chat_role_code_placeholder",  // 要追加的角色编码
    customRule                          // 自定义规则对象 (如果API允许，也可传递 null)
);


// 样例10: 查询聊天群组的详细信息，可选是否包含角色信息
// 该样例展示了如何获取群组的详细数据传输对象。
// 请将占位符替换为您实际的业务值。
IChatGroupService service = IChatGroupService.get();
chatgroup.dto.ChatGroupInfoDto groupInfo = service.queryChatGroupInfo(
    "chat_group_code_to_query_placeholder", // 要查询的群组编码
    true                                    // 是否联动查询角色信息 (设为 true 或 false)
);
// 您可以处理返回的 groupInfo 对象。


// 样例11: 查询聊天角色的详细信息
// 该样例展示了如何获取角色的详细数据传输对象。
// 请将占位符替换为您实际的业务值。
IChatGroupService service = IChatGroupService.get();
chatgroup.dto.ChatRoleInfoDto roleInfo = service.queryChatRoleInfo(
    "chat_role_code_to_query_placeholder" // 要查询的角色编码
);
// 您可以处理返回的 roleInfo 对象。
```

---

**未提取的代码说明：**

以下方法未被提取为独立样例，主要原因是它们不符合“可靠性”和“通用Java类型作为前提条件”的严格标准：

*   `void buildRole2GroupLink(IDao dao, Form chatGroupForm)`: 依赖于 `IDao` 和 `Form` 这两个非通用Java类型作为输入。`IDao` 通常与数据库会话或事务相关，难以独立构造；`Form` 在此语境下也非简单可实例化的通用类型。
*   `Form createOrUpdateChatGroupForm(IDao dao, Form chatGroupForm)`: 同上，依赖于 `IDao` 和 `Form`。
*   `GptServiceConfigDto getDefaultLLMConfig(ChatGroupInfoDto chatGroupInfoDto, String groupRole)`: `ChatGroupInfoDto` 是一个复杂的数据传输对象（DTO），它通常是某个查询的结果，而不是一个可以直接简单地 `new` 出来并有意义的通用Java类型，因此作为独立样例的输入不具备可靠性。
*   `PairDto<String, byte[]> buildRoleNameAndProfile(Form chatGroupForm, Form chatRoleForm)`: 依赖于 `Form`，原因同上。
*   `ChatGroupInfoDto buildChatGroupInfo(Form chatGroupForm, boolean withRoleInfo)`: 依赖于 `Form`，原因同上。
*   `ChatRoleInfoDto buildChatRoleInfo(Form chatRoleForm)`: 依赖于 `Form`，原因同上。

我专注于提取那些能够清晰、原子地展示API核心功能的代码片段，并确保它们在没有私有库源码访问权限的情况下，也能为AI提供可学习和复用的模式。希望这些样例能极大帮助您的AI编程助手学习如何正确使用这些API。