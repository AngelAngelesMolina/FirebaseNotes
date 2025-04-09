package com.example.firebasenotes.presentation.views.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.firebasenotes.presentation.viewmodels.LoginViewModel
import kotlin.math.log


@Composable
fun TabsView(loginVm: LoginViewModel, navController: NavController, pad: PaddingValues) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Iniciar sesion", "Registrarse")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(pad)
    ) {
        TabRow(
            selectedTabIndex = selectedTab,
            contentColor = Color.Black,
            indicator = { tabPosition ->
                SecondaryIndicator(Modifier.tabIndicatorOffset(tabPosition[selectedTab]))
            }) {
            tabs.forEachIndexed { index, title ->
                Tab(selected = selectedTab == index, onClick = { selectedTab = index }, text = {
                    Text(text = title)
                })
            }
        }

        when (selectedTab) {
            0 -> LoginView(loginVm = loginVm, navController = navController)
            1 -> RegisterView(navController = navController, loginVm = loginVm)
        }
    }
}