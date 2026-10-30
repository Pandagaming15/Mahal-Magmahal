package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.ItemSecretId
import com.example.model.RoomId
import com.example.ui.theme.CapizAmber
import com.example.ui.theme.CapizGlow
import com.example.ui.theme.NarraDark
import com.example.ui.theme.NarraWood
import com.example.ui.theme.PixelFontFamily
import com.example.ui.theme.RainBlue
import com.example.ui.theme.RainCyan
import com.example.ui.theme.SampaguitaWhite
import com.example.ui.theme.TwilightDark
import com.example.ui.theme.TwilightDeep
import kotlin.random.Random

@Composable
fun ParallaxRoomView(
    room: RoomId,
    playerX: Float,
    isFacingRight: Boolean,
    unlockedSecrets: Set<ItemSecretId>,
    onInteractSecret: (ItemSecretId) -> Unit,
    onTriggerDialogue: (String) -> Unit,
    onMovePlayer: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "room_anim")
    val rainDropPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rain_phase"
    )

    val lampGlow by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "lamp_glow"
    )

    // Precomputed rain droplets for consistent look
    val rainOffsets = remember {
        List(40) {
            Pair(Random.nextFloat(), Random.nextFloat())
        }
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(TwilightDark)
    ) {
        val totalWidth = maxWidth
        val totalHeight = maxHeight

        // 1. Background Scene Art
        val bgDrawable = when (room) {
            RoomId.SALA -> R.drawable.img_sala_interior
            RoomId.SILID -> R.drawable.img_baul_memories
            RoomId.COMEDOR -> R.drawable.img_sala_interior
            RoomId.AZOTEA -> R.drawable.img_hero_title
        }

        Image(
            painter = painterResource(id = bgDrawable),
            contentDescription = room.titleTagalog,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // 2. Ambient Lighting Gradient Overlay (Warm amber from Capiz windows vs twilight blue rain)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0x770F0B18),
                            Color(0x331E182E),
                            Color(0x992B150A)
                        )
                    )
                )
        )

        // 3. Falling Rain Particles (Simulating outdoor monsoon on Capiz glass)
        Canvas(modifier = Modifier.fillMaxSize()) {
            val rainColor = RainCyan.copy(alpha = 0.35f * lampGlow)
            for (drop in rainOffsets) {
                val rx = drop.first * size.width
                val ry = ((drop.second + rainDropPhase) % 1f) * size.height
                drawLine(
                    color = rainColor,
                    start = Offset(rx, ry),
                    end = Offset(rx - 8f, ry + 24f),
                    strokeWidth = 2f
                )
            }
        }

        // 4. Room-Specific Interactive Hotspots
        when (room) {
            RoomId.SALA -> {
                // Altar Hotspot (Left)
                InteractiveHotspot(
                    label = "Altar ng Santo Niño & Sampaguita",
                    xRatio = 0.18f,
                    yRatio = 0.45f,
                    onClick = {
                        onTriggerDialogue("baul_discussion")
                    },
                    modifier = Modifier.testTag("hotspot_altar")
                )

                // Ancestral Portrait Hotspot (Center-Right)
                InteractiveHotspot(
                    label = "Kuwadro nina Lolo Ramon at Lola Elena",
                    xRatio = 0.62f,
                    yRatio = 0.40f,
                    onClick = {
                        onInteractSecret(ItemSecretId.PAWN_TICKET)
                    },
                    modifier = Modifier.testTag("hotspot_portrait")
                )

                // Maya is waiting in the Sala initially
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .offset(x = totalWidth * 0.76f, y = (-70).dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Surface(
                            color = TwilightDeep.copy(alpha = 0.9f),
                            shape = RoundedCornerShape(6.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, CapizAmber),
                            modifier = Modifier
                                .clickable { onTriggerDialogue("intro_3") }
                                .testTag("talk_maya_button")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Visibility,
                                    contentDescription = null,
                                    tint = CapizAmber,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Maya ● Kausapin",
                                    fontFamily = PixelFontFamily,
                                    fontSize = 14.sp,
                                    color = SampaguitaWhite
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        PixelCharacter(isFacingRight = false, isMaya = true)
                    }
                }
            }

            RoomId.SILID -> {
                // Baul Hotspot (Center)
                InteractiveHotspot(
                    label = "Antigong Baul (Lihim ng 1984)",
                    xRatio = 0.48f,
                    yRatio = 0.52f,
                    isImportant = true,
                    onClick = {
                        onTriggerDialogue("baul_inspect")
                    },
                    modifier = Modifier.testTag("hotspot_baul")
                )

                // Love letter inspect shortcut
                InteractiveHotspot(
                    label = "Liham ni Lola Elena",
                    xRatio = 0.72f,
                    yRatio = 0.46f,
                    onClick = {
                        onInteractSecret(ItemSecretId.ELENA_LETTER)
                    },
                    modifier = Modifier.testTag("hotspot_letter")
                )
            }

            RoomId.COMEDOR -> {
                // Foreclosure Notice on dining table
                InteractiveHotspot(
                    label = "Sulat ng Bangko & Lata ng Sardinas",
                    xRatio = 0.38f,
                    yRatio = 0.52f,
                    isImportant = true,
                    onClick = {
                        onInteractSecret(ItemSecretId.FORECLOSURE_NOTICE)
                    },
                    modifier = Modifier.testTag("hotspot_foreclosure")
                )

                // Flight Ticket on side shelf
                InteractiveHotspot(
                    label = "E-Ticket ni Maya patungong Abu Dhabi",
                    xRatio = 0.75f,
                    yRatio = 0.46f,
                    onClick = {
                        onInteractSecret(ItemSecretId.FLIGHT_TICKET)
                    },
                    modifier = Modifier.testTag("hotspot_ticket")
                )
            }

            RoomId.AZOTEA -> {
                // Azotea Rain Rail
                InteractiveHotspot(
                    label = "Tanawin sa Ulan (Kalsada ng Bayan)",
                    xRatio = 0.30f,
                    yRatio = 0.48f,
                    onClick = {
                        onInteractSecret(ItemSecretId.TWO_PESO_COIN)
                    },
                    modifier = Modifier.testTag("hotspot_street")
                )

                // Maya on the Balcony
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .offset(x = totalWidth * 0.65f, y = (-70).dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Surface(
                            color = TwilightDeep.copy(alpha = 0.95f),
                            shape = RoundedCornerShape(6.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, CapizAmber),
                            modifier = Modifier
                                .clickable { onTriggerDialogue("azotea_climax") }
                                .testTag("talk_maya_balcony")
                        ) {
                            Text(
                                text = "Maya ● Ang Huling Pamamaalam",
                                fontFamily = PixelFontFamily,
                                fontSize = 14.sp,
                                color = CapizGlow,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        PixelCharacter(isFacingRight = false, isMaya = true)
                    }
                }
            }
        }

        // 5. Mateo Pixel Player Character walking horizontally
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(
                    x = (totalWidth - 60.dp) * playerX,
                    y = (-70).dp
                )
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Mateo",
                    fontFamily = PixelFontFamily,
                    fontSize = 13.sp,
                    color = CapizAmber
                )
                PixelCharacter(isFacingRight = isFacingRight, isMaya = false)
            }
        }

        // 6. Polished Narra Floorboard bottom frame
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            NarraWood,
                            NarraDark
                        )
                    )
                )
        ) {
            // Wood plank lines
            Canvas(modifier = Modifier.fillMaxSize()) {
                val plankWidth = size.width / 5f
                for (i in 1..4) {
                    val px = i * plankWidth
                    drawLine(
                        color = Color(0x44000000),
                        start = Offset(px, 0f),
                        end = Offset(px, size.height),
                        strokeWidth = 2f
                    )
                }
                // Ambient floor reflection
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0x33F3C363), Color.Transparent)
                    )
                )
            }
        }
    }
}

@Composable
fun InteractiveHotspot(
    label: String,
    xRatio: Float,
    yRatio: Float,
    isImportant: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val posX = maxWidth * xRatio
        val posY = maxHeight * yRatio

        Box(
            modifier = modifier
                .offset(x = posX, y = posY)
                .clickable(onClick = onClick)
        ) {
            Surface(
                color = if (isImportant) CapizAmber.copy(alpha = 0.95f) else TwilightDeep.copy(alpha = 0.88f),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isImportant) SampaguitaWhite else CapizAmber
                ),
                shadowElevation = 6.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(if (isImportant) Color(0xFFE55353) else CapizAmber)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = label,
                        fontFamily = PixelFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (isImportant) TwilightDark else SampaguitaWhite
                    )
                }
            }
        }
    }
}
