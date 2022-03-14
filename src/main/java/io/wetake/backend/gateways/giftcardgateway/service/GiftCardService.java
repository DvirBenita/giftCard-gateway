package io.wetake.backend.gateways.giftcardgateway.service;

import io.wetake.backend.gateways.giftcardgateway.client.GiftCardClient;
import io.wetake.backend.gateways.giftcardgateway.data.FileObjectStorage;
import io.wetake.backend.gateways.giftcardgateway.model.ColorAndAmountDto;
import io.wetake.backend.gateways.giftcardgateway.model.GiftCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class GiftCardService {
  public static String giftCardServicePort;
  public static String storageGatewayPort;
  public static String url = "";
  @Autowired ConfigService configService;
  @Autowired private ApplicationContext context;
  @Autowired private RestTemplate restTemplate;

  // To convert file to byte array
  private static byte[] toByteArray(File file) throws IOException {

    // Creating an object of FileInputStream to
    // read from a file
    FileInputStream fl = new FileInputStream(file);

    // Now creating byte array of same length as file
    byte[] arr = new byte[(int) file.length()];

    // Reading file content to byte array
    // using standard read() method
    fl.read(arr);

    // lastly closing an instance of file input stream
    // to avoid memory leakage
    fl.close();

    // Returning above byte array
    return arr;
  }

  public GiftCard addGiftCard(String privateId, String userId, float amount, GiftCard.Color color) {
    GiftCardClient client = (GiftCardClient) this.context.getBean("giftCardClient");

    File giftcard = changeAmountAndColor(amount, color);

    // upload giftcard via storage gateway
    upload(giftcard);

    GiftCard ans = null;
    try {
      ans = client.addGiftCard(privateId, userId, amount, color, giftcard.getName());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return ans;
  }

  private File changeAmountAndColor(float amount, GiftCard.Color color) {
    try {
      giftCardServicePort = this.configService.getByKey("giftCardServicePort");
    } catch (IOException e) {
      e.printStackTrace();
    }
    url = "http://localhost:" + giftCardServicePort + "/giftCard";
    GiftCardClient client = (GiftCardClient) this.context.getBean("giftCardClient");
    System.out.println("THE URL IS - " + url);
    File giftcard =
        restTemplate.postForObject(
            url + "/change", new ColorAndAmountDto(amount, color), File.class);
    return giftcard;
  }

  private void upload(File giftcard) {
    FileObjectStorage fileObjectStorage = new FileObjectStorage();
    try {
      fileObjectStorage.setFile(toByteArray(giftcard));
    } catch (IOException e) {
      e.printStackTrace();
    }
    fileObjectStorage.setName(giftcard.getName());
    fileObjectStorage.setTimestamp(System.currentTimeMillis());
    fileObjectStorage.setMimeType("png");
    try {
      storageGatewayPort = this.configService.getByKey("storageGatewayPort");
    } catch (IOException e) {
      e.printStackTrace();
    }
    url = "http://localhost:" + storageGatewayPort + "/storage";
    String foo = restTemplate.postForObject(url + "/upload", fileObjectStorage, String.class);
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

  public String editGiftCard(GiftCard giftCard) {

    GiftCardClient client = (GiftCardClient) this.context.getBean("giftCardClient");

    String ans = "null";

    try {
      ans = client.editGiftCard(giftCard);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return ans;
  }
}
