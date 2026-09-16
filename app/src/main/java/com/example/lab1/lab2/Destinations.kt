package com.example.lab1.lab2

/**
 * 首页目的地：实验2 导航图的起点。
 *
 * 用 object 而非普通 class：它只是路由常量的载体，无需实例。
 */
object HomeDestination {
    /** 首页路由，无参数 */
    const val ROUTE: String = "lab2/home"
}

/**
 * 详情页目的地：接收一个 taskId 参数。
 *
 * 设计要点：**模板、参数名、拼装函数三者在同一处定义**。
 * 若把参数名散落在 NavHost 与调用点，一旦改名就会出现
 * 「模板用 {taskId}、取值用 "id"」这类只有运行期才暴露的错误。
 */
object TaskDetailDestination {

    /** 参数名常量，NavHost 声明与取值共用，杜绝拼写漂移 */
    const val ARG_TASK_ID: String = "taskId"

    /** 路由模板，供 NavHost 注册使用 */
    const val ROUTE: String = "lab2/detail/{$ARG_TASK_ID}"

    /**
     * 由 taskId 拼出可实际跳转的路由。
     *
     * 之所以必须用本函数而非手写字符串：手写时极易把参数名写错，
     * 而错误只会表现为「详情页拿不到 id（取到 null）」，
     * 编译期毫无提示 —— 此坑已写入报告故障记录。
     *
     * @param taskId 目标任务 id
     * @return 形如 `lab2/detail/2` 的具体路由
     */
    fun createRoute(taskId: Int): String = "lab2/detail/$taskId"
}

/** 参数缺失或非法时使用的哨兵值，便于详情页给出明确提示而非空白页 */
const val INVALID_TASK_ID: Int = 0
