package io.wetake.backend.gateways.giftcardgateway.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class ColorAndAmountDto {
  float amount;
  GiftCard.Color color;
}
