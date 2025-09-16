import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coursework.dto.CurrentWeather

@Composable
fun WeatherCurrentCard(
    current: CurrentWeather
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${current.temperature_2m}°C",
                    style = MaterialTheme.typography.displaySmall,
                    fontSize = 50.sp,
                )
                Text(
                    text = "Ощущается как ${current.apparent_temperature}°C",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Влажность: ${current.relative_humidity_2m} %", fontSize = 14.sp)
                Text("Давление: ${current.pressure_msl} hPa", fontSize = 14.sp)
            }

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Облачность: ${current.cloud_cover} %", fontSize = 14.sp)
                Text("Осадки: ${current.precipitation} мм", fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Ветер:", style = MaterialTheme.typography.titleMedium, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Скорость: ${current.wind_speed_10m} км/ч", fontSize = 14.sp)
            Text("Направление: ${current.wind_direction_10m}°", fontSize = 14.sp)
            Text("Порывы: ${current.wind_gusts_10m} км/ч", fontSize = 14.sp)

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
