### 1. 文件核心功能
这个文件定义了一个名为 `ActionMethodParams` 的Java自定义注解（Annotation）。其核心功能是：

1.  **提供定制动作方法的参数说明**：它旨在用于标记特定“动作方法”（Action Method），并为其提供详细的参数元数据。这通常用于Web框架、RPC服务或命令解析器中，以便在运行时动态地理解和处理方法的输入参数。
2.  **聚合参数信息**：它通过包含一个 `ActionMethodParam` 数组来聚合多个参数的描述信息。这意味着一个 `ActionMethodParams` 注解可以描述一个方法的所有或部分参数。
3.  **运行时可用性**：通过 `@Retention(RetentionPolicy.RUNTIME)`，该注解的信息在Java虚拟机运行时仍然可用，使得应用程序可以通过反射机制读取和处理这些参数元数据。
4.  **弃用标记**：该注解已被标记为 `@Deprecated`，并明确指出应使用 `InputDeclare` 代替。这表明该组件正在被淘汰，后续开发应避免使用，并逐步迁移到新的解决方案。

在整个项目中，`ActionMethodParams` 扮演着元数据提供者的角色，用于为业务逻辑层或控制器层的“动作方法”附加结构化的参数描述，从而支持自动化处理、验证、文档生成或API Gateway等功能。然而，其`@Deprecated`状态意味着它是一个遗留组件，正在被替换。

### 2. 主要组件/类定义

| 类/组件名          | 继承自/实现             | 主要职责                                                                                                                                                                                                                                                                   |
| :----------------- | :---------------------- | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `ActionMethodParams` | `java.lang.annotation.Annotation` (隐式) | 定义一个用于标记方法并提供其参数元数据的注解。该注解包含一个 `ActionMethodParam` 数组，用于描述方法中的每个参数。它支持运行时反射，且被标记为已弃用，建议使用 `InputDeclare` 代替。它仅能应用于方法 (`ElementType.METHOD`)。 |

#### 方法与属性详情
对于 `ActionMethodParams` 注解：

| 方法/属性 | 类型              | 描述                                                               |
| :-------- | :---------------- | :----------------------------------------------------------------- |
| `meta()`  | `ActionMethodParam[]` | 一个注解元素，返回一个 `ActionMethodParam` 数组。数组中的每个 `ActionMethodParam` 注解预期用于描述被注解方法的一个参数。 |

### 3. 主要函数/方法 (如果适用)
此文件定义了一个注解接口，不包含独立的工具类方法，因此本节不适用。

### 4. 对外依赖与交互

该文件导入并使用了以下重要的外部库或项目内的其他类：

1.  **`java.lang.annotation.*` (Java标准注解API)**:
    *   `Documented`: 表明该注解会被包含在Javadoc中。
    *   `ElementType`: `ElementType.METHOD` 指定了该注解只能应用于方法上。
    *   `Retention`: `RetentionPolicy.RUNTIME` 指定了该注解在运行时通过反射是可访问的。
    *   `RetentionPolicy`: 定义了注解的保留策略。
    *   `Target`: 定义了注解可以应用于哪些程序元素。
    *   **交互**: 这是Java注解机制的基础，`ActionMethodParams` 依赖这些标准API来定义其自身的行为和生命周期。

2.  **`cmn.anotation.ClassDeclare` (项目内部注解)**:
    *   这是一个自定义的类声明注解，用于为 `ActionMethodParams` 注解自身提供元数据（如作者、版本、创建时间、功能描述等）。
    *   **交互**: 这表明项目中存在一个通用的元数据声明机制，用于内部文档和代码理解。`ActionMethodParams` 作为项目的一部分，也遵循了这种内部的元数据规范。

3.  **`jit.anotation.ActionMethodParam` (项目内部注解)**:
    *   这是 `ActionMethodParams` 注解中 `meta()` 元素所使用的类型。这意味着 `ActionMethodParam` 是一个独立的注解，用于描述**单个**方法参数的详细信息（例如参数名称、类型、描述、是否可选等）。
    *   **交互**: `ActionMethodParams` 与 `ActionMethodParam` 形成了一个复合关系。`ActionMethodParams` 用于包装多个 `ActionMethodParam`，共同为方法的参数提供全面的元数据。应用程序会读取 `ActionMethodParams` 来获取其内部的 `ActionMethodParam` 数组，进而解析每个参数的详细信息。

4.  **外部交互（被动）**:
    *   `ActionMethodParams` 本身是元数据，不主动与外部系统交互。
    *   其主要交互方式是被其他组件（如反射工具、AOP框架、Web框架的控制器调度器）在运行时通过Java反射机制读取和解析，以实现参数校验、数据绑定、API文档生成或动态方法调用等功能。
    *   由于被标记为 `@Deprecated`，它与未来的 `InputDeclare` 注解存在替换关系，开发者需要将其现有用法迁移到新的注解上。

