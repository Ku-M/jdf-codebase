### 1. 文件核心功能
`GpfDCBasicI18n.java` 文件的核心功能是作为 `gpf.dc.basic` 模块的国际化（Internationalization, I18n）资源管理器。它通过继承 `AbsI18n` 类，并结合 `@I18nDeclare` 注解，提供了一套集中管理、访问和格式化多语言文本字符串的机制。该文件采用单例模式，确保整个应用程序中只有一个国际化实例，方便统一管理和获取文本资源。它包含了该模块特有的文本，同时也整合并引用了其他通用模块（如`FeI18n`和`GpfDCFeI18n`）的国际化文本，以实现文本资源的复用和统一。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :-------- | :---------- | :------- |
| `public class GpfDCBasicI18n` | `AbsI18n` | 提供 `gpf.dc.basic` 模块所需的国际化文本资源。通过单例模式对外暴露接口，管理并加载名为 `gpfdcbasic_i18n.setting` 的资源文件，并提供字符串格式化功能。同时，它定义了大量带有 `@I18nDeclare` 注解的 `public static final String` 字段，这些字段代表了在应用程序中使用的具体国际化文本键及其默认值或引用值。 |

#### 方法与属性详情

**类: `GpfDCBasicI18n`**

| 方法/属性 | 类型 | 描述 |
| :--------------------------------- | :----- | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| `protected static GpfDCBasicI18n inst` | `GpfDCBasicI18n` | `GpfDCBasicI18n` 类的单例实例。 |
| `private GpfDCBasicI18n()` | 构造函数 | 私有构造函数，用于实现单例模式。在内部调用 `super()` 初始化父类 `AbsI18n`。 |
| `public static GpfDCBasicI18n get()` | `GpfDCBasicI18n` | 获取 `GpfDCBasicI18n` 的单例实例的公共静态方法。这是访问该国际化管理器的主要入口。 |
| `public String getResourceFileName()` | `String` | 重写父类 `AbsI18n` 的方法，返回当前国际化资源文件（`properties` 或 `setting` 文件）的名称，本例中是 `"gpfdcbasic_i18n.setting"`。系统会根据此名称加载对应的多语言资源。 |
| `public static String getString(String s, Object... params)` | `String` | 静态方法，用于获取指定键 `s` 对应的国际化字符串，并支持使用 `params` 进行格式化（例如，替换 `{1}`，`{2}` 等占位符）。它内部调用单例实例的 `format` 方法。 |
| 各种 `public static final String` 字段 (例如: `Language`, `Loading`, `HomePage` 等) | `String` | 大量声明为 `public static final String` 的字段，它们代表了应用程序中使用的国际化文本的键。这些字段通常通过 `@I18nDeclare` 注解标记，其值可以是硬编码的默认文本、引用其他国际化类（如 `FeI18n` 和 `GpfDCFeI18n`）的常量，或者通过调用自身的 `getString()` 方法从资源文件中动态加载。 |

### 3. 主要函数/方法 (如果适用)
本文件主要通过类及其静态方法提供功能，没有独立的工具类函数。`getString` 方法已在上述表格中详细描述。

### 4. 对外依赖与交互
`GpfDCBasicI18n.java` 依赖于多个外部库和项目内部的其他类，以构建其国际化功能。

*   **`cmn.anotation.I18nDeclare`**:
    *   **作用**: 这是一个自定义注解，用于标记国际化文本常量和国际化资源组类。
    *   **交互**: `@I18nDeclare(defaultGroup=true)` 注解在类级别，表明 `GpfDCBasicI18n` 是一个默认的国际化资源组。类内部的每个 `public static final String` 字段上也使用了 `@I18nDeclare`，指示这些字段是需要被国际化系统识别和处理的文本键。框架可能会通过反射扫描这些注解来自动收集和管理国际化资源。

*   **`cmn.i18n.AbsI18n`**:
    *   **作用**: 这是国际化功能的抽象基类，提供了加载资源文件、格式化字符串等基础能力。
    *   **交互**: `GpfDCBasicI18n` 继承自 `AbsI18n`，意味着它重用了 `AbsI18n` 中定义的国际化核心逻辑，例如字符串格式化（通过 `inst.format(s, params)` 调用）。它必须实现 `getResourceFileName()` 方法来指定其资源文件的位置。

*   **`fe.util.i18n.FeI18n`**:
    *   **作用**: 看起来是前端（fe）模块提供的一套通用国际化资源。
    *   **交互**: `GpfDCBasicI18n` 直接引用了 `FeI18n` 中的多个 `public static final String` 常量（例如 `Search = FeI18n.Search`）。这表明 `gpf.dc.basic` 模块会复用前端通用的国际化文本，避免重复定义，保持文本一致性。

*   **`gpf.dc.fe.component.relation.FilterColumnTable`**:
    *   **作用**: 可能是 `gpf.dc.fe` 模块中一个与前端组件相关的类，其中定义了某些文本常量。
    *   **交互**: `GpfDCBasicI18n` 引用了 `FilterColumnTable.Value_Label`。这表明 `basic` 模块在某些情况下需要使用 `fe` 模块特定组件中定义的国际化文本。

*   **`gpf.dc.fe.util.GpfDCFeI18n`**:
    *   **作用**: 可能是 `gpf.dc.fe` 模块中另一个国际化资源类，提供该模块特有的或通用的前端国际化文本。
    *   **交互**: 类似于 `FeI18n`，`GpfDCBasicI18n` 也引用了 `GpfDCFeI18n` 中的多个常量和通过 `getString` 方法获取的文本（例如 `TRUE = GpfDCFeI18n.TRUE`，`Status = GpfDCFeI18n.getString("status")`）。这进一步强调了不同模块间国际化资源的共享和整合。

*   **`gpfdcbasic_i18n.setting` 文件**:
    *   **作用**: 这是 `GpfDCBasicI18n` 类实际加载国际化字符串的配置文件。
    *   **交互**: `getResourceFileName()` 方法返回此文件名。运行时，`AbsI18n` 的机制会根据当前语言环境加载相应的 `gpfdcbasic_i18n_xx_YY.setting` 文件。当 `getString("key")` 被调用时，系统会从该文件中查找对应的翻译文本。如果没有找到，或者文件不存在，则会使用类中 `public static final String` 字段定义的默认值或引用的值。值得注意的是，代码中有很多 `getString("key")` 形式的赋值，这意味着这些特定的键值将优先从 `gpfdcbasic_i18n.setting` 文件中获取，而不是使用硬编码的字符串字面量作为默认值。

