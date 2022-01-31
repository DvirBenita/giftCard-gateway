package io.wetake.backend.gateways.giftcardgateway.resolver;

import graphql.kickstart.tools.GraphQLMutationResolver;
import io.wetake.backend.gateways.giftcardgateway.service.GiftCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MutationResolver implements GraphQLMutationResolver {

  @Autowired GiftCardService giftCardService;

  public boolean addGiftCard(String privateId, String userId, float amount) {
    return giftCardService.addGiftCard(privateId, userId, amount);
  }

  public boolean deleteGiftCard(String privateId) {
    return giftCardService.deleteGiftCard(privateId);
  }
}
