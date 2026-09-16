package com.example.lab1.lab2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.lab1.ui.theme.Lab1Theme

/**
 * 实验2 独立入口。
 *
 * 延续实验1 的做法：基础实验与业务无关，独立 Activity 既能完整演示导航，
 * 又不会破坏实验1 已验收的 [com.example.lab1.MainActivity]。
 */
class Lab2Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            Lab1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 默认用三条常规数据；改 CurriculumData.empty / hundred 可切换演示场景
                    Lab2NavHost(tasks = CurriculumData.normal)
                }
            }
        }
    }
}
