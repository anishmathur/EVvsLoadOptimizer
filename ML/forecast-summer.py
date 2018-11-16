# -*- coding: utf-8 -*-
"""
Created on Thu Nov 15 09:01:27 2018

@author: rajgupta9
"""

import pandas as pd
import pyodbc
from collections import OrderedDict
from fbprophet import Prophet
from pytz import all_timezones
import matplotlib.pyplot as plt
import numpy as np, numpy.random

server = 'myhackathonsqlserver.database.windows.net' 
database = 'LoadFCST' 
username = 'rgu103' 
password = 'Chandler12#' 

baseYear = 2018
baseEVs = 5000
percentageIncreaseEVs = 40
startWindow = 9
endWindow = 12
EvLoad = 105

l = [(baseYear, baseEVs)]
odict = OrderedDict(l)
lastYear = sorted(odict.keys())[-1]

while lastYear < 2025 :
    lastKey = sorted(odict.keys())[-1]
    lastValue =  odict.get(sorted(odict.keys())[-1])
    dict2 = {lastKey+1: int(lastValue+lastValue*percentageIncreaseEVs/100) }
    odict.update(dict2)
    lastYear = lastYear + 1
    
for k,v in odict.items():
    print(k, 'corresponds to', v)
        
#startYear 2018-01-01 00:00:00
#endYear 2018-12-31 23:00:00  
def queryForecast(startYear, endYear):
    cnxn = pyodbc.connect('DRIVER={ODBC Driver 13 for SQL Server};SERVER='+server+';DATABASE='+database+';UID='+username+';PWD='+ password)
    query = "SELECT si.[effective_start_time] as ds, fw.[YHAT] as y FROM [dbo].[FORECAST_SUMMER] fw, [dbo].[SETTLEMENT_INTERVAL] si WHERE si.id=fw.settlement_interval and si.effective_start_time>='"+startYear+"' and si.effective_start_time <='"+endYear+"'"
    #print(query)
    dfFcstSummer = pd.read_sql(query, cnxn)
    #print(dfFcstSummer)
    cnxn.close()
    return dfFcstSummer

dfFcstSummer = queryForecast('2018-01-01 00:00:00', '2020-12-31 23:00:00')

#query = "SELECT  [id]  FROM [dbo].[SETTLEMENT_INTERVAL] si where si.effective_start_time>="+startYear+" and si.effective_start_time <="+endYear
#dfSettlement = pd.read_sql(query, cnxn)
#query = "SELECT  [id] ,[EV_percentage] ,[window] FROM [dbo].[permutation] p "
#dfPermutation = pd.read_sql(query, cnxn)

#
def IncreaseMWByPercentageEVs(ds, yhat, yearForIncrease, startWindow, endWindow):
    datetime = pd.to_datetime(ds)
    if datetime.year==yearForIncrease and (datetime.hour>=startWindow and datetime.hour<endWindow):
        print("original",datetime,yhat)
        return yhat + (odict.get(datetime.year)* EvLoad)/1000;
    return yhat

def RandomlyIncreaseMWByPercentageEVs(ds, yhat, yearForIncrease, startWindow, endWindow):
    datetime = pd.to_datetime(ds)
    loadPerDay = ((odict.get(datetime.year)* EvLoad)/1000)*3
    randomNumber = np.random.dirichlet(np.ones(24), size =1)
    for i in range(24):
        
    if datetime.year==yearForIncrease:
        print("original",datetime,yhat)
    return yhat + (odict.get(datetime.year)* EvLoad)/1000;   


randomNumber = np.random.dirichlet(np.ones(24), size =1)
def RandomlyIncreaseMWByPercentageEVs(yearForIncrease):

    loadPerDay = ((odict.get(yearForIncrease)* EvLoad)/1000)*3
    i = 0
    for index,row in dfFcstSummer.iterrows():            
        dfFcstSummer.at[index, 'y'] = dfFcstSummer.at[index, 'y'] + loadPerDay*randomNumber[0][i]
        #row['y'] = a            
        #dfFcstSummer.at[index, 'y']=a
        i+=1
        if(i==23):
            i=0
    #print(ds)
    return dfFcstSummer;   

dfFcstSummer = RandomlyIncreaseMWByPercentageEVs(2018)
dfFcstSummer = RandomlyIncreaseMWByPercentageEVs(2019)
dfFcstSummer = RandomlyIncreaseMWByPercentageEVs(2020)

print(dfFcstSummer)
dfFcstSummer['y'] = dfFcstSummer.apply(lambda row: IncreaseMWByPercentageEVs(row['ds'], row['y'], 2018, startWindow, endWindow), axis=1)
dfFcstSummer['y'] = dfFcstSummer.apply(lambda row: IncreaseMWByPercentageEVs(row['ds'], row['y'], 2019, startWindow, endWindow), axis=1)
dfFcstSummer['y'] = dfFcstSummer.apply(lambda row: IncreaseMWByPercentageEVs(row['ds'], row['y'], 2020, startWindow, endWindow), axis=1)

#filter summer months
dfFcstSummer = dfFcstSummer[ (dfFcstSummer['ds'].dt.month >= 4) & (dfFcstSummer['ds'].dt.month < 10) ]
print(dfFcstSummer)

#add these 3 years to the 7 years 2010-2018
dfTrainingSet = pd.read_csv('D:/AESO/Hackathon 2018/LoadModel/Load_2010_2017.csv')
dfTrainingSet['ds'] = pd.to_datetime(dfTrainingSet['ds'])
dfTrainingSet = dfTrainingSet[ (dfTrainingSet['ds'].dt.month >= 4) & (dfTrainingSet['ds'].dt.month < 10) ]
#dfTrainingSet.concat(dfFcstSummer)
dfTrainingSet = pd.concat([dfTrainingSet,dfFcstSummer])
print (dfTrainingSet)
#train
m = Prophet()
m.fit(dfTrainingSet)

#predict for 2021 and 2022
futuresummer = m.make_future_dataframe(periods=17520, freq='H')
fcstsummer = m.predict(futuresummer)
figsummer = m.plot(fcstsummer)
figsummer = m.plot_components(fcstsummer)

#Insert Records
insertSummerForecast(fcstsummer, percentageIncreaseEVs)

# DF TO EXCEL
from pandas import ExcelWriter

writer = ExcelWriter('D:/AESO/Hackathon 2018/LoadModel/Forecast-SummerWithEVs_9_12.xlsx')
fcstsummer.to_excel(writer,'Sheet1')
writer.save()

#create a for loop
#run these steps for all times and all percentage
def insertSummerForecast(fcstsummer, percentageIncreaseEVs):
    server = 'myhackathonsqlserver.database.windows.net' 
    database = 'LoadFCST' 
    username = 'rgu103' 
    password = 'Chandler12#' 
    cnxn = pyodbc.connect('DRIVER={ODBC Driver 13 for SQL Server};SERVER='+server+';DATABASE='+database+';UID='+username+';PWD='+ password)

    dfsummercopy = fcstsummer.copy()
    query = "SELECT  [id] ,[effective_start_time] ,[effective_end_time] FROM [dbo].[SETTLEMENT_INTERVAL] si where si.effective_start_time>='2018-01-01 00:00:00' and si.effective_start_time <'2023-01-01 00:00:00'"
    dfSettlement = pd.read_sql(query, cnxn)
    query = "SELECT  [id] ,[EV_percentage] ,[window] FROM [dbo].[permutation] p "
    dfPermutation = pd.read_sql(query, cnxn)
    dfsummercopy = dfsummercopy[ (dfsummercopy['ds'].dt.year == 2022) & ((dfsummercopy['ds'].dt.month >= 4) & (dfsummercopy['ds'].dt.month < 10))]
    rowPermutation = dfPermutation.loc[(dfPermutation['EV_percentage']== percentageIncreaseEVs) & (dfPermutation['window']==4)]

    #print(rowPermutation)
    cursor = cnxn.cursor()
    #print(dfsummercopy)
    
    for index,row in dfsummercopy.iterrows():
        rowSettlement = dfSettlement.loc[dfSettlement['effective_start_time'] == row['ds']]
        print(rowPermutation['id'].values[0], rowSettlement['id'].values[0], row['yhat'])
        permutation = int(rowPermutation['id'].values[0])
        settlement = int(rowSettlement['id'].values[0])
        yhat = row['yhat']
        cursor.execute("INSERT INTO dbo.forecast_summer([SETTLEMENT_INTERVAL],[PERMUTATION],[YHAT]) values (?, ?, ?)", settlement, permutation , yhat) 
    
    cnxn.commit()
    cursor.close()
    cnxn.close()