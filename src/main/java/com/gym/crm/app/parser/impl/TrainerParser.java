package com.gym.crm.app.parser.impl;

import com.gym.crm.app.entity.Trainer;
import com.gym.crm.app.parser.Parser;
import com.gym.crm.app.parser.ParserHelper;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Setter(onMethod_ = @Autowired)
public class TrainerParser implements Parser<String, Trainer> {

    private static final int SPECIALIZATION_ID_INDEX = 2;

    private static final String SPLIT_REGEX = ",";

    private ParserHelper utils;

    @Override
    public Trainer parse(@NonNull String inputLine) {
        String[] data = inputLine.split(SPLIT_REGEX);

        Long specializationId = parseValue(data, SPECIALIZATION_ID_INDEX, utils::parseLong);

        return Trainer.builder()
                .specializationId(specializationId)
                .build();
    }
}
