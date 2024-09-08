package com.gym.crm.app.model.repository.implementation;

import com.gym.crm.app.model.entity.User;
import com.gym.crm.app.model.storage.Storage;
import com.gym.crm.app.utils.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserDaoImplTest {

    @Mock
    private Storage storage;

    @InjectMocks
    private UserDaoImpl repository;

    @Test
    @DisplayName("Test find user by id functionality")
    public void givenId_whenFindById_thenUserIsReturned() {
        // given
        User expected = DataUtils.getUserJohnDoe();
        Long id = expected.getId();

        given(storage.get(id, User.class))
                .willReturn(expected);

        // when
        Optional<User> actual = repository.findById(id);

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test find user by incorrect id functionality")
    public void givenIncorrectId_whenFindById_thenNullIsReturned() {
        // given
        Long id = 1L;

        given(storage.get(id, User.class))
                .willReturn(null);

        // when
        Optional<User> actual = repository.findById(id);

        // then
        assertThat(actual.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Test find all user functionality")
    public void givenUsers_whenFindAll_thenUsersIsReturned() {
        // given
        User user1 = DataUtils.getUserJohnDoe();
        User user2 = DataUtils.getUserJaneSmith();
        User user3 = DataUtils.getUserMichaelJohnson();

        List<User> expected = List.of(user1, user2, user3);

        given(storage.getAll(User.class))
                .willReturn(expected);

        // when
        List<User> actual = repository.findAll();

        // then
        assertThat(actual.size()).isEqualTo(expected.size());
        assertThat(actual).containsAll(expected);
    }

    @Test
    @DisplayName("Test save user functionality")
    public void givenUserToSave_whenSaveOrUpdateUser_thenStorageIsCalled() {
        // given
        User userToSave = spy(DataUtils.getUserJohnDoe());
        userToSave.setId(null);

        given(storage.put(anyLong(), eq(userToSave)))
                .willReturn(userToSave);

        // when
        User actual = repository.saveOrUpdate(userToSave);

        // then
        assertThat(actual.getId()).isNotNull();

        verify(userToSave, times(1)).setId(anyLong());
        verify(storage, only()).put(anyLong(), eq(userToSave));
    }

    @Test
    @DisplayName("Test update user functionality")
    public void givenUserToUpdate_whenSaveOrUpdateUser_thenStorageIsCalled() {
        // given
        User userToUpdate = spy(DataUtils.getUserJohnDoe());
        Long id = userToUpdate.getId();

        given(storage.put(id, userToUpdate))
                .willReturn(userToUpdate);

        // when
        User actual = repository.saveOrUpdate(userToUpdate);

        // then
        assertThat(actual).isEqualTo(userToUpdate);

        verify(userToUpdate, never()).setId(anyLong());
        verify(storage, only()).put(id, userToUpdate);
    }

    @Test
    @DisplayName("Test delete user by id functionality")
    public void givenId_whenDeleteById_thenStorageIsCalled() {
        // given
        Long id = 1L;

        doNothing().when(storage).remove(id, User.class);

        // when
        repository.deleteById(id);

        // then
        verify(storage, only()).remove(id, User.class);
    }
}