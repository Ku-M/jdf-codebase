### 1. 文件核心功能

`UploadFile.java` 文件的核心功能是**封装和管理前端文件选择并上传至服务端的逻辑**。它作为一个“能力”（Ability）对象，定义了文件上传的各种参数（如目标目录、文件类型、大小限制、是否多选、图片压缩等），并通过回调机制与前端进行交互。

在整个项目中，它扮演着一个关键的**前后端文件上传桥梁**的角色。后端通过构建 `UploadFile` 对象并触发其 `action` 或 `asyncAction` 方法，能够指示前端调用底层的公共服务（如 `IFileUploadService`）来完成文件的实际上传，并接收上传后的文件信息（`RemoteFile`）作为结果。这实现了文件上传流程的标准化和参数化配置。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class UploadFile` | `BasicAbility<UploadFileResult>` | 封装文件上传的参数和行为，作为一种可被前端调用的“能力”。它定义了文件上传的配置项（如文件类型、大小限制、是否多选等），并通过回调机制与前端交互，触发文件选择和上传操作，最终返回上传结果。 |

#### 方法与属性详情

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `serialVersionUID` | `long` | 类的序列化版本UID。 |
| `tempFolder` | `String` | 指定文件上传到服务端的临时目录（相对路径）。如果为空，则由公共服务自行决定。 |
| `tempFilePrefix` | `String` | 指定暂存临时文件的前缀。 |
| `allowMultiple` | `boolean` | 是否允许前端选择多个文件进行上传，默认为`false`（单选）。 |
| `fileType` | `PickFileType` | 指定文件选择器的文件类型，例如图片、视频或自定义类型。如果限定扩展名，需设为`PickFileType.custom`。 |
| `extFilter` | `List<String>` | 指定允许上传的文件扩展名列表，仅当`fileType`为`custom`时有效。 |
| `filePaths` | `List<String>` | 指定要上传的本地文件路径，主要用于非Web环境（如移动端App）直接上传文件，Web端不可用。 |
| `silence` | `Boolean` | 是否以静默方式执行上传，即不显示等待、进度框等UI提示，默认为`false`。 |
| `completeListener` | `UploadFileListener` | 异步模式下，文件上传完成时调用的监听器，用于接收上传结果。 |
| `sizeLimit` | `UploadFileSizeLimitDto` | 上传文件大小的限制设置，包含最大文件大小、最小文件大小等。 |
| `imageCompress` | `ImageCompressDto` | 图片文件上传时的压缩设置，如是否压缩、压缩质量等。 |
| `UploadFile()` | 构造函数 | 默认构造函数，初始化并设置默认的超时时间为两小时（120*60*1000毫秒）。 |
| `specifiedFile(Boolean silence, List<String> filePath)` | `static UploadFile` | 静态工厂方法，用于创建一个预设了指定文件路径和静默模式的`UploadFile`实例。 |
| `specifiedFile(Boolean silence, String... filePath)` | `static UploadFile` | 静态工厂方法重载，同上，但参数为可变字符串数组，方便传入多个文件路径。 |
| `isAllowMultiple()` | `boolean` | 获取是否允许多选的配置。 |
| `setAllowMultiple(boolean allowMultiple)` | `UploadFile` | 设置是否允许多选，并支持链式调用。 |
| `getFileType()` | `PickFileType` | 获取文件类型配置。 |
| `setFileType(PickFileType fileType)` | `UploadFile` | 设置文件类型，并支持链式调用。 |
| `getTempFolder()` | `String` | 获取目标上传目录。 |
| `setTempFolder(String tempFolder)` | `UploadFile` | 设置目标上传目录，并支持链式调用。 |
| `getTempFilePrefix()` | `String` | 获取临时文件前缀。 |
| `setTempFilePrefix(String tempFilePrefix)` | `UploadFile` | 设置临时文件前缀，并支持链式调用。 |
| `getExtFilter()` | `List<String>` | 获取扩展名过滤器列表。 |
| `setExtFilter(List<String> extFilter)` | `UploadFile` | 设置扩展名过滤器列表，并支持链式调用。 |
| `setExtFilter(String ... extFilter)` | `UploadFile` | 设置扩展名过滤器（可变参数版本），并支持链式调用。 |
| `getFilePaths()` | `List<String>` | 获取指定上传文件路径列表。 |
| `setFilePaths(List<String> filePaths)` | `UploadFile` | 设置指定上传文件路径列表，并支持链式调用。 |
| `setFilePaths(String... filePath)` | `UploadFile` | 设置指定上传文件路径（可变参数版本），并支持链式调用。 |
| `getSilence()` | `Boolean` | 获取是否静默模式的配置。 |
| `setSilence(Boolean silence)` | `UploadFile` | 设置是否静默模式，并支持链式调用。 |
| `setCompleteListener(UploadFileListener completeListener)` | `void` | 设置异步完成时的监听器。 |
| `getSizeLimit()` | `UploadFileSizeLimitDto` | 获取文件大小限制设置。 |
| `setSizeLimit(UploadFileSizeLimitDto sizeLimit)` | `UploadFile` | 设置文件大小限制，并支持链式调用。 |
| `getImageCompress()` | `ImageCompressDto` | 获取图片压缩设置。 |
| `setImageCompress(ImageCompressDto imageCompress)` | `UploadFile` | 设置图片压缩设置，并支持链式调用。 |
| `action(PanelContext ctx)` | `UploadFileResult` | 同步执行文件上传操作。通过`PanelContext`将此`UploadFile`对象回调给前端，等待前端执行文件选择和上传，并返回结果。 |
| `asyncAction(PanelContext ctx, UploadFileListener onComplete)` | `void` | 异步执行文件上传操作。设置完成监听器后，通过`PanelContext`回调给前端，上传结果将通过监听器返回。 |
| `upload(PanelContext ctx, String targetFolder)` | `static UploadFileResult` | 静态便利方法，用于快速创建一个`UploadFile`实例并指定目标文件夹，然后执行同步上传操作。 |

### 3. 主要函数/方法 (如果适用)

已在上述“方法与属性详情”中详细描述了所有重要的方法，特别是 `action`、`asyncAction` 和静态 `upload` 方法。

### 4. 对外依赖与交互

`UploadFile.java` 文件为了实现其功能，依赖并与以下外部库或项目内的其他类进行交互：

*   **Java标准库**:
    *   `java.util.Arrays`: 用于将可变参数转换为列表。
    *   `java.util.List`: 用于定义列表类型的属性（如`extFilter`, `filePaths`）和参数。
    *   `java.util.stream.Collectors`: 用于将数组流收集为列表。
*   **内部通用工具**:
    *   `com.leavay.common.util.ToolUtilities`: 用于辅助创建新的ArrayList，例如在`setExtFilter(String ... extFilter)`方法中。
*   **项目内数据模型与接口**:
    *   `fe.cmn.data.BasicAbility`: `UploadFile` 的父类，定义了“能力”的基础结构和行为，例如超时的设置。
    *   `fe.cmn.data.ImageCompressDto`: 用于封装图片压缩相关的配置参数。
    *   `fe.cmn.data.PickFileType`: 枚举类型，用于指定文件选择器的预设文件类型。
    *   `fe.cmn.data.UploadFileListener`: 异步上传完成时的回调接口，用于接收上传结果。
    *   `fe.cmn.data.UploadFileResult`: 封装文件上传操作的最终结果，包含上传的文件信息、是否被用户取消等。
    *   `fe.cmn.widget.UploadFileSizeLimitDto`: 用于封装文件大小限制的配置参数。
*   **项目内面板上下文**:
    *   `fe.cmn.panel.PanelContext`: 这是最重要的交互点。`UploadFile` 对象通过调用 `ctx.callback(this)` 方法，将自身作为参数传递给前端面板上下文，触发前端实际的文件选择和上传逻辑。前端在完成操作后，会将结果通过此上下文机制返回。
*   **注解**:
    *   `flutter.coder.annt.DefaultGetter`: 一个自定义注解，可能用于代码生成或在某些框架中提供属性的默认值（例如，`silence`属性默认为`false`）。

**交互流程概括**:
后端代码创建 `UploadFile` 实例，配置其参数，然后通过 `PanelContext` 的 `callback` 方法将此实例传递给前端。前端接收到回调后，根据 `UploadFile` 中的配置（如 `allowMultiple`, `fileType`, `extFilter` 等）调起文件选择界面。用户选择文件后，前端会调用底层的 `IFileUploadService`（注释中提及的公共服务，但未直接导入，意味着是通过前端间接调用）将文件上传到后端。上传完成后，前端将后端返回的 `RemoteFile` 信息封装到 `UploadFileResult` 中，并通过回调机制将结果返回给后端。后端通过 `action` 方法的返回值或 `UploadFileListener` 接收此结果进行后续处理。

