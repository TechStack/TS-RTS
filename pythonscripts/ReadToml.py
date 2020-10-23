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

def createPageHeader(doc):
    with doc.head:
        link(rel='stylesheet', href='style.css')
    with doc:
        with div(id="Header"):
            img(src="banner.png")


def createPageNavBar(doc, Units, Buildings):
    with doc:
        with div(id="NavBar", cls="column" ):
            h3("")
            with ul():
                li(   a("Home", href="Index.html")  )
            h3("Units:")
            with ul():
                for U in Units:
                    li(a(   Units[U]["Name"], href="Unit_"+Units[U]["Name"]+".html"  ) )
            h3("Buildings:")
            with ul():
                for B in Buildings:
                    li(a(   Buildings[B]["Name"], href="Building_"+Buildings[B]["Name"]+".html"  ) )

                        


def processUnits(config, Units,buildings):

    for U in Units:
        #Create a page for this unit.
        doc = dominate.document(Units[U]["Name"])
        createPageHeader(doc)
        div(id="row", cls="row" )
        createPageNavBar(doc, Units,buildings)
        with doc:
            with div(id="Text" , cls="column"):
                GetUnitText(config, Units[U]["Description"])
        with doc:
            with div(id="unitDetail",  cls="column" ):
                h1("Unit: "+ Units[U]["Name"] )
                img(src="./img/" +Units[U]["Name"]  + ".gif")
                print (Units[U]["Name"])
                GetUnitCosts(config ,Units[U]["Name"], doc)
                GetUnitAttributes (config, Units[U]["AttributeName"],doc)

        with open( 'Unit_'+ Units[U]["Name"] +'.html'  , 'w') as file:
            file.write(doc.render())
        print ("")


def GetUnitText(config, Description):
   
    with div():
        p(Description)



def GetUnitAttributes(config, UnitName,doc):
    UnitName = UnitName.lower()
  
    if UnitName == "trebuchet":
        print ("not valid yet for Trebs")
        return 


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
                    td("Movement Speed:")
                    td(getMovement_Speed(config['unit_attributes']["unit_"+UnitName+"_attributes"]))
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



def compareUnits(config, Units, buildings):

    
    doc = dominate.document("Unit Comparision")
    createPageHeader(doc)  
    div(id="row", cls="row" )
    createPageNavBar(doc, Units,buildings)
   
    with doc:
        div(id="comparerow", cls="row" )
        for U in Units:
            with div(id="unitDetail",  cls="comparecolumn" ):
                h1("Unit: "+ Units[U]["Name"] )
                img(src="./img/" +Units[U]["Name"]  + ".gif")
                print (Units[U]["Name"])
                GetUnitCosts(config ,Units[U]["Name"], doc)
                GetUnitAttributes (config, Units[U]["AttributeName"],doc)

    with open( 'Unit_comparison.html'  , 'w') as file:
        file.write(doc.render())
    print ("")



def pharseConfig(FileName, Units, Buildings):
    config = toml.load(FileName)
 

    processUnits(config, Units, Buildings)
    processBulidings(config,Units, Buildings)

    compareUnits(config , Units, Buildings)



def processBulidings(config,Units, Buildings):
    for B in Buildings:
        #Create a page for this unit.
        doc = dominate.document(Buildings[B]["Name"])
        createPageHeader(doc)
        div(id="row", cls="row" )
        createPageNavBar(doc, Units,Buildings)
        with doc:
            with div(id="Text" , cls="column"):
                GetUnitText(config, Buildings[B]["Description"])
        with doc:
            with div(id="buildingDetail",  cls="column" ):
                h1("Unit: "+ Buildings[B]["Name"] )
                img(src="./img/" +Buildings[B]["Name"]  + ".gif")
                print (Buildings[B]["Name"])
                # GetUnitCosts(config ,Buildings[B]["Name"], doc)
                # GetUnitAttributes (config, Buildings[B]["AttributeName"],doc)

        with open( 'Building_'+ Buildings[B]["Name"] +'.html'  , 'w') as file:
            file.write(doc.render())
        print ("")



def getBuildingDict():
    buildings={
        "Building1":{
            "Name":"Town Hall",
            "building_cost_key":"",
            "structure_health_key":"buildingHealthTownHall",
            "Description":"The town hall is important"
        },
        "Building2":{
            "Name":"Farm",
            "building_cost_key":"farmBulidingCosts",
            "structure_health_key":"buildingHealthFarm",
            "Description":"Farm desc"
        }
    }
    return buildings

def getUnitDict():
    units={
        "Unit1":{
        "Name":"Minion",
        "AttributeName":"minion",
        "Description":"A basic unit good in hand to hand combat",
        "Type":"Meele"
        },
        "Unit2":{
        "Name":"Archer",
        "AttributeName":"archer",
        "Description":"A ranged unit armed with a bow",
        "Type":"Ranged"
        },
        "Unit3":{
        "Name":"Crossbowmen",
        "AttributeName":"crossbowman",
        "Description":"An advanced ranged unit armed with a crossbow and years of experience in light leather armor",
        "Type":"Ranged"
        },
        "Unit4":{
        "Name":"Sapper",
        "AttributeName":"sapper",
        "Description":"A demolision expert"        ,
        "Type":"Siege"
        },
        "Unit5":{
        "Name":"Lancer",
        "AttributeName":"mounted",
        "Description":"A mounted unit that relies on his steed for speed and armed with a lance"        ,
        "Type":"Mounted"
        },
        "Unit6":{
        "Name":"Trebuchet",
        "AttributeName":"trebuchet",
        "Description":"A ranged sige weapon"        ,
        "Type":"Siege"
        },
        "Unit7":{
        "Name":"Longbowmen",
        "AttributeName":"longbowman",
        "Description":"An expereicend archer armed with a longbow for greater range"        ,
        "Type":"Ranged"
        },
        "Unit8":{
        "Name":"AdvancedKnight",
        "AttributeName":"advanced_knight",
        "Description":"A Knight in the best armor emeralds can buy"
        ,
        "Type":"Meele"
        },
        "Unit9":{
        "Name":"Knight",
        "AttributeName":"knight",
        "Description":"A Knight in the iron armor"
        ,
        "Type":"Meele"
        },
        "Unit10":{
        "Name":"Pikeman",
        "AttributeName":"pikeman",
        "Description":"A meele unit armed with a pike"
        ,
        "Type":"Meele"
        },
        "Unit11":
        {
        "Name":"Cleric",
        "AttributeName":"cleric",
        "Description":"A Healer unit."
        ,
        "Type":"Healer" 
        }
        
    }
    return units

def BuildIndexPage(Units, Buildings):
    doc = dominate.document("RSTCraft Mod - Home")
    createPageHeader(doc)
    div(id="row", cls="row" )
    createPageNavBar(doc, Units,Buildings)
    with open( 'Index.html'  , 'w') as file:
        file.write(doc.render())
#main code


Units=getUnitDict()
Buildings=getBuildingDict()

BuildIndexPage( Units,Buildings)
pharseConfig("..\\run\\config\\tsrts-common.toml",Units,Buildings)

