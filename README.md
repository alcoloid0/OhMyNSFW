# OH MY NSFW

## Commands

| Usage                      | Permission            | Description                                                |
|----------------------------|-----------------------|------------------------------------------------------------|
| /nsfw reload               | ohmynsfw.reload       | Reloads plugin settings                                    |
| /nsfw nekobot \<type\>     | ohmynsfw.use.nekobot  | Fetches image from Nekobot API based on the specified type |
| /nsfw rule34 \[tags...\]   | ohmynsfw.use.rule34   | Fetches image from Rule34 with the specified tags          |
| /nsfw reddit \<subreddit\> | ohmynsfw.use.reddit   | Fetches image from Reddit based on the specified subreddit |
| /nsfw gelbooru \[tags...\] | ohmynsfw.use.gelbooru | Fetches image from Gelbooru with the specified tags        |

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

proxy-settings:
  type: DIRECT # DIRECT, HTTP, SOCKS
  address: '127.0.0.1:8080'

message: # https://docs.advntr.dev/minimessage/format.html
  settings-reloaded: '<yellow>Plugin settings reloaded. (<ms>ms)'
  request-prepare: '<white>Hold on! We''re loading the NSFW content up on the server right now. (<name>)'
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
| Spigot   |   ❌   |   ❌    |   ✔️   |  ✔️  |  ✔️  |  ✔️  |  ✔️  |  ✔️  |
| Paper    |   ❌   |   ❌    |   ✔️   |  ✔️  |  ✔️  |  ✔️  |  ✔️  |  ✔️  |
| Folia    |   ➖   |   ➖    |   ➖    |  ➖   |  ➖   |  ❌   |  ❌   |  ❌   |


## License

OhMyNSFW is licensed under the GNU General Public License v3.0. See [COPYING](COPYING) for more information.