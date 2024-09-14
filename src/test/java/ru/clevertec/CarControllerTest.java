package ru.clevertec;

import ru.clevertec.model.Car;
import ru.clevertec.service.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Autowired
    private ObjectMapper objectMapper;

    @ParameterizedTest
    @CsvSource({
            "Toyota, Corolla, 2020",
            "Honda, Civic, 2019"
    })
    void getCarByMake_ShouldReturnCar(String make, String model, int year) throws Exception {
        Car car = new Car();
        car.setId(1L);
        car.setMake(make);
        car.setModel(model);
        car.setYear(year);

        when(carService.getCarById(1L)).thenReturn(car);

        mockMvc.perform(get("/cars/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.make").value(make))
                .andExpect(jsonPath("$.model").value(model))
                .andExpect(jsonPath("$.year").value(year));
    }

    @Test
    void getCarById_ShouldReturnCar() throws Exception {
        Car car = new Car();
        car.setId(1L);
        car.setMake("Toyota");
        car.setModel("Corolla");
        car.setYear(2020);

        when(carService.getCarById(1L)).thenReturn(car);

        mockMvc.perform(get("/cars/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.make").value("Toyota"))
                .andExpect(jsonPath("$.model").value("Corolla"))
                .andExpect(jsonPath("$.year").value(2020));
    }

    @Test
    void getAllCars_ShouldReturnCarList() throws Exception {
        Car car1 = new Car();
        car1.setId(1L);
        car1.setMake("Toyota");
        car1.setModel("Corolla");
        car1.setYear(2020);

        Car car2 = new Car();
        car2.setId(2L);
        car2.setMake("Honda");
        car2.setModel("Civic");
        car2.setYear(2019);

        List<Car> cars = List.of(car1, car2);

        when(carService.getAllCars()).thenReturn(cars);

        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].make").value("Toyota"))
                .andExpect(jsonPath("$[1].make").value("Honda"));
    }

    @Test
    void createCar_ShouldReturnCreatedCar() throws Exception {
        Car car = new Car();
        car.setMake("Toyota");
        car.setModel("Corolla");
        car.setYear(2020);

        when(carService.createCar(any(Car.class))).thenReturn(car);

        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.make").value("Toyota"))
                .andExpect(jsonPath("$.model").value("Corolla"))
                .andExpect(jsonPath("$.year").value(2020));
    }

    @Test
    void updateCar_ShouldReturnUpdatedCar() throws Exception {
        Car car = new Car();
        car.setMake("Toyota");
        car.setModel("Corolla");
        car.setYear(2020);

        when(carService.updateCar(eq(1L), any(Car.class))).thenReturn(car);

        mockMvc.perform(put("/cars/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.make").value("Toyota"))
                .andExpect(jsonPath("$.model").value("Corolla"))
                .andExpect(jsonPath("$.year").value(2020));
    }

    @Test
    void deleteCar_ShouldReturnNoContent() throws Exception {
        doNothing().when(carService).deleteCar(1L);

        mockMvc.perform(delete("/cars/1"))
                .andExpect(status().isNoContent());
    }
}
