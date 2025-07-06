### 1. 文件核心功能
这个文件 `FormModelSelectorParam.java` 的主要职责是作为一个数据传输对象（DTO）或配置参数类，用于封装和传递配置信息给一个“模型选择器”UI组件。它定义了该组件在表单中显示和行为的各种属性，例如：是单选还是多选、显示哪些元素（如模型ID、管理入口、详情）、是否允许创建新模型，以及如何进行模型搜索（如父节点限制、可选模型列表）。

它在整个项目中扮演的角色是：
*   **配置载体**: 为前端或UI层提供一个标准化的参数集，以便于动态配置“模型选择器”组件的行为和外观。
*   **数据模型**: 定义了与模型选择器相关的业务逻辑和UI展示所需要的所有可配置项。
*   **解耦**: 将UI组件的配置从其实现逻辑中解耦出来，使得组件更具通用性和可复用性。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class FormModelSelectorParam` | `fe.util.component.param.BaseWidgetParam` | 定义一个模型选择器UI组件的配置参数集，包括其显示样式、选中状态、交互行为（如多选、是否允许创建、显示详情、显示管理入口）以及数据过滤规则（如搜索父节点、可选模型列表）。它是一个POJO（Plain Old Java Object），主要用于数据封装。 |

#### 方法与属性详情

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `serialVersionUID` | `long` | Java序列化ID，用于版本控制。 |
| `modelSelectComponent` | `String` | 模型选择器的具体组件样式或类型标识。 |
| `selectedModelId` | `String` | 当选择器为单选时，存储已选中的模型ID。 |
| `selectedModelIds` | `List<String>` | 当选择器为多选时，存储已选中的模型ID列表。默认初始化为空的`ArrayList`。 |
| `showModelIdAtLabel` | `boolean` | 配置在显示模型名称时是否同时显示模型ID。 |
| `showManageEntry` | `boolean` | 配置是否显示管理入口按钮，允许用户进入模型管理界面。 |
| `viewDetail` | `boolean` | 配置是否显示查看详情的入口，允许用户查看选中模型的详细信息。 |
| `allowCreate` | `boolean` | 配置是否允许用户通过选择器创建新的模型。 |
| `isMultiSelect` | `boolean` | 配置选择器是否支持多选功能。 |
| `parentModelIds` | `List<String>` | 配置模型搜索的起始父节点ID列表。如果`selectableModelIds`存在，此配置不生效。 |
| `parentModelSelectable` | `boolean` | 配置父节点是否可以被选中。 |
| `selectableModelIds` | `List<String>` | 配置可选的模型ID列表。如果此项有值，`parentModelIds`配置将不生效，选择器仅限于这些ID中的模型。 |
| `getModelSelectComponent()` | `String` | 获取模型选择样式。 |
| `setModelSelectComponent(String)` | `FormModelSelectorParam` | 设置模型选择样式，并返回当前对象，支持链式调用。 |
| `getSelectedModelId()` | `String` | 获取已选中的模型ID。 |
| `setSelectedModelId(String)` | `FormModelSelectorParam` | 设置已选中的模型ID，并返回当前对象，支持链式调用。 |
| `getSelectedModelIds()` | `List<String>` | 获取已选中的模型ID列表。 |
| `setSelectedModelIds(List<String>)` | `FormModelSelectorParam` | 设置已选中的模型ID列表，并返回当前对象，支持链式调用。 |
| `isShowModelIdAtLabel()` | `boolean` | 获取是否显示模型ID。 |
| `setShowModelIdAtLabel(boolean)` | `FormModelSelectorParam` | 设置是否显示模型ID，并返回当前对象，支持链式调用。 |
| `isViewDetail()` | `boolean` | 获取是否显示详情。 |
| `setViewDetail(boolean)` | `FormModelSelectorParam` | 设置是否显示详情，并返回当前对象，支持链式调用。 |
| `isAllowCreate()` | `boolean` | 获取是否允许新增。 |
| `setAllowCreate(boolean)` | `FormModelSelectorParam` | 设置是否允许新增，并返回当前对象，支持链式调用。 |
| `getParentModelIds()` | `List<String>` | 获取开始搜索的父节点ID列表。 |
| `setParentModelIds(List<String>)` | `FormModelSelectorParam` | 设置开始搜索的父节点ID列表，并返回当前对象，支持链式调用。 |
| `getSelectableModelIds()` | `List<String>` | 获取可选的模型节点ID列表。 |
| `setSelectableModelIds(List<String>)` | `FormModelSelectorParam` | 设置可选的模型节点ID列表，并返回当前对象，支持链式调用。 |
| `isShowManageEntry()` | `boolean` | 获取是否显示管理入口。 |
| `setShowManageEntry(boolean)` | `FormModelSelectorParam` | 设置是否显示管理入口，并返回当前对象，支持链式调用。 |
| `isMultiSelect()` | `boolean` | 获取是否多选。 |
| `setMultiSelect(boolean)` | `FormModelSelectorParam` | 设置是否多选，并返回当前对象，支持链式调用。 |
| `isParentModelSelectable()` | `boolean` | 获取父节点是否允许选中。 |
| `setParentModelSelectable(boolean)` | `FormModelSelectorParam` | 设置父节点是否允许选中，并返回当前对象，支持链式调用。 |

### 3. 主要函数/方法 (如果适用)
此文件主要定义了一个POJO类，不包含独立的工具函数或业务逻辑方法。其所有方法都是标准属性的getter和setter。

### 4. 对外依赖与交互
这个文件导入了以下重要的外部库或项目内的其他类：

*   **`java.util.ArrayList`**: 用于初始化`selectedModelIds`列表。
*   **`java.util.List`**: 用于定义存储模型ID的列表类型，如`selectedModelIds`, `parentModelIds`, `selectableModelIds`。
*   **`fe.util.component.param.BaseWidgetParam`**: 这是它继承的基类。这意味着`FormModelSelectorParam`扩展了系统中已有的基础组件参数定义，可能继承了通用的组件配置属性和行为。

**它可能如何与它们交互？**
*   `FormModelSelectorParam`作为参数对象，通常会被一个实际的UI组件（例如，一个名为`FormModelSelectorComponent`或类似的组件）在初始化或渲染时接收。该组件会读取此参数对象中的属性，以决定如何显示自己（例如，是否显示管理入口、是否显示模型ID）以及如何处理用户输入（例如，是单选还是多选）。
*   它通过继承`BaseWidgetParam`，可以利用基类中定义的通用参数，确保所有UI组件参数都遵循一定的规范和结构。
*   通过`List`和`ArrayList`，它能够存储和管理多个模型ID，支持多选场景和基于ID的过滤/搜索功能。
*   这个参数对象在前后端数据传输中也可能被序列化和反序列化，因为其`serialVersionUID`表明其是可序列化的，这允许配置信息在不同系统层之间安全地传递。

