# 截图目录说明

本目录同时收纳**实验1** 与**实验2** 的截图，两个实验**全程使用 Android Studio
内置模拟器（AVD）**，无需真机。按下列文件名放到本目录，实验报告即可直接引用。

## 实验1 截图

| 文件名 | 内容 | 获取方式 |
|---|---|---|
| `01-hello-compose.png` | Compose 应用在模拟器上运行 | 模拟器窗口旁的工具条点相机图标，或 `adb exec-out screencap -p > 01-hello-compose.png` |
| `02-preview-ongoing.png` | 预览面板中 TaskCard「进行中」 | 打开 `TaskCard.kt`，Preview 面板切到 `TaskCard - 进行中`，点面板右上角截图按钮 |
| `03-preview-completed.png` | 预览面板中 TaskCard「已完成」 | 同上，切到 `TaskCard - 已完成` |
| `04-lab1-screen.png` | 运行结果：3 条任务，含「未分配」 | 在模拟器上打开「实验1 · 课程任务」后截图 |

## 实验2 截图

实验2 的入口是**另一个 Launcher 图标「实验2 · 列表与导航」**，
装好两个实验后可分别点击进入，互不干扰。

| 文件名 | 内容 | 获取方式 |
|---|---|---|
| `05-lab2-home-3items.png` | 首页 3 条任务，卡片右侧有跳转箭头 | 点桌面图标「实验2 · 列表与导航」 |
| `06-lab2-home-empty.png` | 首页空态「暂无课程任务」 | 把 `Lab2Activity` 里改成 `CurriculumData.empty` 后重新运行 |
| `07-lab2-detail-task2.png` | 详情页首行显示「任务 id = 2」 | 在首页点第 2 条卡片后截图 |
| `08-lab2-back-to-home.png` | 点返回箭头后回到首页 | 在详情页点左上角 ← 后截图 |
| `09-lab2-hundred-scroll.png` | 100 条数据滚动中，卡片无错位 | 把 `Lab2Activity` 里改成 `CurriculumData.hundred`，滚动后截图 |
| `10-lab2-swipe-back.png` | 系统返回手势也能回退 | 在详情页从屏幕左缘右滑后截图 |

> `06` 与 `09` 需要临时改数据源。切换只需要动 `Lab2Activity.kt` 一处：
> `Lab2NavHost(tasks = CurriculumData.normal)` → 换成 `.empty` 或 `.hundred`，
> 截图完成后记得改回 `.normal`。

---

## 一、创建并启动模拟器（AVD）

1. Android Studio 右侧工具栏点 **Device Manager**（或菜单 `Tools → Device Manager`）；
2. 点 **Create Device**（`+` 号）→ 选 **Phone** 分类，例如 `Pixel 6`；
3. 下一步选择系统镜像：推荐 **API 34 / 35 的 `x86_64`**（本机为 x86 架构，运行最快）；
   若列表为空，点镜像右侧的下载箭头，等待下载完成；
4. 后面几步保持默认，**Finish** 保存；
5. 在 Device Manager 列表中点该 AVD 右侧的 ▶ 启动按钮。

> **优先选 x86_64 镜像**：ARM 镜像在 x86 电脑上需要额外翻译层，启动慢且卡顿。
> 本工程 `minSdk = 24`，因此 API 24 及以上的镜像都可以跑。

## 二、在模拟器中运行本应用

1. 在 Android Studio 顶部工具栏的**设备下拉框**选中刚启动的模拟器；
2. 确认左侧运行配置为 `app`；
3. 点绿色 ▶ **Run**（或 `Shift + F10`），等待 Gradle 构建后自动安装并启动；
4. 应用窗口标题为「实验1 · 课程任务」，即为验收界面。

也可以只用命令行（构建已在 `build.bat` 中配好环境）：

```batch
build.bat installDebug
adb shell am start -n com.example.lab1/.MainActivity
```

## 三、截图方法

### 方式一：Device Manager 直接截图（最省事）

1. 打开 Device Manager，鼠标停在目标 AVD 上；
2. 点右侧的 **相机图标**（Screenshot）；
3. 弹窗中点 **Save** 保存到本目录。

### 方式二：模拟器窗口工具条

模拟器窗口右侧竖条工具条上也有相机图标，点击即可截图，存到指定位置。

### 方式三：adb 命令（可精确指定文件名）

```batch
:: 确认模拟器已连接（列表中应出现 emulator-5554 这类设备）
adb devices

:: 截屏写入文件；用 exec-out 而非 shell，避免 Windows 把 \n 换成 \r\n 导致 PNG 损坏
adb exec-out screencap -p > 04-lab1-screen.png
```

> **必须用 `exec-out`**：`adb shell screencap -p > x.png` 在 Windows 上会因换行符转换把
> PNG 二进制流破坏成无法打开的图片，这是常见坑。

## 四、预览面板截图

`02` / `03` 两张图来自 Preview 面板，不需要启动模拟器：

1. 打开 `app/src/main/java/com/example/lab1/TaskCard.kt`；
2. 右上角切到 **Split** 或 **Design** 模式，Preview 面板即出现；
3. 面板顶部下拉框列出全部 4 个 Preview，选 `TaskCard - 进行中`；
4. 点面板右上角的**相机图标**（Save Preview Image）保存截图；
5. 重复步骤 3–4，选 `TaskCard - 已完成` 保存第二张。

若 Preview 面板显示 `Rendering Problems`，点面板内的 **Build & Refresh** 重新渲染即可。

---

## 附：真机方式（本次实验未使用，仅备查）

若日后改用真机，需先在手机上：设置 → 关于手机 → 连点「版本号」7 次 →
开发者选项 → 打开「USB 调试」，USB 连接后确认「允许 USB 调试」弹窗，
再用 `adb devices` 验证设备已识别。

