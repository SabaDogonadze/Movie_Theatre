package com.example.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.resource.R

object NavigationCommands {
    // Graph-level deep links
    const val DEEP_LINK_LOGIN_GRAPH = "movieapp://feature/login"
    const val DEEP_LINK_REGISTER_GRAPH = "movieapp://feature/register"
    const val DEEP_LINK_HOME_GRAPH = "movieapp://feature/home"

    const val DEEP_LINK_MOVIE_QUIZ_GRAPH = "movieapp://feature/movie_quiz"


    const val DEEP_LINK_MOVIE_DETAIL_GRAPH = "movieapp://feature/movie_detail"
    const val DEEP_LINK_MOVIE_DETAIL = "movieapp://moviedetail/"

    // Seat module deep links
    const val DEEP_LINK_SEAT_GRAPH = "movieapp://feature/seat"
    const val DEEP_LINK_SEAT = "movieapp://seat/"


    const val DEEP_LINK_PAYMENT_GRAPH = "movieapp://feature/payment"
    const val DEEP_LINK_PAYMENT = "movieapp://payment/"

    const val DEEP_LINK_PROFILE_GRAPH = "movieapp://feature/profile"


    // Navigation animations
    object Animations {
        val defaultAnimations = NavOptions.Builder()
            .setEnterAnim(R.anim.from_right)
            .setExitAnim(R.anim.to_left)
            .setPopEnterAnim(R.anim.from_left)
            .setPopExitAnim(R.anim.to_right)
            .build()

        val expandAnimations = NavOptions.Builder()
            .setEnterAnim(R.anim.from_center_to_expand)
            .setExitAnim(R.anim.from_center_to_expand)
            .setPopEnterAnim(R.anim.from_center_to_expand)
            .setPopExitAnim(R.anim.from_center_to_expand)
            .build()
    }

    fun navigateToPayment(
        navController: NavController,
        screeningId: Int,
        seats: Array<String>,
        totalPrice: Float,
    ) {
        // URI encoding for array data
        val seatsJson = Uri.encode(seats.joinToString(","))
        val uri = Uri.parse("${DEEP_LINK_PAYMENT}$screeningId/$seatsJson/$totalPrice")
        navController.navigate(uri, Animations.defaultAnimations)
    }

    fun navigateToMovieDetail(navController: NavController, movieId: Int) {
        val uri = Uri.parse("${DEEP_LINK_MOVIE_DETAIL}$movieId")
        navController.navigate(uri, Animations.defaultAnimations)
    }

    // Navigate from movie detail to seat selection
    fun navigateToSeatSelection(
        navController: NavController,
        screeningId: Int,
        ticketPrice: Float,
    ) {
        val uri = Uri.parse("${DEEP_LINK_SEAT}$screeningId/$ticketPrice")
        navController.navigate(uri, Animations.defaultAnimations)
    }

    fun navigateToLoginGraph(navController: NavController) {
        navController.navigate(Uri.parse(DEEP_LINK_LOGIN_GRAPH), Animations.defaultAnimations)
    }

    fun navigateToMovieQuizGraph(navController: NavController) {
        navController.navigate(Uri.parse(DEEP_LINK_MOVIE_QUIZ_GRAPH), Animations.defaultAnimations)
    }

    fun navigateToRegisterGraph(navController: NavController) {
        navController.navigate(Uri.parse(DEEP_LINK_REGISTER_GRAPH), Animations.defaultAnimations)
    }

    fun navigateToHomeGraph(navController: NavController) {
        navController.navigate(Uri.parse(DEEP_LINK_HOME_GRAPH), Animations.defaultAnimations)
    }

}