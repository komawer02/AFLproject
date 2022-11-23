import tensorflow as tf
import numpy as np
from flask import Flask
from flask import request
set = np.zeros((7,60))
k = 1
print(tf.__version__)
while k < 60:           
    set[0][k] = 10   
    k = k + 3      

k = 2    
while k < 60:
    set[1][k] = 10      
    k = k + 3
k = 0
while k < 60:
    set[2][k] = 10      
    k = k + 3
k = 1
while k<60:
    set[3][k] = 10
    k = k + 3
k = 2
while k<60:
    set[3][k] = 10    
    k = k + 3

k = 0
while k<60:
    set[4][k] = 10
    k = k + 3
k = 1
while k<60:
    set[4][k] = 10      
    k = k + 3
k = 0
while k<60:
    set[5][k] = 10
    k = k + 1   

k = 0
while k<60:
    set[6][k] = 10
    k = k + 3
k = 2
while k<60:
    set[6][k] = 10      
    k = k + 3


x = np.array([set[0], set[1], set[2], set[3], set[4], set[5], set[6]])

y = np.array([[1,0,0,0,0,0,0], [0,1,0,0,0,0,0], [0,0,1,0,0,0,0], [0,0,0,1,0,0,0], [0,0,0,0,1,0,0], [0,0,0,0,0,1,0] ,[0,0,0,0,0,0,1]])
print(x)
model = tf.keras.models.Sequential([
    tf.keras.layers.Dense(64, activation = 'tanh'),     
    tf.keras.layers.Dense(128, activation = 'tanh'),
    tf.keras.layers.Dense(7, activation = 'sigmoid')
])


model.compile(optimiser='adam', loss='binary_crossentropy', metrics=['accuracy'])  


model.fit(x, y, epochs=100)     




app = Flask(__name__) 

@app.route('/')
def home():
    data = request.args.get("data")     
    data = data.split(',')  
    data = list(map(float, data))  

    data = np.array(([data]))



    
    result = model.predict(data)   
    
   
   
    result = ' '.join(map(str, result))  
    
    return result

if __name__ == '__main__':  
    app.run(host='0.0.0.0', port=5002)



