# Analysis for: gpf_dc_orchestration\src\src\chatgroup\dto\ChatGroupGptMessageDto.java

## Original File Path
`D:\TMP\CLASS_BUS_PROJECT\gpf_dc_orchestration\src\src\chatgroup\dto\ChatGroupGptMessageDto.java`

## Extracted Snippets & Analysis
根据您提供的[核心规则]，我从 `ChatGroupGptMessageDto` 类中提取出以下核心代码样例：

---

### 样例1: 构建 ChatGroupGptMessageDto 实例并指定群组角色

**描述**: 演示如何使用构造函数创建一个 `ChatGroupGptMessageDto` 的新实例，并初始化其 `groupRole` 属性。

```java
// 构建 ChatGroupGptMessageDto 实例并指定群组角色
ChatGroupGptMessageDto message = new ChatGroupGptMessageDto("your_group_role");
```

---

### 样例2: 构建 ChatGroupGptMessageDto 实例并指定群组角色和角色提示词

**描述**: 演示如何使用另一个构造函数创建一个 `ChatGroupGptMessageDto` 的新实例，并初始化 `groupRole` 和 `rolePrompt` 属性。

```java
// 构建 ChatGroupGptMessageDto 实例并指定群组角色和角色提示词
ChatGroupGptMessageDto message = new ChatGroupGptMessageDto("your_group_role", "your_role_prompt");
```

---

### 样例3: 使用链式调用（Builder 模式）配置 ChatGroupGptMessageDto 的多个属性

**描述**: 演示如何利用 `ChatGroupGptMessageDto` 的 setter 方法返回 `this` 的特性，进行链式调用来一次性设置多个属性，高效地配置对象。这是该 DTO 常用且推荐的配置方式。

```java
// 使用链式调用（builder模式）配置 ChatGroupGptMessageDto 的多个属性
ChatGroupGptMessageDto message = new ChatGroupGptMessageDto("initial_group_role")
    .setCode("your_unique_message_code")
    .setInstanceUuid("your_instance_uuid")
    .setChatRange("your_chat_range_setting")
    .setTarget("your_target_user_or_group_id")
    .setWhisper(true) // 设置为悄悄话
    .setCreateTime(System.currentTimeMillis()) // 设置创建时间为当前系统时间
    .setAgentInstanceUuid("your_agent_instance_uuid");
    // 您可以根据需要继续链式调用其他setter方法，例如：
    // .setResponseTime(your_response_time_long_variable);
```