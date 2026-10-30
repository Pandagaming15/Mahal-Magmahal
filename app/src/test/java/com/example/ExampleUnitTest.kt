package com.example

import com.example.model.ItemSecretId
import com.example.model.StoryDatabase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testInitialDialogueIntegrity() {
        val initial = StoryDatabase.getInitialDialogue()
        assertEquals("Mateo", initial.speaker)
        assertNotNull(initial.nextNodeId)
    }

    @Test
    fun testAllSecretsHaveTagalogAndEnglish() {
        val secrets = StoryDatabase.allSecrets
        assertEquals(6, secrets.size)
        secrets.forEach { secret ->
            assertTrue(secret.nameTagalog.isNotEmpty())
            assertTrue(secret.nameEnglish.isNotEmpty())
            assertTrue(secret.detailTagalog.isNotEmpty())
            assertTrue(secret.detailEnglish.isNotEmpty())
        }
    }

    @Test
    fun testEndingStoriesIntegrity() {
        val courageEnding = StoryDatabase.getEnding("COURAGE")
        assertTrue(courageEnding.titleTagalog.contains("Sabay Tayong Lalaban"))

        val sacrificeEnding = StoryDatabase.getEnding("SACRIFICE")
        assertTrue(sacrificeEnding.titleTagalog.contains("Ang Sakripisyo"))

        val promiseEnding = StoryDatabase.getEnding("PROMISE")
        assertTrue(promiseEnding.titleTagalog.contains("Mahal ang Magmahal"))
    }
}
