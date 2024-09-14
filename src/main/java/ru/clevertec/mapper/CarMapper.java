package ru.clevertec.mapper;

import ru.clevertec.model.Car;
import ru.clevertec.model.CarDto;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    public CarDto toDto(Car car) {
        CarDto dto = new CarDto();
        dto.setId(car.getId());
        dto.setMake(car.getMake());
        dto.setModel(car.getModel());
        dto.setYear(car.getYear());
        return dto;
    }

    public Car toEntity(CarDto carDto) {
        Car car = new Car();
        car.setId(carDto.getId());
        car.setMake(carDto.getMake());
        car.setModel(carDto.getModel());
        car.setYear(carDto.getYear());
        return car;
    }
}
