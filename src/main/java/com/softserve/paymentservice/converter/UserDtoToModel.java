package com.softserve.paymentservice.converter;

import com.softserve.paymentservice.dto.UserDto;
import com.softserve.paymentservice.model.User;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;
@Component
public class UserDtoToModel implements Converter<UserDto, User>{
    @Override
    public User convert(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getUserUUID());
        return user;
    }
}
