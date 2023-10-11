package pet.ui.screens

import androidx.compose.runtime.Composable
import com.carpisoft.guau.SharedRes
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun GetGenderPet(gender: String) :String{
    return when (gender) {
        "Male" -> {
            stringResource(SharedRes.strings.male)
        }

        "Female" -> {
            stringResource(SharedRes.strings.female)
        }

        else -> {
            stringResource(SharedRes.strings.unknown)
        }
    }
}