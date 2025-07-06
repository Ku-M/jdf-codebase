### 1. 文件核心功能
`BorderRadiusDto.java` 文件主要职责是定义和封装一个UI组件（例如矩形框）的四个角的圆角半径信息。它作为一个数据传输对象（DTO），提供了便捷的方式来创建和管理统一、水平、垂直或各自独立的圆角设置。该类旨在将UI设计中的圆角概念抽象化并数据化，以便于在后端或跨平台（结合Flutter）进行传输和渲染。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class BorderRadiusDto` | `CsonPojo` | 定义一个数据传输对象（DTO），用于表示一个矩形UI元素的四个角（左上、右上、左下、右下）的圆角半径。它聚合了四个 `RadiusDto` 对象来描述每个角的圆角属性。 |

#### 方法与属性详情

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `serialVersionUID` | `private static final long` | Java序列化版本UID，用于确保序列化和反序列化的兼容性。 |
| `topLeft` | `RadiusDto` | 表示UI元素左上角的圆角半径设置。 |
| `topRight` | `RadiusDto` | 表示UI元素右上角的圆角半径设置。 |
| `bottomLeft` | `RadiusDto` | 表示UI元素左下角的圆角半径设置。 |
| `bottomRight` | `RadiusDto` | 表示UI元素右下角的圆角半径设置。 |
| `getTopLeft()` | `RadiusDto` | 获取左上角圆角半径的 `RadiusDto` 对象。 |
| `setTopLeft(RadiusDto topLeft)` | `BorderRadiusDto` | 设置左上角圆角半径，并返回当前 `BorderRadiusDto` 实例（支持链式调用）。 |
| `getTopRight()` | `RadiusDto` | 获取右上角圆角半径的 `RadiusDto` 对象。 |
| `setTopRight(RadiusDto topRight)` | `BorderRadiusDto` | 设置右上角圆角半径，并返回当前 `BorderRadiusDto` 实例（支持链式调用）。 |
| `getBottomLeft()` | `RadiusDto` | 获取左下角圆角半径的 `RadiusDto` 对象。 |
| `setBottomLeft(RadiusDto bottomLeft)` | `BorderRadiusDto` | 设置左下角圆角半径，并返回当前 `BorderRadiusDto` 实例（支持链式调用）。 |
| `getBottomRight()` | `RadiusDto` | 获取右下角圆角半径的 `RadiusDto` 对象。 |
| `setBottomRight(RadiusDto bottomRight)` | `BorderRadiusDto` | 设置右下角圆角半径，并返回当前 `BorderRadiusDto` 实例（支持链式调用）。 |
| `all(RadiusDto radiusDto)` | `public static BorderRadiusDto` | 静态工厂方法，创建一个 `BorderRadiusDto` 实例，其中所有四个角都具有相同的圆角半径。 |
| `horizontal(RadiusDto leftRadiusDto, RadiusDto rightRadiusDto)` | `public static BorderRadiusDto` | 静态工厂方法，创建一个 `BorderRadiusDto` 实例，其中左右两侧（左上、左下使用`leftRadiusDto`；右上、右下使用`rightRadiusDto`）的圆角半径不同。 |
| `vertical(RadiusDto topRadiusDto, RadiusDto bottomRadiusDto)` | `public static BorderRadiusDto` | 静态工厂方法，创建一个 `BorderRadiusDto` 实例，其中上下两侧（左上、右上使用`topRadiusDto`；左下、右下使用`bottomRadiusDto`）的圆角半径不同。 |
| `zero()` | `public static BorderRadiusDto` | 静态工厂方法，创建一个 `BorderRadiusDto` 实例，表示所有角的圆角半径均为零（即直角）。 |

### 3. 主要函数/方法 (如果适用)
此文件中的主要功能均作为 `BorderRadiusDto` 类的静态方法或实例方法存在，已在上述“方法与属性详情”中详细描述。

### 4. 对外依赖与交互
*   **`cson.core.CsonPojo`**:
    *   `BorderRadiusDto` 继承自 `CsonPojo`，这意味着它是一个CSON（一种类似JSON的数据格式）兼容的POJO（Plain Old Java Object）。这表明 `BorderRadiusDto` 实例可能被用于数据的序列化和反序列化，方便在不同的系统或层之间（例如，后端服务与前端应用）传输UI相关的圆角配置数据。
*   **`flutter.coder.annt.FlutterCode`**:
    *   类上方的 `@FlutterCode` 注解非常关键。它明确指示此Java DTO与Flutter前端代码的生成或直接映射有关。注解内容提供了一个Flutter端的构造函数示例 (`BorderRadiusDto.all(double radius)`)，暗示了可以通过Java对象直接生成对应的Flutter UI代码，或者在Flutter侧有对应的DTO结构来解析和使用此Java DTO。这表明该文件是跨平台UI配置或代码生成框架的一部分。
*   **`RadiusDto` (隐式依赖)**:
    *   虽然 `RadiusDto` 未在此文件中通过 `import` 语句显式导入，但它是 `BorderRadiusDto` 的核心组成部分。`BorderRadiusDto` 的所有角成员变量 (`topLeft`, `topRight`, `bottomLeft`, `bottomRight`) 和静态工厂方法的参数都使用 `RadiusDto` 类型。这表明 `RadiusDto` 是一个更基础的DTO，用于表示单个圆角的具体半径信息（可能包括X轴和Y轴半径，或简单的一个半径值），而 `BorderRadiusDto` 则是其聚合形式，用于描述一个矩形元素的整体圆角样式。两者协同工作，共同定义UI元素的圆角属性。

