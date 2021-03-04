# Box of Placeholders

It's a simple addon/compability mod, that adds placeholders from other mods (+ few own ones)

For list of placeholders, see https://github.com/Patbox/FabricPlaceholderAPI/wiki/Box-Of-Placeholders

![](https://i.imgur.com/KK0NOYh.png)

## Animation format:

```json5
{
  "id": "example", //Id - used in placeholders
  "frames": [      // Frames of animation
    "<red>Hello there</red>",
    "<blue>Placeholders! %bof:animation/example2%</blue>",
    "<gold>Tiny potato <color:#e2bdba>[▋<gray>:)</gray>▉▉]</color></gold>"
  ],
  "updateRate": 20 // How many display updates (from mod using placeholder) frame changes
}
```