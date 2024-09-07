package com.crm.gym.app.model.parser.implementation;

import com.crm.gym.app.exception.ParseException;
import com.crm.gym.app.model.entity.Trainer;
import com.crm.gym.app.util.ParseUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TrainerParserTest {

    @Mock
    private ParseUtils utils;

    @InjectMocks
    private TrainerParser parser;

    @Test
    @DisplayName("Test parse method with null input")
    @SuppressWarnings("all")
    public void givenNullInput_whenParse_thenThrowsNullPointerException() {
        // given

        // when & then
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    @DisplayName("Test parse with correct data functionality")
    public void givenCorrectInput_whenParse_thenSuccessfulReturn() {
        // given
        String input = "4,Emily,Davis,1,1";

        Long userId = 4L;
        Long trainerId = 1L;
        Long specializationId = 1L;

        given(utils.parseLong(userId.toString()))
                .willReturn(userId);

        given(utils.parseLong(trainerId.toString()))
                .willReturn(trainerId);

        given(utils.parseLong(specializationId.toString()))
                .willReturn(specializationId);


        // when
        Trainer actual = parser.parse(input);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(trainerId);
        assertThat(actual.getUserId()).isEqualTo(userId);
        assertThat(actual.getSpecializationId()).isEqualTo(specializationId);
    }

    @Test
    @DisplayName("Test parse with missing values functionality")
    public void givenMissingValuesInput_whenParse_thenNullValuesIsReturned() {
        // given
        String input = "4,Emily,Davis,,1";

        Long userId = 4L;
        Long specializationId = 1L;

        given(utils.parseLong(userId.toString()))
                .willReturn(userId);

        given(utils.parseLong(specializationId.toString()))
                .willReturn(specializationId);

        // when
        Trainer actual = parser.parse(input);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNull();
        assertThat(actual.getUserId()).isEqualTo(userId);
        assertThat(actual.getSpecializationId()).isEqualTo(specializationId);
    }

    @Test
    @DisplayName("Test parse with incorrect data functionality")
    public void givenIncorrectInput_whenParse_thenSuccessfulReturn() {
        // given
        String input = "4,Emily,Davis,1,spec";

        Long userId = 4L;
        Long trainerId = 1L;
        String specializationId = "spec";

        given(utils.parseLong(userId.toString()))
                .willReturn(userId);

        given(utils.parseLong(trainerId.toString()))
                .willReturn(trainerId);

        given(utils.parseLong(specializationId))
                .willThrow(new ParseException("Number is not a valid: %s".formatted(specializationId)));
        // when
        ParseException ex = assertThrows(ParseException.class, () -> parser.parse(input));

        // then
        assertThat(ex.getMessage()).isEqualTo("Number is not a valid: %s".formatted(specializationId));
    }

    @Test
    @DisplayName("Test parse with empty input data functionality")
    public void givenEmptyInput_whenParse_thenEmptyTrainerIsReturned() {
        // given
        String input = "";

        // when
        Trainer actual = parser.parse(input);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNull();
        assertThat(actual.getUserId()).isNull();
        assertThat(actual.getSpecializationId()).isNull();
    }

}