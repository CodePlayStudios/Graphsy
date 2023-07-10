package com.graphsy.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.graphsy.app.ui.theme.GraphsyTheme
import com.graphsy.compose.donut.DonutChart
import com.graphsy.compose.models.DonutData
import com.graphsy.compose.models.DonutSlice

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GraphsyTheme {
                Box(
                    modifier = Modifier.size(width = 240.dp, height = 240.dp)
                ) {
                    DonutChart(
                        modifier = Modifier.fillMaxSize(),
                        data = arrayOf(DATA)
                    )
                }
            }
        }
    }

    companion object {
        val DATA = DonutData(
            masterSlice = DonutSlice.MasterSlice(
                circumferencePercentage = 0.5f,
                value = 8f,
                color = Color.Gray
            ),
            sections = listOf(
                DonutSlice.SectionSlice(
                    value = 1.3f,
                    color = Color.Green
                ),
                DonutSlice.SectionSlice(
                    value = 1.2f,
                    color = Color.Magenta
                ),
                DonutSlice.SectionSlice(
                    value = 3.21f,
                    color = Color.Red
                )
            )
        )
    }
}