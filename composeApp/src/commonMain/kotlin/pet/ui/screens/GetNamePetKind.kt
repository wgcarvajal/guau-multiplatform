package pet.ui.screens

import androidx.compose.runtime.Composable
import com.carpisoft.guau.SharedRes
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun GetNamePetKind (name: String):String{
    return when (name) {
        "Dog" -> {
            stringResource(SharedRes.strings.dog)
        }

        "Cat" -> {
            stringResource(SharedRes.strings.cat)
        }

        else -> {
            stringResource(SharedRes.strings.other)
        }
    }
}
