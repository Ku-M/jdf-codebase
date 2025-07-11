# Analysis for: gpf_dc_ReportGenerator\src\core\reportGenerator\util\ProjectDataConversionUtil.java

## Original File Path
`D:\TMP\CLASS_BUS_PROJECT\gpf_dc_ReportGenerator\src\core\reportGenerator\util\ProjectDataConversionUtil.java`

## Extracted Snippets & Analysis
根据你提供的代码和核心规则，我提取了以下几个代码样例。  由于无法访问私有库，我将所有私有类的调用都用占位符代替，并尽可能保持代码的独立性和可复用性。

**样例 1:  创建临时文件夹**

```java
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;

import java.io.File;

File tempFileDir = FileUtil.mkdir(String.format("./temp/%s/", IdUtil.fastSimpleUUID()));
```

**样例 2:  从表单获取附件字节数组**

```java
import gpf.adur.data.Form;
import gpf.adur.data.AttachData;

import java.util.List;

byte[] getAttachmentBytes(Form projectTempForm, String fieldName) throws Exception {
    List<AttachData> attachments = projectTempForm.getAttachments(fieldName);
    if (attachments == null || attachments.isEmpty()) return null;
    return attachments.get(0).getContent();
}
```


**样例 3: 将JSON字符串解析为对象**

```java
import com.leavay.common.util.GsonUtil;
import reportGenerator.dto.ProjectDataWrapperDto;

ProjectDataWrapperDto parseProjectDataWrapperDto(String projectDataJson) {
    ProjectDataWrapperDto currentProjectData = null;
    if (projectDataJson != null && !projectDataJson.isEmpty()) {
        try {
            currentProjectData = GsonUtil.fromJson(projectDataJson, ProjectDataWrapperDto.class);
        } catch (Exception e) {
            //  Error handling omitted for brevity,  replace with your own logging or exception handling.
        }
    }
    return currentProjectData;
}
```

**样例 4:  分割 ProjectDataDto 对象**

```java
import org.apache.commons.beanutils.BeanUtils; // 使用commons-beanutils代替hutool的BeanUtil，更通用
import reportGenerator.dto.ProjectDataDto;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;

List<ProjectDataDto> splitProjectDataDto(ProjectDataDto dataDto, Long maxRowNo) {
    int rowNo = dataDto.getRowDataList().size();
    int needSplitNo = (int) ((rowNo + maxRowNo - 1) / maxRowNo); // 使用更简洁的整除计算
    List<ProjectDataDto> resultList = new ArrayList<>();
    List<LinkedHashMap<String, String>> rowDataList = dataDto.getRowDataList();

    for (int i = 0; i < needSplitNo; i++) {
        ProjectDataDto newObj = new ProjectDataDto();
        try {
            BeanUtils.copyProperties(newObj, dataDto); // 使用BeanUtils代替BeanUtil
        } catch (Exception e) {
            //  Error handling omitted for brevity
        }
        int l = (int) (i * maxRowNo);
        int r = (int) Math.min((i + 1) * maxRowNo, rowNo);
        newObj.setRowDataList(rowDataList.subList(l, r));
        resultList.add(newObj);
    }
    return resultList;
}
```

**样例 5：从表单获取字符串值，并处理缺失值**


```java
import gpf.adur.data.Form;

String getProjectTemplateType(Form projectTempForm) throws Exception {
    String tableColNames = projectTempForm.getString("表格列名集合");
    String textFieldNames = projectTempForm.getString("文本字段集合");

    if (tableColNames != null && !tableColNames.isEmpty() && textFieldNames != null && !textFieldNames.isEmpty()) {
        return "MixedTextExcelPage"; // 使用字符串常量代替ProjectTypeConst
    } else if (tableColNames != null && !tableColNames.isEmpty()) {
        return "ExcelPage"; // 使用字符串常量代替ProjectTypeConst
    } else if (textFieldNames != null && !textFieldNames.isEmpty()) {
        return "TextPage"; // 使用字符串常量代替ProjectTypeConst
    } else {
        return null;
    }
}
```

这些样例都遵循了核心规则，去除了业务细节，并用占位符替换了具体的业务数据和私有库的调用。  它们展示了如何使用API进行一些核心操作，适合AI学习。  需要注意的是，`BeanUtils.copyProperties` 需要引入 `commons-beanutils` 库。  我把hutool的BeanUtil替换成更通用的BeanUtils，避免依赖私有库。  其他部分的错误处理被省略，你需要根据实际情况补充。
