package com.example.lab1.lab2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab1.CourseTask
import com.example.lab1.displayOwner
import com.example.lab1.sampleTasks
import com.example.lab1.ui.theme.CompletedGreen
import com.example.lab1.ui.theme.Lab1Theme
import com.example.lab1.ui.theme.OngoingBlue
import com.example.lab1.ui.theme.PriorityHigh
import com.example.lab1.ui.theme.PriorityLow
import com.example.lab1.ui.theme.PriorityNormal

/**
 * 实验2 的可点击任务卡片。
 *
 * 与实验1 `TaskCard` 的三点差异：
 * 1. 右侧增加跳转箭头，给用户「可点进下一层」的视觉暗示；
 * 2. 去掉 onClick 的默认空实现，避免误用成不可跳转的卡片；
 * 3. 独立成文件，避免实验1 那份已验收的 TaskCard.kt 超出 200 行约束。
 *
 * **解耦要点：本组件不接收 NavController，也不知道点击后要去哪。**
 * 由 Lab2NavHost 决定跳转，因此可以脱离导航单独 Preview、单独测试。
 *
 * @param title 任务标题
 * @param owner 负责人展示文本，建议由 [displayOwner] 生成
 * @param completed 是否已完成
 * @param priority 优先级，决定左侧色条颜色
 * @param onClick 点击回调，由父级注入跳转行为
 * @param modifier 外部修饰符
 */
@Composable
fun TaskCard(
    title: String,
    owner: String,
    completed: Boolean,
    priority: CourseTask.Priority,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
        ) {
            // 左侧优先级色条：沿用实验1 的 4dp 方案，保持两个实验视觉一致
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(4.dp)
                    .background(priorityColor(priority))
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textDecoration = if (completed) TextDecoration.LineThrough else TextDecoration.None,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "负责人：$owner",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            StatusBadge(
                text = if (completed) "已完成" else "进行中",
                containerColor = if (completed) CompletedGreen else OngoingBlue
            )
            // 跳转箭头：这一处是实验2 新增的「可进入详情」视觉提示
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "查看详情",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(20.dp)
                    .padding(end = 4.dp)
            )
        }
    }
}

/**
 * 右侧状态标签。
 *
 * @param text 标签文本
 * @param containerColor 标签背景色
 */
@Composable
private fun StatusBadge(text: String, containerColor: Color) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .padding(vertical = 20.dp, horizontal = 8.dp)
            .background(containerColor, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = Color.White
        )
    }
}

/**
 * 优先级到颜色的映射。
 *
 * @param priority 任务优先级
 * @return 该优先级对应的强调色
 */
private fun priorityColor(priority: CourseTask.Priority): Color = when (priority) {
    CourseTask.Priority.HIGH -> PriorityHigh
    CourseTask.Priority.NORMAL -> PriorityNormal
    CourseTask.Priority.LOW -> PriorityLow
}

/** Preview：可点击卡片（进行中 + 高优先级） */
@Preview(name = "Lab2TaskCard - 可点击", showBackground = true)
@Composable
private fun Lab2TaskCardPreview() {
    Lab1Theme {
        TaskCard(
            title = sampleTasks[1].title,
            owner = displayOwner(sampleTasks[1]),
            completed = false,
            priority = CourseTask.Priority.HIGH,
            onClick = {},
            modifier = Modifier.padding(12.dp)
        )
    }
}
