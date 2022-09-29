# JRogueClone

Rogue is a terminal-based dungeon crawling adventure developed at U.C Berkley in the 70's where the player fights through endless hords monsters, collects
terrifying weapons, and a wide variety of weapons. JRogueClone hopes to deliver the same amount of fun or more that users in the 70's experienced to players
today. Join **WHAT IS BOOLEAN** sutdios as we embark on yet another adventure.

## Controls
| control | function                 |
|---------|----------                |
| W/A/S/D | movement                 |
| I       | open/close inventory     |
| E       | interact with chest/item |
| SPACE   | cycle frame              |
| H       | show hidden map          |

## Analysis of a Rogue Game World

```

                                                            
                                                ╔═══╬══════╗          
                                                ║..........║                
                                ################╬(Fig J.)♯.║                  
                                #               ║..........║                  
                                #               ║..........║                  
                                #               ╚══════════╝                  
                                #                                                
                                #                                                
                                #                                              
                                #                                             
                                #                                             
                                #                                              
                                @ (Fig A.)                                     
                                #                                            
                            ╔═══╬══════╗                                     
                            ║(Fig I).V.║                                     
                    (Fig C.)╬..........║                                     
                            ║(Fig B.).=║                                    
                            ║..........║                                    
                            ╚══════════╝                                    
 (Fig D.) (Fig E.)  (Fig F.) (Fig G.)     (Fig H.)                           
 KILLS: 1 LVL: 1 PLR_LVL: 1 XP: (1/10) | HP: (100/100) ▆▆▆▆▆▆▆▆▆▆▆▆▆▆▆▆▆▆▆▆
```
|Figure  | Description   |
|--------|---------------|
| Fig. A | Player (You!) in a hallway. Yes, you can walk down these to get to other rooms! |
| Fig. B | Chest. Press **<e>** to get loot! |
| Fig. C | Room exit. This is how you get to a hallway. You can walk on this
| Fig. D | Kill count (increase this by killing more monsters. |
| Fig. E | Game Level. This is the difficulty of the game. |
| Fig. F | Player level (increase this by killing more monsters and gaining XP. |
| Fig. G | Experience bar. Format (CURRENT_XP/TARGET_XP). Fight monsters to increase CURRENT_XP. Once CURRENT_XP equals TARGET_XP, you level up, your health capacity incrases, and you recieve full health |
| Fig. H | Health bar. Don't let this get to 0 or you are what the doctors call "dead<sup>tm</sup> ". |
| Fig. I | A monster. A bat, to be specific. These will follow you around the room trying to kill you. Beware! |
| Fig. J | Staircase. Press **<e>** to exit the level! |

## Fighting
Move into the enemy to hurt them. That's it.
  
## Weapons
These can be picked up from loot boxes. Some are good and some are bad. All weapons have a damage, a hit chance, and a maximum durability. Every time you attack
a monster, you have a chance (displayed on the weapon's name in the inventory) to deal the weapon's damage (displayed on the weapon's name in the inventory) to
the monster. Everytime you hit a monster 1 is subtracted from the weapon's durability (You know where you can find this). When the weapon's durability hits
0, the weapon breaks. A new one is automatically equipped if you have one available. Otherwise, you are a sitting duck for a monster to kill you. Be sure
to have multiple weapons on you at all times!
  
## Monsters
  
| Name     | Description|
|----------|------------|
| Bat      | Low health. Annoying flying mammals. There are so many of them, it's kinda hard to believe that they're endangered. But you're stuck in a dungeon so who cares |
| Snake    | Low health. Annoying slithering reptile. A reptile with the personality of a bat. |
| Eye      | A floating eye. It can't damage you physically so it'll do it emotionally |
| Skeleton | Mischevious little rascals. It may seem like they're just trying to give you a hug. Really, they're trying to make you die of tetnis with that rusty weapon of theirs. Beware! |
| Orc      | This big guy's as lost as you. As he is built like a tank, it may take a few whacks to get through him |
| Snowman  | I have no clue how this thing exists. It isn't even below freezing in the dungeon. Impossibility aside, don't let this guy hit you. You might get frozen! |
  
 ## Inventory
 Press I and look at the bottom
  
## Final words
  
Have fun!
  
