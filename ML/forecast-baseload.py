# -*- coding: utf-8 -*-
"""
Created on Thu Nov 15 06:20:26 2018

@author: rajgupta9
"""

import pandas as pd
from fbprophet import Prophet

df = pd.read_csv('D:/AESO/Hackathon 2018/LoadModel/Load_Price_v4.csv')

baseYear = 2018
baseEVs = 5000
percentageIncreaseEVs = 0
startWindow = 9
endWindow = 12
EvLoad = 105

#summer
dfsummer = df.copy()
dfsummer['ds'] = pd.to_datetime(dfsummer['ds'])
dfsummer = dfsummer[ (dfsummer['ds'].dt.month >= 4) & (dfsummer['ds'].dt.month < 10) ]
#print(dfsummer)

m = Prophet().fit(dfsummer)
futuresummer = m.make_future_dataframe(periods=43800, freq='H')
fcstsummer = m.predict(futuresummer)
figsummer = m.plot(fcstsummer)
fig2summer = m.plot_components(fcstsummer)


# DF TO EXCEL
from pandas import ExcelWriter

writer = ExcelWriter('D:/AESO/Hackathon 2018/LoadModel/Forecast-Summer.xlsx')
fcstsummer.to_excel(writer,'Sheet1')
writer.save()


import pandas as pd
import pyodbc

server = 'myhackathonsqlserver.database.windows.net' 
database = 'LoadFCST' 
username = 'rgu103' 
password = 'Chandler12#' 
cnxn = pyodbc.connect('DRIVER={ODBC Driver 13 for SQL Server};SERVER='+server+';DATABASE='+database+';UID='+username+';PWD='+ password)

query = "SELECT  [id] ,[effective_start_time] ,[effective_end_time] FROM [dbo].[SETTLEMENT_INTERVAL] si where si.effective_start_time>='2018-01-01 00:00:00' and si.effective_start_time <'2023-01-01 00:00:00'"
dfSettlement = pd.read_sql(query, cnxn)
query = "SELECT  [id] ,[EV_percentage] ,[window] FROM [dbo].[permutation] p "
dfPermutation = pd.read_sql(query, cnxn)

dfsummercopy = fcstsummer.copy()

dfsummercopy = dfsummercopy[ (dfsummercopy['ds'].dt.year == 2022) & ((dfsummercopy['ds'].dt.month >= 4) & (dfsummercopy['ds'].dt.month < 10))]
rowPermutation = dfPermutation.loc[(dfPermutation['EV_percentage']== percentageIncreaseEVs) & (dfPermutation['window']==1)]
print(rowPermutation)
cursor = cnxn.cursor()
print(dfsummercopy)

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