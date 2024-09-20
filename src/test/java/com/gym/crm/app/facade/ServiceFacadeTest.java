package com.gym.crm.app.facade;

import com.gym.crm.app.entity.Trainee;
import com.gym.crm.app.entity.Trainer;
import com.gym.crm.app.entity.Training;
import com.gym.crm.app.entity.User;
import com.gym.crm.app.service.TraineeService;
import com.gym.crm.app.service.TrainerService;
import com.gym.crm.app.service.TrainingService;
import com.gym.crm.app.service.UserService;
import com.gym.crm.app.utils.EntityTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ServiceFacadeTest {

    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TrainingService trainingService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ServiceFacade serviceFacade;

    @Test
    @DisplayName("Test find trainee by id functionality")
    void givenTraineeId_whenFindTraineeById_thenReturnTrainee() {
        // given
        long id = 1L;
        Trainee expectedTrainee = EntityTestData.getPersistedTraineeJohnDoe();

        given(traineeService.findById(id))
                .willReturn(expectedTrainee);

        // when
        Trainee trainee = serviceFacade.findTraineeById(id);

        // then
        assertThat(trainee).isEqualTo(expectedTrainee);
    }

    @Test
    @DisplayName("Test save trainee functionality")
    void givenTrainee_whenSaveTrainee_thenDelegateSaveToService() {
        // given
        Trainee trainee = EntityTestData.getPersistedTraineeJohnDoe();

        // when
        serviceFacade.saveTrainee(trainee);

        // then
        verify(traineeService).save(trainee);
    }

    @Test
    @DisplayName("Test update trainee functionality")
    void givenTrainee_whenUpdateTrainee_thenDelegateUpdateToService() {
        // given
        Trainee trainee = EntityTestData.getPersistedTraineeJohnDoe();

        // when
        serviceFacade.updateTrainee(trainee);

        // then
        verify(traineeService).update(trainee);
    }

    @Test
    @DisplayName("Test delete trainee by id functionality")
    void givenTraineeId_whenDeleteTraineeById_thenDelegateDeleteToService() {
        // given
        long id = 1L;

        // when
        serviceFacade.deleteTraineeById(id);

        // then
        verify(traineeService).deleteById(id);
    }

    @Test
    @DisplayName("Test find trainer by id functionality")
    void givenTrainerId_whenFindTrainerById_thenReturnTrainer() {
        // given
        long id = 1L;
        Trainer expectedTrainer = EntityTestData.getPersistedTrainerDavidBrown();
        given(trainerService.findById(id))
                .willReturn(expectedTrainer);

        // when
        Trainer trainer = serviceFacade.findTrainerById(id);

        // then
        assertThat(trainer).isEqualTo(expectedTrainer);
    }

    @Test
    @DisplayName("Test save trainer functionality")
    void givenTrainer_whenSaveTrainer_thenDelegateSaveToService() {
        // given
        Trainer trainer = EntityTestData.getPersistedTrainerDavidBrown();

        // when
        serviceFacade.saveTrainer(trainer);

        // then
        verify(trainerService).save(trainer);
    }

    @Test
    @DisplayName("Test update trainer functionality")
    void givenTrainer_whenUpdateTrainer_thenDelegateUpdateToService() {
        // given
        Trainer trainer = EntityTestData.getPersistedTrainerDavidBrown();

        // when
        serviceFacade.updateTrainer(trainer);

        // then
        verify(trainerService).update(trainer);
    }

    @Test
    @DisplayName("Test find training by id functionality")
    void givenTrainingId_whenFindTrainingById_thenReturnTraining() {
        // given
        long id = 1L;
        Training expectedTraining = EntityTestData.getPersistedTrainingDavidBrown();

        given(trainingService.findById(id))
                .willReturn(expectedTraining);

        // when
        Training training = serviceFacade.findTrainingById(id);

        // then
        assertThat(training).isEqualTo(expectedTraining);
    }

    @Test
    @DisplayName("Test save training functionality")
    void givenTraining_whenSaveTraining_thenDelegateSaveToService() {
        // given
        Training training = EntityTestData.getPersistedTrainingDavidBrown();

        // when
        serviceFacade.saveTraining(training);

        // then
        verify(trainingService).save(training);
    }

    @Test
    @DisplayName("Test find user by id functionality")
    void givenUserId_whenFindUserById_thenReturnUser() {
        // given
        long id = 1L;
        User expectedUser = EntityTestData.getPersistedUserJohnDoe();

        given(userService.findById(id))
                .willReturn(expectedUser);

        // when
        User user = serviceFacade.findUserById(id);

        // then
        assertThat(user).isEqualTo(expectedUser);
    }

    @Test
    @DisplayName("Test save user functionality")
    void givenUser_whenSaveUser_thenDelegateSaveToService() {
        // given
        User user = EntityTestData.getPersistedUserJohnDoe();

        // when
        serviceFacade.saveUser(user);

        // then
        verify(userService).save(user);
    }

    @Test
    @DisplayName("Test update user functionality")
    void givenUser_whenUpdateUser_thenDelegateUpdateToService() {
        // given
        User user = EntityTestData.getPersistedUserJohnDoe();

        // when
        serviceFacade.updateUser(user);

        // then
        verify(userService).update(user);
    }

    @Test
    @DisplayName("Test delete user by id functionality")
    void givenUserId_whenDeleteUserById_thenDelegateDeleteToServiceById() {
        // given
        long id = 1L;

        // when
        serviceFacade.deleteUserById(id);

        // then
        verify(userService).deleteById(id);
    }
}
