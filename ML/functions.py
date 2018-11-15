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

server = 'myhackathonsqlserver.database.windows.net' 
database = 'LoadFCST' 
username = 'rgu103' 
password = 'Chandler12#' 

baseYear = 2018
baseEVs = 2000
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
    query = "SELECT si.[effective_start_time] as ds, fw.[YHAT] as y FROM [dbo].[FORECAST_WINTER] fw, [dbo].[SETTLEMENT_INTERVAL] si WHERE si.id=fw.settlement_interval and si.effective_start_time>='"+startYear+"' and si.effective_start_time <='"+endYear+"'"
    #print(query)
    dfFcstWinter = pd.read_sql(query, cnxn)
    #print(dfFcstWinter)
    cnxn.close()
    return dfFcstWinter

dfFcstWinter = queryForecast('2018-01-01 00:00:00', '2020-12-31 23:00:00')

#query = "SELECT  [id]  FROM [dbo].[SETTLEMENT_INTERVAL] si where si.effective_start_time>="+startYear+" and si.effective_start_time <="+endYear
#dfSettlement = pd.read_sql(query, cnxn)
#query = "SELECT  [id] ,[EV_percentage] ,[window] FROM [dbo].[permutation] p "
#dfPermutation = pd.read_sql(query, cnxn)

#
def IncreaseMWByPercentageEVs(ds, yhat, yearForIncrease, startWindow, endWindow):
    datetime = pd.to_datetime(ds)
    if datetime.year==yearForIncrease and (datetime.hour>=startWindow and datetime.hour<=endWindow):
        print("original",datetime,yhat)
    return yhat + (odict.get(datetime.year)* EvLoad)/1000;   
    
dfFcstWinter['y'] = dfFcstWinter.apply(lambda row: IncreaseMWByPercentageEVs(row['ds'], row['y'], 2018, startWindow, endWindow), axis=1)
dfFcstWinter['y'] = dfFcstWinter.apply(lambda row: IncreaseMWByPercentageEVs(row['ds'], row['y'], 2019, startWindow, endWindow), axis=1)
dfFcstWinter['y'] = dfFcstWinter.apply(lambda row: IncreaseMWByPercentageEVs(row['ds'], row['y'], 2020, startWindow, endWindow), axis=1)

#filter winter months
dfFcstWinter = dfFcstWinter[ (dfFcstWinter['ds'].dt.month < 4) | (dfFcstWinter['ds'].dt.month == 12) ]
print(dfFcstWinter)

#add these 3 years to the 7 years 2010-2018
dfTrainingSet = pd.read_csv('D:/AESO/Hackathon 2018/LoadModel/Load_2010_2017.csv')
dfTrainingSet['ds'] = pd.to_datetime(dfTrainingSet['ds'])
dfTrainingSet = dfTrainingSet[ (dfTrainingSet['ds'].dt.month < 4) | (dfTrainingSet['ds'].dt.month == 12) ]
#dfTrainingSet.concat(dfFcstWinter)
dfTrainingSet = pd.concat([dfTrainingSet,dfFcstWinter])
print (dfTrainingSet)
#train
m = Prophet()
m.fit(dfTrainingSet)

#predict for 2021 and 2022
futurewinter = m.make_future_dataframe(periods=17520, freq='H')
fcstwinter = m.predict(futurewinter)
figwinter = m.plot(fcstwinter)
figwinter = m.plot_components(fcstwinter)

# DF TO EXCEL
from pandas import ExcelWriter

writer = ExcelWriter('D:/AESO/Hackathon 2018/LoadModel/PythonExportHourlyWinter.xlsx')
fcstwinter.to_excel(writer,'Sheet5')
writer.save()

#create a for loop
#run these steps for all times and all percentage
