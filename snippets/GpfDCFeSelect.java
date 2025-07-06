`GpfDCFeSelect.java` 文件分析

### 1. 文件核心功能

`GpfDCFeSelect.java` 文件是一个核心的工具类，专门用于在应用程序的前端（FE）或设计中心（DC）模块中生成各种可供选择的项（例如，下拉列表、多选框的选项）和配置树结构。它通过反射、动态类加载、枚举处理以及国际化等手段，提供了一系列静态方法来：

1.  **生成枚举类型的选择项：** 将Java枚举转换为可供前端展示的键值对列表，并支持国际化标签。
2.  **生成表单字段和模型相关的选择项：** 动态发现和加载特定基类或接口的扩展实现类（如表单模型扩展、字段扩展、数据转换器），并将其作为选择项提供给前端，通常用于配置或插件化功能。
3.  **构建字段修改权限配置树：** 递归地生成一个表单字段的读写权限配置树结构，支持嵌套模型和排除特定字段。
4.  **提供通用的选择项过滤功能。**

它在整个项目中扮演着**数据提供者和配置构造器**的角色，是前端UI组件（如表单设计器、字段属性编辑器）动态加载和展示选项的重要后端支持。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :-------- | :---------- | :------- |
| `public class GpfDCFeSelect` | `FeSelectUtil` | 提供一系列静态工具方法，用于生成前端界面所需的选择项数据（如枚举、动态加载的扩展类）和表单字段的权限配置树。 |

#### 方法与属性详情

| 方法/属性 | 类型 | 描述 |
| :-------- | :--- | :--- |
| `public static PairDto<String, String>[] getFormFieldDataTypeSelectItems()` | `PairDto<String, String>[]` | 获取所有表单字段数据类型 (`DataType`) 的选择项列表，排除了特定类型（如二进制、图片、子表单、关键字段等），并进行国际化处理。 |
| `public static PairDto<String, String>[] getEnumItems(Class<? extends Enum> clazz,boolean labelWithPrefix)` | `PairDto<String, String>[]` | 将任意枚举类的所有枚举常量转换为键值对列表。`key` 为枚举名，`value` 为国际化后的中文标签。`labelWithPrefix` 参数控制标签是否包含类名前缀。 |
| `public static PairDto<String, String>[] getEnumItemsInGroup(Class<? extends Enum> clazz,AbsI18n i18n)` | `PairDto<String, String>[]` | 将枚举转换为键值对列表，标签通过指定`AbsI18n`实例的 `formatInGroup` 方法进行国际化处理，适用于分组国际化场景。 |
| `public static String getEnumCnLabel(Class<? extends Enum> clazz,String name)` | `String` | 根据枚举类和枚举名获取对应的国际化中文标签。 |
| `public static String getEnumCnLabel(Enum e)` | `String` | 获取单个枚举实例的国际化中文标签，标签格式为 `枚举类简单名_枚举常量名`。 |
| `public static PairDto<String, String>[] getEnumItemsByGroup(Class<? extends Enum> clazz)` | `PairDto<String, String>[]` | 转换为键值对列表，标签通过 `getEnumCnLabelByGroup` 方法获取，用于基于分组的国际化标签。 |
| `public static String getEnumCnLabelByGroup(Enum e)` | `String` | 获取单个枚举实例的国际化中文标签，通过 `GpfDCFeI18n` 实例的 `formatInGroup` 方法进行分组国际化处理。 |
| `public static List<TmpltAllowModifyFieldSetting> getAllModifyFieldTree(String parentPath,ActionModel model,List<TmpltAllowModifyFieldSetting> orgFieldSettings,Set<String> excludeFields)` | `List<TmpltAllowModifyFieldSetting>` | **（重载方法之一）** 递归构建允许修改的实例参数读写配置树。根据 `ActionModel` 中的字段列表，生成 `TmpltAllowModifyFieldSetting` 列表，支持嵌套模型 (`DataType.NestingModel`)、排除字段和合并现有配置。 |
| `public static List<TmpltAllowModifyFieldSetting> getAllModifyFieldTree(String parentPath,FormModel model,List<TmpltAllowModifyFieldSetting> orgFieldSettings,Set<String> excludeFields)` | `List<TmpltAllowModifyFieldSetting>` | **（重载方法之二）** 功能同上，但参数类型为 `FormModel`，适用于直接基于表单模型的树构建。 |
| `public static PairDto[] getFormModelExtendOptions(FormModel model)` | `PairDto[]` | 动态查找并加载 `BaseFormModelExtend` 的子类，如果这些子类实现了 `FormModelDisplayIntf` 且被当前 `FormModel` 接受，则将其作为可选扩展项返回。标签来源于 `@Comment` 注解或类简单名。 |
| `public static PairDto[] filterItems(PairDto[] items,String keyword)` | `PairDto[]` | 根据关键字过滤 `PairDto` 数组，匹配键或值（不区分大小写）。 |
| `public static PairDto[] getFormFieldExtendOptions(FormField field)` | `PairDto[]` | 动态查找并加载 `BaseFormFieldExtend` 的子类，如果实现了 `FormFieldDisplayIntf` 且被当前 `FormField` 接受，则将其作为可选扩展项返回。标签来源于 `ClassDeclare` 注解。 |
| `public static PairDto[] getFormFieldDataTransClassOptions(String dataType)` | `PairDto[]` | 动态查找并加载 `FormFieldDataTransIntf` 的实现类，如果它们被指定的数据类型 `dataType` 接受，则作为可选的数据转换类项返回。标签来源于 `ClassDeclare` 注解。 |
| `public static List<PairDto> getCustomImageItems()` | `List<PairDto>` | 动态查找并加载 `CustomImageIntf` 的实现类，将其作为自定义图片选择项返回。标签来源于 `@Comment` 注解。 |

### 3. 主要函数/方法 (如果适用)

所有方法都是静态工具方法，已在“方法与属性详情”中详细描述。该文件不包含独立于类的函数。

### 4. 对外依赖与交互

该文件依赖于多个外部库和项目内部类：

*   **Java标准库：**
    *   `java.lang.reflect.Modifier`: 用于在动态加载类时检查类的修饰符（如是否为抽象类）。
    *   `java.util.ArrayList`, `java.util.Arrays`, `java.util.HashSet`, `java.util.List`, `java.util.Map`, `java.util.Set`: 核心集合类，用于数据存储、转换和处理。

*   **Nutz框架：**
    *   `org.nutz.dao.entity.annotation.Comment`: Nutz Dao框架的注解，用于为类或字段提供注释，在此文件中被用于获取动态加载类的显示名称。

*   **Kwaidoo微服务工具：**
    *   `com.kwaidoo.ms.tool.CmnUtil`: 通用工具类，提供字符串操作（如`isStringEqual`, `getString`）等。
    *   `com.kwaidoo.ms.tool.ToolUtilities`: 通用工具类，提供列表与数组之间的转换 (`list2Array`)。

*   **Leavay公共工具：**
    *   `com.leavay.common.util.javac.ClassFactory`: **核心依赖**，用于在运行时动态搜索指定包下某个基类的所有子类，是实现插件化/扩展机制的关键。

*   **项目内部模块 (`gpf`, `cmn`, `fe`, `adur`)：**
    *   `cell.gpf.adur.data.IFormMgr`: 表单管理器接口，用于在构建树结构时查询嵌套表单模型。
    *   `cmn.anotation.ClassDeclare`: 自定义注解，可能用于声明类的元数据或中文标签，与`@Comment`作用类似。
    *   `cmn.i18n.AbsI18n`: 国际化抽象基类，`GpfDCFeI18n` 是其具体实现，用于提供多语言支持。
    *   `fe.cmn.data.PairDto`: 前端通用数据传输对象，表示键值对，广泛用于生成选择项。
    *   `fe.util.FeSelectUtil`: 当前类的父类，表明继承了基础的选择工具功能。
    *   `fe.util.intf.CustomImageIntf`: 自定义图片接口，用于动态加载自定义图片选项。
    *   `gpf.adur.action.ActionModel`, `gpf.adur.data.BaseFormFieldExtend`, `gpf.adur.data.BaseFormModelExtend`, `gpf.adur.data.DataType`, `gpf.adur.data.FormField`, `gpf.adur.data.FormFieldDataTransIntf`, `gpf.adur.data.FormModel`: 定义了表单、字段、数据类型、表单/字段扩展以及数据转换相关的核心数据模型和接口。这些是本文件操作的主要业务实体。
    *   `gpf.dc.fe.dto.fieldextend.FormFieldDisplayIntf`, `gpf.dc.fe.dto.modelextend.FormModelDisplayIntf`: 定义了表单字段和表单模型扩展在前端显示时必须实现的接口，通常包含一个 `accept` 方法用于判断该扩展是否适用于特定的字段或模型。
    *   `gpf.dto.model.TmpltAllowModifyFieldSetting`: 模板允许修改字段设置的数据传输对象，用于构建字段权限配置树。

**交互方式：**

*   **反射与动态加载：** 通过 `ClassFactory` 结合 `Modifier` 和 `isAssignableFrom` 来动态查找、过滤和实例化特定接口或基类的实现类。
*   **国际化：** 调用 `GpfDCFeI18n` 和 `AbsI18n` 的方法，将枚举名或其他标识符转换为用户可读的中文标签。
*   **数据模型操作：** 接收 `FormModel`、`FormField`、`ActionModel` 等业务数据模型作为输入，并根据其内部信息生成相应的选择项或配置树。
*   **层级调用：** 在 `getAllModifyFieldTree` 方法中，通过 `IFormMgr` 查询嵌套表单模型，实现递归构建。
*   **返回通用DTO：** 大部分方法返回 `PairDto` 数组或列表，这是一种通用的前端数据格式，便于UI组件直接使用。

