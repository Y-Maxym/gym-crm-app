package com.gym.crm.app.service.impl;

import com.gym.crm.app.entity.Training;
import com.gym.crm.app.entity.TrainingSearchFilter;
import com.gym.crm.app.exception.EntityValidationException;
import com.gym.crm.app.logging.MessageHelper;
import com.gym.crm.app.repository.TrainingRepository;
import com.gym.crm.app.service.common.EntityValidator;
import com.gym.crm.app.service.spectification.TraineeTrainingSpecification;
import com.gym.crm.app.service.spectification.TrainerTrainingSpecification;
import com.gym.crm.app.utils.EntityTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Optional;

import static com.gym.crm.app.util.Constants.ERROR_TRAINING_WITH_ID_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {

    @Mock
    private MessageHelper messageHelper;

    @Mock
    private EntityValidator entityValidator;

    @Mock
    private TrainingRepository repository;

    @InjectMocks
    private TrainingServiceImpl service;

    @Test
    @DisplayName("Test find training by id functionality")
    public void givenId_whenFindById_thenTrainingIsReturned() {
        // given
        Training expected = EntityTestData.getPersistedTrainingEmilyDavis();
        long id = expected.getId();

        doNothing().when(entityValidator).checkId(id);
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
        long id = 1L;
        String message = "Training with id %s not found".formatted(id);

        doNothing().when(entityValidator).checkId(id);
        given(repository.findById(id))
                .willReturn(Optional.empty());
        given(messageHelper.getMessage(ERROR_TRAINING_WITH_ID_NOT_FOUND, id))
                .willReturn(message);

        // when
        EntityValidationException ex = assertThrows(EntityValidationException.class, () -> service.findById(id));

        // then
        assertThat(ex.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("Test save training functionality")
    public void givenSaveTraining_whenSave_thenRepositoryIsCalled() {
        // given
        Training training = EntityTestData.getPersistedTrainingEmilyDavis();

        doNothing().when(entityValidator).checkEntity(training);

        // when
        service.save(training);

        // then
        verify(repository, only()).save(training);
    }


    @Test
    @DisplayName("Test find trainee trainings by criteria functionality")
    public void givenTraineeCriteria_whenFindTraineeTrainingByCriteria_thenRepositoryIsCalled() {
        // given
        String username = "username";
        LocalDate from = LocalDate.parse("2020-01-01");
        LocalDate to = LocalDate.parse("2020-01-01");
        String trainerName = "trainerName";
        String trainingType = "trainingType";

        TrainingSearchFilter searchFilter = TrainingSearchFilter.builder()
                .username(username)
                .from(from)
                .to(to)
                .profileName(trainerName)
                .trainingType(trainingType)
                .build();
        Specification<Training> specification = TraineeTrainingSpecification.findByCriteria(searchFilter);

        MockedStatic<TraineeTrainingSpecification> mockedStatic = mockStatic(TraineeTrainingSpecification.class);
        mockedStatic.when(() -> TraineeTrainingSpecification.findByCriteria(searchFilter))
                .thenReturn(specification);

        // when
        service.findTraineeTrainingByCriteria(searchFilter);

        // then
        verify(repository).findAll(specification);

        mockedStatic.close();
    }


    @Test
    @DisplayName("Test find trainings by criteria functionality")
    public void givenCriteria_whenFindByCriteria_thenRepositoryIsCalled() {
        // given
        String username = "username";
        LocalDate from = LocalDate.parse("2020-01-01");
        LocalDate to = LocalDate.parse("2020-01-01");
        String traineeName = "trainee";
        String trainingType = "trainingType";

        TrainingSearchFilter searchFilter = TrainingSearchFilter.builder()
                .username(username)
                .from(from)
                .to(to)
                .profileName(traineeName)
                .trainingType(trainingType)
                .build();
        Specification<Training> specification = TrainerTrainingSpecification.findByCriteria(searchFilter);

        MockedStatic<TrainerTrainingSpecification> mockedStatic = mockStatic(TrainerTrainingSpecification.class);
        mockedStatic.when(() -> TrainerTrainingSpecification.findByCriteria(searchFilter))
                .thenReturn(specification);

        // when
        service.findTrainerTrainingByCriteria(searchFilter);

        // then
        verify(repository).findAll(specification);

        mockedStatic.close();
    }
}
