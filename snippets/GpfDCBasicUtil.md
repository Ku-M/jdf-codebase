## 文件级技术知识库：`GpfDCBasicUtil.java`

### 1. 文件核心功能

`GpfDCBasicUtil.java` 是一个通用的实用工具类，提供了一系列静态方法，用于处理业务系统中的基础功能，包括：

*   **安全访问控制**: 实现Web访问的白名单检查机制。
*   **配置管理**: 通过反射将键值对形式的配置数据应用到Java对象上。
*   **UI组件定义合并**: 根据后端数据模型 (`FormField`) 与前端视图配置 (`FormFieldDefine`, `SearchBarDefine`, `TableColumnDefine`) 进行智能合并，生成最终的UI组件定义列表。
*   **前端设置项加载**: 从JSON资源文件中加载各种UI组件（如表格、表单、树、卡片）的预定义设置项。
*   **数据结构处理**: 提供构建树形结构和递归修剪对象中字符串属性的功能。

它在项目中扮演着提供底层通用能力的角色，尤其在前端UI动态渲染、数据绑定和安全校验方面提供支撑。

### 2. 主要组件/类定义

`GpfDCBasicUtil` 是一个公共静态工具类，不包含实例状态，其所有功能都通过静态方法提供。因此，不适用于以下表格的类定义描述，主要关注其内部方法。

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class GpfDCBasicUtil` | `Object` (隐式) | 提供各种静态工具方法，涵盖Web安全、数据转换、UI配置合并、资源加载及通用对象处理。 |

#### 方法与属性详情

由于 `GpfDCBasicUtil` 是一个工具类，其所有功能都体现在静态方法上，下面将在“3. 主要函数/方法”中详细描述。

**静态属性：**

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `public static List<InetAddress> addressList` | `List<InetAddress>` | 缓存本地IP地址列表，用于 `checkAllowWebAccess` 方法中的地址比对，避免重复获取。初始化为 `null`，首次使用时通过 `ToolUtilities.getAllAddress(true)` 填充。 |

### 3. 主要函数/方法

| 函数名 | 参数 | 返回值 | 功能描述 |
| :--- | :--- | :--- | :--- |
| `checkAllowWebAccess` | `PanelContext context`, `AppCacheMgrIntf cacheMgr` | `void` | 检查当前的Web访问是否被允许。通过解析HTTP和WebSocket的URL，获取host和port，与服务器配置进行比对。如果端口与配置一致且host相同（或为本地地址），则允许；否则，会检查 `web.access.whitelist` 配置的白名单，支持文本匹配和正则匹配。如果均不通过，则抛出 `VerifyException`。该方法还利用缓存来存储和获取 `UrlMsgDto` 和 `WebSocketUrl`。 |
| `getSetting` | `<T> T setting`, `List<Map<String,String>> settingProps` | `T` | 通过反射机制，将 `settingProps`（一个包含"key"和"value"的Map列表）中的配置值设置到目标 `setting` 对象的对应字段中。它会获取目标对象的所有字段，并根据Map中的键名找到匹配的字段进行赋值。 |
| `setFieldValue` | `Object setting`, `Field f`, `String value` | `void` | 辅助方法，用于将字符串 `value` 根据字段 `f` 的类型（如Integer, Long, Double, Float, Boolean, String）转换为对应的类型，然后通过反射设置到 `setting` 对象的指定字段上。 |
| `mergeFormField` | `List<FormField> fields`, `List<FormFieldDefine> formFieldDefines` | `List<FormFieldDefine>` | 合并业务数据模型 `FormField` 与前端表单字段定义 `FormFieldDefine`。以 `FormField` 为基础，将 `formFieldDefines` 中匹配的字段的UI相关属性（如别名、提示、样式、宽度等）覆盖或补充到 `FormFieldDefine` 对象中，形成最终的表单字段定义列表。 |
| `mergeSearchBarField` | `List<FormField> fields`, `List<SearchBarDefine> formFieldDefines` | `List<SearchBarDefine>` | 合并业务数据模型 `FormField` 与前端搜索栏字段定义 `SearchBarDefine`。逻辑与 `mergeFormField` 类似，将 `SearchBarDefine` 中匹配的字段的UI相关属性合并到结果中。特别处理了关联字段 `DataType.Relate`，默认设置为多选。 |
| `mergeFormFieldByTableColumnDefines` | `List<FormField> fields`, `List<TableColumnDefine> tableColDefines` | `List<FormFieldDefine>` | 合并业务数据模型 `FormField` 与表格列定义 `TableColumnDefine`。以 `FormField` 为基础生成 `FormFieldDefine`，并从 `tableColDefines` 中合并别名、样式、数据过滤函数、扩展信息等。此外，该方法还会将 `tableColDefines` 中标记为 `isCustom()` 的自定义列作为新的 `FormFieldDefine` 添加到结果列表中。 |
| `getTableSettingItems` | 无 | `List<SettingItemDto>` | 从 `resource/TableSetting.json` 文件中读取内容，并将其反序列化为 `List<SettingItemDto>` 对象列表。用于加载表格的通用设置项。 |
| `getAppViewSettingItems` | 无 | `List<SettingItemDto>` | 从 `resource/ApplicationSetting.json` 文件中读取内容，并将其反序列化为 `List<SettingItemDto>` 对象列表。用于加载应用视图的通用设置项。 |
| `getTreeSettingItems` | 无 | `List<SettingItemDto>` | 从 `resource/TreeSetting.json` 文件中读取内容，并将其反序列化为 `List<SettingItemDto>` 对象列表。用于加载树组件的通用设置项。 |
| `getCardSettingItems` | 无 | `List<SettingItemDto>` | 从 `resource/CardSetting.json` 文件中读取内容，并将其反序列化为 `List<SettingItemDto>` 对象列表。用于加载卡片组件的通用设置项。 |
| `getFormSettingItems` | 无 | `List<SettingItemDto>` | 从 `resource/FormSetting.json` 文件中读取内容，并将其反序列化为 `List<SettingItemDto>` 对象列表。用于加载表单组件的通用设置项。 |
| `buildPreloadTree` | `<T extends Form> List<T> children`, `String parentKeyFieldName` | `List<PreloadTreeNode<T>>` | 根据给定的子节点列表 `children` 和表示父节点ID的字段名 `parentKeyFieldName`，构建一个 `PreloadTreeNode` 类型的树结构。它通过Map存储节点，然后遍历构建父子关系。 |
| `trimObject` | `<T> T object` | `T` | 递归地对目标对象 `object` 中所有的字符串类型属性（包括嵌套的List、Array、Map中的字符串值和Map的键）进行去空格处理（trim）。对于基本类型和其包装类直接返回原值。 |

### 4. 对外依赖与交互

`GpfDCBasicUtil` 文件广泛依赖于多个内部和外部库，主要用于数据处理、系统配置、UI渲染和通用工具功能。

**重要的外部库/项目内类及其交互方式：**

*   **`com.kwaidoo.ms.tool.ToolUtilities`**: 提供了大量的通用工具方法，本文件与其交互频繁，用于：
    *   `ToolUtilities.getInteger/getLong/getDouble/getFloat`: 字符串到数字类型的转换。
    *   `ToolUtilities.getAllFieldMap/getAllField`: 获取类的所有字段，用于反射操作。
    *   `ToolUtilities.setFieldValue/getFieldValue`: 通过反射设置和获取字段值。
    *   `ToolUtilities.copyFields`: 复制对象字段值。
    *   `ToolUtilities.getAllAddress`: 获取本地所有IP地址。
*   **`com.leavay.common.util.Utils`**: 提供文件操作和流关闭的工具，用于：
    *   `Utils.getFileContent`: 读取文件内容。
    *   `Utils.close`: 关闭输入流。
*   **`com.leavay.common.util.javac.ClassFactory`**: 用于获取类路径下的资源URL，本文件用于加载JSON配置文件：
    *   `ClassFactory.getResourceURL`: 获取 `resource/*.json` 文件的URL。
*   **`com.leavay.ms.tool.CmnUtil`**: 提供通用辅助方法，用于：
    *   `CmnUtil.getInteger/getLong/getDouble/getFloat/getBoolean`: 字符串到各种数据类型的转换。
    *   `CmnUtil.isStringEqual`: 字符串相等判断。
    *   `CmnUtil.isStringEmpty`: 字符串是否为空判断。
    *   `CmnUtil.isInheritFrom`: 判断一个类是否继承或实现了另一个类/接口。
*   **`cell.cmn.IJson`, `cell.cmn.IJsonService`**: JSON处理框架的接口，用于：
    *   `IJsonService.get().getJson()`: 获取JSON服务实例。
    *   `json.fromJsonByType`: 将JSON字符串反序列化为指定泛型类型对象。
*   **`cell.cmn.util.IServerConfig`**: 服务器配置接口，用于：
    *   `IServerConfig.get().getString`: 获取字符串类型的服务器配置项（如 `web.access.whitelist`）。
    *   `IServerConfig.get().getHttpPort/getRpcPort`: 获取HTTP和RPC端口配置。
*   **`cell.fe.gpf.dc.basic.IAppCacheMgr`, `gpf.dc.basic.fe.intf.AppCacheMgrIntf`**: 应用缓存管理接口，用于：
    *   `cacheMgr.getCacheValue/setCacheValue`: 获取和设置缓存值，用于优化 `UrlMsgDto` 和 `WebSocketUrl` 的获取。
*   **`cmn.dto.PreloadTreeNode`**: 用于构建预加载树结构的数据传输对象。
*   **`cmn.reflect.TypeToken`**: 泛型类型辅助类，用于在JSON反序列化时保留泛型信息。
*   **`cmn.util.ClassUtil`**: 类操作工具，用于判断是否为原始类型包装类。
*   **`cmn.util.StringUtil`**: 字符串工具类，用于字符串修剪。
*   **`cmn.util.TraceUtil`, `cmn.util.Tracer`**: 日志追踪工具，用于输出调试信息和追踪日志。
*   **`fe.cmn.data.UrlMsgDto`**: URL消息数据传输对象，封装了URL的主机、路径等信息。
*   **`fe.cmn.panel.PanelContext`**: 面板上下文对象，提供当前请求的通道信息。
*   **`fe.cmn.sys.QueryUrl`, `fe.cmn.sys.QueryWebSocketUrl`**: 查询URL和WebSocket URL的工具类，用于在缓存未命中时获取URL信息。
*   **`gpf.adur.data.DataType`, `gpf.adur.data.Form`, `gpf.adur.data.FormField`**: 定义了数据类型和表单字段的业务模型。
*   **`gpf.dc.basic.i18n.GpfDCBasicI18n`**: 国际化资源，用于获取提示信息。
*   **`gpf.dc.basic.param.view.dto.*`**: 定义了前端视图层的DTOs，如 `FormFieldDefine`, `SearchBarDefine`, `TableColumnDefine`, `SettingItemDto`，用于配置UI组件的显示和行为。
*   **`gpf.exception.VerifyException`**: 自定义验证异常类，在访问检查不通过时抛出。

文件通过方法调用、接口依赖和数据传输对象（DTO）与这些外部组件进行紧密的交互，实现了其核心功能。它作为中间层，将底层工具能力、系统配置、业务模型与前端视图配置有效衔接起来。

