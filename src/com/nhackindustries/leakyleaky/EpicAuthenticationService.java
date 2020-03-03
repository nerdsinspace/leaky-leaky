package com.nhackindustries.leakyleaky;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EpicAuthenticationService implements MinecraftSessionService {

  private static final Logger log = LogManager.getLogger(EpicAuthenticationService.class);

  private final MinecraftSessionService wrapped;

  public EpicAuthenticationService(MinecraftSessionService wrapped) {
    this.wrapped = wrapped;
  }

  @Override
  public void joinServer(GameProfile profile, String authenticationToken, String serverId)
      throws AuthenticationException {

    String authUrl =
        String.format(
            "http://session.minecraft.net/game/joinserver.jsp?user=%s&sessionId=%s&serverId=%s",
            profile.getName(), authenticationToken, serverId);

    log.info(String.format("trying %s", authUrl));
    try (CloseableHttpResponse response =
        HttpClient.getInstance().getClosableHttpClient().execute(new HttpGet(authUrl))) {
      String responseStr = EntityUtils.toString(response.getEntity());

      if (!responseStr.equalsIgnoreCase("OK")) {
        throw new AuthenticationException(
            String.format("Failed to authenticate: %s", responseStr));
      }
    } catch (IOException e) {
      throw new AuthenticationUnavailableException("Cannot contact authentication server", e);
    }
  }

  @Override
  public GameProfile hasJoinedServer(GameProfile user, String serverId, InetAddress address)
      throws AuthenticationUnavailableException {
    return this.wrapped.hasJoinedServer(user, serverId, address);
  }

  @Override
  public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> getTextures(
      GameProfile profile, boolean requireSecure) {
    return this.wrapped.getTextures(profile, requireSecure);
  }

  @Override
  public GameProfile fillProfileProperties(GameProfile profile, boolean requireSecure) {
    return wrapped.fillProfileProperties(profile, requireSecure);
  }
}
