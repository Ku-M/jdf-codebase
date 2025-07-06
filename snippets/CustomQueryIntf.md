### 1. 文件核心功能
`CustomQueryIntf.java` 文件定义了一个名为 `CustomQueryIntf` 的泛型接口。其核心功能是作为**自定义查询操作的顶级接口或标记接口**。

它在整个项目中扮演的角色是：
1.  **定义通用契约**: 为项目中所有涉及“自定义查询”的操作提供一个统一的接口规范，尽管它自身没有定义任何方法，但它通过继承 `BaseFeActionIntf` 间接定义了这种规范。
2.  **类型约束与抽象**: 利用泛型 `T extends CustomQueryParameter`，确保所有实现此接口的自定义查询操作都与 `CustomQueryParameter` 或其子类型的数据进行交互，从而在编译时强制类型安全。
3.  **模块化与扩展点**: 作为业务逻辑和数据层之间的一个抽象层，允许不同的实现来处理查询表格、树列表或单个表单数据的需求，同时保持接口的一致性。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public interface CustomQueryIntf<T>` | `BaseFeActionIntf<T>` | 定义一个用于自定义查询的通用接口。它通过继承父接口来获取其行为规范，并限定泛型参数 `T` 必须是 `CustomQueryParameter` 或其子类型，从而统一了查询参数的类型。 |

#### 方法与属性详情
`CustomQueryIntf` 接口自身**没有定义任何方法或属性**。它是一个空的接口，其目的是作为父接口 `BaseFeActionIntf` 的一个特定化（Specialization），并添加了对泛型参数 `T` 的类型约束。这意味着所有 `CustomQueryIntf` 的实现类将继承并需要实现 `BaseFeActionIntf<T>` 中定义的方法。

### 3. 主要函数/方法 (如果适用)
该文件不包含独立的工具类函数。

### 4. 对外依赖与交互
`CustomQueryIntf.java` 文件导入了以下重要的外部类或项目内的其他类：

1.  **`gpf.dc.basic.fe.component.BaseFeActionIntf`**:
    *   **类型**: 接口。
    *   **交互**: `CustomQueryIntf` 直接继承了 `BaseFeActionIntf`。这意味着 `CustomQueryIntf` 是 `BaseFeActionIntf` 的一个子类型，继承了 `BaseFeActionIntf` 中定义的所有方法契约。所有实现 `CustomQueryIntf` 的类也必须实现 `BaseFeActionIntf` 中定义的方法。`BaseFeActionIntf` 很可能定义了前端（FE - Front-End）操作或行为的一些基本骨架或规范。

2.  **`gpf.dc.basic.param.view.CustomQueryParameter`**:
    *   **类型**: 类或接口（根据其名称，很可能是一个参数类）。
    *   **交互**: 它被用作 `CustomQueryIntf` 接口泛型参数 `T` 的上限 (`T extends CustomQueryParameter`)。这强制要求所有使用 `CustomQueryIntf` 的地方，其操作的参数类型 `T` 必须是 `CustomQueryParameter` 类或其任何子类。这确保了自定义查询的参数具备统一的结构或行为，提高了类型安全性和代码的可预测性。

总结来说，`CustomQueryIntf` 与这两个依赖的交互主要体现在**继承其行为规范** (`BaseFeActionIntf`) 和**约束其操作的数据类型** (`CustomQueryParameter`) 上，共同构建了一个类型安全且规范化的自定义查询操作体系。

