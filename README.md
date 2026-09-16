# 实验1 · Kotlin 与 Compose 开发环境及基础界面

课程实验1 的独立工程。基于 **Kotlin + Jetpack Compose（Material 3）**，
实现课程任务列表界面与可复用的 `TaskCard` 组件，
并演示 `data class` 与 Kotlin 空安全在 UI 数据中的作用。

---

## 一、实验目标对应关系

| 实验目标 | 实现位置 |
|---|---|
| 环境验证（SDK / 模拟器或真机） | `build.bat`、本文档第四节 |
| 创建 Compose 项目并能运行 | 整个工程，入口 `MainActivity` |
| 至少 3 种基本组件 | `Lab1Screen.kt`（Scaffold / TopAppBar / LazyColumn / Card / Row / Column / Box / Text） |
| 可复用 TaskCard | `TaskCard.kt` |
| 理解 data class 与空安全 | `CourseTask.kt` + `CourseTaskTest.kt` |

---

## 二、工程结构

```
lab1-android/
├── app/
│   ├── build.gradle.kts
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── java/com/example/lab1/
│       │   │   ├── MainActivity.kt              # 应用入口
│       │   │   ├── Lab1Screen.kt                # 任务列表界面（含整屏 Preview）
│       │   │   ├── TaskCard.kt                  # 可复用卡片（含 3 个 Preview）
│       │   │   ├── CourseTask.kt                # 数据类 + sampleTasks + displayOwner
│       │   │   └── ui/theme/                    # Color / Type / Theme
│       │   └── res/                             # strings / themes / colors / 图标
│       └── test/java/com/example/lab1/
│           └── CourseTaskTest.kt                # 6 个纯逻辑单测
├── docs/
│   ├── 实验1-实验报告.md
│   └── screenshots/                             # 运行截图（按 README 命名）
├── build.bat                                    # 一键构建脚本
└── gradle/libs.versions.toml                    # 依赖版本集中管理
```

---

## 三、技术栈

| 类别 | 选型 | 版本 |
|---|---|---|
| 语言 | Kotlin | 2.0.21 |
| UI | Jetpack Compose（Material 3） | BOM 2024.09.00 |
| 构建 | AGP 8.11.0 + Gradle 8.13 + JDK 21（jvmTarget 17） | — |
| 测试 | JUnit 4.13.2（JVM 单测） | — |
| SDK | minSdk 24 / targetSdk 35 / compileSdk 35 | — |

---

## 四、构建与运行

```batch
build.bat assembleDebug      :: 编译，产出 app/build/outputs/apk/debug/app-debug.apk
build.bat installDebug       :: 安装到模拟器/真机
build.bat testDebugUnitTest  :: 运行单元测试
```

`build.bat` 已内置 JDK、SDK 路径，并修正了中文路径导致的编码问题，
无需手工配置环境变量。详见实验报告第五节故障记录。

首次运行前请确认 `local.properties` 中的 `sdk.dir` 指向本机 SDK：

```properties
sdk.dir=D\:\\sdk
```

---

## 五、关键实现说明

### 1. 空安全：三步收敛，全程不用 `!!`

```kotlin
fun displayOwner(task: CourseTask): String =
    task.owner?.trim()?.takeIf { it.isNotEmpty() } ?: "未分配"
```

`owner` 声明为 `String?`，让「未指派」在类型层面显式存在。
`?.` 保证不 NPE，`takeIf` 把空串也归为未分配，`?:` 统一兜底为中文文案。
处理点只有这一处，UI 组件拿到的永远是可直接展示的 `String`。

### 2. 可复用组件：无状态 + 事件向上

`TaskCard(title, owner, completed, onClick, priority, modifier)` 不持有任何状态，
因此可跨页面复用，也能脱离 Activity 在 Preview 中独立渲染。
点击事件向上抛给 `Lab1Screen`，由父级用 `copy()` 生成新列表写回状态。

### 3. 样式：优先级的语义色 + 主题字体

- 优先级用左侧 4dp 色条表达（高=红 / 中=蓝 / 低=灰），不额外占用布局空间；
- 全部字号取自 `MaterialTheme.typography`，组件内无一处硬编码 `sp`。

### 4. Preview

共 4 个：`TaskCard` 的进行中 / 已完成 / 未分配，以及整屏 `Lab1Screen`。
均包在 `Lab1Theme` 内，保证预览效果与实际运行一致。

---

## 六、单元测试

`CourseTaskTest` 覆盖 6 个用例，全部通过：

| 用例 | 验证内容 |
|---|---|
| owner 为 null 时显示未分配 | Elvis 兜底分支 |
| owner 为空白串时同样显示未分配 | `takeIf` 对伪空值的收敛 |
| owner 有值时去除首尾空格并原样返回 | `trim()` 行为 |
| copy 返回新对象且原对象不被修改 | 数据类不可变语义（Compose 重组依据） |
| data class 按内容判等 | `equals` 行为 |
| sampleTasks 含三条数据且恰有一条未分配负责人 | 测试数据的完整性 |

---

## 七、文档

| 文件 | 说明 |
|---|---|
| `docs/实验1-实验报告.md` | 完整实验报告：环境、关键代码、验收自检、故障记录、四问自答 |
| `docs/screenshots/README.md` | 截图命名规范与真机 USB 调试开启步骤 |
