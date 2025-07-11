# Analysis for: gpf_dc_OctoCM_Workbench\src\core\octo\cm\util\UniformStyleUtil.java

## Original File Path
`D:\TMP\CLASS_BUS_PROJECT\gpf_dc_OctoCM_Workbench\src\core\octo\cm\util\UniformStyleUtil.java`

## Extracted Snippets & Analysis
作为一名资深的软件架构师，我已仔细分析了您提供的代码，并严格遵循了您设定的[核心规则]来提取高价值的代码样例。这些样例旨在以原子化的方式，清晰地展示框架API的调用模式，并去除了业务上下文，确保AI编程助手能够从中学习到可复用且可靠的API使用方式。

以下是提取出的符合要求的代码样例：

---

### **1. 文本样式 (CTextStyle)**

#### 1.1 构建文本样式对象并设置字体大小和颜色
```java
import fe.cmn.text.CTextStyle;
import java.awt.Color;

// 模式：构建文本样式对象并设置字体大小和颜色
CTextStyle yourTextStyle = new CTextStyle()
    .setFontSize(your_font_size_double_value) // 例如: 15.0D
    .setColor(new Color(your_r_value, your_g_value, your_b_value)); // 例如: new Color(102, 105, 104)
```

#### 1.2 构建文本样式对象并设置字体颜色、大小和字重
```java
import fe.cmn.text.CFontWeight;
import fe.cmn.text.CTextStyle;
import fe.cmn.data.CColor;

// 模式：构建文本样式对象并设置颜色、字体大小和字重
CTextStyle yourDetailedTextStyle = new CTextStyle()
    .setColor(new CColor(your_r_value, your_g_value, your_b_value, your_alpha_float_value)) // 例如: new CColor(20, 20, 20, 0.7f)
    .setFontSize(your_font_size_double_value) // 例如: 17.0D
    .setFontWeight(CFontWeight.your_font_weight_enum_member); // 例如: CFontWeight.w600, CFontWeight.bold
```

### **2. 颜色 (CColor)**

#### 2.1 使用RGBA值创建CColor对象
```java
import fe.cmn.data.CColor;

// 模式：使用RGBA值创建CColor对象
CColor yourCColor = new CColor(your_r_value, your_g_value, your_b_value, your_alpha_float_value); // 例如: new CColor(0, 0, 0, 0.15f)
```

#### 2.2 从标准java.awt.Color对象创建CColor对象
```java
import fe.cmn.data.CColor;
import java.awt.Color;

// 模式：从标准java.awt.Color对象创建CColor对象
CColor yourCColorFromAWT = CColor.fromColor(new Color(your_r_value, your_g_value, your_b_value)); // 例如: CColor.fromColor(new Color(255, 243, 236))
```

### **3. 图标样式 (IconStyleDto / IconDecorationDto)**

#### 3.1 构建图标样式对象并设置颜色和尺寸
```java
import fe.cmn.widget.decoration.IconStyleDto;
import java.awt.Color;

// 模式：构建图标样式对象并设置图标颜色和尺寸
IconStyleDto yourIconStyle = new IconStyleDto()
    .setIconColor(Color.your_color_constant) // 例如: Color.BLACK, new Color(R, G, B)
    .setSize(your_size_double_value); // 例如: 20.0D
```

#### 3.2 构建图标装饰对象并设置尺寸、颜色和右侧边距
```java
import fe.cmn.widget.decoration.IconDecorationDto;
import fe.cmn.widget.InsetDto;
import java.awt.Color;

// 模式：构建图标装饰对象并设置尺寸、颜色和右侧边距
IconDecorationDto yourIconDecoration = new IconDecorationDto()
    .setSize(your_size_double_value) // 例如: 19.0D
    .setIconColor(Color.your_color_constant) // 例如: Color.BLACK, new Color(R, G, B)
    .setMargin(new InsetDto().setRight(your_margin_right_double_value)); // 例如: new InsetDto().setRight(20.0D)
```

### **4. 边距/填充 (InsetDto)**

#### 4.1 创建一个所有边距/填充都相同的InsetDto
```java
import fe.cmn.widget.InsetDto;

// 模式：创建一个所有边距/填充都相同的InsetDto（静态工厂方法）
InsetDto yourUniformInset = InsetDto.all(your_value_double); // 例如: InsetDto.all(15.0D)
```

#### 4.2 创建一个仅设置右侧填充/边距的InsetDto
```java
import fe.cmn.widget.InsetDto;

// 模式：创建一个仅设置右侧填充/边距的InsetDto
InsetDto yourRightInset = new InsetDto().setRight(your_value_double); // 例如: new InsetDto().setRight(20.0D)
```

### **5. 边框 (BorderDto / BorderSideDto)**

#### 5.1 构建边框侧边样式
```java
import fe.cmn.widget.decoration.BorderSideDto;
import java.awt.Color;

// 模式：构建边框侧边样式对象（颜色和厚度）
BorderSideDto yourBorderSide = new BorderSideDto(Color.your_color_constant, your_thickness_double_value); // 例如: new BorderSideDto(Color.WHITE, 5.0D)
```

#### 5.2 构建边框样式并设置左侧边框
```java
import fe.cmn.widget.decoration.BorderDto;
import fe.cmn.widget.decoration.BorderSideDto;
import java.awt.Color;

// 模式：构建边框样式对象并设置左侧边框
BorderDto yourBorder = new BorderDto()
    .setLeft(new BorderSideDto(Color.your_color_constant, your_thickness_double_value)); // 例如: new BorderDto().setLeft(new BorderSideDto(Color.WHITE, 5.0D))
```

### **6. 边框圆角 (RadiusDto / BorderRadiusDto)**

#### 6.1 创建一个圆形半径对象
```java
import fe.cmn.widget.decoration.RadiusDto;

// 模式：创建一个圆形半径对象（静态工厂方法）
RadiusDto yourCircularRadius = RadiusDto.circular(your_radius_double_value); // 例如: RadiusDto.circular(10.0D)
```

#### 6.2 设置所有角为统一圆角的边框半径
```java
import fe.cmn.widget.decoration.BorderRadiusDto;
import fe.cmn.widget.decoration.RadiusDto;

// 模式：设置所有角为统一圆角的边框半径（静态工厂方法）
BorderRadiusDto yourBorderRadius = BorderRadiusDto.all(RadiusDto.circular(your_radius_double_value)); // 例如: BorderRadiusDto.all(RadiusDto.circular(10.0D))
```

### **7. 树节点装饰 (TreeNodeDecorationDto)**

#### 7.1 构建默认树节点装饰对象
```java
import fe.cmn.tree.decoration.TreeNodeDecorationDto;
import fe.cmn.text.CTextStyle;
import fe.cmn.widget.decoration.IconStyleDto;
import fe.cmn.widget.decoration.BorderDto;
import fe.cmn.widget.decoration.BorderSideDto;
import java.awt.Color;

// 模式：构建默认树节点装饰对象，包括文本样式、图标样式、右侧内边距和边框
TreeNodeDecorationDto defaultNodeDecoration = new TreeNodeDecorationDto();
defaultNodeDecoration.setTextStyle(new CTextStyle().setFontSize(your_font_size).setColor(new Color(your_r, your_g, your_b)))
                     .setIconStyle(new IconStyleDto().setIconColor(Color.your_icon_color).setSize(your_icon_size))
                     .setIconRightPadding(your_padding_double_value) // 例如: 10.0D
                     .setBorder(new BorderDto().setLeft(new BorderSideDto(Color.your_border_color, your_border_thickness))); // 例如: new BorderDto().setLeft(new BorderSideDto(Color.WHITE, 5.0D))
```

#### 7.2 构建悬停状态的树节点装饰对象
```java
import fe.cmn.tree.decoration.TreeNodeDecorationDto;
import fe.cmn.text.CTextStyle;
import fe.cmn.widget.decoration.IconStyleDto;
import fe.cmn.widget.decoration.BorderDto;
import fe.cmn.widget.decoration.BorderSideDto;
import java.awt.Color;

// 模式：构建悬停状态的树节点装饰对象
TreeNodeDecorationDto hoverNodeDecoration = new TreeNodeDecorationDto();
hoverNodeDecoration.setTextStyle(new CTextStyle().setFontSize(your_font_size).setColor(new Color(your_r, your_g, your_b)))
                   .setIconStyle(new IconStyleDto().setIconColor(Color.your_icon_color).setSize(your_icon_size))
                   .setIconRightPadding(your_padding_double_value) // 例如: 10.0D
                   .setBorder(new BorderDto().setLeft(new BorderSideDto(Color.your_border_color, your_border_thickness))); // 例如: new BorderDto().setLeft(new BorderSideDto(Color.WHITE, 5.0D))
```

#### 7.3 构建选中状态的树节点装饰对象
```java
import fe.cmn.tree.decoration.TreeNodeDecorationDto;
import fe.cmn.text.CTextStyle;
import fe.cmn.widget.decoration.IconStyleDto;
import fe.cmn.widget.decoration.BorderDto;
import fe.cmn.widget.decoration.BorderSideDto;
import fe.cmn.data.CColor;
import java.awt.Color;

// 模式：构建选中状态的树节点装饰对象
TreeNodeDecorationDto selectedNodeDecoration = new TreeNodeDecorationDto();
selectedNodeDecoration.setTextStyle(new CTextStyle().setFontSize(your_font_size).setColor(new Color(your_r, your_g, your_b)));
selectedNodeDecoration.setBackground(CColor.fromColor(new Color(your_bg_r, your_bg_g, your_bg_b))) // 例如: CColor.fromColor(new Color(255, 243, 236))
                      .setIconStyle(new IconStyleDto().setIconColor(Color.your_icon_color).setSize(your_icon_size))
                      .setBorder(new BorderDto().setLeft(new BorderSideDto(Color.your_border_color, your_border_thickness))); // 例如: new BorderDto().setLeft(new BorderSideDto(new Color(R, G, B), 5.0D))
```

### **8. 树的整体装饰 (TreeDecorationDto)**

#### 8.1 构建树的整体装饰对象
```java
import fe.cmn.tree.decoration.TreeDecorationDto;
import fe.cmn.tree.decoration.TreeNodeDecorationDto;

// 模式：构建树的整体装饰对象，设置节点高度、各种节点装饰和展开图标位置
TreeDecorationDto yourTreeDecoration = new TreeDecorationDto();
yourTreeDecoration.setNodeHeight(your_node_height_double_value) // 例如: 40.0D
                  .setDefalutNodeDecorationDto(your_default_node_decoration_instance)
                  .setHoverNodeDecorationDto(your_hover_node_decoration_instance)
                  .setSelectedNodeDecorationDto(your_selected_node_decoration_instance)
                  .setExpandIconOnFront(your_boolean_value); // 例如: false
```

#### 8.2 为TreeDto实例设置装饰
```java
import fe.cmn.tree.TreeDto;
import fe.cmn.tree.decoration.TreeDecorationDto;

// 模式：为TreeDto实例设置整体装饰
your_tree_dto_instance.setDecoration(your_tree_decoration_instance);
```

### **9. 菜单装饰 (MenuDecorationDto)**

#### 9.1 构建菜单的整体装饰对象
```java
import fe.cmn.menu.MenuDecorationDto;
import fe.cmn.widget.InsetDto;
import fe.cmn.data.CColor;
import fe.cmn.widget.decoration.BorderRadiusDto;
import fe.cmn.widget.decoration.RadiusDto;
import java.awt.Color;

// 模式：构建菜单的整体装饰对象，设置内边距、外边距、背景色、阴影和边框圆角
MenuDecorationDto yourMenuDecoration = new MenuDecorationDto();
yourMenuDecoration.setPadding(InsetDto.all(your_padding_double_value)) // 例如: InsetDto.all(15.0D)
                  .setMargin(InsetDto.all(your_margin_double_value)) // 例如: InsetDto.all(15.0D)
                  .setBackgroundColor(Color.your_background_color_constant) // 例如: Color.WHITE
                  .setShadowColor(new CColor(your_r, your_g, your_b, your_alpha_float_value)) // 例如: new CColor(0, 0, 0, 0.15f)
                  .setBorderRadius(BorderRadiusDto.all(RadiusDto.circular(your_radius_double_value))); // 例如: BorderRadiusDto.all(RadiusDto.circular(10.0D))
```

#### 9.2 为TreeMenuDto实例设置首选宽度
```java
import fe.cmn.tree.TreeMenuDto;

// 模式：为TreeMenuDto实例设置首选宽度
your_tree_menu_dto_instance.setPreferWidth(your_width_double_value); // 例如: 150.0D
```

#### 9.3 为TreeMenuDto实例设置装饰
```java
import fe.cmn.tree.TreeMenuDto;
import fe.cmn.menu.MenuDecorationDto;

// 模式：为TreeMenuDto实例设置装饰
your_tree_menu_dto_instance.setDecoration(your_menu_decoration_instance);
```

### **10. 菜单项装饰 (MenuItemDecorationDto)**

#### 10.1 构建菜单项的默认装饰对象
```java
import fe.cmn.menu.MenuItemDecorationDto;
import fe.cmn.widget.decoration.IconDecorationDto;
import fe.cmn.widget.InsetDto;
import fe.cmn.text.CTextStyle;
import fe.cmn.data.CColor;
import fe.cmn.text.CFontWeight;
import java.awt.Color;

// 模式：构建菜单项的默认装饰对象，包括起始/结束内边距、高度、背景色、图标装饰和文本样式
MenuItemDecorationDto defaultMenuItemDecoration = new MenuItemDecorationDto()
    .setStartPadding(your_start_padding_double_value) // 例如: 10.0D
    .setEndPadding(your_end_padding_double_value) // 例如: 10.0D
    .setHeight(your_height_double_value) // 例如: 40.0D
    .setBackground(Color.your_background_color_constant) // 例如: Color.WHITE
    .setIconDecoration(new IconDecorationDto()
                        .setSize(your_icon_size_double_value) // 例如: 19.0D
                        .setIconColor(Color.your_icon_color_constant) // 例如: Color.BLACK
                        .setMargin(new InsetDto().setRight(your_icon_margin_right_double_value))) // 例如: new InsetDto().setRight(20.0D)
    .setTextStyle(new CTextStyle()
                   .setColor(new CColor(your_r, your_g, your_b, your_alpha_float_value)) // 例如: new CColor(20, 20, 20, 0.7f)
                   .setFontSize(your_font_size_double_value) // 例如: 17.0D
                   .setFontWeight(CFontWeight.your_font_weight_enum_member)); // 例如: CFontWeight.w600
```

#### 10.2 构建菜单项的突出显示装饰对象
```java
import fe.cmn.menu.MenuItemDecorationDto;
import fe.cmn.widget.decoration.IconDecorationDto;
import fe.cmn.widget.InsetDto;
import fe.cmn.text.CTextStyle;
import fe.cmn.widget.decoration.BorderRadiusDto;
import fe.cmn.widget.decoration.RadiusDto;
import fe.cmn.text.CFontWeight;
import java.awt.Color;

// 模式：构建菜单项的突出显示装饰对象，包括起始/结束内边距、高度、背景色、边框圆角、图标装饰和文本样式
MenuItemDecorationDto highlightMenuItemDecoration = new MenuItemDecorationDto()
    .setStartPadding(your_start_padding_double_value) // 例如: 10.0D
    .setEndPadding(your_end_padding_double_value) // 例如: 10.0D
    .setHeight(your_height_double_value) // 例如: 40.0D
    .setBackground(Color.your_background_color_constant) // 例如: Color.WHITE, new Color(255, 135, 0)
    .setBorderRadius(BorderRadiusDto.all(RadiusDto.circular(your_radius_double_value))) // 例如: BorderRadiusDto.all(RadiusDto.circular(8.0D))
    .setIconDecoration(new IconDecorationDto()
                        .setSize(your_icon_size_double_value) // 例如: 19.0D
                        .setIconColor(new Color(your_r, your_g, your_b)) // 例如: new Color(255, 135, 0)
                        .setMargin(new InsetDto().setRight(your_icon_margin_right_double_value))) // 例如: new InsetDto().setRight(20.0D)
    .setTextStyle(new CTextStyle()
                   .setColor(new Color(your_r, your_g, your_b)) // 例如: new Color(255, 135, 0)
                   .setFontSize(your_font_size_double_value) // 例如: 17.0D
                   .setFontWeight(CFontWeight.your_font_weight_enum_member)); // 例如: CFontWeight.w600
```

#### 10.3 为MenuItemDto实例设置默认装饰
```java
import fe.cmn.menu.MenuItemDto;
import fe.cmn.menu.MenuItemDecorationDto;

// 模式：为MenuItemDto实例设置默认装饰
your_menu_item_dto_instance.setMenuItemDecoration(your_menu_item_decoration_instance);
```

#### 10.4 为MenuItemDto实例设置突出显示装饰
```java
import fe.cmn.menu.MenuItemDto;
import fe.cmn.menu.MenuItemDecorationDto;

// 模式：为MenuItemDto实例设置突出显示装饰
your_menu_item_dto_instance.setHighlightMenuItemDecoration(your_highlight_menu_item_decoration_instance);
```