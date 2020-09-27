import toml
import dominate
from dominate.tags import *

def splitResources(resourceString):
    resourcevalues = resourceString.split(",")
    return resourcevalues


def getFood(resourceString):
    return splitResources(resourceString)[0]
def getWood(resourceString):
    return splitResources(resourceString)[1]
def getStone(resourceString):
    return splitResources(resourceString)[2]
def getIron(resourceString):
    return splitResources(resourceString)[3]
def getGold(resourceString):
    return splitResources(resourceString)[4]
def getDiamond(resourceString):
    return splitResources(resourceString)[5]
def getEmerald(resourceString):
    return splitResources(resourceString)[6]


def splitEntityAttributes(resourecString):
    resourcevalues = resourecString.split(",")
    return resourcevalues

def getMax_Health(resourecString):
    return splitEntityAttributes(resourecString)[0]

def getKnock_Back_Resistance(resourecString):
    return splitEntityAttributes(resourecString)[1]


def getMovement_Speed(resourecString):
    return splitEntityAttributes(resourecString)[2]

def getArmor(resourecString):
    return splitEntityAttributes(resourecString)[3]

def getArmor_toughness(resourecString):
    return splitEntityAttributes(resourecString)[4]


def getAttack_knockback(resourecString):
    return splitEntityAttributes(resourecString)[5]

def getAttack_damage(resourecString):
    return splitEntityAttributes(resourecString)[6]


def getFolow_range(resourecString):
    return splitEntityAttributes(resourecString)[7]




def processUnits(config, Units):

    for U in Units:
        #Create a page for this unit.
        doc = dominate.document(Units[U]["Name"])
        with doc:
            h1("Unit: "+ Units[U]["Name"] )
            img(src="./img/" +Units[U]["Name"]  + ".gif")
        print (Units[U]["Name"])
        GetUnitCosts(config ,Units[U]["Name"], doc)
        GetUnitAttributes (config, Units[U]["AttributeName"],doc)

        with open( 'Unit_'+ Units[U]["Name"] +'.html'  , 'w') as file:
            file.write(doc.render())
        print ("")


def GetUnitAttributes(config, UnitName,doc):
    UnitName = UnitName.lower()
  
    if UnitName == "trebuchet":
        print ("not valid yet for Trebs")
        return 

    with doc:
        with div():
            with table():
                with thead():
                    with tr():
                        th("Unit Attributes")
                with tbody():
                    with tr():
                        td("Max Health:")                                
                        td(getMax_Health(config['unit_attributes']["unit_"+UnitName+"_attributes"]))
                    with tr():                        
                        td("Knock back resistance:")
                        td(getKnock_Back_Resistance(config['unit_attributes']["unit_"+UnitName+"_attributes"]))
                    with tr():                        
                        td("Armor: ")
                        td(getArmor(config['unit_attributes']["unit_"+UnitName+"_attributes"]))
                    with tr():                        
                        td("Armor Toughness: ")
                        td(getArmor_toughness(config['unit_attributes']["unit_"+UnitName+"_attributes"]))
                    with tr():                        
                        td("Attack Knockback: ")
                        td(getAttack_knockback(config['unit_attributes']["unit_"+UnitName+"_attributes"]))
                    with tr():                        
                        td("Attack Damage: ")
                        td(getAttack_damage(config['unit_attributes']["unit_"+UnitName+"_attributes"]))
                    with tr():                        
                        td("Follow Range: ")
                        td(getFolow_range(config['unit_attributes']["unit_"+UnitName+"_attributes"]))

    print( "Max Health: " +   getMax_Health(config['unit_attributes']["unit_"+UnitName+"_attributes"]))
    print( "Knock back resistance: " +getKnock_Back_Resistance(config['unit_attributes']["unit_"+UnitName+"_attributes"]))
    print( "Movement Speed: " +getMovement_Speed(config['unit_attributes']["unit_"+UnitName+"_attributes"]))
    print( "Armor: " +getArmor(config['unit_attributes']["unit_"+UnitName+"_attributes"]))
    print( "Armor Toughness: " +getArmor_toughness(config['unit_attributes']["unit_"+UnitName+"_attributes"]))
    print( "Attack Knockback: " +getAttack_knockback(config['unit_attributes']["unit_"+UnitName+"_attributes"]))
    print( "Attack Damage: " +getAttack_damage(config['unit_attributes']["unit_"+UnitName+"_attributes"]))
    print( "Follow Range: " +getFolow_range(config['unit_attributes']["unit_"+UnitName+"_attributes"]))

def GetUnitCosts(config ,UnitName,doc):
    headers = ["Food", "Wood", "Stone", "Iron", "Gold", "Diamond", "Emerald" ]
    with doc:
        with div():
            with table():
                with thead():
                    with tr():
                        th("Unit Costs")
                with tbody():
                    with tr():
                        with td():
                             img(src="./img/food.png")
                        td("Food:")
                                
                        td(getFood(config['unit_cost']["unitCosts"+UnitName] ))
                    with tr():
                        with td():
                            img(src="./img/wood.png")
                        td("Wood:")
                        td(getWood(config['unit_cost']["unitCosts"+UnitName] ))
                    with tr():
                        with td():
                            img(src="./img/stone.png")
                        td("Stone:")
                        td(getStone(config['unit_cost']["unitCosts"+UnitName] ))
                    with tr():
                        with td():
                            img(src="./img/iron.png")
                        td("Iron:")
                        td(getIron(config['unit_cost']["unitCosts"+UnitName] ))
                    with tr():
                        with td():
                            img(src="./img/gold.png")
                        td("Gold:")
                        td(getGold(config['unit_cost']["unitCosts"+UnitName] ))
                    with tr():
                        with td():
                            img(src="./img/diamond.png")
                        td("Diamond:")
                        td(getDiamond(config['unit_cost']["unitCosts"+UnitName] ))
                    with tr():
                        with td():
                            img(src="./img/emerald.png")
                        td("Emerald:")
                        td(getEmerald(config['unit_cost']["unitCosts"+UnitName] ))
    # print ( "Food: " +  getFood(config['unit_cost']["unitCosts"+UnitName] ))
    # print ( "wood: " +  getWood(config['unit_cost']["unitCosts"+UnitName] ))
    # print ( "Stone: " +  getStone(config['unit_cost']["unitCosts"+UnitName] ))
    # print ( "Iron: " +  getIron(config['unit_cost']["unitCosts"+UnitName] ))
    # print ( "Gold: " +  getGold(config['unit_cost']["unitCosts"+UnitName] ))
    # print ( "Diamond: " +  getDiamond(config['unit_cost']["unitCosts"+UnitName] ))
    # print ( "Emerald: " +  getEmerald(config['unit_cost']["unitCosts"+UnitName] ))

def pharseConfig(FileName, Units):
    config = toml.load(FileName)


    

    processUnits(config, Units)
  
      



def getUnitDict():
    units={
        "Unit1":{
        "Name":"Minion",
        "AttributeName":"minion"
        },
        "Unit2":{
        "Name":"Archer",
        "AttributeName":"archer"
        },
        "Unit3":{
        "Name":"Crossbowmen",
        "AttributeName":"crossbowman"
        },
        "Unit4":{
        "Name":"Sapper",
        "AttributeName":"sapper"
        },
        "Unit5":{
        "Name":"Lancer",
        "AttributeName":"mounted"
        },
        "Unit6":{
        "Name":"Trebuchet",
        "AttributeName":"trebuchet"
        },
        "Unit7":{
        "Name":"Longbowmen",
        "AttributeName":"longbowman"
        },
        "Unit8":{
        "Name":"AdvancedKnight",
        "AttributeName":"advanced_knight"
        },
        "Unit9":{
        "Name":"Knight",
        "AttributeName":"knight"
        },
        "Unit10":{
        "Name":"Pikeman",
        "AttributeName":"pikeman"
        }
        
    }
    return units



#main code

pharseConfig("..\\run\\config\\tsrts-common.toml", getUnitDict())
