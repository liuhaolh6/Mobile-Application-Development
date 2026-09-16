package com.example.lab1.lab2

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab1.CourseTask

/**
 * 实验2 导航宿主：**全实验唯一持有 NavController 的文件**。
 *
 * 之所以把 NavController 收敛在这一个文件里：
 * 各页面只通过 `onXxx` 回调上报意图，导航细节不外泄，
 * 页面因此可独立 Preview、独立单测；日后换成别的导航方案也只改这里。
 *
 * @param tasks 首页数据源，默认 [CurriculumData.normal]；切成 empty/hundred 即可看空态与滚
 * @param modifier 外部修饰符
 */
@Composable
fun Lab2NavHost(
    tasks: List<CourseTask> = CurriculumData.normal,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeDestination.ROUTE,
        modifier = modifier
    ) {
        composable(route = HomeDestination.ROUTE) {
            Lab2HomeScreen(
                tasks = tasks,
                // 只有这里知道「点击后要去详情页」，页面本身不知道
                onOpenTask = { taskId ->
                    navController.navigate(TaskDetailDestination.createRoute(taskId))
                }
            )
        }

        composable(
            route = TaskDetailDestination.ROUTE,
            arguments = listOf(
                navArgument(TaskDetailDestination.ARG_TASK_ID) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            // 取值用的 key 与 ROUTE 模板里的占位符同源，杜绝拼写不一致
            val taskId = backStackEntry.arguments?.getInt(TaskDetailDestination.ARG_TASK_ID)
                ?: INVALID_TASK_ID
            TaskDetailScreen(
                taskId = taskId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
