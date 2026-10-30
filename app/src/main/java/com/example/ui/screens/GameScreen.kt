package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material.icons.filled.Tv
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ItemSecretId
import com.example.model.RoomId
import com.example.ui.GameViewModel
import com.example.ui.ScreenState
import com.example.ui.components.DialogueBox
import com.example.ui.components.ItemInspectionOverlay
import com.example.ui.components.JukeboxSheet
import com.example.ui.components.ParallaxRoomView
import com.example.ui.components.PhoneMessengerView
import com.example.ui.components.ScanlineOverlay
import com.example.ui.components.ScrapbookView
import com.example.ui.theme.CapizAmber
import com.example.ui.theme.CapizGlow
import com.example.ui.theme.HeartRed
import com.example.ui.theme.HeritageSerifFontFamily
import com.example.ui.theme.NarraDark
import com.example.ui.theme.PixelFontFamily
import com.example.ui.theme.RainCyan
import com.example.ui.theme.SampaguitaWhite
import com.example.ui.theme.TwilightDark
import com.example.ui.theme.TwilightDeep
import com.example.ui.theme.TwilightSurface

@Composable
fun GameScreen(
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    val screenState by viewModel.screenState.collectAsState()
    val currentRoom by viewModel.currentRoom.collectAsState()
    val playerX by viewModel.playerX.collectAsState()
    val isFacingRight by viewModel.isFacingRight.collectAsState()
    val unlockedSecrets by viewModel.unlockedSecrets.collectAsState()
    val currentDialogue by viewModel.currentDialogue.collectAsState()
    val inspectedItem by viewModel.inspectedItem.collectAsState()
    val showEnglishSubtitles by viewModel.showEnglishSubtitles.collectAsState()
    val showCrtScanlines by viewModel.showCrtScanlines.collectAsState()
    val statusNotification by viewModel.statusNotification.collectAsState()
    val pusoScore by viewModel.pusoScore.collectAsState()
    val realidadScore by viewModel.realidadScore.collectAsState()

    BackHandler {
        when (screenState) {
            ScreenState.DIALOGUE -> viewModel.advanceDialogue()
            ScreenState.INSPECTION -> viewModel.closeInspection()
            ScreenState.PHONE_MESSENGER -> viewModel.closePhone()
            ScreenState.JUKEBOX -> viewModel.closeJukebox()
            ScreenState.SCRAPBOOK -> viewModel.closeScrapbook()
            ScreenState.EXPLORATION -> viewModel.restartGame()
            else -> {}
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(TwilightDark)
    ) {
        // 1. Central 2.5D Room View
        ParallaxRoomView(
            room = currentRoom,
            playerX = playerX,
            isFacingRight = isFacingRight,
            unlockedSecrets = unlockedSecrets,
            onInteractSecret = { secret -> viewModel.inspectSecret(secret) },
            onTriggerDialogue = { id -> viewModel.triggerDialogue(id) },
            onMovePlayer = { dx -> viewModel.movePlayer(dx) }
        )

        // 2. Retro CRT Scanline Filter
        if (showCrtScanlines) {
            ScanlineOverlay()
        }

        // 3. Top Navigation & Action Bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            Surface(
                color = TwilightDark.copy(alpha = 0.90f),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x33F3C363)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Room Name Title
                        Column {
                            Text(
                                text = currentRoom.titleTagalog,
                                fontFamily = HeritageSerifFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = CapizAmber
                            )
                            if (showEnglishSubtitles) {
                                Text(
                                    text = currentRoom.titleEnglish,
                                    fontFamily = HeritageSerifFontFamily,
                                    fontSize = 11.sp,
                                    color = RainCyan
                                )
                            }
                        }

                        // Toolbar Icons
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Phone Messenger Button
                            IconButton(
                                onClick = { viewModel.openPhone() },
                                modifier = Modifier.testTag("open_phone_button")
                            ) {
                                Box {
                                    Icon(
                                        imageVector = Icons.Default.PhoneAndroid,
                                        contentDescription = "Phone Messages",
                                        tint = CapizAmber
                                    )
                                    // Notification badge
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(HeartRed)
                                            .align(Alignment.TopEnd)
                                    )
                                }
                            }

                            // Scrapbook Button
                            IconButton(
                                onClick = { viewModel.openScrapbook() },
                                modifier = Modifier.testTag("open_scrapbook_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoStories,
                                    contentDescription = "Ancestral Scrapbook",
                                    tint = CapizGlow
                                )
                            }

                            // Jukebox Button
                            IconButton(
                                onClick = { viewModel.openJukebox() },
                                modifier = Modifier.testTag("open_jukebox_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MusicNote,
                                    contentDescription = "Jukebox",
                                    tint = RainCyan
                                )
                            }

                            // Subtitles toggle
                            IconButton(
                                onClick = { viewModel.toggleSubtitles() },
                                modifier = Modifier.testTag("toggle_subtitles_game_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Subtitles,
                                    contentDescription = "Toggle Subtitles",
                                    tint = if (showEnglishSubtitles) CapizAmber else Color(0x66F3C363)
                                )
                            }
                        }
                    }

                    // Room Switcher Tabs
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        RoomId.values().forEach { r ->
                            val isSelected = r == currentRoom
                            Surface(
                                color = if (isSelected) CapizAmber else TwilightSurface,
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { viewModel.changeRoom(r) }
                                    .testTag("room_tab_${r.name.lowercase()}")
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                ) {
                                    Text(
                                        text = when (r) {
                                            RoomId.SALA -> "Sala"
                                            RoomId.SILID -> "Silid"
                                            RoomId.COMEDOR -> "Kusina"
                                            RoomId.AZOTEA -> "Azotea"
                                        },
                                        fontFamily = PixelFontFamily,
                                        fontSize = 13.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) TwilightDark else SampaguitaWhite
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Notification Toast (When secrets are unlocked)
            AnimatedVisibility(
                visible = statusNotification != null,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                statusNotification?.let { msg ->
                    Surface(
                        color = CapizAmber,
                        shape = RoundedCornerShape(20.dp),
                        shadowElevation = 8.dp,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = "✦ $msg ✦",
                            fontFamily = PixelFontFamily,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TwilightDark,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // 4. Bottom Virtual Controls (Walk Left / Right, Interact)
        if (screenState == ScreenState.EXPLORATION) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Movement D-Pad
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Surface(
                        color = TwilightDeep.copy(alpha = 0.90f),
                        shape = CircleShape,
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, CapizAmber),
                        modifier = Modifier
                            .size(52.dp)
                            .clickable { viewModel.movePlayer(-0.08f) }
                            .testTag("move_left_button")
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Move Left",
                                tint = CapizAmber
                            )
                        }
                    }

                    Surface(
                        color = TwilightDeep.copy(alpha = 0.90f),
                        shape = CircleShape,
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, CapizAmber),
                        modifier = Modifier
                            .size(52.dp)
                            .clickable { viewModel.movePlayer(0.08f) }
                            .testTag("move_right_button")
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Move Right",
                                tint = CapizAmber
                            )
                        }
                    }
                }

                // Interact / Action Button
                Surface(
                    color = CapizAmber,
                    shape = RoundedCornerShape(24.dp),
                    shadowElevation = 6.dp,
                    modifier = Modifier
                        .clickable {
                            when (currentRoom) {
                                RoomId.SALA -> viewModel.triggerDialogue("intro_3")
                                RoomId.SILID -> viewModel.triggerDialogue("baul_inspect")
                                RoomId.COMEDOR -> viewModel.inspectSecret(ItemSecretId.FORECLOSURE_NOTICE)
                                RoomId.AZOTEA -> viewModel.triggerDialogue("azotea_climax")
                            }
                        }
                        .testTag("interact_button")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(HeartRed)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "● Pagsusuri / Kausapin",
                            fontFamily = PixelFontFamily,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TwilightDark
                        )
                    }
                }
            }
        }

        // 5. Overlays (Dialogue, Inspection, Messenger, Jukebox, Scrapbook)
        if (screenState == ScreenState.DIALOGUE && currentDialogue != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter),
                contentAlignment = Alignment.BottomCenter
            ) {
                DialogueBox(
                    dialogue = currentDialogue!!,
                    showEnglishSubtitles = showEnglishSubtitles,
                    onAdvance = { viewModel.advanceDialogue() },
                    onChooseOption = { choice -> viewModel.chooseDialogueOption(choice) }
                )
            }
        }

        if (screenState == ScreenState.INSPECTION && inspectedItem != null) {
            ItemInspectionOverlay(
                item = inspectedItem!!,
                showEnglishSubtitles = showEnglishSubtitles,
                onClose = { viewModel.closeInspection() }
            )
        }

        if (screenState == ScreenState.PHONE_MESSENGER) {
            PhoneMessengerView(
                onClose = { viewModel.closePhone() }
            )
        }

        if (screenState == ScreenState.JUKEBOX) {
            JukeboxSheet(
                soundEngine = viewModel.soundEngine,
                onClose = { viewModel.closeJukebox() }
            )
        }

        if (screenState == ScreenState.SCRAPBOOK) {
            ScrapbookView(
                unlockedSecrets = unlockedSecrets,
                pusoScore = pusoScore,
                realidadScore = realidadScore,
                onInspectSecret = { secret -> viewModel.inspectSecret(secret) },
                onClose = { viewModel.closeScrapbook() }
            )
        }
    }
}
