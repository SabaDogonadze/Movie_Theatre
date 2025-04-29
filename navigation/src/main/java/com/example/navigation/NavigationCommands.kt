package com.example.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.core.presentation.R

object NavigationCommands {

    private const val DEEP_LINK_LOGIN_GRAPH = "movieapp://feature/login"
    private const val DEEP_LINK_REGISTER_GRAPH = "movieapp://feature/register"
    private const val DEEP_LINK_HOME_GRAPH = "movieapp://feature/home"

    private const val DEEP_LINK_MOVIE_QUIZ_GRAPH = "movieapp://feature/movie_quiz"

    private const val DEEP_LINK_SHOP_GRAPH = "movieapp://feature/shop"

    const val DEEP_LINK_MOVIE_DETAIL_GRAPH = "movieapp://feature/movie_detail"
    private const val DEEP_LINK_MOVIE_DETAIL = "movieapp://moviedetail/"

    const val DEEP_LINK_SEAT_GRAPH = "movieapp://feature/seat"
    private const val DEEP_LINK_SEAT = "movieapp://seat/"


    const val DEEP_LINK_PAYMENT_GRAPH = "movieapp://feature/payment"
    private const val DEEP_LINK_PAYMENT = "movieapp://payment/"

    const val DEEP_LINK_PROFILE_GRAPH = "movieapp://feature/profile"


    object Animations {
        val defaultAnimations = NavOptions.Builder()
            .setEnterAnim(R.anim.from_right)
            .setExitAnim(R.anim.to_left)
            .setPopEnterAnim(R.anim.from_left)
            .setPopExitAnim(R.anim.to_right)
            .build()
    }

    fun navigateToPayment(
        navController: NavController,
        screeningId: Int,
        seats: Array<String>,
        totalPrice: Float,
    ) {
        val seatsJson = Uri.encode(seats.joinToString(","))
        val uri = Uri.parse("${DEEP_LINK_PAYMENT}$screeningId/$seatsJson/$totalPrice")
        navController.navigate(uri, Animations.defaultAnimations)
    }

    fun navigateToMovieDetail(navController: NavController, movieId: Int) {
        val uri = Uri.parse("${DEEP_LINK_MOVIE_DETAIL}$movieId")
        navController.navigate(uri, Animations.defaultAnimations)
    }

    fun navigateToSeatSelection(
        navController: NavController,
        screeningId: Int,
        ticketPrice: Float,
    ) {
        val uri = Uri.parse("${DEEP_LINK_SEAT}$screeningId/$ticketPrice")
        navController.navigate(uri, Animations.defaultAnimations)
    }

    fun navigateToLoginGraph(navController: NavController) {
        val navGraphId = navController.graph.id

        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.from_center_to_expand)
            .setExitAnim(R.anim.from_center_to_expand)
            .setPopEnterAnim(R.anim.from_center_to_expand)
            .setPopExitAnim(R.anim.from_center_to_expand)
            .setPopUpTo(navGraphId, true)
            .build()
        navController.navigate(Uri.parse(DEEP_LINK_LOGIN_GRAPH), navOptions)
    }

    fun navigateToMovieQuizGraph(navController: NavController) {
        navController.navigate(Uri.parse(DEEP_LINK_MOVIE_QUIZ_GRAPH), Animations.defaultAnimations)
    }

    fun navigateToRegisterGraph(navController: NavController) {
        navController.navigate(Uri.parse(DEEP_LINK_REGISTER_GRAPH), Animations.defaultAnimations)
    }

    fun navigateToHomeGraph(navController: NavController) {

        val navGraphId = navController.graph.id

        val navOptions = NavOptions.Builder()
            .setPopUpTo(navGraphId, true)
            .build()

        navController.navigate(Uri.parse(DEEP_LINK_HOME_GRAPH), navOptions)
    }

    fun navigateToShopGraph(navController: NavController) {

        val navGraphId = navController.graph.id

        val navOptions = NavOptions.Builder()
            .setPopUpTo(navGraphId, true)
            .build()

        navController.navigate(Uri.parse(DEEP_LINK_SHOP_GRAPH), navOptions)
    }

}