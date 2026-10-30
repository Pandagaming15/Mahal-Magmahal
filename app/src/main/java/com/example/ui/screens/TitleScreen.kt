package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.ui.platform.LocalContext
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
import com.example.ui.components.ScanlineOverlay
import com.example.ui.theme.CapizAmber
import com.example.ui.theme.CapizGlow
import com.example.ui.theme.HeritageSerifFontFamily
import com.example.ui.theme.MutedText
import com.example.ui.theme.PixelFontFamily
import com.example.ui.theme.RainCyan
import com.example.ui.theme.SampaguitaWhite
import com.example.ui.theme.TwilightDark
import com.example.ui.theme.TwilightDeep
import com.example.ui.theme.TwilightSurface

@Composable
fun TitleScreen(
    showEnglishSubtitles: Boolean,
    showCrtScanlines: Boolean,
    onStartGame: () -> Unit,
    onOpenJukebox: () -> Unit,
    onToggleSubtitles: () -> Unit,
    onToggleCrt: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showAboutDialog by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "title_glow")
    val titleAlpha by infiniteTransition.animateFloat(
        initialValue = 0.82f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha_glow"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(TwilightDark)
    ) {
        // Hero Pixel Art Background
        Image(
            painter = painterResource(id = R.drawable.img_hero_title),
            contentDescription = "Mahal ang Magmahal Title Scene",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Melancholic Twilight & Vignette Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xBB120E1E),
                            Color(0x6619142B),
                            Color(0xEE120E1E)
                        )
                    )
                )
        )

        if (showCrtScanlines) {
            ScanlineOverlay()
        }

        // Title and Interactive Menu
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Title Header Block
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Surface(
                    color = TwilightDeep.copy(alpha = 0.85f),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, CapizAmber.copy(alpha = titleAlpha)),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Mahal ang Magmahal",
                            fontFamily = HeritageSerifFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            color = CapizGlow,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "A 2.5D Pixel Narrative of Heritage and Heart",
                            fontFamily = HeritageSerifFontFamily,
                            fontStyle = FontStyle.Italic,
                            fontSize = 13.sp,
                            color = RainCyan,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Hango sa awit ng Esremborak ● Inspirasyon ng 'Until Then'",
                            fontFamily = PixelFontFamily,
                            fontSize = 13.sp,
                            color = MutedText,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Center Emotional Quote
            Surface(
                color = TwilightDark.copy(alpha = 0.75f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "\"Kung mamahalin kita ngayon, sabay tayong magugutom.\"",
                        fontFamily = HeritageSerifFontFamily,
                        fontStyle = FontStyle.Italic,
                        fontSize = 15.sp,
                        color = SampaguitaWhite,
                        textAlign = TextAlign.Center
                    )
                    if (showEnglishSubtitles) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "\"If I love you right now, we will just starve together.\"",
                            fontFamily = HeritageSerifFontFamily,
                            fontSize = 12.sp,
                            color = Color(0xFFAAB8CC),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Bottom Menu Options
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onStartGame,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(52.dp)
                        .testTag("start_game_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CapizAmber,
                        contentColor = TwilightDark
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Simulan ang Paglalakbay ● Start",
                        fontFamily = PixelFontFamily,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(0.85f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onOpenJukebox,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("jukebox_button"),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CapizAmber),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = CapizGlow
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Hearing,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Awit ● Harana",
                            fontFamily = PixelFontFamily,
                            fontSize = 13.sp
                        )
                    }

                    OutlinedButton(
                        onClick = { showAboutDialog = true },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("about_button"),
                        border = androidx.compose.foundation.BorderStroke(1.dp, RainCyan),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = RainCyan
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Tungkol sa Kwento",
                            fontFamily = PixelFontFamily,
                            fontSize = 13.sp
                        )
                    }
                }

                // Subtitle & CRT Toggles
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = onToggleSubtitles,
                        modifier = Modifier.testTag("toggle_subtitles_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Subtitles,
                            contentDescription = null,
                            tint = if (showEnglishSubtitles) CapizAmber else MutedText,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (showEnglishSubtitles) "English Subtitles: ON" else "Subtitles: OFF",
                            fontFamily = PixelFontFamily,
                            fontSize = 13.sp,
                            color = if (showEnglishSubtitles) CapizAmber else MutedText
                        )
                    }

                    TextButton(
                        onClick = onToggleCrt,
                        modifier = Modifier.testTag("toggle_crt_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Tv,
                            contentDescription = null,
                            tint = if (showCrtScanlines) RainCyan else MutedText,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (showCrtScanlines) "CRT Effect: ON" else "CRT: OFF",
                            fontFamily = PixelFontFamily,
                            fontSize = 13.sp,
                            color = if (showCrtScanlines) RainCyan else MutedText
                        )
                    }
                }
            }
        }
    }

    if (showAboutDialog) {
        AlertDialog(
            onDismissRequest = { showAboutDialog = false },
            title = {
                Text(
                    text = "Tungkol sa 'Mahal ang Magmahal'",
                    fontFamily = HeritageSerifFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = CapizAmber
                )
            },
            text = {
                val context = LocalContext.current
                Column {
                    Text(
                        text = "Ang 'Mahal Magmahal' ay isang awit ng bandang Esremborak tungkol sa matinding reyalidad ng pag-ibig sa gitna ng kahirapan at implasyon.\n\nSa wikang Filipino, ang salitang 'Mahal' ay may dalawang kahulugan: (1) minamahal / pag-ibig, at (2) mataas ang presyo / gastusin.\n\nSa larong ito, tutuklasin mo ang Bahay na Bato ng pamilya Ramos, aalamin ang lihim ng mga ninuno, at gagawa ng masidhing desisyon kasama si Maya bago ang kanyang paglipad patungong Abu Dhabi.",
                        fontFamily = HeritageSerifFontFamily,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        color = SampaguitaWhite
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Surface(
                        color = TwilightDark,
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CapizAmber),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "Band Credits: Esremborak",
                                fontFamily = PixelFontFamily,
                                fontSize = 13.sp,
                                color = CapizAmber,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "All copyrights belong to the band Esremborak.\nOfficial Video: https://youtu.be/m0GgL1SOnzU",
                                fontFamily = HeritageSerifFontFamily,
                                fontSize = 11.sp,
                                color = MutedText
                            )
                            Spacer(modifier = Modifier.height(6.dp))
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
                                    .testTag("title_youtube_link_button"),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFCC0000),
                                    contentColor = SampaguitaWhite
                                ),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "▶ Panoorin sa YouTube",
                                    fontFamily = PixelFontFamily,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showAboutDialog = false },
                    modifier = Modifier.testTag("close_about_dialog")
                ) {
                    Text(
                        text = "Isara ● Close",
                        fontFamily = PixelFontFamily,
                        fontSize = 14.sp,
                        color = CapizAmber
                    )
                }
            },
            containerColor = TwilightDeep,
            shape = RoundedCornerShape(12.dp)
        )
    }
}
