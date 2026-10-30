package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DialogueChoice
import com.example.model.DialogueNode
import com.example.ui.theme.CapizAmber
import com.example.ui.theme.CapizGlow
import com.example.ui.theme.HeartRed
import com.example.ui.theme.HeritageSerifFontFamily
import com.example.ui.theme.NarraDark
import com.example.ui.theme.NarraWood
import com.example.ui.theme.PesoGold
import com.example.ui.theme.PixelFontFamily
import com.example.ui.theme.RainBlue
import com.example.ui.theme.SampaguitaWhite
import com.example.ui.theme.TwilightDark
import com.example.ui.theme.TwilightDeep
import com.example.ui.theme.TwilightSurface

@Composable
fun DialogueBox(
    dialogue: DialogueNode,
    showEnglishSubtitles: Boolean,
    onAdvance: () -> Unit,
    onChooseOption: (DialogueChoice) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (dialogue.choices.isEmpty()) {
                        onAdvance()
                    }
                }
                .testTag("dialogue_card"),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = TwilightDeep.copy(alpha = 0.96f)
            ),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, CapizAmber.copy(alpha = 0.7f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Speaker Header & Avatar
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Character Portrait Badge
                    val avatarBg = when (dialogue.speaker) {
                        "Maya" -> Color(0xFFC06C84)
                        "Mateo" -> Color(0xFF2C3E50)
                        else -> NarraWood
                    }
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(avatarBg)
                            .border(1.dp, CapizAmber, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = dialogue.speaker.first().toString(),
                            fontFamily = HeritageSerifFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = SampaguitaWhite
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = dialogue.speaker,
                            fontFamily = HeritageSerifFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = CapizAmber
                        )
                        Text(
                            text = "Damdamin: ${dialogue.emotion}",
                            fontFamily = PixelFontFamily,
                            fontSize = 13.sp,
                            color = RainBlue
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    if (dialogue.choices.isEmpty()) {
                        Surface(
                            color = TwilightSurface,
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.clickable { onAdvance() }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Ipagpatuloy",
                                    fontFamily = PixelFontFamily,
                                    fontSize = 13.sp,
                                    color = CapizGlow
                                )
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = null,
                                    tint = CapizGlow,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Tagalog Primary Dialogue
                Text(
                    text = dialogue.tagalogText,
                    fontFamily = HeritageSerifFontFamily,
                    fontSize = 16.sp,
                    lineHeight = 23.sp,
                    color = SampaguitaWhite
                )

                // Optional English Subtitles
                if (showEnglishSubtitles) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = dialogue.englishText,
                        fontFamily = HeritageSerifFontFamily,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        fontStyle = FontStyle.Italic,
                        color = Color(0xFFBAC3D2)
                    )
                }

                // Branching Choices (if present)
                if (dialogue.choices.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "● Pumili ng Desisyon / Emotional Choice:",
                        fontFamily = PixelFontFamily,
                        fontSize = 14.sp,
                        color = CapizAmber,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        dialogue.choices.forEachIndexed { index, choice ->
                            ChoiceCard(
                                choice = choice,
                                showEnglishSubtitles = showEnglishSubtitles,
                                onClick = { onChooseOption(choice) },
                                modifier = Modifier.testTag("dialogue_choice_$index")
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChoiceCard(
    choice: DialogueChoice,
    showEnglishSubtitles: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        color = TwilightDark.copy(alpha = 0.95f),
        border = androidx.compose.foundation.BorderStroke(1.dp, CapizAmber.copy(alpha = 0.6f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = choice.textTagalog,
                    fontFamily = HeritageSerifFontFamily,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = SampaguitaWhite,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                if (choice.pusoDelta > 0) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(HeartRed.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = HeartRed,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "+${choice.pusoDelta} Puso",
                            fontFamily = PixelFontFamily,
                            fontSize = 12.sp,
                            color = HeartRed
                        )
                    }
                }

                if (choice.realidadDelta > 0) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(PesoGold.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Payments,
                            contentDescription = null,
                            tint = PesoGold,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "+${choice.realidadDelta} Realidad",
                            fontFamily = PixelFontFamily,
                            fontSize = 12.sp,
                            color = PesoGold
                        )
                    }
                }
            }

            if (showEnglishSubtitles) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = choice.textEnglish,
                    fontFamily = HeritageSerifFontFamily,
                    fontStyle = FontStyle.Italic,
                    fontSize = 12.sp,
                    color = Color(0xFFA5B0C2)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Epekto: ${choice.reactionHint}",
                fontFamily = PixelFontFamily,
                fontSize = 12.sp,
                color = CapizAmber.copy(alpha = 0.8f)
            )
        }
    }
}
