package io.wetake.backend.gateways.giftcardgateway.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GiftCard {

  private String privateId;
  private long timestamp;
  private Float amount;
  private String userId;
}
