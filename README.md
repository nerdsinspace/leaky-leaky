```
 ███▄    █ ▓█████  ██▀███  ▓█████▄   ██████     ██▓ ███▄    █  ▄████▄  
 ██ ▀█   █ ▓█   ▀ ▓██ ▒ ██▒▒██▀ ██▌▒██    ▒    ▓██▒ ██ ▀█   █ ▒██▀ ▀█  
▓██  ▀█ ██▒▒███   ▓██ ░▄█ ▒░██   █▌░ ▓██▄      ▒██▒▓██  ▀█ ██▒▒▓█    ▄ 
▓██▒  ▐▌██▒▒▓█  ▄ ▒██▀▀█▄  ░▓█▄   ▌  ▒   ██▒   ░██░▓██▒  ▐▌██▒▒▓▓▄ ▄██▒
▒██░   ▓██░░▒████▒░██▓ ▒██▒░▒████▓ ▒██████▒▒   ░██░▒██░   ▓██░▒ ▓███▀ ░
░ ▒░   ▒ ▒ ░░ ▒░ ░░ ▒▓ ░▒▓░ ▒▒▓  ▒ ▒ ▒▓▒ ▒ ░   ░▓  ░ ▒░   ▒ ▒ ░ ░▒ ▒  ░
░ ░░   ░ ▒░ ░ ░  ░  ░▒ ░ ▒░ ░ ▒  ▒ ░ ░▒  ░ ░    ▒ ░░ ░░   ░ ▒░  ░  ▒   
   ░   ░ ░    ░     ░░   ░  ░ ░  ░ ░  ░  ░      ▒ ░   ░   ░ ░ ░        
         ░    ░  ░   ░        ░          ░      ░           ░ ░ ░      
                            ░                                 ░        
```

## Minecraft Account Session Vulnerability Security Advisory

README based on https://gist.github.com/ajvpot/3115176

### Details
**Severity**: High

**Exploit Date**: Febuary 25, 2020

**Public**: March 3, 2020

**Advisory**: March 3, 2020

### Vulnerability Scope
This vulnerability affects all Minecraft accounts.

### Description
A malicious attacker can log on using any Minecraft account to any Minecraft server relying on Mojang Specifications’ official authentication servers to verify user authenticity. This can allow an attacker to gain access to players’ accounts causing losses within the game, or allow an attacker to gain access to a privileged account on the server. Depending on common server modifications, privileged accounts could be used to acquire access to the operating system, or cause serious damage to data on the machine, which includes but is not limited to common software and data found in unison with a Minecraft server such as:

  * Server map files
  * Operating system files
  * Player data
  * Database and webserver data
  * Proprietary server modifications and source code

### Reproduction
This vulnerability seems to be caused by a failure to validate an account's ownership of the session token when logging into a server using the legacy Minecraft authentication API. joinServer.jsp will accept any valid session id from a account for another account username so long as the session id is valid.

To reproduce this issue an attacker needs to follow the following steps.

   1. Log in to Minecraft with any account.
   2. Instead of using Mojang's modern join server authentication api, use the legacy authentication api. Replace the username sent to the api with the any valid username.

### Resolution
This vulnerability needs to be fixed on the authentication level by Mojang Specifications, it cannot be resolved on a server locally.

### Mitigation
There is no way to protect your account from this exploit directly. If you are a player, the best layer of defense is to hide all of your items and kill yourself to spawn while letting your friends know that anyone on your account could very well not be you. As a server administrator we recommend relying on an additional layer of defense (2 factor authentication) by [configuring WorldGuard host keys](https://worldguard.enginehub.org/en/latest/host-keys/) or by using a 2FA plugin such as [MCAuthenticator](https://www.spigotmc.org/resources/mcauthenticator.18727).

### Closing thoughts

If this all sounds familar, it is because it's the exact same exploit previously disclosed by Team Avolition that occured back in July 2012 except this time it affects all Minecraft accounts regardless of migration status.