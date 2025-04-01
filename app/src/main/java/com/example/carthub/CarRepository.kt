package com.example.carthub



data class Car(
    val id: Int,
    val name: String,
    val price: Double,
    val imageResId: Int
)

object CarRepository {
    fun getCars(): List<Car> {
        return listOf(
            Car(1, "Hyundai Sonata 2021", 25000.0, R.drawable.hyundai_sonata),
            Car(2, "Toyota Camry 2022", 28000.0, R.drawable.toyota_camry),
            Car(3, "Honda Accord 2023", 30000.0, R.drawable.honda_accord),
            Car(4, "Tesla Model 3 2024", 45000.0, R.drawable.tesla_model3),
            Car(5, "BMW 3 Series 2021", 40000.0, R.drawable.bmw_3series),
            Car(6, "Mercedes-Benz C-Class 2022", 42000.0, R.drawable.mercedes_cclass),
            Car(7, "Audi A4 2023", 39000.0, R.drawable.audi_a4),
            Car(8, "Ford Mustang 2024", 55000.0, R.drawable.ford_mustang)
        )
    }
}
