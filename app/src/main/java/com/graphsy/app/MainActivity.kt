package com.graphsy.app

import android.graphics.Path
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp
import com.graphsy.app.ui.theme.GraphsyTheme
import com.graphsy.compose.common.verticalDottedLine
import com.graphsy.compose.models.StartAngle
import com.graphsy.compose.config.DonutChartConfiguration
import com.graphsy.compose.donut.DonutChart
import com.graphsy.compose.models.DonutData
import com.graphsy.compose.models.DonutSlice
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {


    private val data1 = MutableStateFlow(set1)
    private val config2 = MutableStateFlow(DonutChartConfiguration(
        gapWidthDegrees = StartAngle.CustomAngle(3f),
        gapAngleDegrees = StartAngle.RightAngle,
        direction = Path.Direction.CCW,
        drawOverGraph = {
            verticalDottedLine()
        }
    ))

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
                    DonutChart(
                        modifier = Modifier
                            .size(width = 250.dp, height = 250.dp),
                        data = data1,
                        configuration = DonutChartConfiguration(
                            gapWidthDegrees = StartAngle.CustomAngle(3f)
                        )
                    )
                    val data2 by data2.collectAsState()
                    val config by config2.collectAsState()
                    DonutChart(
                        modifier = Modifier
                            .size(width = 250.dp, height = 250.dp),
                        data = data2,
                        configuration = config
                    )

                }
            }
        }
    }

    companion object {

        val set1 = listOf(
            DonutData(
                masterSlice = DonutSlice.MasterSlice(
                    circumferencePercentage = 0.497f,
                    value = 8f,
                    strokeWidth = 140f,
                    color = Color.LightGray
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
            ),
            DonutData(
                masterSlice = DonutSlice.MasterSlice(
                    circumferencePercentage = 0.497f,
                    strokeWidth = 40f,
                    value = 8f,
                    color = Color.LightGray
                ),
                sections = listOf(
                    DonutSlice.SectionSlice(
                        value = 1.3f,
                        color = Color.Yellow
                    ),
                    DonutSlice.SectionSlice(
                        value = 1.2f,
                        color = Color.Blue
                    ),
                    DonutSlice.SectionSlice(
                        value = 3.21f,
                        color = Color.Black
                    )
                )
            )
        )
        val sectionTest = listOf(
            DonutSlice.SectionSlice(
                value = 1f,
                color = Color.Green
            ),
            DonutSlice.SectionSlice(
                value = 1f,
                color = Color.Magenta
            ),
            DonutSlice.SectionSlice(
                value = 1f,
                color = Color.Red
            )
        )
        val set2 = listOf(
            DonutData(
                masterSlice = DonutSlice.MasterSlice(
                    circumferencePercentage = 0.497f,
                    strokeWidth = 140f,
                    value = 8f,
                    color = Color.LightGray
                ),
                sections = sectionTest
            ),
            DonutData(
                masterSlice = DonutSlice.MasterSlice(
                    circumferencePercentage = 0.497f,
                    strokeWidth = 70f,
                    value = 8f,
                    color = Color.LightGray
                ),
                sections = listOf(
                    DonutSlice.SectionSlice(
                        value = 1.3f,
                        color = Color.Yellow
                    ),
                    DonutSlice.SectionSlice(
                        value = 1.2f,
                        color = Color.Blue
                    ),
                    DonutSlice.SectionSlice(
                        value = 3.21f,
                        color = Color.Black
                    )
                )
            ),
//            DonutData(
//                masterSlice = DonutSlice.MasterSlice(
//                    circumferencePercentage = 0.497f,
//                    strokeWidth = 35f,
//                    value = 8f,
//                    color = Color.LightGray
//                ),
//                sections = listOf(
//                    DonutSlice.SectionSlice(
//                        value = 1.3f,
//                        color = Color.Cyan
//                    ),
//                    DonutSlice.SectionSlice(
//                        value = 1.2f,
//                        color = Color(0xFFc4ec74)
//                    ),
//                    DonutSlice.SectionSlice(
//                        value = 3.21f,
//                        color = Color(0xFF92dc7e)
//                    )
//                )
//            )
        )

    }
}