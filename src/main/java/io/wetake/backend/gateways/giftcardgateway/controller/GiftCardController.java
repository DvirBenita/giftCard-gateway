package io.wetake.backend.gateways.giftcardgateway.controller;

import io.wetake.backend.gateways.giftcardgateway.model.GiftCard;
import io.wetake.backend.gateways.giftcardgateway.service.GiftCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/giftCard")
public class GiftCardController {
  @Autowired GiftCardService giftCardService;

  @PostMapping("/edit")
  public String editGiftCard(@RequestBody GiftCard giftCard) {
    return giftCardService.editGiftCard(giftCard);
  }
}
