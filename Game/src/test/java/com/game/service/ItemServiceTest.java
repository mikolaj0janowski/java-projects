package com.game.service;

import com.game.enums.RaceEnum;
import com.game.input.CharacterInput;
import com.game.input.ItemInput;
import com.game.model.CharacterEntity;
import com.game.model.ItemEntity;
import com.game.repository.CharacterRepository;
import com.game.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigInteger;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {
    @Mock
    ItemRepository repository;
    @InjectMocks
    ItemService service;
    @Test
    public void saveNewItem() {
        //given
        ItemInput input = ItemInput.builder()
                .addHP(BigInteger.TEN)
                .addAttack(BigInteger.TEN)
                .addDefense(BigInteger.TEN)
                .name("TestItem")
                .build();
        ArgumentCaptor<ItemEntity> captor = ArgumentCaptor.forClass(ItemEntity.class);
        given(repository.save(captor.capture())).willReturn(new ItemEntity());
        //when
        ItemEntity characterEntity = service.saveNewItem(input);
        //then
        ItemEntity value = captor.getValue();
        assertThat(value.getAddHP()).isEqualTo(BigInteger.TEN);
        assertThat(value.getAddAttack()).isEqualTo(BigInteger.TEN);
        assertThat(value.getAddDefense()).isEqualTo(BigInteger.TEN);
        assertThat(value.getName()).isEqualTo("TestItem");
        assertThat(characterEntity).isNotNull();
    }
}
