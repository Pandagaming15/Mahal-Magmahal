package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.EndingStory
import com.example.ui.components.ScanlineOverlay
import com.example.ui.theme.CapizAmber
import com.example.ui.theme.CapizGlow
import com.example.ui.theme.HeartRed
import com.example.ui.theme.HeritageSerifFontFamily
import com.example.ui.theme.PesoGold
import com.example.ui.theme.PixelFontFamily
import com.example.ui.theme.RainCyan
import com.example.ui.theme.SampaguitaWhite
import com.example.ui.theme.TwilightDark
import com.example.ui.theme.TwilightDeep
import com.example.ui.theme.TwilightSurface

@Composable
fun EndingScreen(
    ending: EndingStory,
    pusoScore: Int,
    realidadScore: Int,
    secretsCount: Int,
    showEnglishSubtitles: Boolean,
    showCrtScanlines: Boolean,
    onRestart: () -> Unit,
    onOpenJukebox: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(TwilightDark)
    ) {
        // Ending Background
        val bgRes = when (ending.endingType) {
            "COURAGE" -> R.drawable.img_sala_interior
            "SACRIFICE" -> R.drawable.img_baul_memories
            else -> R.drawable.img_hero_title
        }

        Image(
            painter = painterResource(id = bgRes),
            contentDescription = ending.titleTagalog,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xDD120E1E))
        )

        if (showCrtScanlines) {
            ScanlineOverlay()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Epilogue Badge
            Surface(
                color = TwilightSurface,
                shape = RoundedCornerShape(20.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CapizAmber)
            ) {
                Text(
                    text = "● PAGWAWAKAS NG KUWENTO ●",
                    fontFamily = PixelFontFamily,
                    fontSize = 13.sp,
                    color = CapizAmber,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Title
            Text(
                text = ending.titleTagalog,
                fontFamily = HeritageSerifFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = CapizGlow,
                textAlign = TextAlign.Center
            )

            if (showEnglishSubtitles) {
                Text(
                    text = ending.titleEnglish,
                    fontFamily = HeritageSerifFontFamily,
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp,
                    color = RainCyan,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = ending.subtitle,
                fontFamily = PixelFontFamily,
                fontSize = 14.sp,
                color = Color(0xFFA59EB3),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Narrative Story Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, CapizAmber, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = TwilightDeep.copy(alpha = 0.95f))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = ending.storyTagalog,
                        fontFamily = HeritageSerifFontFamily,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        color = SampaguitaWhite
                    )

                    if (showEnglishSubtitles) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = ending.storyEnglish,
                            fontFamily = HeritageSerifFontFamily,
                            fontStyle = FontStyle.Italic,
                            fontSize = 13.sp,
                            lineHeight = 19.sp,
                            color = Color(0xFFBAC5D6)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Final Journey Summary
            Surface(
                color = TwilightSurface,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = HeartRed,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Puso: $pusoScore",
                            fontFamily = PixelFontFamily,
                            fontSize = 14.sp,
                            color = HeartRed
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Payments,
                            contentDescription = null,
                            tint = PesoGold,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Realidad: $realidadScore",
                            fontFamily = PixelFontFamily,
                            fontSize = 14.sp,
                            color = PesoGold
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$secretsCount / 6",
                            fontFamily = HeritageSerifFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = CapizAmber
                        )
                        Text(
                            text = "Mga Lihim",
                            fontFamily = PixelFontFamily,
                            fontSize = 13.sp,
                            color = CapizAmber
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action Buttons
            Button(
                onClick = onRestart,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(50.dp)
                    .testTag("play_again_button"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CapizAmber,
                    contentColor = TwilightDark
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(imageVector = Icons.Default.Replay, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Muling Maglakbay ● Play Again",
                    fontFamily = PixelFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = onOpenJukebox,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .testTag("ending_jukebox_button"),
                border = androidx.compose.foundation.BorderStroke(1.dp, RainCyan),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = RainCyan),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(imageVector = Icons.Default.MusicNote, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Makinig sa 'Mahal Magmahal' (Esremborak)",
                    fontFamily = PixelFontFamily,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
