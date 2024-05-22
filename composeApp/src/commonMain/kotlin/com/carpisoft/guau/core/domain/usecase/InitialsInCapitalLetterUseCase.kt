package com.carpisoft.guau.core.domain.usecase
class InitialsInCapitalLetterUseCase(){
    operator fun invoke(value:String):String
    {
        var result = ""
        if(value.isNotEmpty())
        {
            val split = value.split(" ")
            for(word in split.withIndex()) {
                if (word.index != 0) {
                    result += " "
                }
                result += "${word.value.replaceFirstChar { it.uppercase() }}"
            }
        }
        return result
    }
}