package com.ampersand.domain.use_case

import com.ampersand.core.R
import com.ampersand.core.UiText
import com.ampersand.domain.model.AsteroidModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class CalculateCloseApproachDaysUseCase {

    operator fun invoke(asteroid: AsteroidModel): UiText {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val closeApproachDate = LocalDate.parse(asteroid.closeApproachDate, formatter)
        val currentDate = LocalDate.now()
        val remainingDays = ChronoUnit.DAYS.between(currentDate, closeApproachDate).toInt()
        return UiText.StringResource(R.string.remaining_days, arrayOf(remainingDays))
    }
}
