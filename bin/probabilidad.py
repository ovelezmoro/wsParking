import sys
import pandas as pd
import numpy as np
#import seaborn as sns
import pymysql
#import matplotlib.pyplot as plt
 
if len(sys.argv) == 9 :
    from sklearn.linear_model import LinearRegression
    from sklearn.metrics import mean_squared_error
    
    pHost = sys.argv[1] #'localhost'
    pPort = int(sys.argv[2]) #3306
    pUser = sys.argv[3] #'root'
    pPass = sys.argv[4] #'XXXX'
    pDaBa = sys.argv[5] #'db'
    
    local = sys.argv[6]
    dia = float(sys.argv[7])
    hora = float(sys.argv[8])
            
    conn = pymysql.connect(host = pHost, port = pPort, user = pUser, password = pPass, db = pDaBa)
    
    datos = pd.read_sql("SELECT * FROM table_2 WHERE local = " + local, conn)
        
    #sns.set_style('darkgrid')
    
    #g=sns.pairplot(datos, hue='Puerto', diag_kind="hist")
    
    #for ax in g.axes.flat:
    #    plt.setp(ax.get_xticklabels(), rotation = 45)
    	
    
    # CodPuerto = datos['CodPuerto'].values	
    Dia = datos['dia'].values	
    Hora = datos['hora'].values	
    Ingreso = datos['ingreso'].values
    
    
    X = np.array([Dia, Hora]).T
    Y = np.array(Ingreso)
    	
    reg = LinearRegression()
    reg = reg.fit(X,Y)
    Y_pred = reg.predict(X)
    
    error = np.sqrt(mean_squared_error(Y, Y_pred))
    r2 = reg.score(X, Y)
    
    #print("Error", error)
    #print("R~2 ", r2)
    #print("Coef", reg.coef_)

    print(reg.predict([[dia, hora]]))
    
    conn.close()

else :    
    print("0")