package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ui.theme.CapizAmber
import com.example.ui.theme.NarraDark
import com.example.ui.theme.RainCyan
import com.example.ui.theme.SampaguitaWhite
import com.example.ui.theme.TwilightDark

@Composable
fun PixelCharacter(
    isFacingRight: Boolean,
    isMaya: Boolean = false,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "char_bob")
    val bobOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 650, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "char_bob_val"
    )

    Box(
        modifier = modifier
            .size(width = 54.dp, height = 96.dp)
            .offset(y = bobOffset.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Canvas(modifier = Modifier.size(width = 54.dp, height = 96.dp)) {
            val scaleX = if (isFacingRight) 1f else -1f
            val centerX = size.width / 2f

            // Floor contact shadow
            drawOval(
                color = Color(0x7709070E),
                topLeft = Offset(centerX - 20f, size.height - 8f),
                size = Size(40f, 10f)
            )

            // Character Body
            val jacketColor = if (isMaya) Color(0xFFC06C84) else Color(0xFF2C3E50)
            val shirtColor = if (isMaya) Color(0xFFF8B195) else SampaguitaWhite
            val hairColor = NarraDark
            val skinColor = Color(0xFFE8B382)
            val pantsColor = if (isMaya) Color(0xFF355C7D) else Color(0xFF1E272C)

            val dirOffset = if (isFacingRight) 2f else -2f

            // Legs
            drawRoundRect(
                color = pantsColor,
                topLeft = Offset(centerX - 10f, size.height - 34f),
                size = Size(8f, 26f),
                cornerRadius = CornerRadius(2f, 2f)
            )
            drawRoundRect(
                color = pantsColor,
                topLeft = Offset(centerX + 2f, size.height - 34f),
                size = Size(8f, 26f),
                cornerRadius = CornerRadius(2f, 2f)
            )

            // Shoes
            drawRoundRect(
                color = Color(0xFF111111),
                topLeft = Offset(centerX - 12f + dirOffset, size.height - 10f),
                size = Size(10f, 6f),
                cornerRadius = CornerRadius(2f, 2f)
            )
            drawRoundRect(
                color = Color(0xFF111111),
                topLeft = Offset(centerX + 2f + dirOffset, size.height - 10f),
                size = Size(10f, 6f),
                cornerRadius = CornerRadius(2f, 2f)
            )

            // Torso / Jacket
            drawRoundRect(
                color = jacketColor,
                topLeft = Offset(centerX - 12f, size.height - 58f),
                size = Size(24f, 26f),
                cornerRadius = CornerRadius(4f, 4f)
            )
            // Inner Shirt
            drawRect(
                color = shirtColor,
                topLeft = Offset(centerX - 4f, size.height - 58f),
                size = Size(8f, 16f)
            )

            // Head / Skin
            drawRoundRect(
                color = skinColor,
                topLeft = Offset(centerX - 10f, size.height - 80f),
                size = Size(20f, 22f),
                cornerRadius = CornerRadius(4f, 4f)
            )

            // Hair
            drawRoundRect(
                color = hairColor,
                topLeft = Offset(centerX - 12f, size.height - 84f),
                size = Size(24f, 12f),
                cornerRadius = CornerRadius(4f, 4f)
            )
            if (isMaya) {
                // Long hair strands
                drawRoundRect(
                    color = hairColor,
                    topLeft = Offset(centerX - 14f, size.height - 76f),
                    size = Size(6f, 22f),
                    cornerRadius = CornerRadius(2f, 2f)
                )
            }

            // Eyes (Pixel art dot)
            val eyeX = if (isFacingRight) centerX + 3f else centerX - 6f
            drawRect(
                color = TwilightDark,
                topLeft = Offset(eyeX, size.height - 72f),
                size = Size(3f, 4f)
            )

            // Umbrella for rain atmosphere
            val umbrellaColor = if (isMaya) RainCyan else CapizAmber
            val umbrellaX = if (isFacingRight) centerX + 14f else centerX - 14f
            drawLine(
                color = Color(0xFF424242),
                start = Offset(umbrellaX, size.height - 40f),
                end = Offset(umbrellaX, size.height - 76f),
                strokeWidth = 3f
            )
            drawArc(
                color = umbrellaColor,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = true,
                topLeft = Offset(umbrellaX - 20f, size.height - 94f),
                size = Size(40f, 24f)
            )
        }
    }
}
