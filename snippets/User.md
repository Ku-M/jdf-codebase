以下是对 `User.java` 文件的技术知识库分析：

### 1. 文件核心功能
`User.java` 文件定义了系统中的用户（User）实体模型。它继承自 `gpf.adur.data.Form` 类，这表明它是一个结构化的数据载体，旨在封装和管理用户的各项属性。其主要职责包括：
*   **定义用户属性**: 通过 `public final static String` 常量定义了用户编码、用户名、密码、邮箱、电话、状态、性别、头像以及 token 有效期等核心属性的键。
*   **数据封装与访问**: 提供了一系列类型安全且遵循 JavaBeans 规范的 `get` 和 `set` 方法，用于访问和修改这些用户属性。这些方法通常通过调用父类 `Form` 提供的方法（如 `getStringByCode`, `setAttrValueByCode`）来操作底层数据。
*   **状态初始化与管理**: 在构造函数中，默认将用户状态设置为“未锁定”（`UserStatus.UnLocked`），并提供了 `isLocked()` 便捷方法以及基于字符串和枚举类型对用户状态和性别的设置能力。
*   **数据模型基石**: 作为用户模块的数据基础，它将用于在业务逻辑层和持久化层之间传递用户相关的数据。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class User` | `gpf.adur.data.Form` | 定义和管理系统中的用户实体数据，包括用户的基本信息（如编码、用户名、密码、邮箱、电话、状态、性别等）。它作为数据模型层，封装了用户属性的存取逻辑，并提供便捷方法来操作用户状态等。 |

#### 方法与属性详情

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `serialVersionUID` | `private static final long` | 用于对象的序列化和反序列化，确保在不同JVM或版本间序列化对象的兼容性。 |
| `Code` | `public final static String` | 定义用户编码的字符串键。 |
| `UserName` | `public final static String` | 定义用户名的字符串键。 |
| `Alias` | `public final static String` | 定义用户别名的字符串键。 |
| `FullName` | `public final static String` | 定义用户全名的字符串键，其值与 `Alias` 相同。 |
| `Password` | `public final static String` | 定义用户密码的字符串键。 |
| `Email` | `public final static String` | 定义用户电子邮箱的字符串键。 |
| `Phone` | `public final static String` | 定义用户手机号码的字符串键。 |
| `ProfilePhoto` | `public final static String` | 定义用户头像的字符串键。 |
| `Status` | `public final static String` | 定义用户状态的字符串键。 |
| `Gender` | `public final static String` | 定义用户性别的字符串键。 |
| `TokenExpireTime` | `public final static String` | 定义用户token有效期的字符串键，其值来自 `gpf.md.user.BasicUser`。 |
| `User()` | 构造函数 | 无参构造函数，在创建 `User` 实例时，默认将用户的 `Status` 属性设置为 `UserStatus.UnLocked`。 |
| `User(String formModelID)` | 构造函数 | 带 `formModelID` 参数的构造函数，通常用于通过一个特定的模型ID初始化表单数据，同时也将用户的 `Status` 属性设置为 `UserStatus.UnLocked`。 |
| `getCode()` | `String` | 获取用户编码。 |
| `setCode(String code)` | `User` | 设置用户编码，返回当前 `User` 实例以支持链式调用。 |
| `getUserName()` | `String` | 获取用户名。 |
| `setUserName(String userName)` | `User` | 设置用户名，返回当前 `User` 实例。 |
| `getFullName()` | `String` | 获取用户全名（实际为用户别名）。 |
| `setFullName(String fullName)` | `User` | 设置用户全名，返回当前 `User` 实例。 |
| `getPassword()` | `gpf.adur.data.Password` | 获取用户密码对象。 |
| `setPassword(gpf.adur.data.Password password)` | `User` | 设置用户密码对象，返回当前 `User` 实例。 |
| `getStatus()` | `String` | 获取用户状态的字符串表示。 |
| `getStatusEnum()` | `UserStatus` | 将用户状态字符串转换为 `UserStatus` 枚举类型并返回。 |
| `setStatus(String status)` | `User` | 设置用户状态（字符串形式），返回当前 `User` 实例。 |
| `setStatus(UserStatus status)` | `User` | 设置用户状态（枚举形式），内部会转换为字符串存储，返回当前 `User` 实例。 |
| `getGender()` | `String` | 获取用户性别的字符串表示。 |
| `setGender(String gender)` | `User` | 设置用户性别（字符串形式），返回当前 `User` 实例。 |
| `setGender(UserGender gender)` | `User` | 设置用户性别（枚举形式），内部会转换为字符串存储，返回当前 `User` 实例。 |
| `isLocked()` | `boolean` | 判断用户当前状态是否为锁定状态 (`UserStatus.Locked`)。 |
| `getEmail()` | `String` | 获取用户电子邮箱。 |
| `setEmail(String email)` | `User` | 设置用户电子邮箱，返回当前 `User` 实例。 |
| `getPhone()` | `String` | 获取用户手机号码。 |
| `setPhone(String phone)` | `User` | 设置用户手机号码，返回当前 `User` 实例。 |
| `getProfilePhoto()` | `byte[]` | 获取用户头像的字节数组。 |
| `setProfilePhoto(byte[] profilePhoto)` | `User` | 设置用户头像的字节数组，返回当前 `User` 实例。 |
| `getTokenExpireTime()` | `Long` | 获取用户token的有效时间（单位：分钟）。 |
| `setTokenExpireTime(Long tokenExpireTime)` | `User` | 设置用户token的有效时间，返回当前 `User` 实例。 |

### 3. 主要函数/方法 (如果适用)
本文件主要定义了一个Java类，其核心功能通过类的实例方法实现，因此不包含独立的工具函数。所有相关功能已在“方法与属性详情”中描述。

### 4. 对外依赖与交互
*   **`gpf.adur.data.Form`**: `User` 类直接继承自 `Form`，这意味着它依赖于 `Form` 类提供的基础数据管理能力。`Form` 类很可能提供了一套通用的机制，用于以键值对的形式存储和检索数据（例如通过一个内部 `Map`），并封装了如 `getStringByCode()`, `setAttrValueByCode()`, `getPasswordByCode()`, `getByteArrayByCode()`, `getLongByCode()` 等方法。`User` 类通过这些父类方法来操作其内部数据。
*   **`gpf.md.user.BasicUser`**: `User` 类通过 `public final static String TokenExpireTime = BasicUser.TokenExpireTime;` 引用了 `BasicUser` 类中定义的常量。这表明 `BasicUser` 可能是一个更基础的用户模型或常量定义集合，提供了跨模块共享的基础属性或配置。
*   **`gpf.adur.data.Password`**: 用户的密码属性被定义为 `gpf.adur.data.Password` 类型而非简单的 `String`。这强烈暗示了密码数据被封装在一个专门的对象中，可能用于实现更复杂的密码安全机制，如哈希、加密、盐值处理或自定义的密码验证逻辑。
*   **`UserStatus` (推断的枚举类型)**: `User` 类通过 `getStatusEnum()` 方法、 `setStatus(UserStatus status)` 方法以及在构造函数中对 `UserStatus.UnLocked` 的引用，表明它依赖于一个名为 `UserStatus` 的枚举类型。这个枚举应定义了用户可能具有的各种状态（例如 `UnLocked`, `Locked`, `Active`, `Inactive` 等），以提供类型安全的状态管理。
*   **`UserGender` (推断的枚举类型)**: 类似于 `UserStatus`，通过 `setGender(UserGender gender)` 方法可以推断存在一个名为 `UserGender` 的枚举类型，它定义了用户的性别选项，提供了类型安全的性别管理。
*   **业务逻辑层**: 作为一个数据模型，`User` 实例将作为数据载体在业务逻辑层中流通，用于执行用户相关的业务操作，如用户认证、信息修改、权限分配等。
*   **持久化层**: `User` 对象很可能被持久化到数据库或其他存储介质中。鉴于其继承自 `Form`，并且存在 `serialVersionUID`，推测其持久化操作可能由 `Form` 类的实现或者通过特定的数据访问对象（DAO）层来完成。`User` 类本身只负责数据封装。

