---

### 1. 文件核心功能
`TableRowListener.java` 文件定义了一个特定于表格行事件的监听器。它继承自 `TableBasicListener`，旨在处理用户对表格行的交互事件。其核心职责包括：

*   **配置事件排除规则**：允许开发者指定某些类型的单元格（如具有内置点击事件的编辑器组件）不触发此行监听器，从而避免事件冲突或重复处理。
*   **配置数据返回策略**：通过一系列 `setBringBack...` 方法，它能够指定在事件发生时，是否需要将表格行DTO、表格行POJO、表格列DTO、表格单元格DTO等相关数据返回或携带。这对于后续的业务逻辑处理或UI更新至关重要。
*   **链式配置方法**：提供了多个重写的setter方法，都返回 `TableRowListener` 自身实例，支持链式调用，使得监听器的配置更为流畅和简洁。

它在项目中扮演的角色是提供一个可配置的、专门用于表格行级别事件处理的监听器，是前端UI组件与后端业务逻辑之间交互的一个桥梁，特别是在富客户端或Web应用中处理表格数据交互时非常有用。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class TableRowListener<T>` | `TableBasicListener<T>` | 负责监听表格行事件，并提供配置选项以排除特定单元格类型的事件触发，以及定义在事件触发时需要返回的数据（如行DTO、POJO、列DTO等）。它通过链式方法调用简化配置。 |

#### 方法与属性详情

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `serialVersionUID` | `long` | Java序列化ID，用于版本控制。 |
| `excludeEditorType` | `List<String>` | 一个字符串列表，包含需要排除在监听器触发范围之外的单元格编辑器类型（例如：`LabelDto.class.getName()`）。这些类型的单元格即使被点击，也不会触发此 `TableRowListener`。 |
| `public TableRowListener()` | 构造函数 | 无参数构造函数。 |
| `public TableRowListener(Class service, String command, boolean synchronize)` | 构造函数 | 带服务类、命令和同步标志的构造函数，调用父类构造器。 |
| `public TableRowListener(Class service, String command, boolean synchronize, T data)` | 构造函数 | 带服务类、命令、同步标志和数据的构造函数，调用父类构造器。 |
| `public List<String> getExcludeEditorType()` | `List<String>` | 获取当前配置的排除编辑器类型列表。 |
| `public TableRowListener setExcludeEditorType(List<String> excludeEditorType)` | `TableRowListener` | 设置需要排除的编辑器类型列表，并返回当前 `TableRowListener` 实例，支持链式调用。 |
| `@Override public TableRowListener setBringBackTableRowDto(Boolean bringBackTableRowDto)` | `TableRowListener` | 重写父类方法，设置是否需要在事件中返回 `TableRowDto`，并支持链式调用。 |
| `@Override public TableRowListener setBringBackTableRowFePojo(Boolean bringBackTableRowFePojo)` | `TableRowListener` | 重写父类方法，设置是否需要在事件中返回 `TableRowFePojo`，并支持链式调用。 |
| `@Override public TableRowListener setBringBackTableColumnDto(Boolean bringBackTableColumnDto)` | `TableRowListener` | 重写父类方法，设置是否需要在事件中返回 `TableColumnDto`，并支持链式调用。 |
| `@Override public TableRowListener setBringBackTableCellDto(Boolean bringBackTableCellDto)` | `TableRowListener` | 重写父类方法，设置是否需要在事件中返回 `TableCellDto`，并支持链式调用。 |
| `@Override public TableRowListener setExecutor(ListenerExecutorDto executor)` | `TableRowListener` | 重写父类方法，设置事件执行器，并支持链式调用。 |
| `@Override public TableRowListener setServerExecutor(Class service, String command)` | `TableRowListener` | 重写父类方法，设置服务器端执行器（服务类和命令），并支持链式调用。 |
| `@Override public TableRowListener setEventExecutor(EventDto event)` | `TableRowListener` | 重写父类方法，设置事件执行器（事件DTO），并支持链式调用。 |
| `@Override public TableRowListener setSynchronize(boolean synchronize)` | `TableRowListener` | 重写父类方法，设置是否同步执行，并支持链式调用。 |
| `@Override public TableRowListener setData(T data)` | `TableRowListener` | 重写父类方法，设置监听器携带的数据，并支持链式调用。 |
| `@Override public TableRowListener setSelfBinaryData()` | `TableRowListener` | 重写父类方法，设置是否使用二进制数据（具体含义取决于父类），并支持链式调用。 |

### 3. 主要函数/方法 (如果适用)
该文件主要定义了一个类及其成员方法，不包含独立的工具类函数。

### 4. 对外依赖与交互
`TableRowListener.java` 文件依赖于以下外部类或项目内其他模块：

*   **`java.util.List`**: 用于定义 `excludeEditorType` 属性，存储需要排除的字符串列表。
*   **`fe.cmn.event.EventDto`**: 用于在 `setEventExecutor` 方法中配置事件执行器，表明监听器可能通过一个 `EventDto` 来触发或携带事件信息。
*   **`fe.cmn.widget.ListenerExecutorDto`**: 用于在 `setExecutor` 方法中配置监听器的执行逻辑，这可能是一个通用的执行器接口或数据传输对象。
*   **`fe.cmn.table.listener.TableBasicListener<T>`**: `TableRowListener` 的父类。它继承了 `TableBasicListener` 的基础功能和属性（如 `service`, `command`, `synchronize`, `data` 以及各种 `bringBack...` 属性的声明），并对其进行特化和重写，特别是将setter方法的返回类型从父类的 `TableBasicListener` 改为 `TableRowListener`，以支持更具体的链式调用。

**交互方式：**

*   `TableRowListener` 通过继承 `TableBasicListener` 重用其核心功能，并在此基础上增加了针对表格行事件的特有配置（如 `excludeEditorType`）。
*   它通过构造函数和各种setter方法接受外部配置，包括服务类、命令、数据、同步标志以及事件和执行器DTO，从而将监听器的行为与具体的业务逻辑和执行机制解耦。
*   `excludeEditorType` 属性表明它与UI组件的渲染和事件分发机制有紧密联系，能够根据UI组件的类型来过滤事件。
*   `setBringBack...` 方法系列暗示了它在事件触发后，会将特定类型的数据（如各种DTO和POJO）准备好，供后续的数据绑定、UI更新或业务处理使用，这通常涉及到数据层或视图层的数据传递。

