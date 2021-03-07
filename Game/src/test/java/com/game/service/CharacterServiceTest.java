package com.game.service;

import com.game.enums.RaceEnum;
import com.game.input.CharacterInput;
import com.game.model.CharacterEntity;
import com.game.repository.CharacterRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import java.math.BigInteger;
import java.time.LocalDateTime;



@RunWith(MockitoJUnitRunner.class)
public class CharacterServiceTest {
    @Mock
    CharacterRepository repository;
    @InjectMocks
    CharacterService service;
    @Test
    public void saveNewCharacter() {
        //given
        CharacterInput input = CharacterInput.builder()
                .attack(BigInteger.TEN)
                .characterGender("male")
                .hp(BigInteger.valueOf(100))
                .characterName("TestCharacter")
                .defense(BigInteger.valueOf(5))
                .race(RaceEnum.ELF)
                .dateOfCreation(LocalDateTime.now())
                .build();
        ArgumentCaptor<CharacterEntity>captor = ArgumentCaptor.forClass(CharacterEntity.class);
        given(repository.save(captor.capture())).willReturn(new CharacterEntity());
        //when
        CharacterEntity characterEntity = service.saveNewCharacter(input);
        //then
        CharacterEntity value = captor.getValue();
        assertThat(value.getAttack()).isEqualTo(BigInteger.TEN);
        assertThat(value.getHp()).isEqualTo(BigInteger.valueOf(100));
        assertThat(value.getDefense()).isEqualTo(BigInteger.valueOf(5));
        assertThat(value.getCharacterGender()).isEqualTo("male");
        assertThat(value.getCharacterName()).isEqualTo("TestCharacter");
        assertThat(value.getRace()).isEqualTo(RaceEnum.ELF);
        assertThat(characterEntity).isNotNull();
    }

  /*  @Test
    public void updateCharacter() {
        //given
        CharacterInput input = CharacterInput.builder()
                .attack(BigInteger.TEN)
                .characterGender("male")
                .hp(BigInteger.valueOf(100))
                .characterName("TestCharacter")
                .defense(BigInteger.valueOf(5))
                .race(RaceEnum.ELF)
                .dateOfCreation(LocalDateTime.now())
                .build();
        CharacterEntity entityToUpdate = CharacterEntity.builder()
                .attack(BigInteger.TEN)
                .characterGender("female")
                .hp(BigInteger.valueOf(50))
                .characterName("WrongTestCharacter")
                .defense(BigInteger.valueOf(10))
                .race(RaceEnum.HUMAN)
                .dateOfCreation(LocalDateTime.now())
                .build();
        ArgumentCaptor<CharacterEntity>captor = ArgumentCaptor.forClass(CharacterEntity.class);
        given(repository.save(captor.capture())).willReturn(entityToUpdate);
        //when
        service.updateCharacter(input, entityToUpdate);
        //then
        CharacterEntity value = captor.getValue();
        assertThat(value.getAttack()).isEqualTo(BigInteger.TEN);
        assertThat(value.getHp()).isEqualTo(BigInteger.valueOf(100));
        assertThat(value.getDefense()).isEqualTo(BigInteger.valueOf(5));
        assertThat(value.getCharacterGender()).isEqualTo("male");
        assertThat(value.getCharacterName()).isEqualTo("TestCharacter");
        assertThat(value.getRace()).isEqualTo(RaceEnum.ELF);
        assertThat(entityToUpdate).isNotNull();
    }*/

}