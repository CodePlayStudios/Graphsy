package com.graphsy.app

import android.graphics.Path
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GraphsyTheme {
                Box(
                    modifier = Modifier
                        .size(width = 250.dp, height = 250.dp)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    DonutChart(
                        modifier = Modifier.fillMaxSize(),
                        data = set1,
                        configuration = DonutChartConfiguration(
                            gapWidthDegrees = StartAngle.CustomAngle(3f)
                        )
                    )

                    DonutChart(
                        modifier = Modifier.fillMaxSize(),
                        data = set2,
                        configuration = DonutChartConfiguration(
                            gapWidthDegrees = StartAngle.CustomAngle(3f),
                            gapAngleDegrees = StartAngle.RightAngle,
                            direction = Path.Direction.CCW,
                            drawOverGraph = {
                                verticalDottedLine()
                            }
                        )
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
        val set2 = listOf(
            DonutData(
                masterSlice = DonutSlice.MasterSlice(
                    circumferencePercentage = 0.497f,
                    strokeWidth = 140f,
                    value = 8f,
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
                        value = 101f,
                        color = Color.Red
                    )
                )
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
            DonutData(
                masterSlice = DonutSlice.MasterSlice(
                    circumferencePercentage = 0.497f,
                    strokeWidth = 35f,
                    value = 8f,
                    color = Color.LightGray
                ),
                sections = listOf(
                    DonutSlice.SectionSlice(
                        value = 1.3f,
                        color = Color.Cyan
                    ),
                    DonutSlice.SectionSlice(
                        value = 1.2f,
                        color = Color(0xFFc4ec74)
                    ),
                    DonutSlice.SectionSlice(
                        value = 3.21f,
                        color = Color(0xFF92dc7e)
                    )
                )
            ),
            DonutData(
                masterSlice = DonutSlice.MasterSlice(
                    circumferencePercentage = 0.497f,
                    strokeWidth = 17.5f,
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
                        color = Color(0xFF92dc7e)
                    ),
                    DonutSlice.SectionSlice(
                        value = 3.21f,
                        color = Color(0xFFc4ec74)
                    )
                )
            )
        )

    }
}