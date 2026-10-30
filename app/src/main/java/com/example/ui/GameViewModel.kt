package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.SoundEngine
import com.example.model.DialogueChoice
import com.example.model.DialogueNode
import com.example.model.EndingStory
import com.example.model.ItemSecretId
import com.example.model.RoomId
import com.example.model.StoryDatabase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class ScreenState {
    TITLE,
    EXPLORATION,
    DIALOGUE,
    INSPECTION,
    PHONE_MESSENGER,
    JUKEBOX,
    SCRAPBOOK,
    ENDING
}

class GameViewModel(application: Application) : AndroidViewModel(application) {

    val soundEngine = SoundEngine(application.applicationContext)

    private val _screenState = MutableStateFlow(ScreenState.TITLE)
    val screenState: StateFlow<ScreenState> = _screenState.asStateFlow()

    private val _currentRoom = MutableStateFlow(RoomId.SALA)
    val currentRoom: StateFlow<RoomId> = _currentRoom.asStateFlow()

    // Player position within current room (normalized percentage 0.0f .. 1.0f)
    private val _playerX = MutableStateFlow(0.35f)
    val playerX: StateFlow<Float> = _playerX.asStateFlow()

    private val _isFacingRight = MutableStateFlow(true)
    val isFacingRight: StateFlow<Boolean> = _isFacingRight.asStateFlow()

    private val _unlockedSecrets = MutableStateFlow(setOf<ItemSecretId>())
    val unlockedSecrets: StateFlow<Set<ItemSecretId>> = _unlockedSecrets.asStateFlow()

    private val _pusoScore = MutableStateFlow(5)
    val pusoScore: StateFlow<Int> = _pusoScore.asStateFlow()

    private val _realidadScore = MutableStateFlow(5)
    val realidadScore: StateFlow<Int> = _realidadScore.asStateFlow()

    private val _currentDialogue = MutableStateFlow<DialogueNode?>(null)
    val currentDialogue: StateFlow<DialogueNode?> = _currentDialogue.asStateFlow()

    private val _inspectedItem = MutableStateFlow<ItemSecretId?>(null)
    val inspectedItem: StateFlow<ItemSecretId?> = _inspectedItem.asStateFlow()

    private val _currentEnding = MutableStateFlow<EndingStory?>(null)
    val currentEnding: StateFlow<EndingStory?> = _currentEnding.asStateFlow()

    private val _showEnglishSubtitles = MutableStateFlow(true)
    val showEnglishSubtitles: StateFlow<Boolean> = _showEnglishSubtitles.asStateFlow()

    private val _showCrtScanlines = MutableStateFlow(true)
    val showCrtScanlines: StateFlow<Boolean> = _showCrtScanlines.asStateFlow()

    private val _statusNotification = MutableStateFlow<String?>(null)
    val statusNotification: StateFlow<String?> = _statusNotification.asStateFlow()

    init {
        soundEngine.startMusic()
    }

    fun startGame() {
        soundEngine.playClickHaptic()
        _screenState.value = ScreenState.DIALOGUE
        _currentDialogue.value = StoryDatabase.getInitialDialogue()
    }

    fun advanceDialogue() {
        val current = _currentDialogue.value ?: return
        soundEngine.playClickHaptic()

        // Check secret unlock
        current.unlocksSecret?.let { secret ->
            unlockSecret(secret)
        }

        val nextId = current.nextNodeId
        if (nextId != null) {
            when {
                nextId == "TRIGGER_ENDING_COURAGE" -> triggerEnding("COURAGE")
                nextId == "TRIGGER_ENDING_SACRIFICE" -> triggerEnding("SACRIFICE")
                nextId == "TRIGGER_ENDING_PROMISE" -> triggerEnding("PROMISE")
                StoryDatabase.dialogueMap.containsKey(nextId) -> {
                    _currentDialogue.value = StoryDatabase.dialogueMap[nextId]
                }
                else -> {
                    _currentDialogue.value = null
                    _screenState.value = ScreenState.EXPLORATION
                }
            }
        } else {
            // Dialogue sequence finished, return to exploration
            _currentDialogue.value = null
            _screenState.value = ScreenState.EXPLORATION
        }
    }

    fun chooseDialogueOption(choice: DialogueChoice) {
        soundEngine.playHeartbeatHaptic()
        _pusoScore.value += choice.pusoDelta
        _realidadScore.value += choice.realidadDelta

        val nextNode = StoryDatabase.dialogueMap[choice.nextDialogueId]
        if (nextNode != null) {
            _currentDialogue.value = nextNode
        } else {
            advanceDialogue()
        }
    }

    fun movePlayer(deltaX: Float) {
        soundEngine.playClickHaptic()
        val newX = (_playerX.value + deltaX).coerceIn(0.08f, 0.92f)
        _playerX.value = newX
        if (deltaX > 0) _isFacingRight.value = true
        if (deltaX < 0) _isFacingRight.value = false
    }

    fun changeRoom(newRoom: RoomId) {
        soundEngine.playClickHaptic()
        _currentRoom.value = newRoom
        _playerX.value = 0.5f

        // Check contextual events based on room arrival
        if (newRoom == RoomId.SILID && !_unlockedSecrets.value.contains(ItemSecretId.ELENA_LETTER)) {
            triggerDialogue("baul_inspect")
        } else if (newRoom == RoomId.AZOTEA && _unlockedSecrets.value.contains(ItemSecretId.ELENA_LETTER)) {
            triggerDialogue("azotea_climax")
        }
    }

    fun triggerDialogue(nodeId: String) {
        StoryDatabase.dialogueMap[nodeId]?.let { node ->
            soundEngine.playHeartbeatHaptic()
            _currentDialogue.value = node
            _screenState.value = ScreenState.DIALOGUE
        }
    }

    fun inspectSecret(secret: ItemSecretId) {
        soundEngine.playClickHaptic()
        unlockSecret(secret)
        _inspectedItem.value = secret
        _screenState.value = ScreenState.INSPECTION
    }

    fun closeInspection() {
        soundEngine.playClickHaptic()
        _inspectedItem.value = null
        _screenState.value = ScreenState.EXPLORATION
    }

    fun openPhone() {
        soundEngine.playClickHaptic()
        _screenState.value = ScreenState.PHONE_MESSENGER
    }

    fun closePhone() {
        soundEngine.playClickHaptic()
        _screenState.value = ScreenState.EXPLORATION
    }

    fun openJukebox() {
        soundEngine.playClickHaptic()
        _screenState.value = ScreenState.JUKEBOX
    }

    fun closeJukebox() {
        soundEngine.playClickHaptic()
        _screenState.value = if (_currentEnding.value != null) ScreenState.ENDING else ScreenState.EXPLORATION
    }

    fun openScrapbook() {
        soundEngine.playClickHaptic()
        _screenState.value = ScreenState.SCRAPBOOK
    }

    fun closeScrapbook() {
        soundEngine.playClickHaptic()
        _screenState.value = ScreenState.EXPLORATION
    }

    fun toggleSubtitles() {
        _showEnglishSubtitles.value = !_showEnglishSubtitles.value
    }

    fun toggleCrt() {
        _showCrtScanlines.value = !_showCrtScanlines.value
    }

    fun unlockSecret(secret: ItemSecretId) {
        if (!_unlockedSecrets.value.contains(secret)) {
            _unlockedSecrets.value = _unlockedSecrets.value + secret
            showTemporaryNotification("Natuklasan: ${secret.nameTagalog}")
        }
    }

    private fun showTemporaryNotification(msg: String) {
        _statusNotification.value = msg
        viewModelScope.launch {
            delay(3500)
            if (_statusNotification.value == msg) {
                _statusNotification.value = null
            }
        }
    }

    fun triggerEnding(type: String) {
        soundEngine.playHeartbeatHaptic()
        _currentEnding.value = StoryDatabase.getEnding(type)
        _screenState.value = ScreenState.ENDING
    }

    fun restartGame() {
        _currentRoom.value = RoomId.SALA
        _playerX.value = 0.35f
        _currentDialogue.value = null
        _inspectedItem.value = null
        _currentEnding.value = null
        _screenState.value = ScreenState.TITLE
    }

    override fun onCleared() {
        super.onCleared()
        soundEngine.stop()
    }
}
