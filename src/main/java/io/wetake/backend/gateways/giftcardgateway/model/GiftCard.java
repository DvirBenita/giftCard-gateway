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
  private float amount;
  private String userId;
  private Color color;
  private String token;
  private String url;
  private boolean used;

  public enum Color {
    Gold
  }
}
