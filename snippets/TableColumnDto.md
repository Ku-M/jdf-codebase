我们来分析 `TableColumnDto.java` 文件。

---

### 1. 文件核心功能
`TableColumnDto` 是一个数据传输对象（DTO），主要用于封装和定义表格中单个列的各种配置信息。它继承自 `FieldMeta`，因此包含了字段的基本元数据（如名称、标签、描述、是否只读、默认编辑器等），并在此基础上增加了表格列特有的展示、交互和行为属性。

在表格组件的构建和渲染过程中，`TableColumnDto` 为每一列提供了详细的配置蓝图和行为定义，例如：列的宽度控制、是否隐藏、是否可拖拽、冻结位置、表头按钮、装饰样式、工具提示、宽度自适应策略、单元格编辑器ID模板以及是否开启界面排序等。它使得表格列的配置集中化、模块化，方便前端UI组件进行解析和渲染。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class TableColumnDto` | `FieldMeta` | 定义和封装表格列的显示、行为和交互配置，是表格列配置的核心数据结构。 |

#### 方法与属性详情

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `private static final long serialVersionUID` | `long` | 序列化版本UID。 |
| `public static final String rowIdVariable` | `String` | 静态常量，定义行ID变量的占位符字符串（"${rowId}"），用于模板替换。 |
| `public static final String columnNameVariable` | `String` | 静态常量，定义列名称变量的占位符字符串（"${columnName}"），用于模板替换。 |
| `minWidth` | `double` | 列的最小宽度。 |
| `maxWidth` | `double` | 列的最大宽度。 |
| `fixWidth` | `double` | 列的固定宽度。 |
| `hide` | `Boolean` | 是否隐藏该列（默认为 `false`）。 |
| `enableColumnDrag` | `Boolean` | 是否允许列拖拽以改变列序。 |
| `columnFrozenType` | `ColumnFrozenType` | 列的冻结类型（例如：不冻结、左冻结、右冻结）。 |
| `columnDragIconPositionType` | `ColumnDragIconPositionType` | **(已废弃)** 列拖拽图标的位置类型。 |
| `gestureDetector` | `TableColumnGestureDetectorDto` | 定义列（通常是表头）的各种手势检测器配置（如点击、长按等）。 |
| `enableToggleAll` | `Boolean` | 是否在表头显示一个布尔值总控编辑器。该编辑器能切换控制整列同类型布尔值单元格的值。默认值为 `false`。 |
| `buttons` | `List<WidgetDto>` | 定义显示在表头区域的按钮列表。 |
| `decoration` | `TableColumnDecorationDto` | 列的装饰设置，用于定义列的视觉样式（如背景、边框、阴影等）。 |
| `toolTip` | `ToolTipDto` | 列的工具提示设置，鼠标悬停时显示。 |
| `autoFitType` | `ColumnWidthAutoFitType` | 列的宽度自适应控制类型。仅在表格开启列自适应且此列无固定宽度时生效。 |
| `editorWidgetIdTemplate` | `String` | 单元格编辑器的 `widgetId` 模板，可使用 `rowIdVariable` 和 `columnNameVariable` 进行动态替换。 |
| `enableGuiSorting` | `Boolean` | 是否开启此列的界面排序功能（若为 `null`，则默认取表格设置）。 |
| `TableColumnDto()` | 构造函数 | 无参构造函数。 |
| `TableColumnDto(String name, String label)` | 构造函数 | 带有名称和标签的构造函数，方便快速初始化。 |
| `get*()` / `set*()` 方法 | 各种类型 / `TableColumnDto` | 为所有私有属性提供标准的 Getter 和 Setter 方法。Setter 方法通常返回 `this`，支持链式调用。 |
| `public TableColumnDto setButtons(WidgetDto... buttons)` | `TableColumnDto` | Setter 的重载方法，允许传入可变参数的 `WidgetDto` 数组来设置按钮列表，内部通过流转换为 `List`。 |
| `public TableColumnDto setName(String name)` | `TableColumnDto` | 覆写父类 `FieldMeta` 的 `setName` 方法，返回类型强制转换为 `TableColumnDto` 以支持链式调用。 |
| `public TableColumnDto setLabel(String label)` | `TableColumnDto` | 覆写父类 `FieldMeta` 的 `setLabel` 方法，返回类型强制转换为 `TableColumnDto` 以支持链式调用。 |
| `public TableColumnDto setDescription(String description)` | `TableColumnDto` | 覆写父类 `FieldMeta` 的 `setDescription` 方法，返回类型强制转换为 `TableColumnDto` 以支持链式调用。 |
| `public TableColumnDto setReadOnly(boolean readOnly)` | `TableColumnDto` | 覆写父类 `FieldMeta` 的 `setReadOnly` 方法，返回类型强制转换为 `TableColumnDto` 以支持链式调用。 |
| `public TableColumnDto setEditor(WidgetDto editor)` | `TableColumnDto` | 覆写父类 `FieldMeta` 的 `setEditor` 方法，返回类型强制转换为 `TableColumnDto` 以支持链式调用。 |

### 3. 主要函数/方法

该文件主要定义了一个 DTO 类及其属性的 Getter/Setter 方法。不包含独立的工具函数或静态方法，除了作为属性值的常量定义。

### 4. 对外依赖与交互

`TableColumnDto` 依赖于多个外部或项目内部的类和枚举，以构建其丰富的配置能力：

*   **继承 (`extends`)**:
    *   `fe.cmn.data.FieldMeta`: `TableColumnDto` 继承自 `FieldMeta`，这意味着它复用了 `FieldMeta` 中定义的字段基本元数据，如 `name`、`label`、`description`、`readOnly`、`editor` 等。这是其配置基础。

*   **组合 (`import` & 属性类型)**:
    *   `fe.cmn.table.decoration.TableColumnDecorationDto`: 用于定义列的视觉装饰效果。
    *   `fe.cmn.widget.ToolTipDto`: 用于配置列的工具提示。
    *   `fe.cmn.widget.WidgetDto`: 这是一个通用的UI组件DTO，用于表示列头中的按钮 (`buttons`) 以及单元格的默认编辑器 (`editor`，继承自 `FieldMeta` 的属性)。
    *   `fe.cmn.table.TableColumnGestureDetectorDto`: 用于定义列（特别是表头）上的手势交互。
    *   枚举类型（未在文件中定义，但作为属性类型被引用）：
        *   `ColumnFrozenType`: 定义列的冻结状态。
        *   `ColumnDragIconPositionType`: 定义列拖拽图标的位置（已废弃）。
        *   `ColumnWidthAutoFitType`: 定义列宽度的自适应行为。

*   **标准Java库**:
    *   `java.util.Arrays`, `java.util.List`, `java.util.stream.Collectors`: 用于处理集合数据，特别是在 `setButtons(WidgetDto... buttons)` 方法中，将可变参数转换为 `List`。

*   **自定义注解**:
    *   `flutter.coder.annt.DefaultGetter`: 这个注解（如 `@DefaultGetter("false")` 用于 `enableToggleAll` 字段）表明可能存在一个代码生成器、Lombok类似的处理器或运行时反射机制，它会在字段值为 `null` 时自动提供一个默认值，简化了调用方对默认值的处理。

**交互方式**:
1.  **作为配置载体**: `TableColumnDto` 实例通常由后端服务生成并传输到前端，或者由前端代码根据业务需求进行构建。
2.  **UI渲染引擎消费**: 前端UI框架（例如基于Flutter或Web的前端）会读取 `TableColumnDto` 的各种属性，以动态地渲染表格的列结构、样式、行为和交互逻辑。
3.  **数据绑定与模板**: `editorWidgetIdTemplate` 中的 `rowIdVariable` 和 `columnNameVariable` 提示，此DTO的消费者（通常是渲染引擎）会进行字符串替换，以生成唯一的单元格编辑器ID。
4.  **行为控制**: `enableColumnDrag`, `columnFrozenType`, `enableGuiSorting`, `enableToggleAll` 等布尔或枚举属性直接控制了表格列在用户界面上的交互行为。

