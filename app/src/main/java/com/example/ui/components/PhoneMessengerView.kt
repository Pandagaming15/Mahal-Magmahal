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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CapizAmber
import com.example.ui.theme.HeartRed
import com.example.ui.theme.HeritageSerifFontFamily
import com.example.ui.theme.MutedText
import com.example.ui.theme.PixelFontFamily
import com.example.ui.theme.RainCyan
import com.example.ui.theme.SampaguitaWhite
import com.example.ui.theme.TwilightDark
import com.example.ui.theme.TwilightDeep
import com.example.ui.theme.TwilightSurface

data class ChatMessage(
    val sender: String,
    val textTagalog: String,
    val time: String,
    val isMe: Boolean
)

@Composable
fun PhoneMessengerView(
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf("Maya") }

    val mayaMessages = remember {
        listOf(
            ChatMessage("Maya", "Mateo, papunta na ako diyan sa Bahay na Bato. Dala ko 'yung mainit na pandesal.", "4:15 PM", false),
            ChatMessage("Mateo", "Ingat sa daan, lumalakas ang buhos ng ulan.", "4:18 PM", true),
            ChatMessage("Maya", "Naisip ko lang... noong high school tayo, nangako tayo na sabay nating aayusin ang bahay na 'to.", "4:22 PM", false),
            ChatMessage("Maya", "Sabi sa kanta ng Esremborak: 'Kung mamahalin kita ngayon, sabay tayong magugutom.' Ang sakit pala kapag totoo.", "4:24 PM", false),
            ChatMessage("Mateo", "Pag-usapan natin 'yan pagdating mo.", "4:26 PM", true)
        )
    }

    val familyMessages = remember {
        listOf(
            ChatMessage("Nanay", "Anak, nakatawag na ba ang bangko tungkol sa Bahay na Bato?", "2:10 PM", false),
            ChatMessage("Mateo", "Opo 'Nay. ₱450,000 ang kailangan bago matapos ang buwan.", "2:15 PM", true),
            ChatMessage("Nanay", "Huwag mong sisihin ang sarili mo. Kung kailangang iwan ang bahay ni Lolo Ramon, ang mahalaga buhay tayo at may kinakain.", "2:18 PM", false)
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xCC090710))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .width(360.dp)
                .border(2.dp, CapizAmber, RoundedCornerShape(20.dp))
                .testTag("phone_card"),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF14101F))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                // Top Phone Speaker Notch & Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "GLOBE 4G ● 4:32 PM",
                        fontFamily = PixelFontFamily,
                        fontSize = 12.sp,
                        color = MutedText
                    )
                    IconButton(
                        onClick = onClose,
                        modifier = Modifier.size(28.dp).testTag("close_phone_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = SampaguitaWhite,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Chat Tabs
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        color = if (selectedTab == "Maya") CapizAmber else TwilightSurface,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedTab = "Maya" }
                    ) {
                        Text(
                            text = "Maya (OFW ✈)",
                            fontFamily = PixelFontFamily,
                            fontSize = 13.sp,
                            color = if (selectedTab == "Maya") TwilightDark else SampaguitaWhite,
                            modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp)
                        )
                    }

                    Surface(
                        color = if (selectedTab == "Nanay") CapizAmber else TwilightSurface,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedTab = "Nanay" }
                    ) {
                        Text(
                            text = "Nanay (Pamilya)",
                            fontFamily = PixelFontFamily,
                            fontSize = 13.sp,
                            color = if (selectedTab == "Nanay") TwilightDark else SampaguitaWhite,
                            modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Messages List
                val messages = if (selectedTab == "Maya") mayaMessages else familyMessages
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(messages) { msg ->
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = if (msg.isMe) Alignment.End else Alignment.Start
                        ) {
                            Surface(
                                color = if (msg.isMe) CapizAmber else TwilightSurface,
                                shape = RoundedCornerShape(
                                    topStart = 10.dp,
                                    topEnd = 10.dp,
                                    bottomStart = if (msg.isMe) 10.dp else 2.dp,
                                    bottomEnd = if (msg.isMe) 2.dp else 10.dp
                                ),
                                modifier = Modifier.width((240).dp)
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(
                                        text = msg.sender,
                                        fontFamily = PixelFontFamily,
                                        fontSize = 11.sp,
                                        color = if (msg.isMe) TwilightDark else RainCyan
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = msg.textTagalog,
                                        fontFamily = HeritageSerifFontFamily,
                                        fontSize = 13.sp,
                                        color = if (msg.isMe) TwilightDark else SampaguitaWhite
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = msg.time,
                                        fontFamily = PixelFontFamily,
                                        fontSize = 10.sp,
                                        color = if (msg.isMe) Color(0x9914101F) else MutedText,
                                        modifier = Modifier.align(Alignment.End)
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
