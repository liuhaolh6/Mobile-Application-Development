package com.example.lab1.lab2

import com.example.lab1.CourseTask
import com.example.lab1.sampleTasks

/**
 * 实验2 专用假数据源。
 *
 * 之所以单独建一份而不改实验1 的 [sampleTasks]：
 * 实验1 的三条数据已被单测断言锁死（数量 3、恰有一条 owner 为 null），
 * 实验2 需要「0 条 / 100 条」这类极端规模样本，混在一起会污染实验1 的验收结果。
 */
object CurriculumData {

    /** 空态场景：验证 EmptyContent 分支 */
    val empty: List<CourseTask> = emptyList()

    /** 常规场景：直接复用实验1 的三条数据，保证两个实验的模型一致性 */
    val normal: List<CourseTask> = sampleTasks

    /**
     * 大数据量场景：100 条，用于验证 LazyColumn 的懒加载与滚动。
     *
     * **id 从 [HUNDRED_ID_START] 起步而非 1**：实验1 的样本占用 1..3，
     * 若此处也从 1 开始，[findTask] 在 (normal + hundred) 中取 firstOrNull
     * 会先命中 normal 的同名 id，导致详情页打开错误的任务
     * （UI 表现完全正常，只有断言能发现）。此坑已写入报告故障记录。
     */
    val hundred: List<CourseTask> = List(100) { index ->
        CourseTask(
            id = HUNDRED_ID_START + index,
            title = "课程任务 ${index + 1}",
            owner = if (index % 5 == 0) null else "同学${index % 7 + 1}",
            completed = index % 3 == 0,
            priority = when (index % 3) {
                0 -> CourseTask.Priority.HIGH
                1 -> CourseTask.Priority.NORMAL
                else -> CourseTask.Priority.LOW
            }
        )
    }

    /** hundred 样本的 id 起点，与 normal 的 1..3 严格隔离 */
    const val HUNDRED_ID_START: Int = 1000

    /**
     * 按 id 查找任务，供详情页使用。
     *
     * @param taskId 目标任务 id；传入不存在的 id 时返回 null
     * @return 命中的任务，未命中返回 null
     */
    fun findTask(taskId: Int): CourseTask? =
        (normal + hundred).firstOrNull { it.id == taskId }
}
