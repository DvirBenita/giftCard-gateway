package io.wetake.backend.gateways.giftcardgateway.resolver;

import graphql.kickstart.tools.GraphQLQueryResolver;
import io.wetake.backend.gateways.giftcardgateway.model.GiftCard;
import io.wetake.backend.gateways.giftcardgateway.service.GiftCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryResolver implements GraphQLQueryResolver {

  @Autowired GiftCardService giftCardService;

  public GiftCard getGiftCard(String privateId) {
    return giftCardService.getGiftCard(privateId);
  }

  public Iterable<GiftCard> getAllGiftCardsByUserId(String userId) {
    return giftCardService.getAllGiftCardsByUserId(userId);
  }
}
