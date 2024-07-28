# OH MY NSFW

## Commands

| Usage                      | Permission                         | Description                                                |
|----------------------------|------------------------------------|------------------------------------------------------------|
| /nsfw reload               | ohmynsfw.reload                    | Reloads plugin settings                                    |
| /nsfw nekobot \<type\>[^1] | ohmynsfw.use.nekobot               | Fetches image from Nekobot API based on the specified type |
| /nsfw rule34 \[tags...\]   | ohmynsfw.use.rule34                | Fetches image from Rule34 API with the specified tags      |
| /nsfw r/nsfw               | ohmynsfw.use.reddit.nsfw           | Fetches image from the r/nsfw subreddit                    |
| /nsfw r/nsfw2              | ohmynsfw.use.reddit.nsfw2          | Fetches image from the r/nsfw2 subreddit                   |
| /nsfw r/hentai             | ohmynsfw.use.reddit.hentai         | Fetches image from the r/hentai subreddit                  |
| /nsfw r/bdsm               | ohmynsfw.use.reddit.bdsm           | Fetches image from the r/bdsm subreddit                    |
| /nsfw r/anal               | ohmynsfw.use.reddit.anal           | Fetches image from the r/anal subreddit                    |
| /nsfw r/boobs              | ohmynsfw.use.reddit.boobs          | Fetches image from the r/boobs subreddit                   |
| /nsfw r/legalteens         | ohmynsfw.use.reddit.legalteens     | Fetches image from the r/legalteens subreddit              |
| /nsfw r/furry              | ohmynsfw.use.reddit.furry          | Fetches image from the r/furry subreddit                   |
| /nsfw r/toocuteforporn     | ohmynsfw.use.reddit.toocuteforporn | Fetches image from the r/toocuteforporn subreddit          |
| /nsfw r/just18             | ohmynsfw.use.reddit.just18         | Fetches image from the r/just18 subreddit                  |

## Settings (settings.yml)

```yaml
# Made with ❤️ by alcoloid (GitHub: https://github.com/alcoloid0)

message-prefix: '<#cb00f5>[OH MY NSFW] '

map-item-settings:
  name: '<#cb00f5>NSFW'
  lore:
    - ''
    - '<rainbow>Just a heads up, don''t show this to mom'
    - ''
  glow-effect: true

message: # https://docs.advntr.dev/minimessage/format.html
  settings-reloaded: '<yellow>Plugin settings reloaded.'
  request-prepare: '<white>Hold on! We''re loading the NSFW content up on the server right now.'
  request-error-occurred: '<red>Oh no! There were some technical issues while loading NSFW content.'
  request-complete: '<white>You''ve been given a map <u>with a bit of adult content...</u> Yummy!'
  must-be-player: '<red>You must be a player to use this command!'
  invalid-enum: '<red>Invalid <parameter>: <input>.'
  missing-argument: '<red>You must provide a value for the <argument>!'
  no-permission: '<red>You do not have permission to execute this command.'
  no-subcommand-specified: '<red>You must specify a subcommand!'
```

## Supported server software

| Software | 1.8.8 | 1.12.2 | 1.16.5 | 1.17 | 1.18 | 1.19 | 1.20 | 1.21 |
|----------|:-----:|:------:|:------:|:----:|:----:|:----:|:----:|:----:|
| Spigot   |   ❌   |   ❌    |   ❌    |  ❌   |  ❌   |  ❌   |  ❌   |  ❌   |
| Paper    |   ❌   |   ❌    |   ❌    |  ❌   |  ❌   |  ❌   |  ✔️  |  ✔️  |
| Folia    |   ➖   |   ➖    |   ➖    |  ➖   |  ➖   |  ❌   |  ❌   |  ❌   |


## License

OhMyNSFW is licensed under the GNU General Public License v3.0. See [COPYING](COPYING) for more information.

[^1]: Image types:
hass, hmidriff, 4k, hentai, holo, hneko, neko, hkitsune, kemonomimi, anal, hanal, gonewild, kanna, ass, pussy,
thigh, hthigh, gah, coffee, food, paizuri, tentacle, boobs, hboobs, yaoi