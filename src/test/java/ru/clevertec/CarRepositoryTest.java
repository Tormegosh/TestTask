package ru.clevertec;

import ru.clevertec.model.Car;
import ru.clevertec.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Test
    void shouldSaveAndFindCar() {
        Car car = new Car();
        car.setMake("Toyota");
        car.setModel("Corolla");
        car.setYear(2020);

        carRepository.save(car);

        Car foundCar = carRepository.findById(car.getId()).orElse(null);
        assertThat(foundCar).isNotNull();
        assertThat(foundCar.getMake()).isEqualTo("Toyota");
    }
}
