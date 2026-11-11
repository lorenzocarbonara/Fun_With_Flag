package com.example.quiz_game

object Soluzioni {

    val listone = listOf(
        Domanda(R.drawable.belgium, "Belgio", "Germania"),
        Domanda(R.drawable.canada, "Canada", "Stati Uniti"),
        Domanda(R.drawable.chile, "Cile", "Francia"),
        Domanda(R.drawable.china, "Cina", "Vietnam"),
        Domanda(R.drawable.colombia, "Colombia", "Belgio"),
        Domanda(R.drawable.france, "Francia", "Spagna"),
        Domanda(R.drawable.germany, "Germania", "Francia"),
        Domanda(R.drawable.japan, "Giappone", "Regno Unito"),
        Domanda(R.drawable.russia, "Russia", "Germania"),
        Domanda(R.drawable.singapore, "Singapore", "Stati Uniti"),
        Domanda(R.drawable.spain, "Spagna", "Vietnam"),
        Domanda(R.drawable.united_kingdom, "Regno Unito", "Belgio"),
        Domanda(R.drawable.united_states, "Stati Uniti", "Singapore"),
        Domanda(R.drawable.vietnam, "Vietnam", "Russia"),
    )

    var listoneMescolato: List<Domanda> = listOf()

    fun inizioQuiz() {
        listoneMescolato = listone.shuffled()
    }
}