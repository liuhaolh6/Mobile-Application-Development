package com.example.lab1.lab2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab1.CourseTask
import com.example.lab1.displayOwner
import com.example.lab1.ui.theme.Lab1Theme

/**
 * 实验2 详情页：展示单个任务的完整信息。
 *
 * **解耦要点：本页面只接收 [taskId] 原始值，不接触 NavController。**
 * 返回行为通过 [onBack] 上报，由 NavHost 决定是 popBackStack 还是别的动作。
 *
 * 之所以页面内自行 `findTask` 而不由调用方传整个对象：
 * 这样「打开哪条数据」完全由 id 决定，与首页传参口径一致，
 * 也便于用纯 JVM 单测验证 id 与数据的对应关系。
 *
 * @param taskId 目标任务 id；无匹配时展示中文错误态
 * @param onBack 返回上一页回调
 * @param modifier 外部修饰符
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    taskId: Int,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val task: CourseTask? = CurriculumData.findTask(taskId)

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("任务详情") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "返回"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 第一行显式展示 id，便于肉眼验收「导航确实带入了正确的 taskId」
            Text(
                text = "任务 id = $taskId",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )

            if (task == null) {
                // 非法 id 给出明确提示，而不是留一片空白让人以为界面坏了
                Text(
                    text = "未找到 id 为 $taskId 的任务，请返回后重新选择。",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            } else {
                Card(
                    modifier = Modifier.fillMaxSize(),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        DetailRow(label = "标题", value = task.title)
                        DetailRow(label = "负责人", value = displayOwner(task))
                        DetailRow(label = "优先级", value = priorityText(task.priority))
                        DetailRow(label = "状态", value = if (task.completed) "已完成" else "进行中")
                    }
                }
            }
        }
    }
}

/**
 * 详情页的一行「标签 + 值」。
 *
 * @param label 字段名
 * @param value 字段值
 */
@Composable
private fun DetailRow(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

/**
 * 优先级的中文展示文本。
 *
 * @param priority 任务优先级
 * @return 中文名称
 */
private fun priorityText(priority: CourseTask.Priority): String = when (priority) {
    CourseTask.Priority.HIGH -> "高"
    CourseTask.Priority.NORMAL -> "中"
    CourseTask.Priority.LOW -> "低"
}

/** Preview 1：合法 id，展示完整信息 */
@Preview(name = "TaskDetailScreen - 合法 id", showBackground = true, showSystemUi = true)
@Composable
private fun TaskDetailScreenPreview() {
    Lab1Theme {
        TaskDetailScreen(taskId = 2, onBack = {})
    }
}

/** Preview 2：非法 id，验证错误态文案 */
@Preview(name = "TaskDetailScreen - 非法 id", showBackground = true, showSystemUi = true)
@Composable
private fun TaskDetailScreenInvalidPreview() {
    Lab1Theme {
        TaskDetailScreen(taskId = 9999, onBack = {})
    }
}
