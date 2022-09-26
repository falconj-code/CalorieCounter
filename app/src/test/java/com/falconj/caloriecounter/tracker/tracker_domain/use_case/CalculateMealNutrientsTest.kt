package com.falconj.caloriecounter.tracker.tracker_domain.use_case

import com.falconj.caloriecounter.core.domain.model.ActivityLevel
import com.falconj.caloriecounter.core.domain.model.Gender
import com.falconj.caloriecounter.core.domain.model.GoalType
import com.falconj.caloriecounter.core.domain.model.UserInfo
import com.falconj.caloriecounter.core.domain.preferences.Preferences
import com.falconj.caloriecounter.tracker.tracker_domain.model.MealType
import com.falconj.caloriecounter.tracker.tracker_domain.model.TrackedFood
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.random.Random

class   CalculateMealNutrientsTest {

    private lateinit var calculateMealNutrients: CalculateMealNutrients

    @Before
    fun setUp() {
        val preferences = mockk<Preferences>(relaxed = true)
        every { preferences.loadUserInfo() } returns UserInfo(
            gender = Gender.Male,
            age = 20,
            weight = 80f,
            height = 180,
            activityLevel = ActivityLevel.Medium,
            goalType = GoalType.KeepWeight,
            carbRatio = 0.4f,
            proteinRatio = 0.3f,
            fatRatio = 0.3f
        )
        calculateMealNutrients = CalculateMealNutrients(preferences)
    }

    @Test
    fun `Calories for breakfast properly calculated`() {
        val trackedFood = (1..30).map {
            TrackedFood(
                name = "name",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                mealType = MealType.fromString(
                    listOf("Breakfast", "Lunch", "Dinner", "Snacks").random()
                ),
                imageUrl = null,
                amount = 100,
                date = LocalDate.now(),
                calories = Random.nextInt(2000)
            )
        }

        val result = calculateMealNutrients(trackedFood)

        val breakfastCalories = result.mealNutrients.values
            .filter { it.mealType is MealType.Breakfast }
            .sumOf { it.calories }
        val expectedCalories = trackedFood
            .filter { it.mealType is MealType.Breakfast }
            .sumOf { it.calories }

        assertThat(breakfastCalories).isEqualTo(expectedCalories)
    }

    @Test
    fun `Protein for lunch properly calculated`() {
        val trackedFood = (1..30).map {
            TrackedFood(
                name = "name",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                mealType = MealType.fromString(
                    listOf("Breakfast", "Lunch", "Dinner", "Snacks").random()
                ),
                imageUrl = null,
                amount = 100,
                date = LocalDate.now(),
                calories = Random.nextInt(2000)
            )
        }

        val result = calculateMealNutrients(trackedFood)

        val lunchProtein = result.mealNutrients.values
            .filter { it.mealType is MealType.Lunch }
            .sumOf { it.calories }
        val expectedProtein = trackedFood
            .filter { it.mealType is MealType.Lunch }
            .sumOf { it.calories }
        assertThat(lunchProtein).isEqualTo(expectedProtein)
    }

    @Test
    fun `Carbs for dinner properly calculated`() {
        val trackedFood = (1..30).map {
            TrackedFood(
                name = "name",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                mealType = MealType.fromString(
                    listOf("Breakfast", "Lunch", "Dinner", "Snacks").random()
                ),
                imageUrl = null,
                amount = 100,
                date = LocalDate.now(),
                calories = Random.nextInt(2000)
            )
        }

        val result = calculateMealNutrients(trackedFood)

        val dinnerCarbs  = result.mealNutrients.values
            .filter { it.mealType is MealType.Dinner }
            .sumOf { it.carbs }
        val expectedCarbs = trackedFood
            .filter { it.mealType is MealType.Dinner }
            .sumOf { it.carbs }

        assertThat(dinnerCarbs ).isEqualTo(expectedCarbs)
    }

    @Test
    fun `Fat for snacks properly calculated`() {
        val trackedFood = (1..30).map {
            TrackedFood(
                name = "name",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                mealType = MealType.fromString(
                    listOf("Breakfast", "Lunch", "Dinner", "Snacks").random()
                ),
                imageUrl = null,
                amount = 100,
                date = LocalDate.now(),
                calories = Random.nextInt(2000)
            )
        }

        val result = calculateMealNutrients(trackedFood)

        val snackFat = result.mealNutrients.values
            .filter { it.mealType is MealType.Snack }
            .sumOf { it.calories }
        val expectedFat = trackedFood
            .filter { it.mealType is MealType.Snack }
            .sumOf { it.calories }

        assertThat(snackFat).isEqualTo(expectedFat)
    }
}

