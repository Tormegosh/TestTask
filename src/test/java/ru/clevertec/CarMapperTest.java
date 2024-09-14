package ru.clevertec;

import ru.clevertec.model.Car;
import ru.clevertec.model.CarDto;
import ru.clevertec.mapper.CarMapper;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CarMapperTest {

    private final CarMapper carMapper = new CarMapper();

    @Test
    void shouldMapCarToCarDto() {
        Car car = new Car();
        car.setId(1L);
        car.setMake("Toyota");
        car.setModel("Corolla");
        car.setYear(2020);

        CarDto carDto = carMapper.toDto(car);

        assertThat(carDto).isNotNull();
        assertThat(carDto.getId()).isEqualTo(car.getId());
        assertThat(carDto.getMake()).isEqualTo(car.getMake());
        assertThat(carDto.getModel()).isEqualTo(car.getModel());
        assertThat(carDto.getYear()).isEqualTo(car.getYear());
    }

    @Test
    void shouldMapCarDtoToCar() {
        CarDto carDto = new CarDto();
        carDto.setId(1L);
        carDto.setMake("Toyota");
        carDto.setModel("Corolla");
        carDto.setYear(2020);

        Car car = carMapper.toEntity(carDto);

        assertThat(car).isNotNull();
        assertThat(car.getId()).isEqualTo(carDto.getId());
        assertThat(car.getMake()).isEqualTo(carDto.getMake());
        assertThat(car.getModel()).isEqualTo(carDto.getModel());
        assertThat(car.getYear()).isEqualTo(carDto.getYear());
    }
}
