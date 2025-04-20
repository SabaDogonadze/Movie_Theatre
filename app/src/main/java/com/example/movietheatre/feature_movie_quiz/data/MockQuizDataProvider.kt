package com.example.movietheatre.feature_movie_quiz.data

import com.example.movietheatre.feature_movie_quiz.domain.model.AnswerOption
import com.example.movietheatre.feature_movie_quiz.domain.model.QuizCategory
import com.example.movietheatre.feature_movie_quiz.domain.model.QuizQuestion
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockQuizDataProvider @Inject constructor() {
    fun provideQuizQuestions(): List<QuizQuestion> {
        return listOf(
            // Category 1: Movie Classics
            QuizQuestion(
                id = 1,
                question = "What movie features a character named Jack Dawson?",
                imageUrl = "https://example.com/titanic.jpg",
                options = listOf(
                    AnswerOption("A", "A", "Titanic"),
                    AnswerOption("B", "B", "The Departed"),
                    AnswerOption("C", "C", "Inception"),
                    AnswerOption("D", "D", "The Revenant")
                ),
                correctOptionId = "A"
            ),
            QuizQuestion(
                id = 6,
                question = "Who played the lead role in 'Casablanca'?",
                imageUrl = "https://example.com/casablanca.jpg",
                options = listOf(
                    AnswerOption("A", "A", "Marlon Brando"),
                    AnswerOption("B", "B", "Humphrey Bogart"),
                    AnswerOption("C", "C", "Clark Gable"),
                    AnswerOption("D", "D", "James Stewart")
                ),
                correctOptionId = "B"
            ),
            QuizQuestion(
                id = 7,
                question = "Which film is known for the line 'Here's looking at you, kid'?",
                imageUrl = "https://example.com/casablanca_quote.jpg",
                options = listOf(
                    AnswerOption("A", "A", "Gone with the Wind"),
                    AnswerOption("B", "B", "Citizen Kane"),
                    AnswerOption("C", "C", "Casablanca"),
                    AnswerOption("D", "D", "The Godfather")
                ),
                correctOptionId = "C"
            ),
            QuizQuestion(
                id = 8,
                question = "What year was 'Gone with the Wind' released?",
                imageUrl = "https://example.com/gone_with_wind.jpg",
                options = listOf(
                    AnswerOption("A", "A", "1939"),
                    AnswerOption("B", "B", "1942"),
                    AnswerOption("C", "C", "1945"),
                    AnswerOption("D", "D", "1936")
                ),
                correctOptionId = "A"
            ),
            QuizQuestion(
                id = 9,
                question = "Which classic film featured the character of Dorothy traveling to Oz?",
                imageUrl = "https://example.com/wizard_of_oz.jpg",
                options = listOf(
                    AnswerOption("A", "A", "Alice in Wonderland"),
                    AnswerOption("B", "B", "The Wizard of Oz"),
                    AnswerOption("C", "C", "Mary Poppins"),
                    AnswerOption("D", "D", "Chitty Chitty Bang Bang")
                ),
                correctOptionId = "B"
            ),
            QuizQuestion(
                id = 10,
                question = "Who directed 'Psycho'?",
                imageUrl = "https://example.com/psycho.jpg",
                options = listOf(
                    AnswerOption("A", "A", "Alfred Hitchcock"),
                    AnswerOption("B", "B", "Orson Welles"),
                    AnswerOption("C", "C", "Stanley Kubrick"),
                    AnswerOption("D", "D", "Billy Wilder")
                ),
                correctOptionId = "A"
            ),
            QuizQuestion(
                id = 11,
                question = "Which actor played Rick Blaine in 'Casablanca'?",
                imageUrl = "https://example.com/rick_blaine.jpg",
                options = listOf(
                    AnswerOption("A", "A", "Cary Grant"),
                    AnswerOption("B", "B", "Humphrey Bogart"),
                    AnswerOption("C", "C", "Gregory Peck"),
                    AnswerOption("D", "D", "James Stewart")
                ),
                correctOptionId = "B"
            ),

            // Category 2: Awards & Recognition
            QuizQuestion(
                id = 2,
                question = "Which movie won the Best Picture Oscar in 2020?",
                imageUrl = "https://example.com/parasite.jpg",
                options = listOf(
                    AnswerOption("A", "A", "1917"),
                    AnswerOption("B", "B", "Joker"),
                    AnswerOption("C", "C", "Parasite"),
                    AnswerOption("D", "D", "Once Upon a Time in Hollywood")
                ),
                correctOptionId = "C"
            ),
            QuizQuestion(
                id = 12,
                question = "Who has won the most Academy Awards for acting?",
                imageUrl = "https://example.com/oscar_statue.jpg",
                options = listOf(
                    AnswerOption("A", "A", "Meryl Streep"),
                    AnswerOption("B", "B", "Jack Nicholson"),
                    AnswerOption("C", "C", "Katharine Hepburn"),
                    AnswerOption("D", "D", "Daniel Day-Lewis")
                ),
                correctOptionId = "C"
            ),
            QuizQuestion(
                id = 13,
                question = "Which film won the Palme d'Or at Cannes Film Festival in 2019?",
                imageUrl = "https://example.com/parasite_cannes.jpg",
                options = listOf(
                    AnswerOption("A", "A", "Once Upon a Time in Hollywood"),
                    AnswerOption("B", "B", "Pain and Glory"),
                    AnswerOption("C", "C", "Parasite"),
                    AnswerOption("D", "D", "Portrait of a Lady on Fire")
                ),
                correctOptionId = "C"
            ),
            QuizQuestion(
                id = 14,
                question = "Which movie won the most Oscars in history?",
                imageUrl = "https://example.com/oscar_record.jpg",
                options = listOf(
                    AnswerOption("A", "A", "Titanic"),
                    AnswerOption("B", "B", "Ben-Hur"),
                    AnswerOption("C", "C", "The Lord of the Rings: The Return of the King"),
                    AnswerOption("D", "D", "All three are tied")
                ),
                correctOptionId = "D"
            ),
            QuizQuestion(
                id = 15,
                question = "Who was the first Black person to win an Academy Award?",
                imageUrl = "https://example.com/oscar_history.jpg",
                options = listOf(
                    AnswerOption("A", "A", "Sidney Poitier"),
                    AnswerOption("B", "B", "Hattie McDaniel"),
                    AnswerOption("C", "C", "Dorothy Dandridge"),
                    AnswerOption("D", "D", "James Earl Jones")
                ),
                correctOptionId = "B"
            ),
            QuizQuestion(
                id = 16,
                question = "Which director has won the most Academy Awards for Best Director?",
                imageUrl = "https://example.com/director_awards.jpg",
                options = listOf(
                    AnswerOption("A", "A", "Steven Spielberg"),
                    AnswerOption("B", "B", "Martin Scorsese"),
                    AnswerOption("C", "C", "John Ford"),
                    AnswerOption("D", "D", "Frank Capra")
                ),
                correctOptionId = "C"
            ),
            QuizQuestion(
                id = 17,
                question = "Which film won the first Academy Award for Best Picture?",
                imageUrl = "https://example.com/first_oscar.jpg",
                options = listOf(
                    AnswerOption("A", "A", "Wings"),
                    AnswerOption("B", "B", "All Quiet on the Western Front"),
                    AnswerOption("C", "C", "Grand Hotel"),
                    AnswerOption("D", "D", "The Broadway Melody")
                ),
                correctOptionId = "A"
            ),

            // Category 3: Directors & Filmmakers
            QuizQuestion(
                id = 3,
                question = "Who directed 'The Dark Knight'?",
                imageUrl = "https://example.com/dark_knight.jpg",
                options = listOf(
                    AnswerOption("A", "A", "Zack Snyder"),
                    AnswerOption("B", "B", "Christopher Nolan"),
                    AnswerOption("C", "C", "Tim Burton"),
                    AnswerOption("D", "D", "J.J. Abrams")
                ),
                correctOptionId = "B"
            ),
            QuizQuestion(
                id = 18,
                question = "Which director is known for films like 'Pulp Fiction' and 'Kill Bill'?",
                imageUrl = "https://example.com/tarantino.jpg",
                options = listOf(
                    AnswerOption("A", "A", "Martin Scorsese"),
                    AnswerOption("B", "B", "Quentin Tarantino"),
                    AnswerOption("C", "C", "Steven Spielberg"),
                    AnswerOption("D", "D", "David Fincher")
                ),
                correctOptionId = "B"
            ),
            QuizQuestion(
                id = 19,
                question = "Who directed 'E.T. the Extra-Terrestrial'?",
                imageUrl = "https://example.com/et.jpg",
                options = listOf(
                    AnswerOption("A", "A", "George Lucas"),
                    AnswerOption("B", "B", "James Cameron"),
                    AnswerOption("C", "C", "Steven Spielberg"),
                    AnswerOption("D", "D", "Robert Zemeckis")
                ),
                correctOptionId = "C"
            ),
            QuizQuestion(
                id = 20,
                question = "Which female director won the Oscar for 'Nomadland'?",
                imageUrl = "https://example.com/nomadland.jpg",
                options = listOf(
                    AnswerOption("A", "A", "Kathryn Bigelow"),
                    AnswerOption("B", "B", "Greta Gerwig"),
                    AnswerOption("C", "C", "Sofia Coppola"),
                    AnswerOption("D", "D", "Chloé Zhao")
                ),
                correctOptionId = "D"
            ),
            QuizQuestion(
                id = 21,
                question = "Who directed 'The Godfather'?",
                imageUrl = "https://example.com/godfather.jpg",
                options = listOf(
                    AnswerOption("A", "A", "Francis Ford Coppola"),
                    AnswerOption("B", "B", "Martin Scorsese"),
                    AnswerOption("C", "C", "Sidney Lumet"),
                    AnswerOption("D", "D", "Brian De Palma")
                ),
                correctOptionId = "A"
            ),
            QuizQuestion(
                id = 22,
                question = "Which director is known for 'Taxi Driver' and 'Goodfellas'?",
                imageUrl = "https://example.com/scorsese.jpg",
                options = listOf(
                    AnswerOption("A", "A", "Francis Ford Coppola"),
                    AnswerOption("B", "B", "Martin Scorsese"),
                    AnswerOption("C", "C", "Woody Allen"),
                    AnswerOption("D", "D", "Stanley Kubrick")
                ),
                correctOptionId = "B"
            ),
            QuizQuestion(
                id = 23,
                question = "Who directed the 'Alien' franchise's first film?",
                imageUrl = "https://example.com/alien.jpg",
                options = listOf(
                    AnswerOption("A", "A", "James Cameron"),
                    AnswerOption("B", "B", "David Fincher"),
                    AnswerOption("C", "C", "Ridley Scott"),
                    AnswerOption("D", "D", "Jean-Pierre Jeunet")
                ),
                correctOptionId = "C"
            ),

            // Category 4: Superhero Movies
            QuizQuestion(
                id = 4,
                question = "Which actor plays Iron Man in the Marvel Cinematic Universe?",
                imageUrl = "https://example.com/iron_man.jpg",
                options = listOf(
                    AnswerOption("A", "A", "Chris Evans"),
                    AnswerOption("B", "B", "Chris Hemsworth"),
                    AnswerOption("C", "C", "Robert Downey Jr."),
                    AnswerOption("D", "D", "Mark Ruffalo")
                ),
                correctOptionId = "C"
            ),
            QuizQuestion(
                id = 5,
                question = "What is the name of the fictional country in Black Panther?",
                imageUrl = "https://example.com/wakanda.jpg",
                options = listOf(
                    AnswerOption("A", "A", "Zamunda"),
                    AnswerOption("B", "B", "Wakanda"),
                    AnswerOption("C", "C", "Genovia"),
                    AnswerOption("D", "D", "Latveria")
                ),
                correctOptionId = "B"
            ),
            QuizQuestion(
                id = 24,
                question = "Who directed 'The Dark Knight Rises'?",
                imageUrl = "https://example.com/dark_knight_rises.jpg",
                options = listOf(
                    AnswerOption("A", "A", "Zack Snyder"),
                    AnswerOption("B", "B", "Christopher Nolan"),
                    AnswerOption("C", "C", "Joss Whedon"),
                    AnswerOption("D", "D", "Tim Burton")
                ),
                correctOptionId = "B"
            ),
            QuizQuestion(
                id = 25,
                question = "Which actor plays Thor in the Marvel Cinematic Universe?",
                imageUrl = "https://example.com/thor.jpg",
                options = listOf(
                    AnswerOption("A", "A", "Chris Evans"),
                    AnswerOption("B", "B", "Chris Hemsworth"),
                    AnswerOption("C", "C", "Chris Pratt"),
                    AnswerOption("D", "D", "Tom Hiddleston")
                ),
                correctOptionId = "B"
            ),
            QuizQuestion(
                id = 26,
                question = "Who played the Joker in 'The Dark Knight'?",
                imageUrl = "https://example.com/joker.jpg",
                options = listOf(
                    AnswerOption("A", "A", "Jack Nicholson"),
                    AnswerOption("B", "B", "Jared Leto"),
                    AnswerOption("C", "C", "Heath Ledger"),
                    AnswerOption("D", "D", "Joaquin Phoenix")
                ),
                correctOptionId = "C"
            ),
            QuizQuestion(
                id = 27,
                question = "Which was the first film in the Marvel Cinematic Universe?",
                imageUrl = "https://example.com/mcu_first.jpg",
                options = listOf(
                    AnswerOption("A", "A", "The Incredible Hulk"),
                    AnswerOption("B", "B", "Iron Man"),
                    AnswerOption("C", "C", "Captain America: The First Avenger"),
                    AnswerOption("D", "D", "Thor")
                ),
                correctOptionId = "B"
            ),
            QuizQuestion(
                id = 28,
                question = "Who is the villain in 'The Avengers' (2012)?",
                imageUrl = "https://example.com/avengers_villain.jpg",
                options = listOf(
                    AnswerOption("A", "A", "Ultron"),
                    AnswerOption("B", "B", "Thanos"),
                    AnswerOption("C", "C", "Loki"),
                    AnswerOption("D", "D", "Red Skull")
                ),
                correctOptionId = "C"
            )
        )
    }

    fun provideQuizCategories(): List<QuizCategory> {
        return listOf(
            QuizCategory(
                id = 1,
                title = "Movie Classics",
                imageUrl = "https://example.com/classics.jpg",
                questionCount = 7,
                rewardCoins = 50,
                difficulty = "Easy"
            ),
            QuizCategory(
                id = 2,
                title = "Awards & Recognition",
                imageUrl = "https://example.com/awards.jpg",
                questionCount = 7,
                rewardCoins = 75,
                difficulty = "Medium"
            ),
            QuizCategory(
                id = 3,
                title = "Directors & Filmmakers",
                imageUrl = "https://example.com/directors.jpg",
                questionCount = 7,
                rewardCoins = 60,
                difficulty = "Medium"
            ),
            QuizCategory(
                id = 4,
                title = "Superhero Movies",
                imageUrl = "https://example.com/superheroes.jpg",
                questionCount = 7,
                rewardCoins = 100,
                difficulty = "Hard"
            )
        )
    }

    // Map to track which questions belong to which category
    private val questionCategoryMap = mapOf(
        // Category 1: Movie Classics
        1 to 1,
        6 to 1,
        7 to 1,
        8 to 1,
        9 to 1,
        10 to 1,
        11 to 1,

        // Category 2: Awards & Recognition
        2 to 2,
        12 to 2,
        13 to 2,
        14 to 2,
        15 to 2,
        16 to 2,
        17 to 2,

        // Category 3: Directors & Filmmakers
        3 to 3,
        18 to 3,
        19 to 3,
        20 to 3,
        21 to 3,
        22 to 3,
        23 to 3,

        // Category 4: Superhero Movies
        4 to 4,
        5 to 4,
        24 to 4,
        25 to 4,
        26 to 4,
        27 to 4,
        28 to 4
    )

    fun getQuestionsForCategory(categoryId: Int): List<QuizQuestion> {
        return provideQuizQuestions().filter { questionCategoryMap[it.id] == categoryId }
    }
}