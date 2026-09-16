package com.example.lab1.lab2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
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
 * 实验2 首页：任务列表。
 *
 * **解耦要点：本页面不持有 NavController。**
 * 点击任务时只把 taskId 通过 [onOpenTask] 抛给上层，
 * 至于「跳到哪里」由 Lab2NavHost 决定 —— 这样本页可脱离导航单独 Preview。
 *
 * 页面内部状态为零：内容完全由 [tasks] 决定（对比实验1 的勾选状态在本页内）。
 *
 * @param tasks 待展示任务列表
 * @param onOpenTask 点击某条任务时回调，参数为任务 id
 * @param modifier 外部修饰符
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Lab2HomeScreen(
    tasks: List<CourseTask>,
    onOpenTask: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text("实验2 · 课程任务") }) }
    ) { innerPadding ->
        if (tasks.isEmpty()) {
            // 空态与列表是互斥分支，避免出现「标题栏下有内容但列表空白」的观感
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                EmptyContent()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Text(
                        text = "共 ${tasks.size} 条，已完成 ${tasks.count { it.completed }} 条",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                // key 必须用稳定的业务 id：若退回用下标，滚动复用时会张冠李戴
                items(items = tasks, key = { it.id }) { task ->
                    TaskCard(
                        title = task.title,
                        owner = displayOwner(task),
                        completed = task.completed,
                        priority = task.priority,
                        // 只上报 id，不含任何跳转细节
                        onClick = { onOpenTask(task.id) }
                    )
                }
            }
        }
    }
}

/** Preview 1：常规三条数据 */
@Preview(name = "Lab2HomeScreen - 三条任务", showBackground = true, showSystemUi = true)
@Composable
private fun Lab2HomeScreenPreview() {
    Lab1Theme {
        Lab2HomeScreen(tasks = CurriculumData.normal, onOpenTask = {})
    }
}

/** Preview 2：空列表，验证 EmptyContent 分支 */
@Preview(name = "Lab2HomeScreen - 空态", showBackground = true, showSystemUi = true)
@Composable
private fun Lab2HomeScreenEmptyPreview() {
    Lab1Theme {
        Lab2HomeScreen(tasks = CurriculumData.empty, onOpenTask = {})
    }
}
