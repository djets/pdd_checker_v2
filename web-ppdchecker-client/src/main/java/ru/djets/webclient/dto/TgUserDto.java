package ru.djets.webclient.dto;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TgUserDto {

    String id, name, lastName;

    Long tgId;
}
