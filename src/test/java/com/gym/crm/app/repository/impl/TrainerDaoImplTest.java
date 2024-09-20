package com.gym.crm.app.repository.impl;

import com.gym.crm.app.entity.Trainer;
import com.gym.crm.app.storage.Storage;
import com.gym.crm.app.utils.EntityTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DirtiesContext
class TrainerDaoImplTest {

    @Mock
    private Storage storage;

    @InjectMocks
    private TrainerDaoImpl repository;

    @Test
    @DisplayName("Test find trainer by id functionality")
    public void givenId_whenFindById_thenTrainerIsReturned() {
        // given
        Trainer expected = EntityTestData.getPersistedTrainerEmilyDavis();
        long id = expected.getId();

        given(storage.get(id, Trainer.class))
                .willReturn(expected);

        // when
        Optional<Trainer> actual = repository.findById(id);

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test find trainer by incorrect id functionality")
    public void givenIncorrectId_whenFindById_thenNullIsReturned() {
        // given
        long id = 1L;

        given(storage.get(id, Trainer.class))
                .willReturn(null);

        // when
        Optional<Trainer> actual = repository.findById(id);

        // then
        assertThat(actual.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Test find all trainer functionality")
    public void givenTrainers_whenFindAll_thenTrainersIsReturned() {
        // given
        Trainer trainer1 = EntityTestData.getPersistedTrainerDavidBrown();
        Trainer trainer2 = EntityTestData.getPersistedTrainerEmilyDavis();

        List<Trainer> expected = List.of(trainer1, trainer2);

        given(storage.getAll(Trainer.class))
                .willReturn(expected);

        // when
        List<Trainer> actual = repository.findAll();

        // then
        assertThat(actual.size()).isEqualTo(expected.size());
        assertThat(actual).containsAll(expected);
    }

    @Test
    @DisplayName("Test save trainer without id functionality")
    public void givenTrainer_whenSaveTrainer_thenStorageIsCalled() {
        // given
        Trainer trainer = spy(EntityTestData.getTransientTrainerEmilyDavis());
        Trainer persisted = EntityTestData.getPersistedTrainerEmilyDavis();

        given(storage.put(anyLong(), any(Trainer.class)))
                .willReturn(persisted);

        // when
        Trainer actual = repository.save(trainer);

        // then
        assertThat(actual.getId()).isNotNull();
        assertThat(actual).isEqualTo(persisted);

        verify(trainer).toBuilder();
        verify(storage, only()).put(anyLong(), any(Trainer.class));
    }

    @Test
    @DisplayName("Test save trainer with id functionality")
    public void givenTrainerWithId_whenSaveTrainer_thenStorageIsCalled() {
        // given
        Trainer trainer = spy(EntityTestData.getPersistedTrainerEmilyDavis());
        long id = trainer.getId();

        given(storage.put(id, trainer))
                .willReturn(trainer);

        // when
        Trainer actual = repository.save(trainer);

        // then
        assertThat(actual).isEqualTo(trainer);

        verify(trainer, never()).toBuilder();
        verify(storage, only()).put(id, trainer);
    }

    @Test
    @DisplayName("Test update trainer functionality")
    public void givenTrainer_whenUpdateTrainer_thenStorageIsCalled() {
        // given
        Trainer trainer = spy(EntityTestData.getPersistedTrainerEmilyDavis());
        long id = trainer.getId();

        given(storage.put(id, trainer))
                .willReturn(trainer);

        // when
        Trainer actual = repository.update(trainer);

        // then
        assertThat(actual).isEqualTo(trainer);

        verify(storage, only()).put(id, trainer);
    }


    @Test
    @DisplayName("Test delete trainer by id functionality")
    public void givenId_whenDeleteById_thenStorageIsCalled() {
        // given
        long id = 1L;

        doNothing().when(storage).remove(id, Trainer.class);

        // when
        repository.deleteById(id);

        // then
        verify(storage, only()).remove(id, Trainer.class);
    }
}
