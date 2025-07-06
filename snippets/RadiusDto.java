### 1. 文件核心功能
`RadiusDto.java` 文件的主要职责是定义一个数据传输对象（DTO），用于封装和表示UI组件的圆角或椭圆角属性。它提供了多种方式来构造这些角的信息，包括圆形半径和椭圆形的长短轴半径。在整个项目中，它扮演着数据模型的角色，用于在不同组件之间传递和共享关于形状圆角或椭圆角配置的信息，可能用于UI渲染、布局计算或样式定义。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class RadiusDto` | `CsonPojo` | 封装UI组件的圆角（圆形或椭圆形）数据，提供多种构造函数和静态工厂方法来便捷地创建和管理这些数据。作为一个数据传输对象，它用于在系统内部传递几何形状的装饰属性。 |

#### 方法与属性详情

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `private static final long serialVersionUID` | `long` | 序列化版本UID，用于在对象序列化和反序列化过程中保持版本兼容性。 |
| `RadiusType type` | `RadiusType` | 圆角的类型，通常是一个枚举，表示是圆形（`circular`）还是椭圆形（`elliptical`）。 |
| `double radius` | `double` | 当圆角类型为圆形时，表示圆的半径值。 |
| `double x` | `double` | 当圆角类型为椭圆形时，表示椭圆在X轴方向的半径值。 |
| `double y` | `double` | 当圆角类型为椭圆形时，表示椭圆在Y轴方向的半径值。 |
| `public RadiusDto()` | 构造函数 | 无参构造函数，用于创建`RadiusDto`的空实例，通常结合链式setter方法使用。 |
| `public RadiusDto(RadiusType type, double radius)` | 构造函数 | 构造一个表示圆形圆角的`RadiusDto`实例，指定类型和单一半径值。 |
| `public RadiusDto(RadiusType type, double x, double y)` | 构造函数 | 构造一个表示椭圆形圆角的`RadiusDto`实例，指定类型和X、Y轴的半径值。 |
| `public static RadiusDto zero()` | `static RadiusDto` | 静态工厂方法。返回一个表示“无圆角”的`RadiusDto`实例，其类型为圆形且半径为0。 |
| `public RadiusType getType()` | `RadiusType` | 获取当前`RadiusDto`实例的圆角类型。 |
| `public RadiusDto setType(RadiusType type)` | `RadiusDto` | 设置圆角类型，并返回当前`RadiusDto`实例，支持链式调用。 |
| `public double getRadius()` | `double` | 获取圆形圆角的半径值。 |
| `public RadiusDto setRadius(double radius)` | `RadiusDto` | 设置圆形圆角的半径值，并返回当前`RadiusDto`实例，支持链式调用。 |
| `public double getX()` | `double` | 获取椭圆形圆角在X轴方向的半径值。 |
| `public RadiusDto setX(double x)` | `RadiusDto` | 设置椭圆形圆角在X轴方向的半径值，并返回当前`RadiusDto`实例，支持链式调用。 |
| `public double getY()` | `double` | 获取椭圆形圆角在Y轴方向的半径值。 |
| `public RadiusDto setY(double y)` | `RadiusDto` | 设置椭圆形圆角在Y轴方向的半径值，并返回当前`RadiusDto`实例，支持链式调用。 |
| `public static RadiusDto circular(double radius)` | `static RadiusDto` | 静态工厂方法。方便地创建一个表示圆形圆角的`RadiusDto`实例，并指定其半径。 |
| `public static RadiusDto elliptical(double x, double y)` | `static RadiusDto` | 静态工厂方法。方便地创建一个表示椭圆形圆角的`RadiusDto`实例，并指定其X、Y轴半径。 |
| `public static RadiusDto clipOvalBySize(double width, double height)` | `static RadiusDto` | 静态工厂方法。根据给定的宽度和高度创建一个椭圆形`RadiusDto`。值得注意的是，其X和Y半径是输入尺寸的8倍，这可能表示一种特定的缩放或裁剪逻辑。 |

### 3. 主要函数/方法 (如果适用)
本文件中所有的关键方法都属于 `RadiusDto` 类，已在“方法与属性详情”章节中详细描述。

### 4. 对外依赖与交互
*   **对外依赖**:
    *   **`cson.core.CsonPojo`**: `RadiusDto` 继承自 `CsonPojo`。这通常意味着 `RadiusDto` 是一个用于与 CSON（可能是某种轻量级数据序列化格式或框架，类似于 JSON）进行数据交换的Java对象。它可能被设计为可序列化和反序列化，以便于数据传输、持久化或网络通信。
    *   **`RadiusType`**: 尽管 `RadiusType` 未在此文件中定义，但它被 `RadiusDto` 作为类型字段使用。这表明 `RadiusDto` 依赖于 `RadiusType` 枚举（或类）的定义，该定义应存在于相同的包 (`fe.cmn.widget.decoration`) 或可导入的其他包中，它限定了圆角的具体种类。
*   **交互**:
    *   **数据传递**: 作为DTO，`RadiusDto` 的核心作用是在应用程序的不同组件或层之间传递关于UI圆角配置的数据。例如，UI渲染引擎可能会接收一个 `RadiusDto` 对象来确定如何绘制带有特定圆角或椭圆角的界面元素。
    *   **配置与样式**: 它可能被用于应用程序的配置系统，定义和存储组件的视觉样式属性，例如在XML、JSON或其他配置文件中表示圆角信息，并通过 CSON 框架进行解析和加载。
    *   **API接口**: 如果该项目提供API，`RadiusDto` 可能会作为API的请求或响应数据结构的一部分，用于客户端和服务器之间交换UI元素的形状信息。
    *   **工厂方法的使用**: `zero()`, `circular()`, `elliptical()`, `clipOvalBySize()` 等静态工厂方法为其他模块提供了方便快捷的方式来创建预配置的 `RadiusDto` 实例，减少了客户端代码的复杂性。

