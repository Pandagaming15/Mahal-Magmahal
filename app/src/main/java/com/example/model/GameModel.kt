package com.example.model

enum class RoomId(
    val titleTagalog: String,
    val titleEnglish: String,
    val descriptionTagalog: String
) {
    SALA(
        titleTagalog = "Ang Sala ng Bahay na Bato",
        titleEnglish = "The Ancestral Living Room",
        descriptionTagalog = "Malamig ang hangin mula sa mga bintanang capiz. Umaalingawngaw ang bawat yabag sa makinis na sahig na narra habang pumapatak ang ulan sa bubong na yero."
    ),
    SILID(
        titleTagalog = "Ang Lumang Silid at Baul",
        titleEnglish = "The Heirloom Bedroom & Camphor Chest",
        descriptionTagalog = "Dito nakatago ang antigong baul ng mga ninuno. Amoy kahoy at lumang papel ang hangin, taglay ang mga lihim ng apatnapung taong nakalipas."
    ),
    COMEDOR(
        titleTagalog = "Ang Comedor at Kusina",
        titleEnglish = "The Dining Room & Foreclosure Notice",
        descriptionTagalog = "Isang lata ng sardinas sa mesa, lumang tasa ng kape, at ang mapait na reyalidad ng sulat mula sa bangko."
    ),
    AZOTEA(
        titleTagalog = "Ang Azotea sa Ilalim ng Ulan",
        titleEnglish = "The Rain-Soaked Balcony",
        descriptionTagalog = "Tanaw ang basang kalsada at lumang poste ng ilaw. Dito nakatayo si Maya, hawak ang payong at ang bigat ng pamamaalam."
    )
}

enum class ItemSecretId(
    val nameTagalog: String,
    val nameEnglish: String,
    val snippet: String,
    val detailTagalog: String,
    val detailEnglish: String,
    val iconName: String
) {
    PAWN_TICKET(
        nameTagalog = "Resibo ng Sanglaan (1984)",
        nameEnglish = "Pawn Ticket (October 1984)",
        snippet = "Sangla ng singsing pangkasal para sa isang kabang bigas...",
        detailTagalog = "Resibo mula sa 'Agencia de Empeños ng Bayan'. Isinangla ni Lolo Ramon ang kanyang singsing at gitara sa halagang ₱180 upang may maipakain sa pamilya noong matinding krisis sa ekonomiya.",
        detailEnglish = "A faded pawn ticket from 1984. Grandfather Ramon pawned his wedding ring and beloved guitar for ₱180 to buy rice and medicine for his starving family.",
        iconName = "receipt"
    ),
    ELENA_LETTER(
        nameTagalog = "Liham ni Lola Elena",
        nameEnglish = "Elena's Unmailed Letter",
        snippet = "Mahal kita, Ramon... pero mahal ang bigas, mahal ang mabuhay...",
        detailTagalog = "\"Ramon, mahal kita nang higit pa sa aking buhay. Ngunit mahal ang bigas, mahal ang magkasakit, mahal ang mabuhay. Pinilit kong itago ang luha ko nang ibenta mo ang iyong gitara... Patawad kung kinailangan kong magpakasal sa iba upang mailigtas ang aking mga kapatid sa gutom. Hindi kailanman nawala ang pagmamahal ko sa 'yo.\"",
        detailEnglish = "\"Ramon, I love you more than life itself. But rice is expensive, sickness is costly, and staying alive has a price. I hid my tears when you sold your guitar... Forgive me if I had to marry someone else so my siblings wouldn't starve. My love for you never died.\"",
        iconName = "mail"
    ),
    ESREMBORAK_TAPE(
        nameTagalog = "Kaset ng Esremborak",
        nameEnglish = "Vintage Harana Cassette",
        snippet = "Kanta ng pag-ibig sa gitna ng kahirapan...",
        detailTagalog = "Isang tape na nirekord noong dekada '80 na may sulat-kamay: 'Mahal Magmahal - Esremborak Harana'. Isang awit ng binatang walang maipagmamalaki kundi tapat na puso, ngunit natatakot na magutom ang kanyang minamahal.",
        detailEnglish = "A handwritten cassette labeled 'Mahal Magmahal - Esremborak Harana'. An acoustic song about wanting to romance the love of your life, but realizing that devotion cannot buy a tomorrow without food.",
        iconName = "music"
    ),
    TWO_PESO_COIN(
        nameTagalog = "Lumang Dalawang Piso (1983)",
        nameEnglish = "Vintage Decagonal Two-Peso Coin",
        snippet = "Ang huling barya sa bulsa ni Lolo Ramon...",
        detailTagalog = "Ang baryang may larawan ni Andres Bonifacio at puno ng niyog. Ito ang natirang barya sa bulsa ni Ramon noong araw na sumakay si Elena sa barko patungong Maynila.",
        detailEnglish = "The old 10-sided Philippine two-peso coin. The exact last coin left in Ramon's pocket on the afternoon Elena departed for Manila.",
        iconName = "coin"
    ),
    FORECLOSURE_NOTICE(
        nameTagalog = "Abiso ng Remate ng Bangko",
        nameEnglish = "Bank Foreclosure Notice",
        snippet = "₱450,000 kabuuang utang ng lupang minana...",
        detailTagalog = "Huling abiso mula sa Rural Bank. Kung hindi mababayaran ang ₱450,000 na accumulated debt bago matapos ang buwan, kukunin na ng bangko ang Bahay na Bato.",
        detailEnglish = "Final foreclosure demand. If ₱450,000 is not settled, the 112-year-old ancestral house will be seized and auctioned by the bank.",
        iconName = "warning"
    ),
    FLIGHT_TICKET(
        nameTagalog = "E-Ticket ni Maya sa Abu Dhabi",
        nameEnglish = "Maya's One-Way Flight Ticket",
        snippet = "Flight PR 656 patungong Abu Dhabi sa Biyernes...",
        detailTagalog = "Isang one-way plane ticket. Si Maya ay lilipad bilang staff nurse sa Gitnang Silangan. Iniipit dito ang resibo ng tuition fee ng kanyang bunsong kapatid na si Bunso.",
        detailEnglish = "A one-way ticket to Abu Dhabi departing this Friday. Tucked inside is the tuition receipt for Maya's youngest brother.",
        iconName = "flight"
    )
}

data class DialogueChoice(
    val textTagalog: String,
    val textEnglish: String,
    val pusoDelta: Int,
    val realidadDelta: Int,
    val nextDialogueId: String,
    val reactionHint: String
)

data class DialogueNode(
    val id: String,
    val speaker: String,
    val tagalogText: String,
    val englishText: String,
    val emotion: String, // "thoughtful", "sad", "surprised", "loving", "melancholic"
    val choices: List<DialogueChoice> = emptyList(),
    val nextNodeId: String? = null,
    val unlocksSecret: ItemSecretId? = null
)

data class EndingStory(
    val titleTagalog: String,
    val titleEnglish: String,
    val subtitle: String,
    val storyTagalog: String,
    val storyEnglish: String,
    val endingType: String // "SACRIFICE", "COURAGE", "PROMISE"
)
