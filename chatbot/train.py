from chatbot import chatbot
from chatterbot.trainers import ListTrainer
import os

for file in os.listdir('./data/'):   
      data = open(r'./data/' + file, encoding='utf-8').readlines()
      trainer = ListTrainer(chatbot)       
      trainer.train(data)
      print("########## Training completed ##########")


