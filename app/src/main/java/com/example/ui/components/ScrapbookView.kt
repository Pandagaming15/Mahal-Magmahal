package com.example.ui.components

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ItemSecretId
import com.example.model.StoryDatabase
import com.example.ui.theme.CapizAmber
import com.example.ui.theme.CapizGlow
import com.example.ui.theme.HeartRed
import com.example.ui.theme.HeritageSerifFontFamily
import com.example.ui.theme.MutedText
import com.example.ui.theme.PesoGold
import com.example.ui.theme.PixelFontFamily
import com.example.ui.theme.RainCyan
import com.example.ui.theme.SampaguitaWhite
import com.example.ui.theme.SepiaDark
import com.example.ui.theme.SepiaPaper
import com.example.ui.theme.TwilightDark
import com.example.ui.theme.TwilightDeep
import com.example.ui.theme.TwilightSurface

@Composable
fun ScrapbookView(
    unlockedSecrets: Set<ItemSecretId>,
    pusoScore: Int,
    realidadScore: Int,
    onInspectSecret: (ItemSecretId) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                .border(2.dp, CapizAmber, RoundedCornerShape(14.dp))
                .testTag("scrapbook_card"),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = TwilightDeep)
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
                        imageVector = Icons.Default.AutoStories,
                        contentDescription = null,
                        tint = CapizAmber,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Talaarawan ng Lahi at Alaala",
                            fontFamily = HeritageSerifFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = CapizAmber
                        )
                        Text(
                            text = "Natuklasang Lihim: ${unlockedSecrets.size} / ${StoryDatabase.allSecrets.size}",
                            fontFamily = PixelFontFamily,
                            fontSize = 13.sp,
                            color = RainCyan
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = onClose,
                        modifier = Modifier.testTag("close_scrapbook_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = SampaguitaWhite
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Emotional Metrics Status (Puso vs Realidad)
                Surface(
                    color = TwilightSurface,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                tint = HeartRed,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Puso: $pusoScore",
                                fontFamily = PixelFontFamily,
                                fontSize = 16.sp,
                                color = HeartRed,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Payments,
                                contentDescription = null,
                                tint = PesoGold,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Realidad: $realidadScore",
                                fontFamily = PixelFontFamily,
                                fontSize = 16.sp,
                                color = PesoGold,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Secrets List
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(StoryDatabase.allSecrets) { secret ->
                        val isUnlocked = unlockedSecrets.contains(secret)
                        Surface(
                            color = if (isUnlocked) SepiaPaper else TwilightDark,
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isUnlocked) CapizAmber else Color(0x33F3C363)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(enabled = isUnlocked) { onInspectSecret(secret) }
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (isUnlocked) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = secret.nameTagalog,
                                            fontFamily = HeritageSerifFontFamily,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = SepiaDark
                                        )
                                        Text(
                                            text = secret.snippet,
                                            fontFamily = HeritageSerifFontFamily,
                                            fontSize = 12.sp,
                                            color = Color(0xFF635242)
                                        )
                                    }
                                    Text(
                                        text = "Buksan ▶",
                                        fontFamily = PixelFontFamily,
                                        fontSize = 13.sp,
                                        color = CapizAmber,
                                        fontWeight = FontWeight.Bold
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = null,
                                        tint = MutedText,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = "Nakatagong Lihim (Mag-explore sa lumang bahay)",
                                        fontFamily = PixelFontFamily,
                                        fontSize = 13.sp,
                                        color = MutedText
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
