### 1. 文件核心功能
`FeLayoutUtil.java` 文件是一个界面布局辅助工具类。它的主要职责是提供静态方法，用于处理UI组件的布局和尺寸计算。具体来说，它实现了将现有UI盒子（Box）包装成带有默认参数的滚动盒子（ScrollBox）的功能，以及根据字体大小和文本内容（考虑中英文字符的宽度差异）精确计算行操作按钮所需宽度的功能。

它在整个项目中扮演的角色是一个通用的UI布局辅助层，旨在封装复杂的布局逻辑和计算，以提高代码的复用性、可维护性，并确保前端（`fe`）模块中UI布局的一致性和准确性。

### 2. 主要组件/类定义

| 类/组件名    | 继承自/实现 | 主要职责                                                                                                                                                                                                                                  |
| :----------- | :---------- | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| FeLayoutUtil | Object (隐式) | 这是一个静态工具类，不包含实例属性。它提供了一系列公共静态方法，用于处理UI布局相关的操作，例如为UI组件添加默认的滚动行为，以及根据业务规则（如中文字符按1个长度，英文字符按0.5个长度）计算UI元素（如按钮）的实际占据宽度。 |

#### 方法与属性详情

| 方法/属性                                                                       | 类型         | 描述                                                                                                                                                                                                                                                                                       |
| :------------------------------------------------------------------------------ | :----------- | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `public static ScrollBoxDto wrapScorllBox(BoxDto mainBox)`                     | `ScrollBoxDto` | **功能**: 将一个 `BoxDto` 对象包装成一个 `ScrollBoxDto`。 <br>**详情**: 它首先将传入 `mainBox` 的主轴尺寸 (`MainAxisSize`) 设置为 `min`，然后通过 `ScrollBoxDto.wrap()` 方法将其包装，并显式设置 `ScrollBoxDto` 的最大子组件高度为 `9999`。这为UI元素提供了默认的滚动行为和空间限制。 |
| `public static int caculateRowOperateWidth(double fontSize, List<ButtonDto> rowOperateButton)` | `int`        | **功能**: 计算一组行操作按钮在特定字体大小下所需的总宽度。 <br>**详情**: 该方法遍历 `rowOperateButton` 列表中的每个 `ButtonDto`。对于每个按钮，它检查是否包含图标（icon）或文本（text）。如果存在图标，则增加 `fontSize + 10` 的宽度；如果存在文本，则通过调用 `caculateLabelFontCnt` 计算文本的“字符数”，并乘以 `fontSize` 得到文本宽度。最终，将所有按钮的宽度累加，并加上每个按钮额外的 `50` 像素间距，最后向上取整并返回整数宽度。 |
| `public static double caculateLabelFontCnt(String label)`                      | `double`     | **功能**: 计算给定标签字符串的“字体字符数量”。 <br>**详情**: 遍历标签字符串中的每个字符。如果字符是中文字符（通过 `CmnUtil.isChinese()` 判断），则计为 `1`；否则（如英文字符、数字等），计为 `0.5`。这提供了一种业务特定的字符宽度估算方式，以便更准确地计算UI元素的宽度。          |

### 3. 主要函数/方法 (如果适用)

已在“方法与属性详情”中详细描述。

### 4. 对外依赖与交互

`FeLayoutUtil` 文件与以下重要的外部库或项目内的其他类进行交互：

*   **`java.util.List`**: Java标准库，用于处理集合类型。在 `caculateRowOperateWidth` 方法中，它接收一个 `List<ButtonDto>` 作为参数，表明它需要处理按钮的集合。
*   **`com.leavay.ms.tool.CmnUtil`**: 这是一个项目内部的通用工具类。
    *   `CmnUtil.isStringEmpty()`: 用于检查字符串是否为空或 null。在 `caculateRowOperateWidth` 中用于判断按钮的图标和文本内容。
    *   `CmnUtil.getInteger()`: 用于将 `double` 类型的值转换为 `int` 类型（带向上取整）。在 `caculateRowOperateWidth` 中用于最终宽度的转换。
    *   `CmnUtil.isChinese()`: 用于判断一个字符是否为中文字符。在 `caculateLabelFontCnt` 中用于区分中英文字符的宽度计算。
    这表明 `FeLayoutUtil` 依赖 `CmnUtil` 提供了基础的工具方法，以避免重复实现通用逻辑。
*   **`fe.cmn.panel.BoxDto`**: `fe.cmn.panel` 包下的一个数据传输对象（DTO），可能代表UI中的一个通用盒子布局容器。它是 `wrapScorllBox` 方法的输入参数，`FeLayoutUtil` 通过它获取和设置布局属性。
*   **`fe.cmn.panel.BoxMainAxisSize`**: `fe.cmn.panel` 包下的一个枚举类型，可能定义了Box组件的主轴尺寸行为（例如，最小化尺寸）。在 `wrapScorllBox` 中用于设置 `BoxDto` 的尺寸属性。
*   **`fe.cmn.panel.ScrollBoxDto`**: `fe.cmn.panel` 包下的一个数据传输对象（DTO），可能代表一个可滚动的盒子布局容器。它是 `wrapScorllBox` 方法的返回值，`FeLayoutUtil` 负责创建和配置它。
*   **`fe.cmn.widget.ButtonDto`**: `fe.cmn.widget` 包下的一个数据传输对象（DTO），可能代表UI中的一个按钮组件。在 `caculateRowOperateWidth` 中，`FeLayoutUtil` 通过它获取按钮的图标和文本信息，以进行宽度计算。

这些依赖关系表明 `FeLayoutUtil` 与项目前端（`fe`）模块中的核心UI组件（`BoxDto`, `ScrollBoxDto`, `ButtonDto`）及其辅助工具类（`CmnUtil`）紧密集成，共同支撑着复杂的UI布局逻辑。

