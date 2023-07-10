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
import com.graphsy.compose.common.StartAngle
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
                    modifier = Modifier.size(width = 240.dp, height = 240.dp)
                ) {
                    DonutChart(
                        modifier = Modifier.fillMaxSize(),
                        data = set1,
                        configuration = DonutChartConfiguration().copy(
                            gapWidthDegrees = StartAngle.CustomAngle(1f),
                            childDonutWidthFactor = 50f
                        )
                    )
                    DonutChart(
                        modifier = Modifier.fillMaxSize(),
                        data = set2,
                        configuration = DonutChartConfiguration().copy(
                            gapWidthDegrees = StartAngle.CustomAngle(1f),
                            gapAngleDegrees = StartAngle.RightAngle,
                            childDonutWidthFactor = 50f
                        )
                    )
                }
            }
        }
    }

    companion object {

        val set1 = arrayOf(
            DonutData(
                masterSlice = DonutSlice.MasterSlice(
                    circumferencePercentage = 0.49f,
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
                        value = 3.21f,
                        color = Color.Red
                    )
                )
            ),
            DonutData(
                masterSlice = DonutSlice.MasterSlice(
                    circumferencePercentage = 0.49f,
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
        val set2 = arrayOf(
            DonutData(
                masterSlice = DonutSlice.MasterSlice(
                    circumferencePercentage = 0.49f,
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
                        value = 3.21f,
                        color = Color.Red
                    )
                )
            ),
            DonutData(
                masterSlice = DonutSlice.MasterSlice(
                    circumferencePercentage = 0.49f,
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

    }
}