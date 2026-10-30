package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.ItemSecretId
import com.example.ui.theme.CapizAmber
import com.example.ui.theme.HeritageSerifFontFamily
import com.example.ui.theme.NarraDark
import com.example.ui.theme.PixelFontFamily
import com.example.ui.theme.RainCyan
import com.example.ui.theme.SampaguitaWhite
import com.example.ui.theme.SepiaDark
import com.example.ui.theme.SepiaPaper
import com.example.ui.theme.TwilightDark
import com.example.ui.theme.TwilightDeep

@Composable
fun ItemInspectionOverlay(
    item: ItemSecretId,
    showEnglishSubtitles: Boolean,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xCC090710))
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, CapizAmber, RoundedCornerShape(12.dp))
                .testTag("inspection_card"),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = TwilightDeep
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Top header with Close Button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = null,
                        tint = CapizAmber,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Pagsusuri sa Lihim ng Pamilya",
                        fontFamily = HeritageSerifFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = CapizAmber
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = onClose,
                        modifier = Modifier.testTag("close_inspection_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = SampaguitaWhite
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Pixel Art Image Illustration
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, CapizAmber.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_baul_memories),
                        contentDescription = item.nameTagalog,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    // Tag label overlay
                    Surface(
                        color = TwilightDark.copy(alpha = 0.85f),
                        shape = RoundedCornerShape(bottomEnd = 8.dp),
                        modifier = Modifier.align(Alignment.TopStart)
                    ) {
                        Text(
                            text = item.nameTagalog,
                            fontFamily = PixelFontFamily,
                            fontSize = 14.sp,
                            color = CapizAmber,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Vintage Paper parchment presentation for letter or pawn ticket
                Surface(
                    color = SepiaPaper,
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, NarraDark),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = item.nameTagalog,
                            fontFamily = HeritageSerifFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = SepiaDark
                        )
                        if (showEnglishSubtitles) {
                            Text(
                                text = item.nameEnglish,
                                fontFamily = HeritageSerifFontFamily,
                                fontStyle = FontStyle.Italic,
                                fontSize = 12.sp,
                                color = Color(0xFF6B5848)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = item.detailTagalog,
                            fontFamily = HeritageSerifFontFamily,
                            fontSize = 15.sp,
                            lineHeight = 22.sp,
                            color = SepiaDark
                        )

                        if (showEnglishSubtitles) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = item.detailEnglish,
                                fontFamily = HeritageSerifFontFamily,
                                fontStyle = FontStyle.Italic,
                                fontSize = 13.sp,
                                lineHeight = 18.sp,
                                color = Color(0xFF5A4638)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onClose,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("dismiss_inspection_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CapizAmber,
                        contentColor = TwilightDark
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Isilid Muli sa Baul ● Put Back",
                        fontFamily = PixelFontFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
