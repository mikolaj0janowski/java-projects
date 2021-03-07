package com.game.service;

import com.game.input.EquipmentInput;
import com.game.model.CharacterEntity;
import com.game.model.EquipmentEntity;
import com.game.model.ItemEntity;
import com.game.repository.CharacterRepository;
import com.game.repository.EquipmentRepository;
import com.game.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigInteger;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class EquipmentServiceTest {
    @Mock
    EquipmentRepository repository;
    @Mock
    ItemRepository itemRepository;
    @Mock
    CharacterRepository characterRepository;
    @InjectMocks
    EquipmentService service;

    @Test
    public void saveNewEquipment_ValidValuesGiven_ShouldExecuteProperly() {
        //given
        EquipmentInput input = EquipmentInput.builder()
                .ownerID(BigInteger.valueOf(1))
                .itemID(BigInteger.valueOf(1))
                .build();
        ArgumentCaptor<EquipmentEntity> captor = ArgumentCaptor.forClass(EquipmentEntity.class);
        given(repository.save(captor.capture())).willReturn(new EquipmentEntity());
        given(itemRepository.findById(any())).willReturn(Optional.of(new ItemEntity()));
        given(characterRepository.findById(any())).willReturn(Optional.of(new CharacterEntity()));
        //when
        EquipmentEntity equipmentEntity = service.saveNewEquipment(input);
        //then
        EquipmentEntity value = captor.getValue();
        assertThat(value.getOwnerID()).isEqualTo(BigInteger.valueOf(1));
        assertThat(value.getItemID()).isEqualTo(BigInteger.valueOf(1));
        assertThat(equipmentEntity).isNotNull();
    }

    @Test
    public void saveNewEquipment_NullOwnerIDGiven_ShouldThrowIllegalArgumentException() {
        //given
        EquipmentInput input = EquipmentInput.builder()
                .itemID(BigInteger.valueOf(1))
                .build();
        //when+then
        assertThatThrownBy(() -> service.saveNewEquipment(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("wrong input");
    }

    @Test
    public void saveNewEquipment_NullItemIDGiven_ShouldThrowIllegalArgumentException() {
        //given
        EquipmentInput input = EquipmentInput.builder()
                .ownerID(BigInteger.valueOf(1))
                .build();
        //when+then
        assertThatThrownBy(() -> service.saveNewEquipment(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("wrong input");
    }

    @Test
    public void saveNewEquipment_EmptyResponseFromBothRepositoriesGiven_ShouldThrowIllegalArgumentException() {
        //given
        EquipmentInput input = EquipmentInput.builder()
                .ownerID(BigInteger.valueOf(1))
                .itemID(BigInteger.valueOf(1))
                .build();
        //when+then
        assertThatThrownBy(() -> service.saveNewEquipment(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("wrong input");
    }

    @Test
    public void saveNewEquipment_EmptyResponseFromItemRepositoryGiven_ShouldThrowIllegalArgumentException() {
        //given
        EquipmentInput input = EquipmentInput.builder()
                .ownerID(BigInteger.valueOf(1))
                .itemID(BigInteger.valueOf(1))
                .build();
        given(characterRepository.findById(any())).willReturn(Optional.of(new CharacterEntity()));
        //when+then
        assertThatThrownBy(() -> service.saveNewEquipment(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("wrong input");
    }

    @Test
    public void saveNewEquipment_EmptyResponseFromCharacterRepositoryGiven_ShouldThrowIllegalArgumentException() {
        //given
        EquipmentInput input = EquipmentInput.builder()
                .ownerID(BigInteger.valueOf(1))
                .itemID(BigInteger.valueOf(1))
                .build();
        given(itemRepository.findById(any())).willReturn(Optional.of(new ItemEntity()));
        //when+then
        assertThatThrownBy(() -> service.saveNewEquipment(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("wrong input");
    }

    @Test
    public void updateEntity() {
        //given
        EquipmentInput input = EquipmentInput.builder()
                .ownerID(BigInteger.valueOf(1))
                .itemID(BigInteger.valueOf(1))
                .build();
        EquipmentEntity entityToUpdate = EquipmentEntity.builder()
                .ownerID(BigInteger.valueOf(1))
                .itemID(BigInteger.valueOf(1))
                .build();
        ArgumentCaptor<EquipmentEntity> captor = ArgumentCaptor.forClass(EquipmentEntity.class);
        given(repository.save(captor.capture())).willReturn(entityToUpdate);
        //when
        service.updateEntity(input, entityToUpdate);
        //then
        EquipmentEntity value = captor.getValue();
        assertThat(value.getOwnerID()).isEqualTo(BigInteger.valueOf(1));
        assertThat(value.getItemID()).isEqualTo(BigInteger.valueOf(1));
        assertThat(entityToUpdate).isNotNull();
    }
}
