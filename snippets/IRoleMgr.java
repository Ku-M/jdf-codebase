## `IRoleMgr.java` 文件分析

### 1. 文件核心功能

`IRoleMgr.java` 文件定义了一个核心服务接口，主要职责是管理应用程序中的**组织模型 (FormModel)**、**组织数据 (Org)** 和**角色/身份数据 (Role)**。它提供了对这三类数据及其关联关系的完整的 **CRUD (创建、读取、更新、删除)** 操作。

该接口在系统中扮演着用户、角色、组织权限管理的核心入口，对外暴露了所有与组织结构和角色分配相关的业务逻辑。特别强调了角色和身份的区别：不指定组织拥有者的 `Role` 被视为“身份”。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public interface IRoleMgr` | `ServiceCellIntf` | 定义了组织模型、组织数据、角色/身份数据的管理操作。作为业务逻辑层与数据持久层之间的接口，负责协调和封装复杂的业务流程，包括数据查询、增删改以及各实体之间的关联操作。 |

#### 方法与属性详情

`IRoleMgr` 接口中定义的方法可以大致分为以下几类：

**A. 组织模型 (FormModel) 相关操作**

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `static IRoleMgr get()` | `IRoleMgr` | 获取 `IRoleMgr` 接口的单例或服务实例。 |
| `boolean isOrgModel(String formModelId)` | `boolean` | 判断给定ID的模型是否为组织模型。 |
| `String getRootOrgModelId()` | `String` | 获取根组织模型的ID。 |
| `List<String> queryInheritPaths(String formModelId)` | `List<String>` | 查询指定模型的继承路径（祖先模型ID列表）。 |
| `FormModel getOrgRootModel()` | `FormModel` | 获取根组织模型对象。 |
| `FormModel createOrgModel(FormModel model)` | `FormModel` | 创建新的组织模型。 |
| `FormModel updateOrgModel(Progress prog, FormModel model)` | `FormModel` | 更新组织模型，支持进度通知。 |
| `void renameOrgModel(Progress prog, Map<String,Pair<String, String>> renameModels)` | `void` | 批量重命名组织模型（ID和中文名），支持进度通知。 |
| `void renameOrgModelId(Progress prog, Map<String,String> renameModels)` | `void` | 批量重命名组织模型ID，支持进度通知。 |
| `FormModel queryOrgModel(String orgModelID)` | `FormModel` | 根据组织模型ID查询组织模型。 |
| `FormModel queryOrgModelByUuid(String uuid)` | `FormModel` | 根据模型的UUID查询组织模型。 |
| `List<FormModel> queryChildOrgModels(String orgModelID)` | `List<FormModel>` | 查询指定组织模型下的所有子组织模型。 |
| `List<FormModel> queryAllOrgModels()` | `List<FormModel>` | 查询所有组织模型。 |
| `List<FormModel> queryOrgModelByPackage(String packagePath)` | `List<FormModel>` | 根据包路径查询组织模型列表。 |
| `ResultSet<FormModel> queryOrgModelPage(List<String> parentIds, String packagePath, String keyword, int pageNo, int pageSize)` | `ResultSet<FormModel>` | 分页查询组织模型结果集，支持父ID、包路径和关键字过滤。 |
| `void deleteOrgModel(String orgModelID)` | `void` | 删除指定的组织模型。 |

**B. 组织数据 (Org) 相关操作**

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `Org copyOrg(Org org)` | `Org` | 构建组织数据的副本。 |
| `Org createOrg(IDao dao, Org org)` | `Org` | 创建组织数据。 |
| `Org createOrg(Progress prog, IDao dao, Org org, FormOpObserver observer)` | `Org` | 创建组织数据，支持进度通知和操作观察者回调。 |
| `Org updateOrg(IDao dao, Org org)` | `Org` | 更新组织数据。 |
| `Org updateOrg(Progress prog, IDao dao, Org org, FormOpObserver observer)` | `Org` | 更新组织数据，支持进度通知和操作观察者回调。 |
| `Org queryOrg(IDao dao, String orgModelID, String orgUuid)` | `Org` | 根据组织模型ID和UUID查询组织数据。 |
| `Org queryOrgByCode(IDao dao, String orgModelID, String code)` | `Org` | 根据组织模型ID和编码查询组织数据。 |
| `String queryOrgUuidByCode(IDao dao, String orgModelID, String code)` | `String` | 根据组织模型ID和编码查询组织UUID。 |
| `Org queryChildOrgByLabel(IDao dao, String orgModelID, String parentOrgUuid, String label)` | `Org` | 根据父组织UUID和标签查询子组织。 |
| `Org queryOrgByPath(IDao dao, String orgModelID, String parentOrgUuid, String path)` | `Org` | 根据组织模型ID、父组织UUID和路径查找组织。 |
| `Org queryOrgOfRole(IDao dao, String orgModelId, String roleUuid)` | `Org` | 查询指定角色所属的组织。 |
| `Map<String,Org> queryPathOfOrg(IDao dao, List<Org> orgList)` | `Map<String,Org>` | 查询组织列表的完整路径。 |
| `ResultSet<Org> queryOrgPage(IDao dao, String orgModelID, Cnd cnd, int pageNo, int pageSize)` | `ResultSet<Org>` | 分页查询组织数据，支持查询条件。 |
| `ResultSet<Org> queryOrgPageBySql(IDao dao, String orgModelID, String querySql, Set<String> extFields,Cnd cnd, int pageNo, int pageSize)` | `ResultSet<Org>` | 根据自定义SQL分页查询组织数据，支持额外字段和查询条件。 |
| `List<Org> queryChildOrg(IDao dao, String orgModelID, String parentOrgUuid, Cnd cnd)` | `List<Org>` | 查询指定父组织下的子组织列表，支持查询条件。 |
| `ResultSet<Org> queryOrgPageOfUser(IDao dao, String orgModelId, Cnd cnd, String userModelId, String userUuid, int pageNo, int pageSize)` | `ResultSet<Org>` | 查询用户关联的组织分页结果。 |
| `ResultSet<User> queryUserPageOfOrg(IDao dao, String orgModelId, String orgUuid, String userModelId, Cnd cnd, int pageNo, int pageSize)` | `ResultSet<User>` | 查询组织下的用户分页结果。 |
| `void deleteOrg(IDao dao, String orgModelID, String orgUuid)` | `void` | 删除组织数据。 |
| `void deleteOrg(Progress prog, IDao dao, String orgModelID, String orgUuid, FormOpObserver observer)` | `void` | 删除组织数据，支持进度通知和操作观察者回调。 |

**C. 角色/身份 (Role) 相关操作**

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `Role createRole(IDao dao, String orgModelID, String orgUuid, Role role)` | `Role` | 在指定组织下创建角色。 |
| `Role createRole(Progress prog, IDao dao, String orgModelID, String orgUuid, Role role, FormOpObserver observer)` | `Role` | 在指定组织下创建角色，支持进度通知和操作观察者回调。 |
| `Role createRole(IDao dao, Role role)` | `Role` | 创建身份（不指定组织拥有者的角色）。 |
| `Role createRole(Progress prog, IDao dao, Role role, FormOpObserver observer)` | `Role` | 创建身份，支持进度通知和操作观察者回调。 |
| `Role queryRole(IDao dao, String roleUuid)` | `Role` | 通过UUID查询角色。 |
| `Role queryRoleByCode(IDao dao, String roleCode)` | `Role` | 通过编码查询角色。 |
| `List<Role> queryMountedRoleList(IDao dao, String roleUuid)` | `List<Role>` | 查询指定角色下挂载的子角色列表。 |
| `List<User> queryMountedUserList(IDao dao, String roleUuid, String userModelID)` | `List<User>` | 查询指定角色下挂载的用户列表。 |
| `ResultSet<User> queryUserPageOfRole(IDao dao, String roleUuid, boolean onlyMountedRole, String userModelID, Cnd cnd, int pageNo, int pageSize)` | `ResultSet<User>` | 查询角色下的用户分页结果。 |
| `ResultSet<Role> queryRolePageOfUser(IDao dao, String userModelId, String userUuid, Cnd cnd, int pageNo, int pageSize, boolean onlyMounted)` | `ResultSet<Role>` | 查询用户关联的角色分页结果。 |
| `void updateRole(IDao dao, Role role)` | `void` | 更新角色数据。 |
| `void updateRole(Progress prog, IDao dao, Role role, FormOpObserver observer)` | `void` | 更新角色数据，支持进度通知和操作观察者回调。 |
| `void deleteRole(IDao dao, List<String> uuids)` | `void` | 删除指定UUID的角色列表。 |
| `void deleteRole(Progress prog, IDao dao, List<String> uuids, FormOpObserver observer)` | `void` | 删除指定UUID的角色列表，支持进度通知和操作观察者回调。 |
| `List<Role> queryRoleListOfOrg(IDao dao, String orgModelID, String orgUuid)` | `List<Role>` | 列出指定组织下的角色列表。 |
| `ResultSet<Role> queryRolePage(IDao dao, Cnd cnd, int pageNo, int pageSize)` | `ResultSet<Role>` | 分页查询角色数据，支持查询条件（通过 `owner` 字段区分角色和身份）。 |
| `ResultSet<Role> queryRolePageBySql(IDao dao, String querySql, Set<String> extFields, Cnd cnd, int pageNo, int pageSize)` | `ResultSet<Role>` | 根据自定义SQL分页查询角色数据，支持额外字段和查询条件。 |
| `ResultSet<Role> queryRolePageOfOrg(IDao dao, String orgModelID, String orgUuid, Cnd cnd, int pageNo, int pageSize)` | `ResultSet<Role>` | 列出指定组织下的角色分页结果。 |
| `void mountRoleToUser(IDao dao, String roleUuid, String userModelId, List<String> userUuids)` | `void` | 将角色分配给用户。 |
| `void unmountRoleFromUser(IDao dao, String roleUuid, String userModelId, List<String> userUuids)` | `void` | 解绑用户和角色关系。 |
| `void mountRoleToRole(IDao dao, List<String> childRoleUuids, String roleUuid)` | `void` | 将子角色分配给父角色（角色嵌套）。 |
| `void unmountRoleFromRole(IDao dao, List<String> childRoleUuids, String roleUuid)` | `void` | 解绑角色和角色关系。 |

**D. SQL构建辅助方法**

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `String buildQueryUserOfOrgSql(String orgModelID, String orgCondition, String userModelID)` | `String` | 构建查询组织下用户的SQL语句。 |
| `String buildQueryUserOfRoleSql(String roleCondition, String userModelID)` | `String` | 构建查询角色下用户的SQL语句。 |
| `String buildQueryRoleOfUserSql(String userModelID, String userCondition)` | `String` | 构建查询用户关联的角色的SQL语句。 |
| `String buildQueryOrgOfUserSql(String userModelID, String userCondition, String orgModelID)` | `String` | 构建查询用户关联的组织的SQL语句。 |
| `String buildQueryOrgOfRoleSql(String roleCondition, String orgModelID)` | `String` | 构建查询角色关联的组织的SQL语句。 |
| `String buildQueryRoleOfOrgSql(String orgModelID, String orgCondition, Map<String,String> extOrgFields)` | `String` | 构建查询组织下角色的SQL语句，支持额外组织属性。 |
| `String buildQueryChildOrgSql(String orgModelID, String orgCondition, boolean allChildren)` | `String` | 构建查询子组织的SQL语句，可选择递归查询所有子组织。 |
| `String buildQueryAncestorOrgSql(String orgModelID, String orgCondition, boolean allAncestor)` | `String` | 构建查询父组织的SQL语句，可选择递归查询所有父组织。 |

### 3. 对外依赖与交互

`IRoleMgr.java` 文件导入了以下重要的外部库或项目内部类：

*   **Java标准库**:
    *   `java.util.List`, `java.util.Map`, `java.util.Set`: 用于处理集合类型数据。

*   **Nutz.Dao 框架**:
    *   `org.nutz.dao.Cnd`: Nutz.Dao 提供的条件表达式类，用于构建数据库查询条件。
    *   `org.nutz.dao.util.cri.SqlExpressionGroup`: Nutz.Dao 提供的SQL表达式组，用于组合更复杂的查询条件。
    *   **交互**: 大量方法接受 `Cnd` 作为参数，表明 `IRoleMgr` 的实现将利用 Nutz.Dao 框架进行灵活的数据库查询。

*   **项目内部依赖**:
    *   `bap.cells.Cells`: 一个服务获取工具类，`IRoleMgr` 通过 `Cells.get(IRoleMgr.class)` 获取自身的实现类实例。
    *   `cell.ServiceCellIntf`: `IRoleMgr` 实现的接口，可能是一个定义服务单元的通用契约。
    *   `cell.cdao.IDao`: 数据访问对象接口，几乎所有与数据操作相关的方法都将 `IDao` 作为第一个参数，表明 `IRoleMgr` 的实现依赖于这个DAO接口进行具体的数据库操作。
    *   `cmn.dto.Progress`: 通用数据传输对象，用于操作进度通知。某些CRUD方法接受此对象，允许在长时间操作中向调用者提供进度反馈。
    *   `gpf.adur.data.FormModel`: 表示组织模型的DTO/实体类。
    *   `gpf.adur.data.ResultSet`: 用于封装分页查询结果的数据结构，包含数据列表和总记录数。
    *   `gpf.adur.role.Org`: 表示组织数据的DTO/实体类。
    *   `gpf.adur.role.Role`: 表示角色/身份数据的DTO/实体类。
    *   `gpf.adur.user.User`: 表示用户数据的DTO/实体类。
    *   `gpf.dc.intf.FormOpObserver`: 表单操作观察者接口，用于在某些数据操作（如创建、更新、删除组织或角色）前后触发回调逻辑，实现解耦和扩展。
    *   `web.dto.Pair`: Web层定义的通用键值对，用于一些方法参数。

**交互模式**:
`IRoleMgr` 作为业务逻辑层，通过依赖注入（或类似 `Cells.get()` 的服务查找机制）获取其实现类，然后利用 `IDao` 接口与底层数据存储进行交互。它封装了对 `FormModel`, `Org`, `Role`, `User` 这些核心业务实体的操作，并提供了灵活的查询（包括基于条件和自定义SQL的查询）以及实体间（如角色与用户、角色与角色、组织与用户）的关联管理。通过 `Progress` 和 `FormOpObserver`，它还支持操作过程中的状态通知和事件回调机制。

