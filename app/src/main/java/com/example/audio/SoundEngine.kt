package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.sin
import kotlin.random.Random

/**
 * SoundEngine handles immersive sound design for "Mahal ang Magmahal":
 * - Melancholic acoustic guitar synthesis playing the "Mahal Magmahal" chord progression & melody
 * - Real-time rain on galvanized roof (yero) sound synthesis
 * - Ticking antique grandfather clock
 * - Retro vinyl warmth
 * - Haptic feedback for emotional heartbeat moments
 */
class SoundEngine(private val context: Context) {

    private val sampleRate = 22050
    private var audioTrack: AudioTrack? = null
    private var isPlayingMusic = false
    private var musicJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Default)

    private val vibrator: Vibrator? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
        vibratorManager?.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }

    private val _isMusicMuted = MutableStateFlow(false)
    val isMusicMuted: StateFlow<Boolean> = _isMusicMuted.asStateFlow()

    private val _isRainEnabled = MutableStateFlow(true)
    val isRainEnabled: StateFlow<Boolean> = _isRainEnabled.asStateFlow()

    private val _currentLyricIndex = MutableStateFlow(0)
    val currentLyricIndex: StateFlow<Int> = _currentLyricIndex.asStateFlow()

    // Frequencies for chords and melody (D Major key)
    // D4: 293.66, F#4: 369.99, A4: 440.00
    // F#m: F#4, A4, C#5 (554.37)
    // G: G4 (392.00), B4 (493.88), D5 (587.33)
    // A: A4 (440.00), C#5 (554.37), E5 (659.25)
    // Bm: B3 (246.94), D4 (293.66), F#4 (369.99), B4 (493.88)
    // Em: E4 (329.63), G4 (392.00), B4 (493.88)

    data class Note(val freq: Double, val durationMs: Int, val isBass: Boolean = false)
    data class LyricEntry(val tagalog: String, val english: String, val emotion: String)

    val songLyrics = listOf(
        LyricEntry(
            tagalog = "Mahal ngayon ang magmahal...",
            english = "It costs so much to fall in love these days...",
            emotion = "Nostalgic Realization"
        ),
        LyricEntry(
            tagalog = "Mahal kita, pero mahal...",
            english = "I love you, but everything is expensive...",
            emotion = "Hesitation & Ache"
        ),
        LyricEntry(
            tagalog = "Mahal na ang bigas, pati lata ng sardinas",
            english = "Even rice is costly now, even a simple tin of sardines",
            emotion = "Harsh Economic Reality"
        ),
        LyricEntry(
            tagalog = "Kung mamahalin kita ngayon, sabay tayong magugutom",
            english = "If I pursue this love with you, we will just starve together",
            emotion = "Painful Truth"
        ),
        LyricEntry(
            tagalog = "'Di sapat ang sahod ko, kahit tapat ako sa 'yo",
            english = "My salary is never enough, though my devotion is true to you",
            emotion = "Deep Sincerity"
        ),
        LyricEntry(
            tagalog = "Anong silbi nitong pagmamahal, kung lahat din ay nagmamahal?",
            english = "What use is romantic love, when the price of everything keeps rising?",
            emotion = "Bitter Irony"
        ),
        LyricEntry(
            tagalog = "Ayoko lang namang makita kang kinikilig habang sa hirap ay nagdurusa",
            english = "I just refuse to see you smiling with butterflies while drowning in hardship",
            emotion = "Pure Protective Sacrifice"
        ),
        LyricEntry(
            tagalog = "Kaya hahayaan munang humupa ang ulan...",
            english = "So I will let this storm pass quietly, until we can breathe again...",
            emotion = "Quiet Hope"
        )
    )

    init {
        initAudioTrack()
    }

    private fun initAudioTrack() {
        val minBufferSize = AudioTrack.getMinBufferSize(
            sampleRate,
            AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        )
        val bufferSize = (minBufferSize * 2).coerceAtLeast(4096)

        audioTrack = AudioTrack.Builder()
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            .setAudioFormat(
                AudioFormat.Builder()
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setSampleRate(sampleRate)
                    .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                    .build()
            )
            .setBufferSizeInBytes(bufferSize)
            .setTransferMode(AudioTrack.MODE_STREAM)
            .build()

        audioTrack?.play()
    }

    fun startMusic() {
        if (isPlayingMusic) return
        isPlayingMusic = true

        musicJob = scope.launch {
            val d4 = 293.66
            val fs4 = 369.99
            val a4 = 440.00
            val g4 = 392.00
            val b4 = 493.88
            val cs5 = 554.37
            val d5 = 587.33
            val e5 = 659.25
            val b3 = 246.94
            val e4 = 329.63
            val a3 = 220.00

            // Fingerpicking progression based on "Mahal Magmahal" chords
            // Verse: D - F#m - G - A
            // Chorus: Bm - G - Em - A
            while (isActive && isPlayingMusic) {
                // Section 1: Intro / Verse
                _currentLyricIndex.value = 0
                playArpeggioChord(d4, fs4, a4, d5, 400)
                playArpeggioChord(fs4, a4, cs5, fs4, 400)

                _currentLyricIndex.value = 1
                playArpeggioChord(g4, b4, d5, g4, 400)
                playArpeggioChord(a3, cs5, e5, a4, 400)

                // Section 2: Chorus "Mahal na ang bigas..."
                _currentLyricIndex.value = 2
                playMelodicNote(fs4, 300)
                playMelodicNote(fs4, 300)
                playMelodicNote(fs4, 300)
                playMelodicNote(e4, 300)
                playMelodicNote(d4, 500)

                _currentLyricIndex.value = 3
                playArpeggioChord(b3, d4, fs4, b4, 350)
                playMelodicNote(d4, 300)
                playMelodicNote(e4, 300)
                playMelodicNote(fs4, 400)
                playMelodicNote(d4, 600)

                // Section 3: "'Di sapat ang sahod ko..."
                _currentLyricIndex.value = 4
                playArpeggioChord(g4, b4, d5, g4, 380)
                playArpeggioChord(a4, cs5, e5, a4, 380)

                // Section 4: "Anong silbi nitong pagmamahal..."
                _currentLyricIndex.value = 5
                playArpeggioChord(b3, fs4, b4, d5, 420)
                playArpeggioChord(g4, d5, g4, b4, 420)

                // Section 5: "Ayoko lang namang makita kang..."
                _currentLyricIndex.value = 6
                playArpeggioChord(e4, g4, b4, e5, 450)
                playArpeggioChord(a3, cs5, e5, a4, 450)

                // Section 6: Resolution
                _currentLyricIndex.value = 7
                playArpeggioChord(d4, fs4, a4, d5, 600)
                delay(600)
            }
        }
    }

    private suspend fun playArpeggioChord(
        root: Double,
        third: Double,
        fifth: Double,
        octave: Double,
        noteDurationMs: Int
    ) {
        val notes = listOf(root, third, fifth, octave, fifth, third)
        for (f in notes) {
            if (!_isMusicMuted.value) {
                renderPluckedString(f, noteDurationMs, isBass = f == root)
            } else {
                renderSilenceOrRain(noteDurationMs)
            }
        }
    }

    private suspend fun playMelodicNote(freq: Double, durationMs: Int) {
        if (!_isMusicMuted.value) {
            renderPluckedString(freq, durationMs, isLead = true)
        } else {
            renderSilenceOrRain(durationMs)
        }
    }

    private fun renderPluckedString(
        freq: Double,
        durationMs: Int,
        isBass: Boolean = false,
        isLead: Boolean = false
    ) {
        val numSamples = (sampleRate * (durationMs / 1000.0)).toInt().coerceAtLeast(100)
        val buffer = ShortArray(numSamples)
        val rainOn = _isRainEnabled.value

        var phase1 = 0.0
        var phase2 = 0.0
        var phase3 = 0.0
        val step1 = 2.0 * PI * freq / sampleRate
        val step2 = 2.0 * PI * (freq * 2.0) / sampleRate
        val step3 = 2.0 * PI * (freq * 3.0) / sampleRate

        val decayFactor = if (isBass) 2.2 else if (isLead) 3.5 else 4.0

        for (i in 0 until numSamples) {
            val t = i.toDouble() / sampleRate
            val envelope = exp(-t * decayFactor)

            // Fundamental + Warm acoustic guitar harmonics
            val sine1 = sin(phase1)
            val sine2 = sin(phase2) * 0.45
            val sine3 = sin(phase3) * 0.20
            val guitarSample = (sine1 + sine2 + sine3) * envelope * 0.55

            // Rain noise filter (simulating rain on tin roof / yero)
            val rainSample = if (rainOn) {
                (Random.nextFloat() * 2f - 1f) * 0.035
            } else 0.0

            // Vinyl crackle occasional pop
            val vinylClick = if (Random.nextInt(1200) == 0) (Random.nextFloat() * 0.08) else 0.0

            val totalSample = (guitarSample + rainSample + vinylClick).coerceIn(-1.0, 1.0)
            buffer[i] = (totalSample * Short.MAX_VALUE).toInt().toShort()

            phase1 += step1
            phase2 += step2
            phase3 += step3
        }

        audioTrack?.write(buffer, 0, buffer.size)
    }

    private fun renderSilenceOrRain(durationMs: Int) {
        val numSamples = (sampleRate * (durationMs / 1000.0)).toInt().coerceAtLeast(100)
        val buffer = ShortArray(numSamples)
        val rainOn = _isRainEnabled.value

        for (i in 0 until numSamples) {
            val rainSample = if (rainOn) {
                (Random.nextFloat() * 2f - 1f) * 0.04
            } else 0.0
            buffer[i] = (rainSample * Short.MAX_VALUE).toInt().toShort()
        }
        audioTrack?.write(buffer, 0, buffer.size)
    }

    fun toggleMusic() {
        _isMusicMuted.value = !_isMusicMuted.value
    }

    fun toggleRain() {
        _isRainEnabled.value = !_isRainEnabled.value
    }

    fun playHeartbeatHaptic() {
        if (vibrator?.hasVibrator() == true) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Double thump heartbeat pattern: lub-dub
                val timings = longArrayOf(0, 70, 90, 110)
                val amplitudes = intArrayOf(0, 160, 0, 220)
                vibrator.vibrate(VibrationEffect.createWaveform(timings, amplitudes, -1))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(150)
            }
        }
    }

    fun playClickHaptic() {
        if (vibrator?.hasVibrator() == true) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(25)
            }
        }
    }

    fun stop() {
        isPlayingMusic = false
        musicJob?.cancel()
        try {
            audioTrack?.stop()
            audioTrack?.release()
        } catch (_: Exception) {}
        audioTrack = null
    }
}
