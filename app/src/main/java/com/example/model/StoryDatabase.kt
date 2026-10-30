package com.example.model

object StoryDatabase {

    val allSecrets = listOf(
        ItemSecretId.PAWN_TICKET,
        ItemSecretId.ELENA_LETTER,
        ItemSecretId.ESREMBORAK_TAPE,
        ItemSecretId.TWO_PESO_COIN,
        ItemSecretId.FORECLOSURE_NOTICE,
        ItemSecretId.FLIGHT_TICKET
    )

    fun getInitialDialogue(): DialogueNode {
        return DialogueNode(
            id = "intro_1",
            speaker = "Mateo",
            tagalogText = "Apatnapung taon nang nakatayo ang Bahay na Bato na 'to... Pero sa susunod na linggo, kukunin na ng bangko ang mga susi.",
            englishText = "This ancestral house has stood for over a century... But next week, the bank will take the keys.",
            emotion = "melancholic",
            nextNodeId = "intro_2"
        )
    }

    val dialogueMap: Map<String, DialogueNode> = mapOf(
        "intro_2" to DialogueNode(
            id = "intro_2",
            speaker = "Mateo",
            tagalogText = "Pumapatak ang ulan sa bubong na yero. Malamig ang hangin mula sa mga bintanang capiz. Bakit ba napakahirap magsimula?",
            englishText = "Rain taps against the tin roof. A damp chill slips past the capiz panes. Why is it so terrifying to say goodbye?",
            emotion = "thoughtful",
            nextNodeId = "intro_3"
        ),
        "intro_3" to DialogueNode(
            id = "intro_3",
            speaker = "Maya",
            tagalogText = "(Humahangos mula sa pinto, bitbit ang basang payong at supot ng pandesal) Mateo! Kanina pa kita hinahanap sa ibaba.",
            englishText = "(Rushing in from the porch with a dripping umbrella and warm pandesal) Mateo! I was looking for you downstairs.",
            emotion = "loving",
            choices = listOf(
                DialogueChoice(
                    textTagalog = "\"Bakit ka pa nagpaulan, Maya? Basa na ang damit mo.\"",
                    textEnglish = "\"Why did you run through the storm, Maya? You're soaking wet.\"",
                    pusoDelta = 2,
                    realidadDelta = 0,
                    nextDialogueId = "maya_cared",
                    reactionHint = "Nag-aalala ka sa kanya."
                ),
                DialogueChoice(
                    textTagalog = "\"Salamat sa pandesal... pero akala ko ba abala ka sa pag-iimpake para sa flight mo?\"",
                    textEnglish = "\"Thanks for the pandesal... but aren't you packing for your flight?\"",
                    pusoDelta = 0,
                    realidadDelta = 2,
                    nextDialogueId = "maya_departure",
                    reactionHint = "Madiin ang reyalidad ng kanyang pag-alis."
                )
            )
        ),
        "maya_cared" to DialogueNode(
            id = "maya_cared",
            speaker = "Maya",
            tagalogText = "Sanay naman ako sa ulan. Naisip ko lang... huling pagkakataon na nating magkasama sa bahay na 'to bago ako lumipad sa Abu Dhabi.",
            englishText = "I'm used to the rain. I just thought... this is our last chance to stand under this roof together before I leave for Abu Dhabi.",
            emotion = "sad",
            nextNodeId = "explore_prompt"
        ),
        "maya_departure" to DialogueNode(
            id = "maya_departure",
            speaker = "Maya",
            tagalogText = "Naka-impake na ang dalawang maleta ko. Ang hindi ko lang maisilid sa bagahe ay itong lungkot sa dibdib ko.",
            englishText = "My two luggage bags are packed. The only thing I can't squeeze inside is the ache in my chest.",
            emotion = "sad",
            nextNodeId = "explore_prompt"
        ),
        "explore_prompt" to DialogueNode(
            id = "explore_prompt",
            speaker = "Maya",
            tagalogText = "Halika, Mateo. Tulungan kitang ayusin ang silid ni Lolo Ramon. Baka may mga gamit pang kailangang iligtas sa loob ng lumang baul.",
            englishText = "Come on, Mateo. Let me help you sort through Lolo Ramon's bedroom. There might still be heirlooms worth saving inside the old camphor chest.",
            emotion = "thoughtful",
            nextNodeId = null
        ),

        // SILID & BAUL DISCOVERY
        "baul_inspect" to DialogueNode(
            id = "baul_inspect",
            speaker = "Mateo",
            tagalogText = "Ang antigong baul ni Lolo Ramon... May lumang kandadong tanso, ngunit nakabukas na.",
            englishText = "Lolo Ramon's heirloom baul... The antique brass padlock is already unlatched.",
            emotion = "surprised",
            nextNodeId = "baul_reveal_1"
        ),
        "baul_reveal_1" to DialogueNode(
            id = "baul_reveal_1",
            speaker = "Maya",
            tagalogText = "Tingnan mo ito, Mateo! Isang lumang resibo ng sanglaan mula 1984... at isang liham na may bahid ng pinatuyong luha.",
            englishText = "Look at this, Mateo! An old pawnshop slip from 1984... and an unmailed letter stained with dried tears.",
            emotion = "surprised",
            unlocksSecret = ItemSecretId.PAWN_TICKET,
            nextNodeId = "baul_reveal_2"
        ),
        "baul_reveal_2" to DialogueNode(
            id = "baul_reveal_2",
            speaker = "Maya",
            tagalogText = "(Binabasa ang liham ni Lola Elena) 'Ramon, mahal kita nang higit pa sa aking buhay... Ngunit mahal ang bigas, mahal ang mabuhay. Patawad kung kinailangan kong magpakasal sa iba upang mailigtas ang aking pamilya sa gutom.'",
            englishText = "(Reading Lola Elena's letter) 'Ramon, I love you more than life itself... But rice is costly, survival is costly. Forgive me if I had to marry someone of wealth to save my brothers from starvation.'",
            emotion = "sad",
            unlocksSecret = ItemSecretId.ELENA_LETTER,
            nextNodeId = "baul_discussion"
        ),
        "baul_discussion" to DialogueNode(
            id = "baul_discussion",
            speaker = "Maya",
            tagalogText = "Apatnapung taon na ang lumipas... pero parang walang pinagbago ang bansa natin. Hanggang ngayon, kailangan pa ring isakripisyo ang pagmamahal para sa sikmura.",
            englishText = "Forty years have passed... yet it feels like nothing has changed. Even today, people are forced to surrender love just to feed their families.",
            emotion = "melancholic",
            choices = listOf(
                DialogueChoice(
                    textTagalog = "\"Hindi ko hahayaang maulit sa atin ang nangyari kina Lolo Ramon at Lola Elena, Maya!\"",
                    textEnglish = "\"I won't let what happened to Ramon and Elena happen to us, Maya!\"",
                    pusoDelta = 3,
                    realidadDelta = 0,
                    nextDialogueId = "mateo_defiant",
                    reactionHint = "Nag-aalab ang iyong puso."
                ),
                DialogueChoice(
                    textTagalog = "\"Tama ka... 'Di sapat ang tapat na pagsinta kung ang kalaban ay gutom at bayarin.\"",
                    textEnglish = "\"You're right... Devotion isn't enough when fighting starvation and debts.\"",
                    pusoDelta = 0,
                    realidadDelta = 3,
                    nextDialogueId = "mateo_realistic",
                    reactionHint = "Yumuko ka sa bigat ng reyalidad."
                )
            )
        ),
        "mateo_defiant" to DialogueNode(
            id = "mateo_defiant",
            speaker = "Maya",
            tagalogText = "Sana ganoon kadali 'yun, Mateo. Pero bawat buwan, hinihintay ni Nanay ang ₱20,000 para sa maintenance niya. Sa ospital dito, ₱14,000 lang ang buong buwan kong sahod bawas pa ang buwis.",
            englishText = "I wish it were that simple, Mateo. But every month, Mama needs ₱20,000 for her dialysis. At the local ward, my entire monthly salary is ₱14,000 before taxes.",
            emotion = "sad",
            nextNodeId = "tape_found"
        ),
        "mateo_realistic" to DialogueNode(
            id = "mateo_realistic",
            speaker = "Maya",
            tagalogText = "Masakit marinig sa 'yo 'yan, pero alam kong totoo. Ang kanta ng Esremborak... 'Ayoko lang namang makita kang kinikilig habang sa hirap ay nagdurusa.'",
            englishText = "It hurts to hear that out loud, but I know it's true. Just like Esremborak's lyric... 'I refuse to see you smiling with butterflies while drowning in misery.'",
            emotion = "melancholic",
            nextNodeId = "tape_found"
        ),
        "tape_found" to DialogueNode(
            id = "tape_found",
            speaker = "Mateo",
            tagalogText = "Tingnan mo, may kaset sa ilalim ng baul. 'Harana sa Ulan - Mahal ang Magmahal'. May lumang dalawang pisong barya rin na nakabalot sa panyo.",
            englishText = "Look, there's a cassette tape underneath. 'Harana in the Rain - Mahal ang Magmahal'. And a vintage two-peso coin wrapped in a handkerchief.",
            emotion = "thoughtful",
            unlocksSecret = ItemSecretId.ESREMBORAK_TAPE,
            nextNodeId = "azotea_call"
        ),
        "azotea_call" to DialogueNode(
            id = "azotea_call",
            speaker = "Maya",
            tagalogText = "Lumakas ang ulan sa labas. Pumunta tayo sa Azotea, Mateo. May gusto akong sabihin sa 'yo bago tuluyang dumilim.",
            englishText = "The rain is pouring harder outside. Let's go to the balcony, Mateo. There is something I need to tell you before night falls.",
            emotion = "loving",
            nextNodeId = null
        ),

        // AZOTEA CLIMAX
        "azotea_climax" to DialogueNode(
            id = "azotea_climax",
            speaker = "Maya",
            tagalogText = "Noong high school tayo, dito tayo tumatambay habang umuulan. Nangako tayo na kahit anong mangyari, hindi tayo maghihiwalay.",
            englishText = "Back in high school, we would sit here and watch the typhoons. We promised each other that no matter what, we would never drift apart.",
            emotion = "thoughtful",
            nextNodeId = "azotea_climax_2"
        ),
        "azotea_climax_2" to DialogueNode(
            id = "azotea_climax_2",
            speaker = "Maya",
            tagalogText = "Mateo... kung sabihin ko bang mahal kita mula pa noon hanggang ngayon... pipigilan mo ba akong sumakay sa eroplano sa Biyernes?",
            englishText = "Mateo... if I told you that I have loved you then, and love you now... would you beg me not to board that flight on Friday?",
            emotion = "loving",
            choices = listOf(
                DialogueChoice(
                    textTagalog = "\"Oo, Maya! Huwag kang umalis. Ibebenta ko ang lupa, magsusumikap ako, pero sabay tayong haharap sa bagyo.\"",
                    textEnglish = "\"Yes, Maya! Don't leave. I will sell the land, I will work double shifts, but we will weather this storm together.\"",
                    pusoDelta = 5,
                    realidadDelta = -2,
                    nextDialogueId = "ending_branch_courage",
                    reactionHint = "Pinili mo ang pag-ibig sa kabila ng lahat ng panganib."
                ),
                DialogueChoice(
                    textTagalog = "\"Hindi kita pipigilan, Maya. Mahal kita nang sobra, kaya hindi ko kayang ikaw ang magbayad ng mga kakulangan ko.\"",
                    textEnglish = "\"I won't stop you, Maya. I love you too much to let my financial shortcomings shackle your family's future.\"",
                    pusoDelta = 1,
                    realidadDelta = 5,
                    nextDialogueId = "ending_branch_sacrifice",
                    reactionHint = "Pinili mo ang mapait na sakripisyo para sa kanyang kinabukasan."
                ),
                DialogueChoice(
                    textTagalog = "\"Pumunta ka sa Abu Dhabi, ngunit mangako ka: hindi ito paalam. Magsusumikap ako rito, at susundan kita saan ka man mapadpad.\"",
                    textEnglish = "\"Go to Abu Dhabi, but promise me: this is not goodbye. I will rebuild our life here and follow you anywhere on earth.\"",
                    pusoDelta = 4,
                    realidadDelta = 4,
                    nextDialogueId = "ending_branch_promise",
                    reactionHint = "Bumuo ka ng pangako ng muling pagkikita."
                )
            )
        ),
        "ending_branch_courage" to DialogueNode(
            id = "ending_branch_courage",
            speaker = "Maya",
            tagalogText = "(Pumapatak ang luha kasabay ng ulan) Mateo... Akala ko mag-isa lang akong natatakot. Kung handa kang lumaban kahit sardinas at tuyo lang ang ulam, hindi ako aalis.",
            englishText = "(Tears falling with the rain) Mateo... I thought I was alone in my terror. If you are truly willing to fight even if we only have canned sardines and salted fish, I won't get on that plane.",
            emotion = "loving",
            nextNodeId = "TRIGGER_ENDING_COURAGE"
        ),
        "ending_branch_sacrifice" to DialogueNode(
            id = "ending_branch_sacrifice",
            speaker = "Maya",
            tagalogText = "(Tahimik na humikbi habang nakatingin sa kalsada) Salamat sa pagiging totoo, Mateo. Tulad ni Lolo Ramon, ang pinakadakilang pagmamahal ay ang pagpaparaya.",
            englishText = "(Weeping quietly into the rain) Thank you for being honest, Mateo. Just like Lolo Ramon, the deepest love is letting go so the other may live.",
            emotion = "melancholic",
            nextNodeId = "TRIGGER_ENDING_SACRIFICE"
        ),
        "ending_branch_promise" to DialogueNode(
            id = "ending_branch_promise",
            speaker = "Maya",
            tagalogText = "Itatago ko ang dalawang pisong barya ni Lolo Ramon sa aking wallet sa Abu Dhabi. Tuwing titingnan ko 'yun, maaalala kong may naghihintay sa akin sa Bahay na Bato.",
            englishText = "I will keep Lolo Ramon's two-peso coin in my wallet in Abu Dhabi. Every time I hold it, I will remember that someone is keeping the lights on in this ancestral home.",
            emotion = "loving",
            nextNodeId = "TRIGGER_ENDING_PROMISE"
        )
    )

    fun getEnding(type: String): EndingStory {
        return when (type) {
            "COURAGE" -> EndingStory(
                titleTagalog = "Wakas 1: Sabay Tayong Lalaban",
                titleEnglish = "Ending 1: We Will Face the Storm Together",
                subtitle = "Ang Tagumpay ng Pusong Hindi Sumuko sa Gutom",
                storyTagalog = "Hindi sumakay si Maya sa eroplano patungong Abu Dhabi. Sa tulong ng komunidad at maliit na kooperatiba, ginawang museo at heritage café ang Bahay na Bato. Hindi naging madali ang buhay—maraming gabi silang nagtipid sa sardinas at instant kape—ngunit kinakanta nila ang 'Mahal Magmahal' na may ngiti, dahil walang presyo ang pag-ibig na ipinaglaban.",
                storyEnglish = "Maya tore up her flight ticket. With the help of the local town artisans, Mateo converted the Bahay na Bato into a heritage café and cultural archive. Life remained financially challenging—many dinners consisted only of rice and sardines—yet whenever the rain poured on the capiz windows, they sang 'Mahal Magmahal' with radiant smiles, knowing true devotion cannot be priced by the world.",
                endingType = "COURAGE"
            )
            "SACRIFICE" -> EndingStory(
                titleTagalog = "Wakas 2: Ang Sakripisyo ng Kahapon",
                titleEnglish = "Ending 2: Echoes of Ancestral Sacrifice",
                subtitle = "Kung Kailan Mas Mahalaga ang Buhay Kaysa Pagsinta",
                storyTagalog = "Lumipad si Maya patungong Abu Dhabi noong Biyernes ng madaling araw. Naging matagumpay siyang nars at napagtapos ang kanyang mga kapatid. Si Mateo naman ay tuluyang isinuko ang Bahay na Bato sa bangko at nagtrabaho sa siyudad. Tulad nina Lolo Ramon at Lola Elena, ang kanilang pag-ibig ay nanatiling isang sagradong alaala: isang awit na pinatugtog sa lumang kaset, minamahal sa malayo ngunit pinaglayo ng reyalidad ng mundo.",
                storyEnglish = "Maya departed for Abu Dhabi in the quiet dawn of Friday. She became a dedicated nurse, sending remittances that educated all her siblings and cured her mother. Mateo surrendered the ancestral home to the bank and relocated to Manila. Like Ramon and Elena before them, their devotion remained a sacred hymn: loved fiercely in silence, separated by the unforgiving economy of the world.",
                endingType = "SACRIFICE"
            )
            else -> EndingStory(
                titleTagalog = "Wakas 3: Mahal ang Magmahal",
                titleEnglish = "Ending 3: Costly to Love, Worth the Wait",
                subtitle = "Ang Pangako sa Ilalim ng Ulan",
                storyTagalog = "Umalis si Maya, ngunit hindi nagtapos ang kanilang kuwento. Bawat buwan, nagpapadala si Maya ng sulat at voice recording, habang si Mateo ay nagtrabaho nang marangal upang matubos ang sangla ng singsing at ng bahay. Lumipas ang tatlong taon. Sa muling pagbuhos ng ulan sa tag-araw, isang pamilyar na yabag ang tumapak sa sahig na narra. Nakatayo si Maya sa pintuan, may dalang supot ng mainit na pandesal.",
                storyEnglish = "Maya flew to the desert, but their story was far from finished. Every month, letters and cassette tapes traversed the ocean. Mateo worked tirelessly, eventually reclaiming Ramon's pawned guitar and restoring the capiz windowpanes. Three years later, as the summer monsoon tapped against the iron roof, familiar footsteps creaked upon the narra floorboards. Maya stood at the threshold, holding a warm bag of pandesal.",
                endingType = "PROMISE"
            )
        }
    }
}
