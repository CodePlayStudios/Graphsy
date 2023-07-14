package com.graphsy.app

import android.graphics.Path
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.graphsy.app.ui.theme.GraphsyTheme
import com.graphsy.compose.common.verticalDottedLine
import com.graphsy.compose.config.DonutChartConfiguration
import com.graphsy.compose.donut.DonutChart
import com.graphsy.compose.models.DonutData
import com.graphsy.compose.models.DonutSlice
import com.graphsy.compose.models.StartAngle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    private val data1 = MutableStateFlow(set1)
    private val data2 = MutableStateFlow(set2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GraphsyTheme {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val data1 by data1.collectAsState()
                    val configuration = DonutChartConfiguration(
                        gapWidthDegrees = StartAngle.CustomAngle(3f)
                    )
                    DonutChart(
                        modifier = Modifier
                            .size(width = 250.dp, height = 250.dp),
                        data = data1,
                        configuration = configuration
                    )
                    val data2 by data2.collectAsState()
                    val configuration2 = DonutChartConfiguration(
                        gapWidthDegrees = StartAngle.CustomAngle(3f),
                        gapAngleDegrees = StartAngle.RightAngle,
                        direction = Path.Direction.CCW,
                        drawOverGraph = {
                            verticalDottedLine()
                        },
                        isLabelsEnabled = false
                    )
                    DonutChart(
                        modifier = Modifier
                            .size(width = 250.dp, height = 250.dp),
                        data = data2
                    )
                }
            }
        }

        Handler().postDelayed({
            data1.value = data1.value.map {
                it.copy(
                    sections = items
                )
            }
            data2.value = data2.value.map {
                it.copy(
                    sections = items
                )
            }
        }, 600L)
    }

    companion object {
        val randomColor
            get() = (Math.random() * 16777215).toInt() or (0xFF shl 24)

        val items
            get() = listOf(
                DonutSlice.SectionSlice(
                    value = Random.nextFloat(),
                    color = Color(randomColor)
                ),
                DonutSlice.SectionSlice(
                    value = Random.nextFloat(),
                    color = Color(randomColor)
                ),
                DonutSlice.SectionSlice(
                    value = Random.nextFloat(),
                    color = Color(randomColor)
                )
            )

        val default = listOf(
            DonutSlice.SectionSlice(
                value = 0f,
                color = Color.LightGray
            ),
            DonutSlice.SectionSlice(
                value = 0f,
                color = Color.LightGray
            ),
            DonutSlice.SectionSlice(
                value = 0f,
                color = Color.LightGray
            )
        )

        val set1 = listOf(
            DonutData(
                masterSlice = DonutSlice.MasterSlice(
                    circumferencePercentage = 0.497f,
                    value = 1f,
                    strokeWidth = 140f,
                    color = Color.LightGray
                ),
                sections = default
            ),
            DonutData(
                masterSlice = DonutSlice.MasterSlice(
                    circumferencePercentage = 0.497f,
                    strokeWidth = 40f,
                    value = 1f,
                    color = Color.LightGray
                ),
                sections = default
            )
        )
        val set2 = listOf(
            DonutData(
                masterSlice = DonutSlice.MasterSlice(
                    circumferencePercentage = 0.497f,
                    strokeWidth = 140f,
                    value = 1f,
                    color = Color.LightGray
                ),
                sections = default
            ),
            DonutData(
                masterSlice = DonutSlice.MasterSlice(
                    circumferencePercentage = 0.497f,
                    strokeWidth = 70f,
                    value = 1f,
                    color = Color.LightGray
                ),
                sections = default
            ),
            DonutData(
                masterSlice = DonutSlice.MasterSlice(
                    circumferencePercentage = 0.497f,
                    strokeWidth = 30f,
                    value = 1f,
                    color = Color.LightGray
                ),
                sections = default
            )
        )
    }
}