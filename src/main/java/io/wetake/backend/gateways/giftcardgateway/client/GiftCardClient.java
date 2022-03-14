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
import org.springframework.web.client.RestTemplate;
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
  @Autowired private RestTemplate restTemplate;
  // add gift card
  public GiftCard addGiftCard(
      String privateId, String userId, float amount, GiftCard.Color color, String giftCardName)
      throws IOException {
    giftCardServicePort = this.configService.getByKey("giftCardServicePort");
    url = "http://localhost:" + giftCardServicePort + "/graphql/";
    WebClient webClient = WebClient.builder().build();

    GraphqlRequestBody graphQLRequestBody = new GraphqlRequestBody();
    // get the query
    final String query = GraphqlSchemaReaderUtil.getSchemaFromFileName("addGiftCard");
    // get query variables
    String variables = GraphqlSchemaReaderUtil.getSchemaFromFileName("variablesAddGiftCard");

    // here set query
    graphQLRequestBody.setQuery(query);

    // here set variables
    variables = variables.replace("giftcardNameTemp", giftCardName);
    variables = variables.replace("amountTemp", Float.toString(amount));
    variables = variables.replace("colorTemp", color.toString());
    variables = variables.replace("userIdTemp", userId);
    graphQLRequestBody.setVariables(variables.replace("privateIdTemp", privateId));
    // send the request to service
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
    // extracts the answer and convert to object
    JSONObject jo1 = new JSONObject(jo.get("data").toString());
    GiftCard ans = gson.fromJson(jo1.getJSONObject("addGiftCard").toString(), GiftCard.class);
    return ans;
  }
  // delete gift card
  public Boolean deleteGiftCard(String privateId) throws IOException {
    giftCardServicePort = this.configService.getByKey("giftCardServicePort");
    url = "http://localhost:" + giftCardServicePort + "/graphql/";
    WebClient webClient = WebClient.builder().build();

    GraphqlRequestBody graphQLRequestBody = new GraphqlRequestBody();
    // get the query
    final String query = GraphqlSchemaReaderUtil.getSchemaFromFileName("deleteGiftCard");
    // get query variables
    String variables = GraphqlSchemaReaderUtil.getSchemaFromFileName("variablesDeleteGiftCard");
    // here set query
    graphQLRequestBody.setQuery(query);
    //  set variables
    graphQLRequestBody.setVariables(variables.replace("privateIdTemp", privateId));
    // send the request to service
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
    // extracts the answer
    JSONObject jo1 = new JSONObject(jo.get("data").toString());
    return jo1.getBoolean("deleteGiftCard");
  }
  // get gift card
  public GiftCard getGiftCard(String privateId) throws IOException {
    giftCardServicePort = this.configService.getByKey("giftCardServicePort");
    url = "http://localhost:" + giftCardServicePort + "/graphql/";
    WebClient webClient = WebClient.builder().build();

    GraphqlRequestBody graphQLRequestBody = new GraphqlRequestBody();
    // get the query
    final String query = GraphqlSchemaReaderUtil.getSchemaFromFileName("getGiftCard");
    // get query variables
    String variables = GraphqlSchemaReaderUtil.getSchemaFromFileName("variablesGetGiftCard");
    // here set query
    graphQLRequestBody.setQuery(query);
    //  set variables
    graphQLRequestBody.setVariables(variables.replace("privateIdTemp", privateId));
    // send the request to service
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
    // extracts the answer and convert to GiftCard
    JSONObject jo1 = new JSONObject(jo.get("data").toString());
    return gson.fromJson(jo1.getJSONObject("getGiftCard").toString(), GiftCard.class);
  }
  // get all gift cards by UserId
  public List<GiftCard> getAllGiftCardsByUserId(String userId) throws IOException {
    giftCardServicePort = this.configService.getByKey("giftCardServicePort");
    url = "http://localhost:" + giftCardServicePort + "/graphql/";
    WebClient webClient = WebClient.builder().build();

    GraphqlRequestBody graphQLRequestBody = new GraphqlRequestBody();
    // get the query
    final String query = GraphqlSchemaReaderUtil.getSchemaFromFileName("getAllGiftCardsByUserId");
    // get query variables
    String variables =
        GraphqlSchemaReaderUtil.getSchemaFromFileName("variablesGetAllGiftCardsByUserId");
    // here set query
    graphQLRequestBody.setQuery(query);
    //  set variables
    graphQLRequestBody.setVariables(variables.replace("userIdTemp", userId));
    // send the request to service
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
    // extracts the answer and convert to List<GiftCard>
    JSONObject jo1 = new JSONObject(jo.get("data").toString());
    JSONArray jo2 = jo1.getJSONArray("getAllGiftCardsByUserId");
    return (List<GiftCard>) buildList(jo2);
  }
  // help function to build list
  private Iterable<GiftCard> buildList(JSONArray jo2) {
    ArrayList<GiftCard> arrayList = new ArrayList<>();
    for (int i = 0; i < jo2.length(); i++) {
      JSONObject obj = jo2.getJSONObject(i);
      arrayList.add(gson.fromJson(obj.toString(), GiftCard.class));
    }
    return arrayList;
  }

  public String editGiftCard(GiftCard giftCard) throws IOException {
    giftCardServicePort = this.configService.getByKey("giftCardServicePort");
    url = "http://localhost:" + giftCardServicePort + "/giftCard";
    return restTemplate.postForObject(url + "/edit", giftCard, String.class);
  }
}
