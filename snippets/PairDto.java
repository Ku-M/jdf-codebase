这是一个关于 `PairDto.java` 文件的技术知识库，专为AI编码助手（如Cursor）设计。

---

### 1. 文件核心功能

`PairDto.java` 文件定义了一个泛型的数据传输对象（DTO），用于封装一个键（key）和值（value）的组合。它的核心功能是：

1.  **数据封装**: 提供一个通用的结构来存储任何类型的键值对，类似于 `java.util.AbstractMap.SimpleEntry` 或 `Map.Entry`。
2.  **跨平台数据模型**: 作为Java后端与Flutter前端之间的数据模型桥梁。通过自定义的 `@FlutterCode` 和 `@FlutterToString` 注解，该文件明确指示一个自动化工具（如代码生成器）如何将此Java类映射并生成对应的Dart/Flutter代码，包括自定义的构造函数、哈希码逻辑、比较逻辑以及字符串表示，以确保前后端数据模型的一致性和行为同步。
3.  **序列化兼容性**: 继承自 `FePojo`，表明它可能集成于一个序列化框架（如CSON），便于数据的传输和持久化。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class PairDto<K, V>` | `FePojo` | 定义一个泛型键值对的数据结构，用作数据传输对象。通过特殊的Flutter注解，支持自动生成跨平台（Java到Dart/Flutter）的代码和行为定义。 |

#### 方法与属性详情

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `serialVersionUID` | `private static final long` | Java序列化版本UID，用于确保序列化和反序列化时的兼容性。 |
| `key` | `K` | 泛型类型 `K` 的实例变量，表示键值对中的键。 |
| `value` | `V` | 泛型类型 `V` 的实例变量，表示键值对中的值。 |
| `public PairDto()` | 构造函数 | 无参构造函数，用于创建空的 `PairDto` 实例。 |
| `public PairDto(K key)` | 构造函数 | 带单个 `key` 参数的构造函数，用于初始化键值对的键部分。 |
| `public PairDto(K key, V value)` | 构造函数 | 带 `key` 和 `value` 两个参数的构造函数，用于完整初始化键值对。 |
| `public K getKey()` | `K` | 获取当前 `PairDto` 实例的键。 |
| `public void setKey(K key)` | `void` | 设置当前 `PairDto` 实例的键。 |
| `public V getValue()` | `V` | 获取当前 `PairDto` 实例的值。 |
| `public void setValue(V value)` | `void` | 设置当前 `PairDto` 实例的值。 |
| `public String toString()` | `String` | 返回此键值对的字符串表示。它利用 `CmnUtil` 工具类将键和值转换为字符串，并以"名称:标签"的形式进行组合。 |

### 3. 主要函数/方法 (如果适用)

此文件不包含独立的工具函数，所有方法均属于 `PairDto` 类。

### 4. 对外依赖与交互

1.  **`com.leavay.ms.tool.CmnUtil`**:
    *   **交互**: 在 `toString()` 方法中使用 `CmnUtil.getNameAndLabel()` 和 `CmnUtil.getString()`。
    *   **作用**: `CmnUtil` 是一个通用的工具类，用于处理字符串格式化和安全地获取对象字符串表示。`PairDto` 依赖它来生成其自身的易读字符串表示，表明它在输出格式方面遵循了项目的通用规范。

2.  **`cson.core.CsonPojo`**:
    *   **交互**: `PairDto` 继承自 `FePojo`，而 `FePojo` 很可能实现了 `CsonPojo` 或与 CSON 框架紧密相关。
    *   **作用**: 这表明 `PairDto` 是作为CSON（一种可能自定义的JSON-like序列化格式）框架的数据模型来设计的，便于在系统内部进行序列化、反序列化和数据传输。

3.  **`flutter.coder.annt.FlutterCode` 和 `flutter.coder.annt.FlutterToString`**:
    *   **交互**: 这两个是核心的自定义注解，它们不直接在Java运行时进行逻辑交互，而是作为元数据被一个**代码生成工具**（例如，一个Java注解处理器或自定义构建脚本）读取和解析。
    *   **作用**:
        *   `@FlutterToString` 提供了一个Dart语言的 `toString` 表达式，指导代码生成器如何为Flutter端的 `PairDto` 类生成其字符串表示。
        *   `@FlutterCode` 包含了一段或多段Dart代码片段，用于指导代码生成器为Flutter端的 `PairDto` 类生成特定的成员。这包括：
            *   **`hashCode` 重载**: 基于 `key` 的哈希码，确保在Flutter端两个 `PairDto` 实例的相等性判断（基于键）与Java端行为一致。
            *   **命名构造函数 `PairDto.pair`**: 提供一个方便的构造方式，支持可选值参数，并可能调用基类的类型设置方法。
            *   **`compare` 方法**: 一个自定义的比较方法，也基于 `key` 的相等性，进一步增强了前后端逻辑的一致性。
            *   **`convertToPairDto` 方法**: 一个实用方法，用于将当前对象的所有字段值复制到一个新的 `PairDto` 实例中，类似于Dart中的 `copyWith` 或深度克隆功能。
    *   **重要性**: 这些注解是 `PairDto` 文件的核心特性，体现了项目在**跨平台代码生成**方面的设计理念，极大地提高了Java后端与Flutter前端数据模型和行为逻辑的同步效率和准确性。

