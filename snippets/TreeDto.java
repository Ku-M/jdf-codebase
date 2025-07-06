### 1. 文件核心功能

`TreeDto.java` 文件的核心功能是定义了一个用于描述前端树形控件（Tree Widget）配置信息的Java数据传输对象（DTO）。它在整个项目中扮演着 **前后端通信协议** 和 **前端UI组件配置蓝图** 的角色。

具体职责包括：
*   封装树控件的各种属性，例如是否显示复选框、工具提示、拖拽能力、展开/勾选事件等。
*   通过 `service` 属性指定一个后端服务接口（`TreeInterface` 的实现），该服务负责提供树节点的数据初始化、查询以及弹出菜单等核心业务逻辑。
*   提供一套链式调用的setter方法（Fluent API），方便在后端代码中以简洁的方式配置前端树控件的各项属性。
*   包含自定义注解如 `@PojoMeta` 和 `@FieldDefine`，这表明它可能被一个元数据或代码生成框架使用，用于自动化生成前端UI代码、管理开发工具中的组件属性，或者进行表单配置。
*   作为 `PanelDto` 的子类，继承了通用UI面板的诸多属性，如布局、大小、可见性、手势识别、装饰等，从而使树控件具备了丰富的通用UI能力。

简而言之，它是后端服务向前端UI“描述”一个可交互、可配置的树控件的规范。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :-------- | :---------- | :------- |
| `public class TreeDto` | `PanelDto` | 定义前端树形控件的各种配置属性，并提供与后端服务、事件监听、节点交互相关的配置项，通过链式调用方便设置。 |

#### 方法与属性详情

**类: `TreeDto`**

| 方法/属性 | 类型 | 描述 |
| :------------------------------- | :----------------------------- | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `serialVersionUID` | `long` | 用于序列化的版本UID。 |
| `service` | `String` | 指定与树控件交互的后端服务接口的完整类名（该服务需实现 `TreeInterface`），负责数据获取和业务逻辑。 |
| `querier` | `TreeNodeQuerier` | 用于定制树节点查询逻辑的接口或对象。 |
| `onSelectValueChanged` | `OnSelectValueChanged` | 选中值改变时的回调监听器，用于响应树节点选择状态的变化。 |
| `showCheckbox` | `boolean` | 是否在树节点旁显示复选框（默认 `false`）。 |
| `showToolTip` | `boolean` | 是否在鼠标悬停时显示工具提示（默认 `false`）。 |
| `checkStrictly` | `boolean` | 在显示复选框时，是否严格遵循父子节点不互相关联的逻辑（默认 `false`）。 |
| `expandOnClick` | `Boolean` | **（已废弃）** 单击非叶子节点时是否自动展开子节点（默认 `true`）。 |
| `checkOnClick` | `Boolean` | **（已废弃）** 单击节点时是否自动勾选。 |
| `highlightEvents` | `List<TreeHighlightEvent>` | 定义哪些用户手势（如单击、双击、长按）会触发节点高亮显示（默认：单击、双击、长按）。 |
| `onTreeNodeHighlight` | `TreeHighlightListener` | 树节点高亮事件的监听器，用于处理节点高亮时的业务逻辑。 |
| `treeNodeGestureDetector` | `TreeNodeGestureDetector` | 用于处理树节点级手势事件的检测器，可以定义更复杂的交互行为。 |
| `alwaysShowButtonBar` | `Boolean` | 是否总是显示树控件的按钮栏（默认 `false`）。 |
| `TreeDto()` | 构造函数 | 无参构造函数，用于创建 `TreeDto` 实例。 |
| `TreeDto(String panelKey)` | 构造函数 | 带 `panelKey` 参数的构造函数，用于创建具有指定全局键的 `TreeDto` 实例。 |
| `getService()` | `String` | 获取当前设置的后端服务类名。 |
| `setService(Class<? extends TreeInterface> serviceCls)` | `TreeDto` | 设置后端服务接口，接受 `Class` 类型并将其全限定名保存为字符串，支持链式调用。 |
| `getQuerier()` | `TreeNodeQuerier` | 获取树节点查询器。 |
| `setQuerier(TreeNodeQuerier querier)` | `TreeDto` | 设置树节点查询器，支持链式调用。 |
| `isExpandOnSingleClick()` | `boolean` | 获取 `expandOnClick` 属性的值（考虑废弃状态和默认值）。 |
| `setExpandOnSingleClick(boolean expandOnSingleClick)` | `TreeDto` | 设置 `expandOnClick` 属性的值，支持链式调用。 |
| `getCheckOnClick()` | `Boolean` | 获取 `checkOnClick` 属性的值。 |
| `setCheckOnClick(Boolean checkOnClick)` | `TreeDto` | 设置 `checkOnClick` 属性的值，支持链式调用。 |
| `getHighlightEvents()` | `List<TreeHighlightEvent>` | 获取触发节点高亮事件的列表。 |
| `setHighlightEvents(List<TreeHighlightEvent> highlightEvents)` | `TreeDto` | 设置触发节点高亮事件的列表，支持链式调用。 |
| `setHighlightEvents(TreeHighlightEvent... highlightEvents)` | `TreeDto` | 以可变参数形式设置触发节点高亮事件，内部转换为列表，支持链式调用。 |
| `getOnTreeNodeHighlight()` | `TreeHighlightListener` | 获取节点高亮事件监听器。 |
| `setOnTreeNodeHighlight(TreeHighlightListener onTreeNodeHighlight)` | `TreeDto` | 设置节点高亮事件监听器，支持链式调用。 |
| `getTreeNodeGestureDetector()` | `TreeNodeGestureDetector` | 获取节点手势检测器。 |
| `setTreeNodeGestureDetector(TreeNodeGestureDetector treeNodeGestureDetector)` | `TreeDto` | 设置节点手势检测器，支持链式调用。 |
| `setPanelGlobalKey(String panelGlobalKey)` | `TreeDto` | 覆写父类方法，设置全局键，并支持链式调用。 |
| `setTopBar(WidgetDto topBar)` | `TreeDto` | 覆写父类方法，设置顶部栏组件，支持链式调用。 |
| `setBottomBar(WidgetDto bottomBar)` | `TreeDto` | 覆写父类方法，设置底部栏组件，支持链式调用。 |
| `setValueAdapter(PanelValueAdapter valueAdapter)` | `TreeDto` | 覆写父类方法，设置值适配器，支持链式调用。 |
| `setWidgetId(String widgetId)` | `TreeDto` | 覆写父类方法，设置组件ID，支持链式调用。 |
| `setDropListener(DropListener dropListener)` | `TreeDto` | 覆写父类方法，设置拖放监听器，支持链式调用。 |
| `setDecoration(DecorationDto decoration)` | `TreeDto` | 覆写父类方法，设置组件装饰器。**强制要求装饰器必须是 `TreeDecorationDto` 类型**，否则抛出运行时异常。 |
| **（其余众多覆写父类 `PanelDto` 的setter方法）** | `TreeDto` | 这些方法继承自 `PanelDto`，并被覆写以返回 `TreeDto` 自身，从而支持链式调用。它们涵盖了设置尺寸、可见性、拖拽能力、手势检测、样式等通用UI属性。 |
| `getOnSelectValueChanged()` | `OnSelectValueChanged` | 获取选中值改变监听器。 |
| `setOnSelectValueChanged(OnSelectValueChanged onSelectValueChanged)` | `TreeDto` | 设置选中值改变监听器，支持链式调用。 |
| `getShowCheckbox()` | `Boolean` | 获取是否显示复选框的配置。 |
| `setShowCheckbox(Boolean showCheckbox)` | `TreeDto` | 设置是否显示复选框的配置，支持链式调用。 |
| `getShowToolTip()` | `Boolean` | 获取是否显示工具提示的配置。 |
| `setShowToolTip(Boolean showToolTip)` | `TreeDto` | 设置是否显示工具提示的配置，支持链式调用。 |
| `getCheckStrictly()` | `Boolean` | 获取是否严格遵循父子不互相关联的复选框逻辑。 |
| `setCheckStrictly(Boolean checkStrictly)` | `TreeDto` | 设置是否严格遵循父子不互相关联的复选框逻辑，支持链式调用。 |
| `getAlwaysShowButtonBar()` | `Boolean` | 获取是否总是显示按钮栏的配置。 |
| `setAlwaysShowButtonBar(Boolean alwaysShowButtonBar)` | `TreeDto` | 设置是否总是显示按钮栏的配置，支持链式调用。 |

### 3. 主要函数/方法 (如果适用)
该文件主要定义了一个DTO类，其核心功能通过属性和Getter/Setter方法体现。不包含独立的工具类函数。

### 4. 对外依赖与交互

`TreeDto.java` 文件导入了以下重要的外部库或项目内的其他类，并与它们进行交互：

*   **项目内部依赖 (`fe.cmn.*` 包)**:
    *   `fe.cmn.panel.PanelDto`: `TreeDto` 的父类。`TreeDto` 继承了 `PanelDto` 定义的通用UI面板属性和行为，如尺寸、可见性、装饰、手势、拖拽等，并通过方法覆写实现链式调用。
    *   `fe.cmn.tree.TreeInterface`: 核心依赖。`TreeDto` 的 `service` 属性需要指向一个实现了此接口的后端服务。前端树控件将通过这个服务获取数据（节点初始化、查询）和执行相关业务操作（如弹出菜单）。
    *   `fe.cmn.tree.TreeNodeQuerier`: 用于定制树节点查询行为的接口/类。
    *   `fe.cmn.tree.listener.OnSelectValueChanged`: 用于处理树控件选中值变化的事件回调接口。
    *   `fe.cmn.tree.decoration.TreeDecorationDto`: 树控件特有的装饰器DTO。`TreeDto` 的 `setDecoration` 方法强制要求使用此类型，表明树控件有专门的视觉定制需求。
    *   `fe.cmn.tree.TreeHighlightEvent`: 定义了触发树节点高亮的枚举或类，与 `highlightEvents` 属性关联。
    *   `fe.cmn.tree.TreeHighlightListener`: 树节点高亮事件的监听器接口。
    *   `fe.cmn.tree.TreeNodeGestureDetector`: 树节点级手势检测器，允许定义更细粒度的节点交互。
    *   `fe.cmn.panel.PanelValueAdapter`: 用于面板值适配。
    *   `fe.cmn.event.EventSubscriberDto`: 用于订阅事件。
    *   `fe.cmn.pojo.annotation.FieldDefine`, `fe.cmn.pojo.annotation.PojoMeta`: 自定义注解，用于标记字段的UI标签、样式属性，以及POJO的元信息（如图标、组件标签）。这些注解通常用于支持自动化代码生成、开发工具的属性面板或UI表单配置。
    *   `fe.cmn.widget.*` (如 `WidgetDto`, `DraggableDto`, `DropListener`, `GestureDetectorDto`, `SizeDto`, `ToolTipDto`, `DecorationDto`): `TreeDto` 通过继承 `PanelDto` 间接或直接使用了这些通用小部件相关的DTO，以配置其拖拽、大小、提示、手势识别等通用UI功能。

*   **第三方/通用库依赖**:
    *   `java.util.Arrays`, `java.util.List`, `java.util.Set`, `java.util.stream.Collectors`: Java标准库的集合框架和Stream API，用于处理列表数据，例如 `setHighlightEvents` 方法中将可变参数转换为列表。
    *   `com.leavay.ms.tool.CmnUtil`: 一个项目内部的通用工具类，例如用于安全地获取布尔值（`CmnUtil.getBoolean`）。
    *   `flutter.coder.annt.DefaultGetter`: 一个自定义注解，其命名暗示它可能与Flutter前端代码生成框架紧密相关，用于指定属性在生成Flutter代码时的默认Getter值，进一步证实了 `TreeDto` 是前端UI组件的后端配置模型。

**交互方式**:
1.  **数据传输**: `TreeDto` 作为DTO，在后端逻辑中构建，然后通过序列化（例如JSON）发送到前端，前端框架（可能是Flutter）解析这些属性来渲染和配置树控件。
2.  **服务调用**: 前端树控件会根据 `service` 属性中指定的后端服务类名，通过某种RPC或REST机制调用该后端服务，以获取树节点数据或执行其他业务操作。
3.  **事件回调**: `OnSelectValueChanged`、`TreeHighlightListener` 和 `TreeNodeGestureDetector` 等属性允许后端定义前端特定事件发生时应触发的后端逻辑。前端事件发生时，会通知后端相应的回调方法。
4.  **配置与渲染**: `TreeDto` 的属性值直接影响前端树控件的视觉表现（如 `showCheckbox`, `showToolTip`, `decoration`）和交互行为（如 `highlightEvents`, `treeNodeGestureDetector`）。
5.  **元数据/代码生成**: `@PojoMeta` 和 `@FieldDefine` 注解表明 `TreeDto` 不仅是运行时的数据对象，也可能参与到开发时期的自动化工具链中，例如根据这些元数据生成前端UI代码、API文档或可视化配置界面。`DefaultGetter` 注解进一步强化了与前端代码生成（特别是Flutter）的联系。

