// this code is for proof-of-concept only
// this class  wont compile 

public class Auth {
  public static UUID getIdFromString(String uuid) {
    if (uuid.contains("-")) {
      return UUID.fromString(uuid);
    } else {
      return UUID.fromString(
          uuid.replaceFirst(
              "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
              "$1-$2-$3-$4-$5"));
    }
  }

  static void spoofClientSession(String username) throws IllegalArgumentException, IllegalAccessException {
    String apiUrl = String.format("https://api.mojang.com/users/profiles/minecraft/%s", username);

    try (CloseableHttpResponse response =
        HttpClient.getInstance().getClosableHttpClient().execute(new HttpGet(apiUrl))) {

      JsonElement e = new JsonParser().parse(EntityUtils.toString(response.getEntity()));

      UUID uuid = getIdFromString(e.getAsJsonObject().get("id").getAsString());
      String correctUsername = e.getAsJsonObject().get("name").getAsString();

      setSession(
          new Session(
              correctUsername,
              uuid.toString(),
              Minecraft.getMinecraft().getSession().getToken(),
              "mojang"));
    } catch (IOException | ParseException e) {
      logger.error(String.format("caught exception trying to parse profile: %s", e.getMessage()));
    }
  }
}
