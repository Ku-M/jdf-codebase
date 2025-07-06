作为一名资深的Java软件工程师，对 `AbsEmbedTablePanel.java` 文件进行了深入分析。

---

### 1. 文件核心功能

`AbsEmbedTablePanel.java` 文件定义了一个抽象类，它是一个用于在表单中嵌入表格组件的基类。其核心职责是：

*   **提供内嵌表格的通用结构和行为**：抽象出内嵌表格的构建、数据查询、行操作（新增、更新、删除）、以及表格可写性切换等公共逻辑。
*   **优化数据存储与传输**：该组件的一个关键设计是将原始的DTO（Data Transfer Object）数据直接存储在 `TableRowDto` 的 `BinaryData` 字段中。这简化了数据获取流程，即在需要时直接从 `TableRowDto` 中取出 `BinaryData` 即可获得完整的原始数据对象，避免了复杂的字段映射和解析。
*   **提供可扩展性**：通过抽象方法，将具体的表格表头定义、原始数据到表格行DTO的转换、以及表格生命周期中的钩子方法（如 `afterQueryTableMeta`, `onAfterQueryTableRows`）留给子类实现，从而实现高度的定制化。
*   **处理表格的可写性切换**：能够根据配置或运行时状态，动态切换表格的读写模式，并在模式切换时重建表格以反映状态变化。

它在项目中扮演的角色是提供一个标准化的、易于扩展的内嵌表格解决方案，特别适用于那些数据对象需要直接作为行数据进行存储和操作的场景。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `AbsEmbedTablePanel<T extends EmbedTableParam<?>>` | `AbsBasicTablePanel<T>` | 作为表单内嵌表格组件的抽象基类，负责处理表格的构建、数据的查询与展示、行操作及可写性管理。它强制要求子类实现数据对象的转换和表头定义。 |

#### 方法与属性详情

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `serialVersionUID` | `long` | 序列化ID。 |
| `doCreateRowData(PanelContext, Object)` | `TableRowDto` | 重写父类方法，将原始行数据通过 `convert2TableRowDto` 转换为 `TableRowDto`。 |
| `doUpdateRowData(PanelContext, Object)` | `TableRowDto` | 重写父类方法，将原始行数据通过 `convert2TableRowDto` 转换为 `TableRowDto`。 |
| `isShowConfirmWhenDelete()` | `boolean` | 重写父类方法，返回 `false`，表示删除行数据时不显示确认提示。 |
| `doDeleteRowData(PanelContext, List<String>)` | `void` | 重写父类方法，空实现。表明内嵌表格的删除操作可能不通过此通用接口或由外部处理。 |
| `doRebuildTableQuerier(ListenerDto, PanelContext, WidgetDto)` | `TableQuerier` | 重写父类方法，创建一个新的 `TableQuerier`，并将当前类的实例作为其 `BinaryData`，以便在查询时获取相关参数。 |
| `getWidget(PanelContext)` | `WidgetDto` | **核心方法**。构建并返回一个 `TableDto` 对象，这是整个内嵌表格的UI表示。该方法配置表格的各种属性，如是否显示复选框、是否可拖拽、是否可操作、行点击事件、最大高度、操作列按钮等，并将 `widgetParam` 作为表格的二进制数据。 |
| `getTableHeader()` | `abstract List<PairDto<String, String>>` | **抽象方法**。强制子类实现，用于定义表格的表头信息（列名和显示标签）。 |
| `buildHeader(List<PairDto<String, String>>)` | `TableHeaderDto` | 根据传入的表头键值对列表，构建 `TableHeaderDto`。所有表格列的编辑器被设置为 `LabelDto`，表示这些列不可编辑。 |
| `queryTableMeta(TableBuilder, TableQuerierContext)` | `TableHeaderDto` | 重写父类方法，负责查询并构建表格的元数据（表头）。它从 `TableQuerierContext` 中获取 `widgetParam`，然后调用 `getTableHeader` 和 `buildHeader`。 |
| `afterQueryTableMeta(TableBuilder, TableQuerierContext, TableHeaderDto)` | `void` | 可重写方法，在表格元数据查询完成后触发，允许子类进行额外操作。 |
| `queryTableRows(TableBuilder, TableQuerier, TableQuerierContext)` | `TableRowsDto` | 重写父类方法，负责查询并构建表格的行数据。它从 `TableQuerierContext` 中获取 `widgetParam`，遍历 `widgetParam` 中存储的原始数据列表，通过 `convert2TableRowDto` 转换为 `TableRowDto`，并设置行ID。 |
| `onAfterQueryTableRows(TableBuilder, TableQuerier, TableQuerierContext, TableRowsDto)` | `void` | 可重写方法，在表格行数据查询完成后触发，允许子类进行额外操作。 |
| `setWritable(PanelContext, WidgetDto, boolean)` | `void` | 重写父类方法，用于设置表格的可写性。当可写状态改变时，它会查询当前表格的所有行数据，将原始数据重新收集到 `widgetParam` 中，更新 `widgetParam` 的可写状态，然后通过 `RebuildChild.rebuild` 机制重建整个表格UI。 |
| `convert2TableRowDto(Object)` | `abstract TableRowDto` | **抽象方法**。强制子类实现，用于将一个原始的数据对象（DTO）转换为 `TableRowDto`。这个方法是该组件设计理念的核心，因为它负责将DTO封装到 `TableRowDto` 的 `BinaryData` 中。 |

### 3. 主要函数/方法 (如果适用)

该文件不包含独立的工具类方法，所有功能都封装在 `AbsEmbedTablePanel` 类及其继承体系中。

### 4. 对外依赖与交互

`AbsEmbedTablePanel` 依赖并与多个外部库和项目内部类进行交互：

*   **Java标准库**:
    *   `java.util.ArrayList`, `java.util.LinkedList`, `java.util.List`: 用于处理集合数据。

*   **通用工具库**:
    *   `com.kwaidoo.ms.tool.ToolUtilities`: 提供通用工具方法，如 `allockUUIDWithUnderline()` 用于生成唯一ID。
    *   `com.leavay.ms.tool.CmnUtil`: 提供常用的工具方法，如 `isStringEmpty()` 和 `isCollectionEmpty()` 用于字符串和集合的判空。

*   **FE (项目内部) 公共模块 (`fe.cmn`)**:
    *   **数据结构 (`fe.cmn.data`)**: `FePojo`, `PairDto`。
    *   **面板相关 (`fe.cmn.panel`)**: `PanelContext` (提供面板上下文信息)。
    *   **面板能力 (`fe.cmn.panel.ability`)**: `RebuildChild` (用于重建子组件，在 `setWritable` 中用于刷新表格UI)。
    *   **表格相关 (`fe.cmn.table`)**: 这是最核心的依赖，定义了构建和操作表格所需的各种DTO和接口：
        *   `TableBuilder`, `TableQuerier`, `TableOpener`: 构建和查询表格的引擎。
        *   `TableDto`, `TableHeaderDto`, `TableColumnDto`, `TableRowDto`, `TableRowsDto`: 表格的UI和数据模型DTO。
        *   `ColumnWidthAutoFitType`, `OperateTableColumnDto`, `TableRowGestureDetectorDto`, `TableValueAdapter_AllRows`: 表格的配置和行为DTO。
        *   `TableQuerierContext`: 表格查询上下文。
        *   `QueryTableRows`: 表格行查询的通用接口或工具。
    *   **UI组件 (`fe.cmn.widget`)**: `ButtonDto`, `LabelDto`, `ListenerDto`, `SizeDto`, `WidgetDto`, `DecorationDto` (用于构建表格内部的UI元素，如按钮、标签)。

*   **FE (项目内部) 工具模块 (`fe.util`)**:
    *   `fe.util.FeLayoutUtil`: 布局工具类，如 `caculateRowOperateWidth()` 用于计算操作列宽度。
    *   `fe.util.FeListenerUtil`: 监听器工具类，如 `OnTableRowClick()` 用于创建表格行点击监听器。

*   **FE (项目内部) 组件相关DTO和监听器**:
    *   `fe.util.component.dto.FeDeliverData`: 数据传递DTO。
    *   `fe.util.component.dto.TableSetting`: 表格的配置设置。
    *   `fe.util.component.extlistener.OnTableRowChanged`: 表格行变化的扩展监听器。
    *   `fe.util.component.param.EmbedTableParam`: 内嵌表格的参数泛型父类，包含了表格的数据和配置。

*   **FE (项目内部) 样式模块**:
    *   `fe.util.style.FeStyleConst`: 定义UI样式常量。

**交互方式**:

*   **通过继承**: 继承 `AbsBasicTablePanel`，重写其生命周期方法和核心业务方法，如 `getWidget`, `queryTableMeta`, `queryTableRows`, `setWritable` 等。
*   **通过泛型参数 `T`**: 强制子类传入一个 `EmbedTableParam` 的子类作为表格的配置和数据源。
*   **通过抽象方法**: 定义了 `getTableHeader()` 和 `convert2TableRowDto()` 两个抽象方法，强制子类实现具体的业务逻辑，如定义表头和数据转换。
*   **通过依赖注入/上下文**: 通过 `PanelContext` 获取当前面板的上下文信息，进行组件的渲染和状态管理。
*   **通过事件监听**: 设置 `TableRowGestureDetectorDto` 的 `OnClick` 监听器和 `OnTableRowChanged` 扩展监听器，响应用户交互事件。
*   **通过工具类协作**: 广泛使用 `CmnUtil`, `ToolUtilities`, `FeLayoutUtil`, `FeListenerUtil` 等工具类来辅助完成各种操作。
*   **通过数据封装**: 将 `EmbedTableParam` 对象封装到 `TableDto` 的 `BinaryData` 中，以及将原始数据对象封装到 `TableRowDto` 的 `BinaryData` 中，实现了数据在UI层和业务逻辑层之间的高效传递和解耦。
*   **通过能力接口**: 调用 `RebuildChild.rebuild` 进行UI刷新。

