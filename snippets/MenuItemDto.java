### 1. 文件核心功能
`MenuItemDto.java` 文件的核心职责是定义一个数据传输对象（DTO），用于表示用户界面中的单个菜单项。它封装了菜单项的各种属性，包括显示文本、图标、自定义头部控件、子菜单项（实现多级菜单）、点击事件监听器以及不同状态下的视觉装饰。

在整个项目中，`MenuItemDto` 扮演着数据模型或配置的角色，用于在前端（FE，Frontend）和后端之间传输菜单结构信息，或者在UI层内部构建和管理菜单的层级和行为。它是构建动态、可配置菜单系统的基础数据单元。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class MenuItemDto` | `CsonPojo` | 定义一个菜单项的数据结构。包含菜单项的视觉属性（如图标、标签、装饰）、交互行为（如点击事件）以及层级关系（子菜单项）。它是一个POJO（Plain Old Java Object），主要用于数据传输和序列化。 |

#### 方法与属性详情

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `serialVersionUID` | `long` | 序列化版本UID，用于确保序列化兼容性。 |
| `icon` | `String` | 菜单项的图标路径或名称。 |
| `label` | `String` | 菜单项的显示文本（标签）。 |
| `headerWidget` | `WidgetDto` | 一个可选的自定义头部控件，位于图标之前。如果设置，要求其 `prefSize`（首选尺寸）必须有 `width` 值，以便进行菜单项宽度计算。 |
| `subItems` | `List<MenuItemDto>` | 菜单项的子项列表，用于构建多级嵌套菜单。 |
| `onClick` | `ListenerDto` | 菜单项被点击时触发的事件监听器数据对象。 |
| `menuItemDecoration` | `MenuItemDecorationDto` | 菜单项在普通状态下的视觉装饰或样式。 |
| `highlightMenuItemDecoration` | `MenuItemDecorationDto` | 菜单项在高亮状态下的视觉装饰或样式。 |
| `getIcon()` | `String` | 获取菜单项的图标。 |
| `setIcon(String icon)` | `MenuItemDto` | 设置菜单项的图标，并返回当前 `MenuItemDto` 实例以支持链式调用。 |
| `getLabel()` | `String` | 获取菜单项的标签文本。 |
| `setLabel(String label)` | `MenuItemDto` | 设置菜单项的标签文本，并返回当前 `MenuItemDto` 实例以支持链式调用。 |
| `getHeaderWidget()` | `WidgetDto` | 获取菜单项的自定义头部控件。 |
| `setHeaderWidget(WidgetDto headerWidget)` | `MenuItemDto` | 设置菜单项的自定义头部控件。**注意：** 此方法内部包含断言，强制要求 `headerWidget` 必须设置 `preferSize` 且 `preferSize` 必须设置 `width`，否则会抛出运行时异常。这确保了布局计算的正确性。 |
| `getSubItems()` | `List<MenuItemDto>` | 获取菜单项的子项列表。 |
| `setSubItems(List<MenuItemDto> subItems)` | `MenuItemDto` | 设置菜单项的子项列表，并返回当前 `MenuItemDto` 实例以支持链式调用。 |
| `addSubItems(MenuItemDto ...menuItems)` | `void` | 向当前菜单项添加一个或多个子菜单项。如果 `subItems` 列表为空，则会初始化一个 `LinkedList`。它利用 `ToolUtilities.array2List` 将可变参数数组转换为列表并添加到子项中。 |
| `getOnClick()` | `ListenerDto` | 获取菜单项的点击事件监听器。 |
| `setOnClick(ListenerDto onClick)` | `MenuItemDto` | 设置菜单项的点击事件监听器，并返回当前 `MenuItemDto` 实例以支持链式调用。 |
| `getMenuItemDecoration()` | `MenuItemDecorationDto` | 获取菜单项的通用装饰样式。 |
| `setMenuItemDecoration(MenuItemDecorationDto menuItemDecoration)` | `MenuItemDto` | 设置菜单项的通用装饰样式，并返回当前 `MenuItemDto` 实例以支持链式调用。 |
| `getHighlightMenuItemDecoration()` | `MenuItemDecorationDto` | 获取菜单项高亮时的装饰样式。 |
| `setHighlightMenuItemDecoration(MenuItemDecorationDto highlightMenuItemDecoration)` | `MenuItemDto` | 设置菜单项高亮时的装饰样式，并返回当前 `MenuItemDto` 实例以支持链式调用。 |

### 3. 主要函数/方法 (如果适用)
此文件主要定义了一个数据模型类及其成员方法，不包含独立的工具类函数。所有方法都是 `MenuItemDto` 类的一部分。

### 4. 对外依赖与交互

*   **`java.util.LinkedList`**, **`java.util.List`**:
    *   **作用**: Java标准库中的集合接口和实现，用于存储和管理 `subItems`（子菜单项）的列表。
    *   **交互**: `MenuItemDto` 通过 `subItems` 属性使用 `List` 接口，并在 `addSubItems` 方法中具体使用 `LinkedList` 作为默认实现来存储子菜单项。

*   **`com.leavay.common.util.ToolUtilities`**:
    *   **作用**: 一个通用工具类库，提供了各种实用方法。
    *   **交互**: `MenuItemDto` 在 `addSubItems` 方法中调用 `ToolUtilities.array2List(menuItems)` 来将可变参数数组转换为 `List`，便于集合操作。

*   **`com.leavay.ms.tool.CmnUtil`**:
    *   **作用**: 一个通用工具类库，可能包含断言、字符串处理等常用功能。
    *   **交互**: `MenuItemDto` 在 `setHeaderWidget` 方法中调用 `CmnUtil.assertNotNull()` 来执行运行时断言，确保 `headerWidget` 及其尺寸属性的有效性，防止空指针异常或不完整的配置。

*   **`cson.core.CsonPojo`**:
    *   **作用**: 作为 `MenuItemDto` 的基类，它暗示 `MenuItemDto` 支持 CSON (Compact Serialized Object Notation) 格式的序列化和反序列化。`CsonPojo` 可能提供了将Java对象转换为CSON字符串或从CSON字符串解析为Java对象的能力，或者提供了其他POJO通用的反射或属性访问机制。
    *   **交互**: `MenuItemDto` 继承自 `CsonPojo`，因此它自动获得了 `CsonPojo` 提供的所有功能，使其适合作为数据传输和持久化的对象。

*   **`fe.cmn.widget.ListenerDto`**:
    *   **作用**: 一个数据传输对象，用于封装事件监听器的相关信息，例如点击事件的回调函数名、参数等。
    *   **交互**: `MenuItemDto` 通过 `onClick` 属性引用 `ListenerDto`，表示菜单项被点击时将触发的事件。

*   **`fe.cmn.widget.WidgetDto`**:
    *   **作用**: 一个数据传输对象，用于描述通用的UI控件或组件。可能包含组件的类型、ID、样式、布局属性等。
    *   **交互**: `MenuItemDto` 通过 `headerWidget` 属性引用 `WidgetDto`，允许在菜单项中嵌入一个自定义的UI控件，增加了菜单项的灵活性和可定制性。

*   **`fe.cmn.menu.MenuItemDecorationDto`**:
    *   **作用**: 一个数据传输对象，用于定义菜单项的视觉装饰或样式属性（如背景色、边框、字体样式等）。
    *   **交互**: `MenuItemDto` 通过 `menuItemDecoration` 和 `highlightMenuItemDecoration` 属性引用 `MenuItemDecorationDto`，分别用于定义菜单项在普通状态和高亮状态下的外观。

