package com.example.lab1.lab2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab1.ui.theme.Lab1Theme

/**
 * 空状态内容。
 *
 * 为什么必须有独立空态：`LazyColumn` 在数据为空时只渲染一片空白，
 * 用户无法区分「没有数据」「还在加载」「渲染失败」三种情况，
 * 因此这里给出明确的中文说明。
 *
 * @param modifier 外部修饰符
 */
@Composable
fun EmptyContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "暂无课程任务",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "切换数据源为 CurriculumData.normal 或 hundred 即可查看列表",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

/** Preview：空状态 */
@Preview(name = "Lab2EmptyContent - 空态", showBackground = true, showSystemUi = true)
@Composable
private fun EmptyContentPreview() {
    Lab1Theme {
        EmptyContent()
    }
}
