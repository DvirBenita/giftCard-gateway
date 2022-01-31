package io.wetake.backend.gateways.giftcardgateway.service;

import io.wetake.backend.gateways.giftcardgateway.client.GiftCardClient;
import io.wetake.backend.gateways.giftcardgateway.model.GiftCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GiftCardService {
  @Autowired private ApplicationContext context;

  public boolean addGiftCard(String privateId, String userId, float amount) {
    GiftCardClient client = (GiftCardClient) this.context.getBean("giftCardClient");

    Boolean ans = false;
    try {
      ans = client.addGiftCard(privateId, userId, amount);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return ans;
  }

  public boolean deleteGiftCard(String privateId) {
    GiftCardClient client = (GiftCardClient) this.context.getBean("giftCardClient");

    Boolean ans = false;
    try {
      ans = client.deleteGiftCard(privateId);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return ans;
  }

  public GiftCard getGiftCard(String privateId) {
    GiftCardClient client = (GiftCardClient) this.context.getBean("giftCardClient");

    GiftCard ans = null;
    try {
      ans = client.getGiftCard(privateId);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return ans;
  }

  public Iterable<GiftCard> getAllGiftCardsByUserId(String userId) {
    GiftCardClient client = (GiftCardClient) this.context.getBean("giftCardClient");

    List<GiftCard> ans = null;
    try {
      ans = client.getAllGiftCardsByUserId(userId);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return ans;
  }
}
