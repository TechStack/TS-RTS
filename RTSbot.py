import os
import sys
import discord
from discord.ext import commands

from dotenv import load_dotenv

import matplotlib.pyplot as plt
import numpy as np


load_dotenv()
TOKEN = os.getenv('DISCORD_TOKEN')
GUILD = os.getenv('DISCORD_GUILD')

client = discord.Client()
channel = client.get_channel(os.getenv('BOT_CHANNEL'))
bot = commands.Bot(command_prefix='!')


@bot.command(name='nickelback', help='Better than a photograph...')
async def nickel(ctx):
    response = 'Look at this graph!'
    await ctx.send(response)

    # Data for plotting
    t = np.arange(0.0, 2.0, 0.01)
    s = 1 + np.sin(2 * np.pi * t)

    fig, ax = plt.subplots()
    ax.plot(t, s)

    ax.set(xlabel='time (s)', ylabel='voltage (mV)',
        title='About as simple as it gets, folks')
    ax.grid()
    fig.savefig('.\\out\\temp.png')
    
    with open('.\\out\\temp.png', 'rb') as fp:
        await ctx.send(file=discord.File(fp, 'new_filename.png'))

    os.remove('.\\out\\temp.png')


@bot.command(name='gamestats', help='Creates game statistics based off the latest.log file.')
async def gamestats(ctx):
    response = 'This is WIP! Check back later...'
    await ctx.send(response)

    import gameStats
    ecodat, builddat, unitdat = gameStats.readGameLog(gameStats.file)

    for team in ['red', 'blue', 'yellow', 'green']:
        if gameStats.wasInMatch(ecodat, team):
            fig, ax = gameStats.teamResourcePlot(ecodat, team=team, p=True)
            fig.savefig('.\\out\\{}_Resources.png'.format(team))

            fig, ax = gameStats.teamTroopPlot(unitdat, team=team)
            fig.savefig('.\\out\\{}_Troops.png'.format(team))

        
    fig, ax = gameStats.ecoIndexPlot(ecodat)
    fig.savefig('.\\out\\Econ_Summary.png')

    fig, ax = gameStats.troopIndexPlot(unitdat)
    fig.savefig('.\\out\\Military_Summary.png')
    
    # List comprehension of all the png files turned into discord file objects
    graphs = [discord.File('.\\out\\{}'.format(f)) for f in os.listdir('.\\out') if f.endswith('png')]
    await ctx.send(files=graphs)

bot.run(TOKEN)

