### 1. 文件核心功能
`GridDto.java` 文件定义了一个数据传输对象（DTO），用于描述和配置一个灵活的、类似Excel的二维单元格布局。它允许开发者通过两种主要方式（命名区域或行列坐标）来组织和放置UI组件。该文件是UI布局配置的基础，用于在运行时或设计时解释如何渲染一个网格化的用户界面。

它在项目中扮演的角色是：
*   **布局配置载体**: 封装了网格布局的所有必要参数，如行列尺寸、单元格间距、组件放置规则。
*   **UI渲染引擎的数据源**: 作为底层UI渲染或布局引擎的输入，指导其如何将组件放置在屏幕上。
*   **设计工具的抽象**: 结合自定义注解（如`@PojoMeta`, `@FieldDefine`），可能作为UI设计工具的数据模型，控制哪些字段可见或可编辑。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :-------- | :---------- | :------- |
| `public class GridDto` | `SpecialLayoutDto` | 定义网格布局的数据结构，支持按命名区域或行列坐标两种方式配置组件位置，并指定行列尺寸及间距。 |

#### 方法与属性详情

| 方法/属性 | 类型 | 描述 |
| :------------------------------ | :---------------------------------------- | :----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `serialVersionUID` | `long` | Java序列化ID。 |
| `NO_NAME` | `public final static String` | 常量，定义在 `areas` 数组中用于表示留空的单元格名称。 |
| `areas` | `public String[][]` | 二维字符串数组，用于定义网格的命名区域。每个字符串代表一个区域名称，相同名称的单元格会合并为一个区域。该字段通常由布局器控制 (`@FieldDefine(visible = false)` )。 |
| `areaMap` | `public Map<String, GridCell>` | 映射表，将 `areas` 中定义的区域名称与具体的 `GridCell`（可能包含 `WidgetDto`）关联起来。该字段通常由布局器控制 (`@FieldDefine(visible = false)`)。 |
| `blocks` | `public List<GridBlock>` | 列表，用于定义按行列索引放置的组件块。当使用此方式时，`areas` 和 `areaMap` 可不填。该字段通常由布局器控制 (`@FieldDefine(visible = false)`)。 |
| `columns` | `public List<GridSize>` | 列表，定义了每一列的尺寸约束（如固定像素、自动适应或弹性比例）。该字段标记为 `@NullSafe` 且由布局器控制 (`@FieldDefine(visible = false)`)。 |
| `rows` | `public List<GridSize>` | 列表，定义了每一行的尺寸约束。该字段标记为 `@NullSafe` 且由布局器控制 (`@FieldDefine(visible = false)`)。 |
| `rowGap` | `Double` | 行之间的间距。 |
| `columnGap` | `Double` | 列之间的间距。 |
| `getAreas()` | `String[][]` | 获取 `areas` 属性。 |
| `setAreas(String[][] areas)` | `GridDto` | 设置 `areas` 属性，并返回当前 `GridDto` 实例（链式调用）。 |
| `getAreaMap()` | `Map<String, GridCell>` | 获取 `areaMap` 属性。 |
| `setAreaMap(Map<String, GridCell> areaMap)` | `GridDto` | 设置 `areaMap` 属性，并返回当前 `GridDto` 实例（链式调用）。 |
| `setArea(String area, WidgetDto wgt)` | `GridDto` | 便捷方法，将一个 `WidgetDto` 包装成 `GridCell` 并设置到指定区域。 |
| `setArea(String area, GridCell wgt)` | `GridDto` | 设置指定区域的 `GridCell`。如果 `areaMap` 为空则初始化。 |
| `getBlocks()` | `List<GridBlock>` | 获取 `blocks` 属性。 |
| `setBlocks(List<GridBlock> blocks)` | `GridDto` | 设置 `blocks` 属性，并返回当前 `GridDto` 实例（链式调用）。 |
| `addBlock(GridBlock... blocks)` | `GridDto` | 添加一个或多个 `GridBlock` 到 `blocks` 列表中，使用 `ToolUtilities.addToList` 工具方法。 |
| `getColumns()` | `List<GridSize>` | 获取 `columns` 属性。 |
| `setColumns(GridSize... columns)` | `GridDto` | 通过变长参数设置 `columns` 属性，使用 `ToolUtilities.newArrayList` 将数组转为列表。 |
| `setColumns(List<GridSize> columns)` | `GridDto` | 设置 `columns` 属性（接受列表）。 |
| `getRows()` | `List<GridSize>` | 获取 `rows` 属性。 |
| `addRow(GridSize gridSize)` | `GridDto` | 添加一个 `GridSize` 到 `rows` 列表中。如果 `rows` 为空则初始化为 `LinkedList`。 |
| `setRows(GridSize... rows)` | `GridDto` | 通过变长参数设置 `rows` 属性，使用 `ToolUtilities.newArrayList` 将数组转为列表。 |
| `setRows(List<GridSize> rows)` | `GridDto` | 设置 `rows` 属性（接受列表）。 |
| `getRowGap()` | `Double` | 获取 `rowGap` 属性。 |
| `setRowGap(Double rowGap)` | `GridDto` | 设置 `rowGap` 属性，并返回当前 `GridDto` 实例（链式调用）。 |
| `getColumnGap()` | `Double` | 获取 `columnGap` 属性。 |
| `addColumn(GridSize gridSize)` | `GridDto` | 添加一个 `GridSize` 到 `columns` 列表中。如果 `columns` 为空则初始化为 `LinkedList`。 |
| `setColumnGap(Double columnGap)` | `GridDto` | 设置 `columnGap` 属性，并返回当前 `GridDto` 实例（链式调用）。 |
| `empty(int row, int column)` | `static GridDto` | 静态工厂方法，创建一个指定行数和列数的空 `GridDto`，其中所有行列尺寸默认为弹性分配（`flex=1`）。 |

### 3. 主要函数/方法 (如果适用)
`GridDto` 主要是一个数据容器，其方法多为属性的存取器和链式设置器。其中 `empty` 方法是一个值得注意的静态工厂方法。

| 函数名 | 参数 | 返回值 | 功能描述 |
| :----- | :--- | :----- | :------- |
| `empty` | `int row`, `int column` | `GridDto` | 创建一个新的 `GridDto` 实例，并为其添加指定数量的行和列。所有新添加的行和列都默认设置为弹性尺寸（`GridSize.flex(1)`），这意味着它们将平均分配可用空间。这提供了一个快速初始化一个基本网格布局的方法。 |

### 4. 对外依赖与交互
`GridDto.java` 依赖于多个外部类和项目内的其他模块，以实现其功能：

*   **Java标准库**:
    *   `java.util.HashMap`, `java.util.LinkedList`, `java.util.List`, `java.util.Map`: 用于存储和操作集合数据，例如 `areaMap` (HashMap), `blocks`, `columns`, `rows` (List可能内部是LinkedList或ArrayList)。
*   **项目内部依赖**:
    *   `com.leavay.common.util.ToolUtilities`: 一个公共工具类，用于简化集合操作，如 `addToList` (向列表中添加元素) 和 `newArrayList` (将数组转换为ArrayList)。这表明项目内部有一个统一的工具库。
    *   `fe.cmn.pojo.annotation.FieldDefine`: 自定义注解，可能用于标记POJO字段的元数据，例如 `visible = false` 表明该字段在某些UI或配置界面中不直接可见或不可编辑，通常由内部逻辑或布局引擎控制。
    *   `fe.cmn.pojo.annotation.PojoMeta`: 自定义注解，用于为POJO（Plain Old Java Object）提供元数据，如 `label` (名称) 和 `icon` (图标路径)。这通常用于设计工具或UI组件库，以便在图形界面中更好地表示和识别此DTO。
    *   `fe.cmn.widget.WidgetDto`: 表示一个抽象的UI组件或小部件的数据传输对象。`GridDto` 中的 `GridCell` 可能会封装 `WidgetDto`，从而将具体的UI组件放入网格布局中。
    *   `SpecialLayoutDto`: `GridDto` 的父类。虽然其具体定义在此文件中不可见，但可以推断它是所有特殊布局DTO的基类，可能包含了布局DTO通用的属性或方法。
    *   `GridCell`, `GridBlock`, `GridSize`, `GridSizeType`: 这些类未在此文件中定义，但被广泛使用。
        *   `GridCell`: 可能封装了要放置在网格单元格中的具体组件（如 `WidgetDto`）以及其在网格中的额外属性。
        *   `GridBlock`: 可能定义了一个组件在网格中的具体位置和跨度（例如，起始行列、占用的行列数）。
        *   `GridSize`: 定义了行列的尺寸约束（例如固定像素、自适应或弹性比例）。
        *   `GridSizeType`: 枚举，定义了 `GridSize` 的类型（例如 `fix`, `auto`, `flex`）。
*   **特定框架或工具依赖**:
    *   `flutter.coder.annt.NullSafe`: 一个自定义注解，可能与代码生成或特定框架（如与Flutter相关的代码生成）的空安全检查机制有关。这暗示了该Java后端/DTO可能与一个基于Flutter的前端或代码生成工具集成。

**交互方式**:
`GridDto` 作为数据模型，主要通过其公开的getter和setter方法与外部系统进行交互。
1.  **配置阶段**: 外部系统（如UI设计器、配置工具或代码）可以调用 `GridDto` 的setter方法来构建所需的网格布局配置，包括定义行列尺寸、命名区域或添加组件块。
2.  **渲染阶段**: UI渲染引擎或布局解析器会读取 `GridDto` 的属性（`areas`, `areaMap`, `blocks`, `rows`, `columns`, `rowGap`, `columnGap`）来理解布局意图，并据此将实际的 `WidgetDto`s 渲染到正确的网格位置和尺寸上。
3.  **元数据集成**: `PojoMeta` 和 `FieldDefine` 注解可能被反射机制读取，以指导外部工具如何展示和操作 `GridDto` 的属性。`NullSafe` 注解可能在编译时或代码生成阶段发挥作用。

