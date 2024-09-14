package ru.clevertec;

import ru.clevertec.model.Car;
import ru.clevertec.repository.CarRepository;
import ru.clevertec.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @Captor
    private ArgumentCaptor<Car> carCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveCar() {
        Car car = new Car();
        car.setMake("Toyota");

        carService.createCar(car);

        verify(carRepository).save(carCaptor.capture());
        assertThat(carCaptor.getValue().getMake()).isEqualTo("Toyota");
    }

    @Test
    void shouldReturnCarById() {
        Car car = new Car();
        car.setId(1L);
        car.setMake("Toyota");
        car.setModel("Corolla");
        car.setYear(2020);

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        Car foundCar = carService.getCarById(1L);

        assertThat(foundCar).isNotNull();
        assertThat(foundCar.getMake()).isEqualTo("Toyota");
    }
}
