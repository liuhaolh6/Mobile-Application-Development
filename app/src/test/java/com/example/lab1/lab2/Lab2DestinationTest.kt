package com.example.lab1.lab2

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * 实验2 纯逻辑单测：路由拼装与 taskId 传递。
 *
 * 这些断言不依赖 Android 框架，能在纯 JVM 下运行 ——
 * 这正是「页面组件不持有 NavController」带来的直接收益：
 * 路由拼装被从 Composable 里逼了出来，变得可测。
 */
class Lab2DestinationTest {

    @Test
    fun `createRoute 把 taskId 拼进路由末尾`() {
        assertEquals("lab2/detail/2", TaskDetailDestination.createRoute(2))
    }

    @Test
    fun `不同 taskId 生成不同路由`() {
        // 若两个 id 生成同一条路由，点击不同卡片会进入同一个详情页
        assertNotEquals(
            TaskDetailDestination.createRoute(1),
            TaskDetailDestination.createRoute(2)
        )
    }

    @Test
    fun `路由模板中的占位符与 ARG_TASK_ID 完全一致`() {
        // 这是最隐蔽的一类 bug：模板写 {taskId}、取值写 "id"，
        // 编译期无提示，只表现为详情页永远拿到 null
        assertTrue(
            "模板中应包含 {${TaskDetailDestination.ARG_TASK_ID}}",
            TaskDetailDestination.ROUTE.contains("{${TaskDetailDestination.ARG_TASK_ID}}")
        )
    }

    @Test
    fun `createRoute 产物与路由模板结构吻合`() {
        val route = TaskDetailDestination.createRoute(42)
        val prefix = TaskDetailDestination.ROUTE.substringBefore("{")
        assertTrue(
            "生成的路由 $route 应以模板前缀 $prefix 开头",
            route.startsWith(prefix)
        )
    }

    @Test
    fun `findTask 能按 id 命中 normal 中的任务`() {
        val task = CurriculumData.findTask(2)
        assertNotNull(task)
        assertEquals(2, task?.id)
    }

    @Test
    fun `findTask 对不存在的 id 返回 null`() {
        // 详情页依赖此行为展示中文错误态，而非崩溃或空白
        assertNull(CurriculumData.findTask(9999))
    }
}

/**
 * 数据源单测：验证三种规模样本的约束。
 */
class CurriculumDataTest {

    @Test
    fun `empty 样本不含任何任务`() {
        assertTrue(CurriculumData.empty.isEmpty())
    }

    @Test
    fun `hundred 恰有 100 条且 id 唯一`() {
        assertEquals(100, CurriculumData.hundred.size)
        // LazyColumn 的 key 依赖 id 唯一，重复会导致滚动时崩或被复用错乱
        assertEquals(100, CurriculumData.hundred.map { it.id }.distinct().size)
    }

    @Test
    fun `hundred 覆盖三种优先级`() {
        // 保证滚动过程中能同时看到三种色条，截图验收才有说服力
        val priorities = CurriculumData.hundred.map { it.priority }.distinct()
        assertEquals(3, priorities.size)
    }

    @Test
    fun `hundred 与 normal 的 id 区间不重叠`() {
        val normalIds = CurriculumData.normal.map { it.id }.toSet()
        val hundredIds = CurriculumData.hundred.map { it.id }.toSet()
        assertTrue(
            "两组样本 id 重叠会导致 findTask 命中错误任务",
            normalIds.intersect(hundredIds).isEmpty()
        )
    }

    @Test
    fun `findTask 能区分 normal 与 hundred 中同名 id 区间的任务`() {
        // 回归测试：曾因 hundred 从 1 开始编号，findTask(2) 先命中 normal，
        // 导致详情页打开错误任务且 UI 表现完全正常
        val fromNormal = CurriculumData.findTask(2)
        val fromHundred = CurriculumData.findTask(CurriculumData.HUNDRED_ID_START + 2)

        assertNotNull(fromNormal)
        assertNotNull(fromHundred)
        assertNotEquals(fromNormal?.title, fromHundred?.title)
    }
}
