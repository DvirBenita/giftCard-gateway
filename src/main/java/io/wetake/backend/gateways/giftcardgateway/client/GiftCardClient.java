package io.wetake.backend.gateways.giftcardgateway.client;

import com.google.gson.Gson;
import io.wetake.backend.gateways.giftcardgateway.data.GraphqlRequestBody;
import io.wetake.backend.gateways.giftcardgateway.data.GraphqlSchemaReaderUtil;
import io.wetake.backend.gateways.giftcardgateway.model.GiftCard;
import io.wetake.backend.gateways.giftcardgateway.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class GiftCardClient {

  public static String giftCardServicePort;
  public static String url;
  @Autowired private ConfigService configService;
  @Autowired private Gson gson;

  public Boolean addGiftCard(String privateId, String userId, float amount) throws IOException {
    giftCardServicePort = this.configService.getByKey("giftCardServicePort");
    url = "http://localhost:" + giftCardServicePort + "/graphql/";
    WebClient webClient = WebClient.builder().build();

    GraphqlRequestBody graphQLRequestBody = new GraphqlRequestBody();

    final String query = GraphqlSchemaReaderUtil.getSchemaFromFileName("addGiftCard");
    String variables = GraphqlSchemaReaderUtil.getSchemaFromFileName("variablesAddGiftCard");

    graphQLRequestBody.setQuery(query);
    variables = variables.replace("amountTemp", Float.toString(amount));
    variables = variables.replace("userIdTemp", userId);
    graphQLRequestBody.setVariables(variables.replace("privateIdTemp", privateId));

    JSONObject jo =
        new JSONObject(
            Objects.requireNonNull(
                webClient
                    .post()
                    .uri(url)
                    .bodyValue(graphQLRequestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block()));
    System.out.println(jo + "121212!!!!!!!!!!!!!!!!!!!!!!");
    JSONObject jo1 = new JSONObject(jo.get("data").toString());
    return jo1.getBoolean("addGiftCard");
  }

  public Boolean deleteGiftCard(String privateId) throws IOException {
    giftCardServicePort = this.configService.getByKey("giftCardServicePort");
    url = "http://localhost:" + giftCardServicePort + "/graphql/";
    WebClient webClient = WebClient.builder().build();

    GraphqlRequestBody graphQLRequestBody = new GraphqlRequestBody();

    final String query = GraphqlSchemaReaderUtil.getSchemaFromFileName("deleteGiftCard");
    String variables = GraphqlSchemaReaderUtil.getSchemaFromFileName("variablesDeleteGiftCard");

    graphQLRequestBody.setQuery(query);

    graphQLRequestBody.setVariables(variables.replace("privateIdTemp", privateId));

    JSONObject jo =
        new JSONObject(
            Objects.requireNonNull(
                webClient
                    .post()
                    .uri(url)
                    .bodyValue(graphQLRequestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block()));

    JSONObject jo1 = new JSONObject(jo.get("data").toString());
    return jo1.getBoolean("deleteGiftCard");
  }

  public GiftCard getGiftCard(String privateId) throws IOException {
    giftCardServicePort = this.configService.getByKey("giftCardServicePort");
    url = "http://localhost:" + giftCardServicePort + "/graphql/";
    WebClient webClient = WebClient.builder().build();

    GraphqlRequestBody graphQLRequestBody = new GraphqlRequestBody();

    final String query = GraphqlSchemaReaderUtil.getSchemaFromFileName("getGiftCard");
    String variables = GraphqlSchemaReaderUtil.getSchemaFromFileName("variablesGetGiftCard");

    graphQLRequestBody.setQuery(query);

    graphQLRequestBody.setVariables(variables.replace("privateIdTemp", privateId));

    JSONObject jo =
        new JSONObject(
            Objects.requireNonNull(
                webClient
                    .post()
                    .uri(url)
                    .bodyValue(graphQLRequestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block()));

    JSONObject jo1 = new JSONObject(jo.get("data").toString());
    return gson.fromJson(jo1.getJSONObject("getGiftCard").toString(), GiftCard.class);
  }

  public List<GiftCard> getAllGiftCardsByUserId(String userId) throws IOException {
    giftCardServicePort = this.configService.getByKey("giftCardServicePort");
    url = "http://localhost:" + giftCardServicePort + "/graphql/";
    WebClient webClient = WebClient.builder().build();

    GraphqlRequestBody graphQLRequestBody = new GraphqlRequestBody();

    final String query = GraphqlSchemaReaderUtil.getSchemaFromFileName("getAllGiftCardsByUserId");
    String variables =
        GraphqlSchemaReaderUtil.getSchemaFromFileName("variablesGetAllGiftCardsByUserId");

    graphQLRequestBody.setQuery(query);

    graphQLRequestBody.setVariables(variables.replace("userIdTemp", userId));

    JSONObject jo =
        new JSONObject(
            Objects.requireNonNull(
                webClient
                    .post()
                    .uri(url)
                    .bodyValue(graphQLRequestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block()));

    JSONObject jo1 = new JSONObject(jo.get("data").toString());
    JSONArray jo2 = jo1.getJSONArray("getAllGiftCardsByUserId");
    return (List<GiftCard>) buildList(jo2);
  }

  private Iterable<GiftCard> buildList(JSONArray jo2) {
    ArrayList<GiftCard> arrayList = new ArrayList<>();
    for (int i = 0; i < jo2.length(); i++) {
      JSONObject obj = jo2.getJSONObject(i);
      arrayList.add(gson.fromJson(obj.toString(), GiftCard.class));
    }
    return arrayList;
  }
}
