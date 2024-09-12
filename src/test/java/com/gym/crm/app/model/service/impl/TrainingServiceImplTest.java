package com.gym.crm.app.model.service.impl;

import com.gym.crm.app.exception.EntityException;
import com.gym.crm.app.model.entity.Training;
import com.gym.crm.app.model.repository.EntityDao;
import com.gym.crm.app.logging.MessageHelper;
import com.gym.crm.app.utils.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.gym.crm.app.util.Constants.ERROR_TRAINING_WITH_ID_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {

    @Mock
    private MessageHelper messageHelper;

    @Mock
    private EntityDao<Long, Training> repository;

    @InjectMocks
    private TrainingServiceImpl service;

    @Test
    @DisplayName("Test find training by id functionality")
    public void givenId_whenFindById_thenTrainingIsReturned() {
        // given
        Training expected = DataUtils.getTrainingEmilyDavisPersisted();
        Long id = expected.getId();

        given(repository.findById(id))
                .willReturn(Optional.of(expected));

        // when
        Training actual = service.findById(id);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test find training by incorrect id functionality")
    public void givenIncorrectId_whenFindById_thenExceptionIsThrown() {
        // given
        Long id = 1L;
        String message = "Training with id %s not found".formatted(id);

        given(repository.findById(id))
                .willReturn(Optional.empty());

        given(messageHelper.getMessage(ERROR_TRAINING_WITH_ID_NOT_FOUND, id))
                .willReturn(message);

        // when
        EntityException ex = assertThrows(EntityException.class, () -> service.findById(id));

        // then
        assertThat(ex.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("Test save training functionality")
    public void givenSaveTraining_whenSave_thenRepositoryIsCalled() {
        // given
        Training trainingToSave = DataUtils.getTrainingEmilyDavisPersisted();

        // when
        service.save(trainingToSave);

        // then
        verify(repository, only()).saveOrUpdate(trainingToSave);
    }

}