package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Launch
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.SoundEngine
import com.example.ui.theme.CapizAmber
import com.example.ui.theme.CapizGlow
import com.example.ui.theme.HeartRed
import com.example.ui.theme.HeritageSerifFontFamily
import com.example.ui.theme.MutedText
import com.example.ui.theme.NarraDark
import com.example.ui.theme.PixelFontFamily
import com.example.ui.theme.RainCyan
import com.example.ui.theme.SampaguitaWhite
import com.example.ui.theme.TwilightDark
import com.example.ui.theme.TwilightDeep
import com.example.ui.theme.TwilightSurface

@Composable
fun JukeboxSheet(
    soundEngine: SoundEngine,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isMuted by soundEngine.isMusicMuted.collectAsState()
    val isRainEnabled by soundEngine.isRainEnabled.collectAsState()
    val currentLyricIndex by soundEngine.currentLyricIndex.collectAsState()

    val infiniteTransition = rememberInfiniteTransition(label = "tape_spin")
    val tapeRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "tape_rot"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xCC090710))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.5.dp, CapizAmber, RoundedCornerShape(14.dp))
                .testTag("jukebox_card"),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = TwilightDeep
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Header with Close
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.MusicNote,
                        contentDescription = null,
                        tint = CapizAmber,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Plaka at Harana: Mahal Magmahal",
                            fontFamily = HeritageSerifFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = CapizAmber
                        )
                        Text(
                            text = "Orihinal na Awit ng Esremborak",
                            fontFamily = PixelFontFamily,
                            fontSize = 13.sp,
                            color = RainCyan
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = onClose,
                        modifier = Modifier.testTag("close_jukebox_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = SampaguitaWhite
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Vintage Cassette Tape Representation
                Surface(
                    color = NarraDark,
                    shape = RoundedCornerShape(10.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CapizAmber),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        // Left Reel
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF262626))
                                .border(2.dp, CapizAmber, CircleShape)
                                .rotate(if (!isMuted) tapeRotation else 0f),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.size(24.dp)) {
                                drawCircle(color = CapizAmber, radius = 6f)
                                drawLine(color = CapizAmber, start = Offset(0f, 12f), end = Offset(24f, 12f), strokeWidth = 2f)
                                drawLine(color = CapizAmber, start = Offset(12f, 0f), end = Offset(12f, 24f), strokeWidth = 2f)
                            }
                        }

                        // Center Label
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "SIDE A ● 1984 / 2024",
                                fontFamily = PixelFontFamily,
                                fontSize = 12.sp,
                                color = CapizAmber
                            )
                            Text(
                                text = "Mahal Magmahal",
                                fontFamily = HeritageSerifFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = SampaguitaWhite
                            )
                            Text(
                                text = "Acoustic Fingerstyle",
                                fontFamily = PixelFontFamily,
                                fontSize = 11.sp,
                                color = MutedText
                            )
                        }

                        // Right Reel
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF262626))
                                .border(2.dp, CapizAmber, CircleShape)
                                .rotate(if (!isMuted) tapeRotation else 0f),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.size(24.dp)) {
                                drawCircle(color = CapizAmber, radius = 6f)
                                drawLine(color = CapizAmber, start = Offset(0f, 12f), end = Offset(24f, 12f), strokeWidth = 2f)
                                drawLine(color = CapizAmber, start = Offset(12f, 0f), end = Offset(12f, 24f), strokeWidth = 2f)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Audio Controls (Music Toggle, Rain Toggle)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { soundEngine.toggleMusic() },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("toggle_music_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!isMuted) CapizAmber else TwilightSurface,
                            contentColor = if (!isMuted) TwilightDark else SampaguitaWhite
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            imageVector = if (!isMuted) Icons.Default.VolumeUp else Icons.Default.VolumeMute,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (!isMuted) "Tugtog: Bukas" else "Tugtog: Pipi",
                            fontFamily = PixelFontFamily,
                            fontSize = 13.sp
                        )
                    }

                    Button(
                        onClick = { soundEngine.toggleRain() },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("toggle_rain_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isRainEnabled) RainCyan else TwilightSurface,
                            contentColor = if (isRainEnabled) TwilightDark else SampaguitaWhite
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.WaterDrop,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isRainEnabled) "Ulan sa Yero: ON" else "Ulan: OFF",
                            fontFamily = PixelFontFamily,
                            fontSize = 13.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Meaning & Lyrics Box
                Text(
                    text = "● Kasaysayan at Titik ng Awit (Lyrics & Narrative Context):",
                    fontFamily = PixelFontFamily,
                    fontSize = 13.sp,
                    color = CapizAmber
                )

                Spacer(modifier = Modifier.height(6.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    itemsIndexed(soundEngine.songLyrics) { index, lyric ->
                        val isCurrent = index == currentLyricIndex
                        Surface(
                            color = if (isCurrent) TwilightSurface else Color.Transparent,
                            shape = RoundedCornerShape(6.dp),
                            border = if (isCurrent) androidx.compose.foundation.BorderStroke(1.dp, CapizGlow) else null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    if (isCurrent) {
                                        Box(
                                            modifier = Modifier
                                                .size(6.dp)
                                                .clip(CircleShape)
                                                .background(CapizAmber)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                    }
                                    Text(
                                        text = lyric.tagalog,
                                        fontFamily = HeritageSerifFontFamily,
                                        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 14.sp,
                                        color = if (isCurrent) CapizGlow else SampaguitaWhite
                                    )
                                }
                                Text(
                                    text = lyric.english,
                                    fontFamily = HeritageSerifFontFamily,
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 12.sp,
                                    color = if (isCurrent) RainCyan else MutedText
                                )
                                Text(
                                    text = "Damdamin: ${lyric.emotion}",
                                    fontFamily = PixelFontFamily,
                                    fontSize = 11.sp,
                                    color = if (isCurrent) CapizAmber else Color(0xFF6B6579)
                                )
                            }
                        }
                    }
                }

                // Official Band Credits & Copyright Attribution
                Spacer(modifier = Modifier.height(10.dp))
                val context = LocalContext.current
                Surface(
                    color = TwilightDark,
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x66F3C363)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Launch,
                                contentDescription = null,
                                tint = CapizAmber,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Kredito sa Musika ● Music Credits:",
                                fontFamily = PixelFontFamily,
                                fontSize = 13.sp,
                                color = CapizAmber,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Awit: 'Mahal Magmahal' | Banda: Esremborak\nAll rights and copyright belong to Esremborak. This game is an artistic tribute celebrating their song and its deep message.",
                            fontFamily = HeritageSerifFontFamily,
                            fontSize = 12.sp,
                            color = MutedText
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                val intent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://youtu.be/m0GgL1SOnzU?si=-aclWwl7u0o4WJ6X")
                                )
                                context.startActivity(intent)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("open_youtube_video_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFCC0000),
                                contentColor = SampaguitaWhite
                            ),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "▶ Panoorin ang Opisyal na Video sa YouTube (Esremborak)",
                                fontFamily = PixelFontFamily,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
